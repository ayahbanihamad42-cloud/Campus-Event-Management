package model.service;

import java.util.List;
import model.dao.CategoryDAO;
import model.dao.DepartmentDAO;
import model.dao.EventDAO;
import model.dao.UserDAO;
import model.entity.Event;
import model.entity.User;

public class AdminService {

    private UserDAO userDAO;
    private EventDAO eventDAO;
    private DepartmentDAO departmentDAO;
    private CategoryDAO categoryDAO;

    public AdminService() {
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        departmentDAO = new DepartmentDAO();
        categoryDAO = new CategoryDAO();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean blockUser(int userId) {
        return userDAO.blockUser(userId);
    }

    public boolean unblockUser(int userId) {
        return userDAO.unblockUser(userId);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Event getEventById(int eventId) {
        return eventDAO.getEventById(eventId);
    }

    public boolean updateEvent(Event event) {
        return eventDAO.updateEvent(event);
    }

    public boolean deleteEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    public boolean updateEventStatus(int eventId, String status) {
        return eventDAO.updateEventStatus(eventId, status);
    }

    public List<String> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }

    public boolean addDepartment(String name) {
        return departmentDAO.addDepartment(name);
    }

    public boolean deleteDepartmentByName(String name) {
        return departmentDAO.deleteDepartmentByName(name);
    }

    public List<String> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public boolean addCategory(String name) {
        return categoryDAO.addCategory(name);
    }

    public boolean deleteCategoryByName(String name) {
        return categoryDAO.deleteCategoryByName(name);
    }
}