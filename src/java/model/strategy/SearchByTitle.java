package model.strategy;

import java.util.ArrayList;
import java.util.List;
import model.entity.Event;

public class SearchByTitle implements SearchStrategy {

    @Override
    public List<Event> search(List<Event> events, String keyword) {
        List<Event> result = new ArrayList<Event>();

        if (events == null || keyword == null || keyword.trim().isEmpty()) {
            return result;
        }

        String searchWord = keyword.trim().toLowerCase();

        for (Event event : events) {
            if (event != null
                    && event.getTitle() != null
                    && event.getTitle().toLowerCase().contains(searchWord)) {
                result.add(event);
            }
        }

        return result;
    }
}