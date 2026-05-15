package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.entity.Category;
import model.entity.Event;
import model.factory.EventFactory;
import model.service.AdminService;

@WebServlet(name = "ManageEventsAdminServlet", urlPatterns = {"/ManageEventsAdminServlet"})
public class ManageEventsAdminServlet extends HttpServlet {

    private AdminService adminService = new AdminService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageEventsAdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageEventsAdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Event> events = adminService.getAllEvents();

        request.setAttribute("events", events);

        request.getRequestDispatcher("/View/Admin/event.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            int eventId = Integer.parseInt(request.getParameter("id"));

            if ("delete".equalsIgnoreCase(action)) {

                adminService.deleteEvent(eventId);
                session.setAttribute("message", "Event deleted.");

            } else if ("status".equalsIgnoreCase(action)) {

                String status = request.getParameter("status");

                adminService.updateEventStatus(eventId, status);
                session.setAttribute("message", "Event status updated.");

            } else if ("update".equalsIgnoreCase(action)) {

                String eventType = request.getParameter("eventType");

                Event event = EventFactory.createEvent(eventType);

                event.setId(eventId);
                event.setTitle(request.getParameter("title"));
                event.setOrganizerName(request.getParameter("organizerName"));
                event.setDescription(request.getParameter("description"));
                event.setDepartmentClub(request.getParameter("departmentClub"));
                event.setEventDateTime(
                        LocalDateTime.parse(request.getParameter("eventDateTime"))
                );
                event.setLocation(request.getParameter("location"));
                event.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                event.setReservedSeats(Integer.parseInt(request.getParameter("reservedSeats")));
                event.setCategory(Category.valueOf(request.getParameter("category")));
                event.setImagePath(request.getParameter("imagePath"));
                event.setStatus(request.getParameter("status"));
                event.setEventType(eventType);

                adminService.updateEvent(event);
                session.setAttribute("message", "Event updated.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Admin event action failed.");
        }

        response.sendRedirect(request.getContextPath() + "/ManageEventsAdminServlet");
    }

    @Override
    public String getServletInfo() {
        return "Manage Events Admin Servlet";
    }
}