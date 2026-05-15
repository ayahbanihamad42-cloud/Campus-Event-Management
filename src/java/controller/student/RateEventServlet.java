package controller.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.dao.RatingDAO;
import model.entity.Rating;

@WebServlet(name = "RateEventServlet", urlPatterns = {"/RateEventServlet"})
public class RateEventServlet extends HttpServlet {

    private RatingDAO ratingDAO = new RatingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventId = request.getParameter("eventId");

        if (eventId == null || eventId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ViewEventsServlet");
            return;
        }

        response.sendRedirect(request.getContextPath()
                + "/View/Student/rateevent.jsp?eventId=" + eventId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
            return;
        }

        try {
            int userId = (Integer) session.getAttribute("userId");
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            int ratingValue = Integer.parseInt(request.getParameter("ratingValue"));
            String comment = request.getParameter("comment");

            Rating rating = new Rating();

            rating.setUserId(userId);
            rating.setEventId(eventId);
            rating.setRating(ratingValue);
            rating.setComment(comment);

            boolean saved = ratingDAO.addRating(rating);

            if (saved) {
                session.setAttribute("message", "Rating submitted successfully.");
            } else {
                session.setAttribute("message", "Rating failed.");
            }

            response.sendRedirect(request.getContextPath()
                    + "/ViewEventsServlet?id=" + eventId);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Invalid rating data.");
            response.sendRedirect(request.getContextPath() + "/ViewEventsServlet");
        }
    }
}