/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organizer;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.entity.Category;
import model.entity.Event;
import model.factory.EventFactory;
import model.service.EventService;

/**
 *
 * @author user
 */
@WebServlet(name = "ManageEventsServlet", urlPatterns = {"/ManageEventsServlet"})
public class ManageEventsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageEventsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageEventsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private EventService eventService = new EventService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String organizerName = request.getParameter("organizerName");

        if (organizerName == null || organizerName.trim().isEmpty()) {
            organizerName = "Organizer";
        }

        List<Event> events = eventService.getEventsByOrganizer(organizerName);

        request.setAttribute("events", events);
        request.setAttribute("organizerName", organizerName);

        request.getRequestDispatcher("/View/Organizer/manageEvents.jsp").forward(request, response);


    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String eventType = request.getParameter("eventType");
            String title = request.getParameter("title");
            String organizerName = request.getParameter("organizerName");
            String description = request.getParameter("description");
            String departmentClub = request.getParameter("departmentClub");
            LocalDateTime eventDateTime = LocalDateTime.parse(request.getParameter("eventDateTime"));
            String location = request.getParameter("location");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            Category category = Category.valueOf(request.getParameter("category"));
            String imagePath = request.getParameter("imagePath");

            Event event = EventFactory.createEvent(eventType);

            if (event == null) {
                request.setAttribute("errorMessage", "Invalid event type.");
                request.getRequestDispatcher("/View/Organizer/createEvent.jsp").forward(request, response);
                return;
            }

            event.setTitle(title);
            event.setOrganizerName(organizerName);
            event.setDescription(description);
            event.setDepartmentClub(departmentClub);
            event.setEventDateTime(eventDateTime);
            event.setLocation(location);
            event.setCapacity(capacity);
            event.setCategory(category);
            event.setImagePath(imagePath);
            event.setStatus("Open");
            event.setEventType(eventType);

            boolean added = eventService.addEvent(event);

            if (added) {
                response.sendRedirect("ManageEventsServlet?organizerName=" + organizerName);
            } else {
                request.setAttribute("errorMessage", "Failed to create event.");
                request.getRequestDispatcher("/View/Organizer/createEvent.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid input data.");
            request.getRequestDispatcher("/View/Organizer/createEvent.jsp").forward(request, response);
        }
     

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
