/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.ArrayList;
import java.util.List;
import model.dao.EventDAO;
import model.entity.Event;

/**
 *
 * @author user
 */
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

    public boolean closeRegistration(int eventId) {
        Event event = eventDAO.getEventById(eventId);

        if (event == null) {
            return false;
        }

        return eventDAO.updateEventStatus(eventId, "Closed");
    }

    public boolean markCompleted(int eventId) {
        Event event = eventDAO.getEventById(eventId);

        if (event == null) {
            return false;
        }

        return eventDAO.updateEventStatus(eventId, "Completed");
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
