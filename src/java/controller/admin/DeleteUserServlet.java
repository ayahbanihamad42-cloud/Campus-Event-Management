/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.UserDAO;
import model.service.AdminService;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    private AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int userId = Integer.parseInt(request.getParameter("id"));

            boolean deleted = adminService.deleteUser(userId);

            if (deleted) {
                session.setAttribute("message", "User deleted successfully.");
            } else {
                session.setAttribute("message", "Failed to delete user.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Invalid user id.");
        }

        response.sendRedirect(request.getContextPath() + "/ManageUsersServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}