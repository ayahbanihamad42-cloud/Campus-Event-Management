package controller.organizer;

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

@WebServlet(name = "AttendanceServlet", urlPatterns = {"/AttendanceServlet"})
public class AttendanceServlet extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AttendanceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AttendanceServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            List<Reservation> reservations =
                    reservationService.getReservationsByEventId(eventId);

            request.setAttribute("reservations", reservations);
            request.setAttribute("eventId", eventId);

            request.getRequestDispatcher("/View/Organizer/attendees.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String attendanceStatus = request.getParameter("attendanceStatus");

            boolean updated = reservationService.updateAttendance(reservationId, attendanceStatus);

            if (updated) {
                session.setAttribute("message", "Attendance updated.");
            } else {
                session.setAttribute("message", "Attendance update failed.");
            }

            response.sendRedirect(request.getContextPath()
                    + "/AttendanceServlet?eventId=" + eventId);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Attendance update failed.");
            response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
        }
    }

    @Override
    public String getServletInfo() {
        return "Attendance Servlet";
    }
}