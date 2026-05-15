package controller.auth;

import java.io.IOException;
import model.entity.User;
import model.service.AuthService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/View/Auth/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String faculty = request.getParameter("faculty");
            String department = request.getParameter("department");
            String role = request.getParameter("role");

            int admissionYear = Integer.parseInt(request.getParameter("admissionYear"));

            if (role == null || role.trim().isEmpty()) {
                role = "student";
            }

            if ("admin".equalsIgnoreCase(role)) {
                role = "student";
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setFaculty(faculty);
            user.setDepartment(department);
            user.setAdmissionYear(admissionYear);
            user.setRole(role);
            user.setStatus("active");

            boolean registered = authService.register(user);

            if (registered) {
                request.setAttribute("message", "Registration successful. Please login.");
                request.getRequestDispatcher("/View/Auth/login.jsp")
                        .forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Email may already exist.");
                request.getRequestDispatcher("/View/Auth/register.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed. Invalid input.");
            request.getRequestDispatcher("/View/Auth/register.jsp")
                    .forward(request, response);
        }
    }
}