package controller.auth;

import java.io.IOException;
import model.dao.UserDAO;
import model.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String faculty = request.getParameter("faculty");
        String department = request.getParameter("department");
        int admissionYear = Integer.parseInt(request.getParameter("admissionYear"));
        String role = request.getParameter("role");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setFaculty(faculty);
        user.setDepartment(department);
        user.setAdmissionYear(admissionYear);
        user.setRole(role);
        user.setStatus("active");

        UserDAO userDAO = new UserDAO();
        boolean isRegistered = userDAO.registerUser(user);

        if (isRegistered) {
            response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
        } else {
            response.getWriter().println("Registration failed!");
        }
    }
}         