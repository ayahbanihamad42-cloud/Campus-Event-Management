package controller.organizer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.service.EventService;

@WebServlet(name = "CloseRegistrationServlet", urlPatterns = {"/CloseRegistrationServlet"})
public class CloseRegistrationServlet extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int eventId = Integer.parseInt(request.getParameter("id"));

            boolean updated = eventService.updateEventStatus(eventId, "Closed");

            if (updated) {
                session.setAttribute("message", "Registration closed successfully.");
            } else {
                session.setAttribute("message", "Failed to close registration.");
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