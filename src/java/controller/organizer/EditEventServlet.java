/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organizer;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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
@WebServlet(name = "EditEventServlet", urlPatterns = {"/EditEventServlet"})
public class EditEventServlet extends HttpServlet {

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
            out.println("<title>Servlet EditEventServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditEventServlet at " + request.getContextPath() + "</h1>");
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
         String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("ManageEventsServlet");
            return;
        }

        int eventId = Integer.parseInt(idParam);
        Event event = eventService.getEventById(eventId);

        request.setAttribute("event", event);
        request.getRequestDispatcher("/View/Organizer/editEvent.jsp").forward(request, response);



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
            int id = Integer.parseInt(request.getParameter("id"));
            String eventType = request.getParameter("eventType");
            String title = request.getParameter("title");
            String organizerName = request.getParameter("organizerName");
            String description = request.getParameter("description");
            String departmentClub = request.getParameter("departmentClub");
            LocalDateTime eventDateTime = LocalDateTime.parse(request.getParameter("eventDateTime"));
            String location = request.getParameter("location");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            int reservedSeats = Integer.parseInt(request.getParameter("reservedSeats"));
            Category category = Category.valueOf(request.getParameter("category"));
            String imagePath = request.getParameter("imagePath");
            String status = request.getParameter("status");

            Event event = EventFactory.createEvent(eventType);

            if (event == null) {
                request.setAttribute("errorMessage", "Invalid event type.");
                doGet(request, response);
                return;
            }

            event.setId(id);
            event.setTitle(title);
            event.setOrganizerName(organizerName);
            event.setDescription(description);
            event.setDepartmentClub(departmentClub);
            event.setEventDateTime(eventDateTime);
            event.setLocation(location);
            event.setCapacity(capacity);
            event.setReservedSeats(reservedSeats);
            event.setCategory(category);
            event.setImagePath(imagePath);
            event.setStatus(status);
            event.setEventType(eventType);

            boolean updated = eventService.updateEvent(event);

            if (updated) {
                response.sendRedirect("ManageEventsServlet?organizerName=" + organizerName);
            } else {
                request.setAttribute("errorMessage", "Failed to update event.");
                request.setAttribute("event", event);
                request.getRequestDispatcher("/View/Organizer/editEvent.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid input data.");
            doGet(request, response);
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
