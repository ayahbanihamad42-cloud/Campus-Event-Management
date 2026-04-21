/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

/**
 *
 * @author user
 */
import java.time.LocalDateTime;

public abstract class Event {
    protected int id;
    protected String title;
    protected String organizerName;
    protected String description;
    protected String departmentClub;
    protected LocalDateTime eventDateTime;
    protected String location;
    protected int capacity;
    protected int reservedSeats;
    protected Category category;
    protected String imagePath;
    protected String status;
    protected String eventType;

    public Event() {
        this.status = "Open";
        this.reservedSeats = 0;
    }

    public Event(int id, String title, String organizerName, String description,
                 String departmentClub, LocalDateTime eventDateTime,
                 String location, int capacity, Category category,
                 String imagePath, String status, String eventType) {
        this.id = id;
        this.title = title;
        this.organizerName = organizerName;
        this.description = description;
        this.departmentClub = departmentClub;
        this.eventDateTime = eventDateTime;
        this.location = location;
        setCapacity(capacity);
        this.category = category;
        this.imagePath = imagePath;
        this.status = status;
        this.eventType = eventType;
        this.reservedSeats = 0;
    }

    public int getAvailableSeats() {
        return capacity - reservedSeats;
    }

    public boolean hasAvailableSeats() {
        return getAvailableSeats() > 0;
    }

    public boolean isOpenForRegistration() {
        return "Open".equalsIgnoreCase(status);
    }

    public void reserveSeat() {
        if (reservedSeats < capacity) {
            reservedSeats++;
        }
    }

    public void cancelSeat() {
        if (reservedSeats > 0) {
            reservedSeats--;
        }
    }

    public boolean isExpired() {
        return eventDateTime != null && eventDateTime.isBefore(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartmentClub() {
        return departmentClub;
    }

    public void setDepartmentClub(String departmentClub) {
        this.departmentClub = departmentClub;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        if (reservedSeats < 0 || reservedSeats > capacity) {
            throw new IllegalArgumentException("Reserved seats must be between 0 and capacity");
        }
        this.reservedSeats = reservedSeats;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStatus() {
        if (isExpired()) {
            return "Expired";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}