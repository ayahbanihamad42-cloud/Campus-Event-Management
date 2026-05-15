package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.entity.Category;
import model.entity.Event;
import model.factory.EventFactory;
import model.util.DBConnection;

public class EventDAO {

    public boolean addEvent(Event event) {
    String sql = "INSERT INTO events "
            + "(title, organizer_name, description, department_club, "
            + "event_date_time, location, capacity, reserved_seats, category, "
            + "image_path, status, event_type) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, event.getTitle());
        ps.setString(2, event.getOrganizerName());
        ps.setString(3, event.getDescription());
        ps.setString(4, event.getDepartmentClub());
        ps.setTimestamp(5, Timestamp.valueOf(event.getEventDateTime()));
        ps.setString(6, event.getLocation());
        ps.setInt(7, event.getCapacity());
        ps.setInt(8, event.getReservedSeats());
        ps.setString(9, event.getCategory().name());
        ps.setString(10, event.getImagePath());
        ps.setString(11, event.getStatus());
        ps.setString(12, event.getEventType());

        int rows = ps.executeUpdate();
        System.out.println("Add event rows inserted: " + rows);

        return rows > 0;

    } catch (Exception e) {
        System.err.println("ADD EVENT ERROR: " + e.getMessage());
        e.printStackTrace();
    }

    return false;
}

    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET "
                + "title=?, organizer_name=?, description=?, department_club=?, "
                + "event_date_time=?, location=?, capacity=?, reserved_seats=?, "
                + "category=?, image_path=?, status=?, event_type=? "
                + "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getOrganizerName());
            ps.setString(3, event.getDescription());
            ps.setString(4, event.getDepartmentClub());
            ps.setTimestamp(5, Timestamp.valueOf(event.getEventDateTime()));
            ps.setString(6, event.getLocation());
            ps.setInt(7, event.getCapacity());
            ps.setInt(8, event.getReservedSeats());
            ps.setString(9, event.getCategory().name());
            ps.setString(10, event.getImagePath());
            ps.setString(11, event.getStatus());
            ps.setString(12, event.getEventType());
            ps.setInt(13, event.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateEventStatus(int eventId, String status) {
        String sql = "UPDATE events SET status=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, eventId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEvent(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        String sql = "SELECT * FROM events ORDER BY event_date_time DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(mapEvent(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public List<Event> getEventsByOrganizer(String organizerName) {
        List<Event> events = new ArrayList<Event>();

        String sql = "SELECT * FROM events WHERE organizer_name=? ORDER BY event_date_time DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, organizerName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    events.add(mapEvent(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public List<Event> getEventsByOrganizerId(int organizerId) {
        return getAllEvents();
    }

    private Event mapEvent(ResultSet rs) throws Exception {
        String eventType = rs.getString("event_type");

        Event event = EventFactory.createEvent(eventType);

        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setOrganizerName(rs.getString("organizer_name"));
        event.setDescription(rs.getString("description"));
        event.setDepartmentClub(rs.getString("department_club"));

        Timestamp timestamp = rs.getTimestamp("event_date_time");
        if (timestamp != null) {
            event.setEventDateTime(timestamp.toLocalDateTime());
        }

        event.setLocation(rs.getString("location"));
        event.setCapacity(rs.getInt("capacity"));
        event.setReservedSeats(rs.getInt("reserved_seats"));
        event.setCategory(Category.valueOf(rs.getString("category")));
        event.setImagePath(rs.getString("image_path"));
        event.setStatus(rs.getString("status"));
        event.setEventType(eventType);

        return event;
    }
}