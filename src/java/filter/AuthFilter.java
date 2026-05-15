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

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        boolean isHome = uri.equals(contextPath + "/")
                || uri.equals(contextPath + "/index.jsp");

        boolean isLoginPage = uri.equals(contextPath + "/View/Auth/login.jsp");
        boolean isRegisterPage = uri.equals(contextPath + "/View/Auth/register.jsp");

        boolean isLoginServlet = uri.equals(contextPath + "/LoginServlet");
        boolean isRegisterServlet = uri.equals(contextPath + "/register");

        boolean isCss = uri.contains("/css/");
        boolean isImage = uri.contains("/images/");

        if (isHome || isLoginPage || isRegisterPage
                || isLoginServlet || isRegisterServlet
                || isCss || isImage) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect(contextPath + "/View/Auth/login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}