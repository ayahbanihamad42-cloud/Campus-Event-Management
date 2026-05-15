<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
%>

<div class="navbar">

    <% if (user == null) { %>

        <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
        <a href="<%= request.getContextPath() %>/View/Auth/login.jsp">Login</a>
        <a href="<%= request.getContextPath() %>/View/Auth/register.jsp">Register</a>

    <% } else if ("student".equalsIgnoreCase(user.getRole())) { %>

        <a href="<%= request.getContextPath() %>/View/Student/dashboard.jsp">Dashboard</a>
       
        <a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>

    <% } else if ("organizer".equalsIgnoreCase(user.getRole())) { %>

        <a href="<%= request.getContextPath() %>/View/Organizer/dashboard.jsp">Dashboard</a>
        
        <a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>

    <% } else if ("admin".equalsIgnoreCase(user.getRole())) { %>

        <a href="<%= request.getContextPath() %>/View/Admin/dashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>

    <% } %>

</div>