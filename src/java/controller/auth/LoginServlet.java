package controller.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.UserDAO;
import model.entity.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LOGIN SERVLET REACHED");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("EMAIL FROM FORM = " + email);
        System.out.println("PASSWORD FROM FORM = " + password);

        if (email != null) {
            email = email.trim();
        }

        if (password != null) {
            password = password.trim();
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {

            System.out.println("USER FOUND");
            System.out.println("ROLE = " + user.getRole());

            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            String role = user.getRole();

            if (role == null) {
                response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
                return;
            }

            role = role.trim().toLowerCase();

            if (role.equals("admin")) {
                System.out.println("REDIRECT TO ADMIN");
                response.sendRedirect(request.getContextPath() + "/View/Admin/dashboard.jsp");
            } else if (role.equals("organizer")) {
                System.out.println("REDIRECT TO ORGANIZER");
                response.sendRedirect(request.getContextPath() + "/View/Organizer/dashboard.jsp");
            } else {
                System.out.println("REDIRECT TO STUDENT");
                response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
            }

        } else {
            System.out.println("USER NOT FOUND");
            response.getWriter().println("Invalid email or password!");
        }
    }
}