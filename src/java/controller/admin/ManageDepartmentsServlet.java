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

@WebServlet(name = "ManageDepartmentsServlet", urlPatterns = {"/ManageDepartmentsServlet"})
public class ManageDepartmentsServlet extends HttpServlet {

    private AdminService adminService = new AdminService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageDepartmentsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageDepartmentsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("departments", adminService.getAllDepartments());

        request.getRequestDispatcher("/View/Admin/department.jsp")
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
                adminService.addDepartment(name);
                session.setAttribute("message", "Department added.");
            }

        } else if ("delete".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");

            if (name != null && !name.trim().isEmpty()) {
                adminService.deleteDepartmentByName(name);
                session.setAttribute("message", "Department deleted.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/ManageDepartmentsServlet");
    }

    @Override
    public String getServletInfo() {
        return "Manage Departments Servlet";
    }
}