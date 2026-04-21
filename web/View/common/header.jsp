<%-- 
    Document   : header
    Created on : Apr 14, 2026, 11:34:07 PM
    Author     : user
--%>

<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String systemName = (String) application.getAttribute("systemName");
    if (systemName == null) {
        systemName = "Campus Event Management System";
    }
%>

<div style="background:#2c3e50;color:white;padding:20px;">
    <h1 style="margin:0;"><%= systemName %></h1>

    <% if (user != null) { %>
        <p style="margin-top:10px;margin-bottom:0;">
            Welcome, <strong><%= user.getName() %></strong>
        </p>
    <% } %>
</div>