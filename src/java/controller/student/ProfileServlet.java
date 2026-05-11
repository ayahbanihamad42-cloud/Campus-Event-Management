package controller.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.UserDAO;
import model.entity.User;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("View/Auth/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);

        request.setAttribute("user", user);
        request.getRequestDispatcher("View/Student/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("View/Auth/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String faculty = request.getParameter("faculty");
        String department = request.getParameter("department");
        int admissionYear = Integer.parseInt(request.getParameter("admissionYear"));

        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setFaculty(faculty);
        user.setDepartment(department);
        user.setAdmissionYear(admissionYear);

        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateUserProfile(user);

        if (updated) {
            session.setAttribute("userName", name);
            request.setAttribute("message", "Profile updated successfully");
        } else {
            request.setAttribute("message", "Profile update failed");
        }

        User updatedUser = userDAO.getUserById(userId);
        request.setAttribute("user", updatedUser);

        request.getRequestDispatcher("View/Student/profile.jsp").forward(request, response);
    }
}