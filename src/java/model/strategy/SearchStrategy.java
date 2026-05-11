/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.strategy;

import java.util.List;
import model.entity.Event;

/**
 *
 * @author user
 */
public interface SearchStrategy {
        List<Event> search(List<Event> events, String keyword);

}
