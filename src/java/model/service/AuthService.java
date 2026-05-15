package model.service;

import model.dao.UserDAO;
import model.entity.User;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        return userDAO.loginUser(email.trim(), password.trim());
    }

    public boolean register(User user) {
        if (user == null) {
            return false;
        }

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return false;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("student");
        }

        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            user.setStatus("active");
        }

        return userDAO.registerUser(user);
    }
}