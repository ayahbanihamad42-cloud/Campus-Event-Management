package controller.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.entity.User;
import model.service.ProfileService;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {

    private ProfileService profileService = new ProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        User user = profileService.getUserById(userId);

        request.setAttribute("user", user);

        request.getRequestDispatcher("/View/Student/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        User user = new User();

        user.setId(userId);
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setFaculty(request.getParameter("faculty"));
        user.setDepartment(request.getParameter("department"));

        try {
            user.setAdmissionYear(Integer.parseInt(request.getParameter("admissionYear")));
        } catch (Exception e) {
            user.setAdmissionYear(0);
        }

        boolean updated = profileService.updateProfile(user);

        User updatedUser = profileService.getUserById(userId);

        if (updatedUser != null) {
            session.setAttribute("user", updatedUser);
            session.setAttribute("userName", updatedUser.getName());
        }

        request.setAttribute("user", updatedUser);

        if (updated) {
            request.setAttribute("message", "Profile updated successfully.");
        } else {
            request.setAttribute("message", "Profile update failed.");
        }

        request.getRequestDispatcher("/View/Student/profile.jsp")
                .forward(request, response);
    }
}