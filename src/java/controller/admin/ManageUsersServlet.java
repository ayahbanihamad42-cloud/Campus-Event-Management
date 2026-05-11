package controller.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.UserDAO;
import model.entity.User;

@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("View/Admin/user.jsp").forward(request, response);
    }
}