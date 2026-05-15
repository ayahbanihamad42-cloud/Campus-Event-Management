package controller.organizer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.service.EventService;

@WebServlet(name = "DeleteEventServlet", urlPatterns = {"/DeleteEventServlet"})
public class DeleteEventServlet extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int eventId = Integer.parseInt(request.getParameter("id"));

            boolean deleted = eventService.deleteEvent(eventId);

            if (deleted) {
                session.setAttribute("message", "Event deleted successfully.");
            } else {
                session.setAttribute("message", "Failed to delete event.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Invalid event id.");
        }

        response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}