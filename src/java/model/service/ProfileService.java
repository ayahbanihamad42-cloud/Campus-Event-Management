package model.service;

import model.dao.UserDAO;
import model.entity.User;

public class ProfileService {

    private UserDAO userDAO;

    public ProfileService() {
        userDAO = new UserDAO();
    }

    public User getUserById(int userId) {
        if (userId <= 0) {
            return null;
        }

        return userDAO.getUserById(userId);
    }

    public boolean updateProfile(User user) {
        if (user == null || user.getId() <= 0) {
            return false;
        }

        return userDAO.updateUserProfile(user);
    }
}