<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");

    String systemName = (String) application.getAttribute("systemName");

    if (systemName == null) {
        systemName = "Campus Event Management System";
    }
%>

<div class="header">
    <h1><%= systemName %></h1>

    <% if (user != null) { %>
        <p>
            Welcome,
            <strong><%= user.getName() %></strong>
        </p>
    <% } else { %>
        <p>Welcome to the campus events platform</p>
    <% } %>
</div>