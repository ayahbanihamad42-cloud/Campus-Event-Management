/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

/**
 *
 * @author user
 */
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
            System.err.println("Database error: " + e.getMessage());        }
        return false;
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND status = 'active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

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
            System.err.println("Database error: " + e.getMessage());        }
        return null;
    }
}
