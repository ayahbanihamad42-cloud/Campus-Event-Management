package controller.student;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.EventDAO;
import model.entity.Event;
import model.service.EventService;

@WebServlet(name = "ViewEventsServlet", urlPatterns = {"/ViewEventsServlet"})
public class ViewEventsServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

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
        try {
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.trim().isEmpty()) {
                int eventId = Integer.parseInt(idParam);

                Event event = eventDAO.getEventById(eventId);

                request.setAttribute("event", event);

                request.getRequestDispatcher("/View/Student/eventDetails.jsp")
                        .forward(request, response);
                return;
            }

            List<Event> events = eventDAO.getAllEvents();

            request.setAttribute("events", events);

            request.getRequestDispatcher("/View/Student/event.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
        }
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
        doGet(request, response);
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