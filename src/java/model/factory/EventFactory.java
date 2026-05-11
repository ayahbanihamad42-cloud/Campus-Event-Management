/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.entity.ClubSocialEvent;
import model.entity.Event;
import model.entity.Seminar;
import model.entity.SportsActivity;
import model.entity.Workshop;

/**
 *
 * @author user
 */
public class EventFactory {
    public static Event createEvent(String type) {
        if (type == null) {
            return null;
        }

        switch (type.trim().toLowerCase()) {
            case "workshop":
                return new Workshop();
            case "seminar":
                return new Seminar();
            case "club social event":
                return new ClubSocialEvent();
            case "sports activity":
                return new SportsActivity();
            default:
                return null;
        }
    }
}
