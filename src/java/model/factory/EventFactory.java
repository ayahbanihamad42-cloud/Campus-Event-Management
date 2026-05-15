package model.factory;

import model.entity.ClubSocialEvent;
import model.entity.Event;
import model.entity.Seminar;
import model.entity.SportsActivity;
import model.entity.Workshop;

public class EventFactory {

    public static Event createEvent(String type) {
        if (type == null) {
            return null;
        }

        String normalizedType = type.trim().toLowerCase().replace("_", " ");

        switch (normalizedType) {
            case "workshop":
                return new Workshop();

            case "seminar":
                return new Seminar();

            case "club social event":
            case "clubsocialevent":
                return new ClubSocialEvent();

            case "sports activity":
            case "sportsactivity":
                return new SportsActivity();

            default:
                return null;
        }
    }
}