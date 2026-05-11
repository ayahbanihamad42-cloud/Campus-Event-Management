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
import model.strategy.SearchByAvailability;
import model.strategy.SearchByCategory;
import model.strategy.SearchByDate;
import model.strategy.SearchByDepartment;
import model.strategy.SearchByTitle;
import model.strategy.SearchByType;
import model.strategy.SearchStrategy;

/**
 *
 * @author user
 */
public class SearchService {
    private EventDAO eventDAO;

    public SearchService() {
        eventDAO = new EventDAO();
    }

    public List<Event> searchEvents(String searchBy, String keyword) {
        List<Event> events = eventDAO.getAllEvents();

        if (events == null) {
            return new ArrayList<Event>();
        }

        SearchStrategy strategy = null;

        if ("title".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByTitle();
        } else if ("department".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByDepartment();
        } else if ("date".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByDate();
        } else if ("category".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByCategory();
        } else if ("type".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByType();
        } else if ("availability".equalsIgnoreCase(searchBy)) {
            strategy = new SearchByAvailability();
        }

        if (strategy == null) {
            return events;
        }

        return strategy.search(events, keyword);
    }
}
