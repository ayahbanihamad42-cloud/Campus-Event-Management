<%-- 
    Document   : navbar
    Created on : Apr 14, 2026, 11:34:18 PM
    Author     : user
--%>

<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
%>

<div style="background:#ecf0f1;padding:12px;margin-bottom:20px;display:flex;justify-content:space-between;align-items:center;">

    <div>
        <% if (user != null) { %>

            <% if ("student".equalsIgnoreCase(user.getRole())) { %>
                <a href="<%= request.getContextPath() %>/ViewEventsServlet">Browse Events</a> |
                <a href="<%= request.getContextPath() %>/View/Student/dashboard.jsp">Dashboard</a> |
                <a href="<%= request.getContextPath() %>/MyReservationsServlet">My Reservations</a> |
                <a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a>

            <% } else if ("organizer".equalsIgnoreCase(user.getRole())) { %>
                <a href="<%= request.getContextPath() %>/OrganizerDashboardServlet?organizerName=<%= user.getName() %>">Dashboard</a> |
                <a href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">Create Event</a> |
                <a href="<%= request.getContextPath() %>/ManageEventsServlet?organizerName=<%= user.getName() %>">Manage Events</a>

            <% } else if ("admin".equalsIgnoreCase(user.getRole())) { %>
                <a href="<%= request.getContextPath() %>/AdminDashboardServlet">Dashboard</a> |
                <a href="<%= request.getContextPath() %>/ManageEventsAdminServlet">Manage Events</a> |
                <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
            <% } %>

        <% } else { %>
            <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
        <% } %>
    </div>

    <div>
        <% if (user != null) { %>
            <span style="margin-right:15px;"><strong>ID:</strong> <%= user.getId() %></span>
            <span style="margin-right:15px;"><strong>Name:</strong> <%= user.getName() %></span>
            <span style="margin-right:15px;"><strong>Role:</strong> <%= user.getRole() %></span>
            <span style="margin-right:15px;"><strong>Department:</strong> <%= user.getDepartment() %></span>
            <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
        <% } %>
    </div>
</div>
