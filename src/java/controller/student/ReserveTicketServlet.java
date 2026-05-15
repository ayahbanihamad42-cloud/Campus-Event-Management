package controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.service.ReservationService;

@WebServlet(name = "ReserveTicketServlet", urlPatterns = {"/ReserveTicketServlet"})
public class ReserveTicketServlet extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReserveTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReserveTicketServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/ViewEventsServlet");
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

            String result = reservationService.reserveTicket(userId, eventId);

            session.setAttribute("message", result);

            response.sendRedirect(request.getContextPath() + "/ViewEventsServlet?id=" + eventId);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Reservation failed.");
            response.sendRedirect(request.getContextPath() + "/ViewEventsServlet");
        }
    }

    @Override
    public String getServletInfo() {
        return "Reserve Ticket Servlet";
    }
}