package model.service;

import java.util.ArrayList;
import java.util.List;
import model.dao.EventDAO;
import model.entity.Event;

public class EventService {

    private EventDAO eventDAO;

    public EventService() {
        eventDAO = new EventDAO();
    }

    public boolean addEvent(Event event) {
        if (event == null) {
            return false;
        }
        return eventDAO.addEvent(event);
    }

    public boolean createEvent(Event event) {
        return addEvent(event);
    }

    public boolean updateEvent(Event event) {
        if (event == null) {
            return false;
        }
        return eventDAO.updateEvent(event);
    }

    public boolean deleteEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    public Event getEventById(int eventId) {
        refreshExpiredEvents();
        return eventDAO.getEventById(eventId);
    }

    public List<Event> getAllEvents() {
        refreshExpiredEvents();
        List<Event> events = eventDAO.getAllEvents();
        return events != null ? events : new ArrayList<Event>();
    }

    public List<Event> getEventsByOrganizer(String organizerName) {
        refreshExpiredEvents();
        List<Event> events = eventDAO.getEventsByOrganizer(organizerName);
        return events != null ? events : new ArrayList<Event>();
    }

    public List<Event> getEventsByOrganizerId(int organizerId) {
        refreshExpiredEvents();
        List<Event> events = eventDAO.getEventsByOrganizerId(organizerId);
        return events != null ? events : new ArrayList<Event>();
    }

    public boolean closeRegistration(int eventId) {
        return updateEventStatus(eventId, "Closed");
    }

    public boolean markCompleted(int eventId) {
        return updateEventStatus(eventId, "Completed");
    }

    public boolean updateEventStatus(int eventId, String status) {
        return eventDAO.updateEventStatus(eventId, status);
    }

    public void refreshExpiredEvents() {
        List<Event> events = eventDAO.getAllEvents();

        if (events == null) {
            return;
        }

        for (Event event : events) {
            if (event != null
                    && event.isExpired()
                    && !"Expired".equalsIgnoreCase(event.getStatus())
                    && !"Completed".equalsIgnoreCase(event.getStatus())) {

                eventDAO.updateEventStatus(event.getId(), "Expired");
            }
        }
    }
}