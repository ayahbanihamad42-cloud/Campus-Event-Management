package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.util.DBConnection;

public class CategoryDAO {

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<String>();

        String sql = "SELECT name FROM event_categories ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }


    public boolean addCategory(String name) {
        String sql = "INSERT INTO event_categories (name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim().toUpperCase());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean deleteCategoryByName(String name) {
        String sql = "DELETE FROM event_categories WHERE name=?";

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