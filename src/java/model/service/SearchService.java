package model.service;

import java.util.ArrayList;
import java.util.List;
import model.dao.EventDAO;
import model.entity.Event;
import model.strategy.SearchByAvailability;
import model.strategy.SearchByCategory;
import model.strategy.SearchByDate;
import model.strategy.SearchByDepartment;
import model.strategy.SearchByTitle;
import model.strategy.SearchByType;
import model.strategy.SearchStrategy;

public class SearchService {

    private EventDAO eventDAO;

    public SearchService() {
        eventDAO = new EventDAO();
    }

    public List<Event> searchEvents(String strategyName, String keyword) {
        List<Event> events = eventDAO.getAllEvents();

        if (events == null) {
            return new ArrayList<Event>();
        }

        SearchStrategy strategy = getStrategy(strategyName);

        if (strategy == null) {
            return new ArrayList<Event>();
        }

        if (keyword == null) {
            keyword = "";
        }

        return strategy.search(events, keyword.trim());
    }

    private SearchStrategy getStrategy(String strategyName) {
        if (strategyName == null) {
            return null;
        }

        if ("title".equalsIgnoreCase(strategyName)) {
            return new SearchByTitle();

        } else if ("department".equalsIgnoreCase(strategyName)) {
            return new SearchByDepartment();

        } else if ("date".equalsIgnoreCase(strategyName)) {
            return new SearchByDate();

        } else if ("category".equalsIgnoreCase(strategyName)) {
            return new SearchByCategory();

        } else if ("type".equalsIgnoreCase(strategyName)) {
            return new SearchByType();

        } else if ("availability".equalsIgnoreCase(strategyName)) {
            return new SearchByAvailability();
        }

        return null;
    }
}