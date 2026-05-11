package model.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.entity.User;
import model.util.DBConnection;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password, faculty, department, admission_year, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFaculty());
            stmt.setString(5, user.getDepartment());
            stmt.setInt(6, user.getAdmissionYear());
            stmt.setString(7, user.getRole());
            stmt.setString(8, user.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return false;
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE TRIM(email) = ? AND TRIM(password) = ? AND TRIM(status) = 'active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            stmt.setString(2, password.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFaculty(rs.getString("faculty"));
                user.setDepartment(rs.getString("department"));
                user.setAdmissionYear(rs.getInt("admission_year"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                return user;
            }

        } catch (Exception e) {
            System.out.println("Login database error: " + e.getMessage());
        }

        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFaculty(rs.getString("faculty"));
                user.setDepartment(rs.getString("department"));
                user.setAdmissionYear(rs.getInt("admission_year"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                return user;
            }

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return null;
    }

    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, faculty = ?, department = ?, admission_year = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFaculty());
            stmt.setString(5, user.getDepartment());
            stmt.setInt(6, user.getAdmissionYear());
            stmt.setInt(7, user.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFaculty(rs.getString("faculty"));
                user.setDepartment(rs.getString("department"));
                user.setAdmissionYear(rs.getInt("admission_year"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));

                users.add(user);
            }

        } catch (Exception e) {
            System.err.println("Get all users error: " + e.getMessage());
        }

        return users;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Delete user error: " + e.getMessage());
        }

        return false;
    }

    public boolean blockUser(int id) {
        String sql = "UPDATE users SET status = 'blocked' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Block user error: " + e.getMessage());
        }

        return false;
    }

    public boolean unblockUser(int id) {
        String sql = "UPDATE users SET status = 'active' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Unblock user error: " + e.getMessage());
        }

        return false;
    }
}