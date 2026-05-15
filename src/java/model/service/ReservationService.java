package model.service;

import java.util.List;
import model.dao.ResarvationDAO;
import model.entity.Reservation;

public class ReservationService {

    private ResarvationDAO reservationDAO;

    public ReservationService() {
        reservationDAO = new ResarvationDAO();
    }

    public String reserveTicket(int userId, int eventId) {
        if (userId <= 0 || eventId <= 0) {
            return "Invalid reservation data.";
        }

        return reservationDAO.reserveTicket(userId, eventId);
    }

    public String cancelReservation(int userId, int reservationId) {
        if (userId <= 0 || reservationId <= 0) {
            return "Invalid cancellation data.";
        }

        return reservationDAO.cancelReservation(userId, reservationId);
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }

    public List<Reservation> getReservationsByEventId(int eventId) {
        return reservationDAO.getReservationsByEventId(eventId);
    }

    public boolean updateAttendance(int reservationId, String attendanceStatus) {
        if (!"Present".equalsIgnoreCase(attendanceStatus)
                && !"Absent".equalsIgnoreCase(attendanceStatus)
                && !"Pending".equalsIgnoreCase(attendanceStatus)) {
            return false;
        }

        return reservationDAO.updateAttendance(reservationId, attendanceStatus);
    }
}