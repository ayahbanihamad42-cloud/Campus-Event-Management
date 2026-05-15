package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.entity.Reservation;
import model.util.DBConnection;

public class ResarvationDAO {

    public String reserveTicket(int userId, int eventId) {
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String existingSql = "SELECT id, reservation_status FROM reservations "
                    + "WHERE user_id=? AND event_id=? FOR UPDATE";

            Integer reservationId = null;
            String oldStatus = null;

            try (PreparedStatement ps = con.prepareStatement(existingSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        reservationId = rs.getInt("id");
                        oldStatus = rs.getString("reservation_status");
                    }
                }
            }

            if ("Reserved".equalsIgnoreCase(oldStatus)) {
                con.rollback();
                return "You already reserved this event.";
            }

            /*
             * Atomic update:
             * This prevents overbooking.
             * If two students reserve the last seat at the same time,
             * only one UPDATE will succeed.
             */
            String seatSql = "UPDATE events "
                    + "SET reserved_seats = reserved_seats + 1 "
                    + "WHERE id=? "
                    + "AND status='Open' "
                    + "AND event_date_time > NOW() "
                    + "AND reserved_seats < capacity";

            int seatUpdated;

            try (PreparedStatement ps = con.prepareStatement(seatSql)) {
                ps.setInt(1, eventId);
                seatUpdated = ps.executeUpdate();
            }

            if (seatUpdated == 0) {
                con.rollback();
                return "Reservation failed. Event is closed, expired, or fully booked.";
            }

            if (reservationId == null) {
                String insertSql = "INSERT INTO reservations "
                        + "(user_id, event_id, reservation_status, attendance_status) "
                        + "VALUES (?, ?, 'Reserved', 'Pending')";

                try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, eventId);
                    ps.executeUpdate();
                }
            } else {
                String updateSql = "UPDATE reservations "
                        + "SET reservation_status='Reserved', "
                        + "attendance_status='Pending', "
                        + "reserved_at=CURRENT_TIMESTAMP "
                        + "WHERE id=?";

                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setInt(1, reservationId);
                    ps.executeUpdate();
                }
            }

            con.commit();
            return "Reserved";

        } catch (SQLException e) {
            rollbackQuietly(con);
            e.printStackTrace();
            return "Reservation failed because of a database error.";
        } finally {
            closeQuietly(con);
        }
    }


    public String cancelReservation(int userId, int reservationId) {
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String selectSql = "SELECT r.id, r.event_id, r.reservation_status, e.event_date_time "
                    + "FROM reservations r "
                    + "JOIN events e ON r.event_id = e.id "
                    + "WHERE r.id=? AND r.user_id=? FOR UPDATE";

            int eventId = 0;
            String status = null;
            boolean found = false;
            boolean eventStarted = false;

            try (PreparedStatement ps = con.prepareStatement(selectSql)) {
                ps.setInt(1, reservationId);
                ps.setInt(2, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        found = true;
                        eventId = rs.getInt("event_id");
                        status = rs.getString("reservation_status");
                        eventStarted = rs.getTimestamp("event_date_time").getTime()
                                <= System.currentTimeMillis();
                    }
                }
            }

            if (!found) {
                con.rollback();
                return "Reservation not found.";
            }

            if (!"Reserved".equalsIgnoreCase(status)) {
                con.rollback();
                return "This reservation is already cancelled.";
            }

            if (eventStarted) {
                con.rollback();
                return "You cannot cancel after the event starts.";
            }

            String cancelSql = "UPDATE reservations "
                    + "SET reservation_status='Cancelled' "
                    + "WHERE id=?";

            try (PreparedStatement ps = con.prepareStatement(cancelSql)) {
                ps.setInt(1, reservationId);
                ps.executeUpdate();
            }

            String seatSql = "UPDATE events "
                    + "SET reserved_seats = GREATEST(reserved_seats - 1, 0) "
                    + "WHERE id=?";

            try (PreparedStatement ps = con.prepareStatement(seatSql)) {
                ps.setInt(1, eventId);
                ps.executeUpdate();
            }

            con.commit();
            return "Cancelled";

        } catch (SQLException e) {
            rollbackQuietly(con);
            e.printStackTrace();
            return "Cancellation failed because of a database error.";
        } finally {
            closeQuietly(con);
        }
    }


    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> reservations = new ArrayList<Reservation>();

        String sql = "SELECT r.*, e.title AS event_title "
                + "FROM reservations r "
                + "JOIN events e ON r.event_id = e.id "
                + "WHERE r.user_id=? "
                + "ORDER BY r.reserved_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapReservation(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }


    public List<Reservation> getReservationsByEventId(int eventId) {
        List<Reservation> reservations = new ArrayList<Reservation>();

        String sql = "SELECT r.*, u.name AS student_name, e.title AS event_title "
                + "FROM reservations r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN events e ON r.event_id = e.id "
                + "WHERE r.event_id=? "
                + "ORDER BY r.reserved_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapReservation(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }


    public boolean updateAttendance(int reservationId, String attendanceStatus) {
        String sql = "UPDATE reservations "
                + "SET attendance_status=? "
                + "WHERE id=? AND reservation_status='Reserved'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, attendanceStatus);
            ps.setInt(2, reservationId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private Reservation mapReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.setId(rs.getInt("id"));
        reservation.setUserId(rs.getInt("user_id"));
        reservation.setEventId(rs.getInt("event_id"));
        reservation.setReservationStatus(rs.getString("reservation_status"));
        reservation.setAttendanceStatus(rs.getString("attendance_status"));
        reservation.setReservedAt(rs.getTimestamp("reserved_at"));

        try {
            reservation.setStudentName(rs.getString("student_name"));
        } catch (SQLException ignored) {
        }

        try {
            reservation.setEventTitle(rs.getString("event_title"));
        } catch (SQLException ignored) {
        }

        return reservation;
    }


    private void rollbackQuietly(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ignored) {
            }
        }
    }


    private void closeQuietly(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }

            try {
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }
}