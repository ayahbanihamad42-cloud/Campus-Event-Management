package controller.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.entity.User;
import model.service.AuthService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = authService.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            String role = user.getRole();

            if (role == null) {
                response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
                return;
            }

            role = role.trim().toLowerCase();

            if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/View/Admin/dashboard.jsp");
            } else if ("organizer".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/View/Organizer/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
            }

        } else {
            request.setAttribute("errorMessage", "Invalid email or password, or account is blocked.");

            request.getRequestDispatcher("/View/Auth/login.jsp")
                    .forward(request, response);
        }
    }
}