package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.util.DBConnection;

public class DepartmentDAO {

    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<String>();

        String sql = "SELECT name FROM departments ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return departments;
    }


    public boolean addDepartment(String name) {
        String sql = "INSERT INTO departments (name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean deleteDepartmentByName(String name) {
        String sql = "DELETE FROM departments WHERE name=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}