/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

             if (null == user.getRole()) {
                 response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
             } else switch (user.getRole()) {
                case "admin":
                    response.sendRedirect(request.getContextPath() + "/View/Admin/dashboard.jsp");
                    break;
                case "organizer":
                    response.sendRedirect(request.getContextPath() + "/View/Organizer/dashboard.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/View/Student/dashboard.jsp");
                    break;
            }

        } else {
            response.getWriter().println("Invalid email or password!");
        }
    }
}