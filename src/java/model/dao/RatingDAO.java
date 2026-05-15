package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.entity.Rating;
import model.util.DBConnection;

public class RatingDAO {

    public boolean addRating(Rating rating) {
        String sql = "INSERT INTO ratings (user_id, event_id, rating, comment) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, rating.getUserId());
            ps.setInt(2, rating.getEventId());
            ps.setInt(3, rating.getRating());
            ps.setString(4, rating.getComment());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Rating> getRatingsByEventId(int eventId) {
        List<Rating> ratings = new ArrayList<Rating>();

        String sql = "SELECT * FROM ratings WHERE event_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Rating rating = new Rating();

                    rating.setId(rs.getInt("id"));
                    rating.setUserId(rs.getInt("user_id"));
                    rating.setEventId(rs.getInt("event_id"));
                    rating.setRating(rs.getInt("rating"));
                    rating.setComment(rs.getString("comment"));
                    rating.setCreatedAt(rs.getTimestamp("created_at"));

                    ratings.add(rating);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ratings;
    }
}