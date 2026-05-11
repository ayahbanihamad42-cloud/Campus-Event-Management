<%-- 
    Document   : index
    Created on : Apr 22, 2026, 2:39:36 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String systemName = (String) application.getAttribute("systemName");
    if (systemName == null) {
        systemName = "Campus Event Management System";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= systemName %></title>
</head>
<body style="font-family:Arial, sans-serif; background:#f4f4f4; margin:0;">

    <div style="background:#2c3e50;color:white;padding:30px;text-align:center;">
        <h1 style="margin:0;"><%= systemName %></h1>
        <p style="margin-top:10px;">Welcome to the system</p>
    </div>

    <div style="text-align:center; margin-top:80px;">
        <a href="<%= request.getContextPath() %>/View/Auth/login.jsp"
           style="padding:12px 25px; background:#3498db; color:white; text-decoration:none; margin-right:15px; border-radius:5px;">
            Login
        </a>

        <a href="<%= request.getContextPath() %>/View/Auth/register.jsp"
           style="padding:12px 25px; background:#2ecc71; color:white; text-decoration:none; border-radius:5px;">
            Register
        </a>
    </div>

</body>
</html>