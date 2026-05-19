package model.service;

import java.util.ArrayList;
import java.util.List;
import model.dao.EventDAO;
import model.entity.Event;

public class SearchService {

    private EventDAO eventDAO;

    public SearchService() {
        eventDAO = new EventDAO();
    }

    public List<Event> searchEvents(String strategy, String keyword) {
        List<Event> allEvents = eventDAO.getAllEvents();
        List<Event> result = new ArrayList<Event>();

        if (allEvents == null || strategy == null) {
            return result;
        }

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim().toLowerCase();

        for (Event event : allEvents) {
            if (event == null) {
                continue;
            }

            if ("title".equalsIgnoreCase(strategy)) {
                if (event.getTitle() != null
                        && event.getTitle().toLowerCase().contains(keyword)) {
                    result.add(event);
                }

            } else if ("department".equalsIgnoreCase(strategy)) {
                if (event.getDepartmentClub() != null
                        && event.getDepartmentClub().toLowerCase().contains(keyword)) {
                    result.add(event);
                }

            } else if ("date".equalsIgnoreCase(strategy)) {
                if (event.getEventDateTime() != null
                        && event.getEventDateTime().toLocalDate().toString().equals(keyword)) {
                    result.add(event);
                }

            } else if ("category".equalsIgnoreCase(strategy)) {
                if (event.getCategory() != null
                        && event.getCategory().name().equalsIgnoreCase(keyword)) {
                    result.add(event);
                }

            } else if ("type".equalsIgnoreCase(strategy)) {
                if (event.getEventType() != null
                        && event.getEventType().equalsIgnoreCase(keyword)) {
                    result.add(event);
                }

            } else if ("availability".equalsIgnoreCase(strategy)) {
                if (event.hasAvailableSeats()
                        && "Open".equalsIgnoreCase(event.getStatus())
                        && !event.isExpired()) {
                    result.add(event);
                }
            }
        }

        return result;
    }
}