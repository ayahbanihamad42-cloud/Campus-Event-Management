package controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.entity.Reservation;
import model.service.ReservationService;

@WebServlet(name = "MyReservationsServlet", urlPatterns = {"/MyReservationsServlet"})
public class MyReservationsServlet extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyReservationsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyReservationsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);

        request.setAttribute("reservations", reservations);

        request.getRequestDispatcher("/View/Student/MyReservation.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "My Reservations Servlet";
    }
}