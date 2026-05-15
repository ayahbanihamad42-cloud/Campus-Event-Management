package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.service.AdminService;

@WebServlet(name = "ManageCategoriesServlet", urlPatterns = {"/ManageCategoriesServlet"})
public class ManageCategoriesServlet extends HttpServlet {

    private AdminService adminService = new AdminService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageCategoriesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageCategoriesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categories", adminService.getAllCategories());

        request.getRequestDispatcher("/View/Admin/categories.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");

            if (name != null && !name.trim().isEmpty()) {
                adminService.addCategory(name);
                session.setAttribute("message", "Category added.");
            }

        } else if ("delete".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");

            if (name != null && !name.trim().isEmpty()) {
                adminService.deleteCategoryByName(name);
                session.setAttribute("message", "Category deleted.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/ManageCategoriesServlet");
    }

    @Override
    public String getServletInfo() {
        return "Manage Categories Servlet";
    }
}