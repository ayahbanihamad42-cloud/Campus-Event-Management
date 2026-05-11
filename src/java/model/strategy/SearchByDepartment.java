/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.strategy;

import java.util.ArrayList;
import java.util.List;
import model.entity.Event;

/**
 *
 * @author user
 */
public class SearchByDepartment implements SearchStrategy{

    @Override
    public List<Event> search(List<Event> events, String keyword) {
       List<Event> result = new ArrayList<Event>();

        if (events == null || keyword == null) {
            return result;
        }

        for (Event event : events) {
            if (event.getDepartmentClub()!= null &&
                event.getDepartmentClub().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(event);
            }
        }

        return result;



    }
    
}
