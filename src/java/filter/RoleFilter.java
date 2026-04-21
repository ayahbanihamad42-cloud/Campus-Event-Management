/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.entity.User;

@WebFilter(filterName = "RoleFilter", urlPatterns = {
    "/View/Student/*",
    "/View/Organizer/*",
    "/View/Admin/*"
})
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/View/Auth/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        String uri = req.getRequestURI();

        if (uri.contains("/View/Admin/") && !"admin".equalsIgnoreCase(role)) {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        if (uri.contains("/View/Organizer/") && !"organizer".equalsIgnoreCase(role)) {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        if (uri.contains("/View/Student/") && !"student".equalsIgnoreCase(role)) {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}