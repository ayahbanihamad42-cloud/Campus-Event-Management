<%-- 
    Document   : profile
    Created on : Apr 14, 2026, 11:38:28 PM
    Author     : user
--%>

<%-- 
    Document   : profile
    Created on : Apr 14, 2026, 11:38:28 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.entity.User"%>

<%
    User user = (User) request.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
        return;
    }

    String role = (String) session.getAttribute("role");
    String dashboardPath = "/View/Student/dashboard.jsp";

    if (role != null) {
        if (role.equalsIgnoreCase("admin")) {
            dashboardPath = "/View/Admin/dashboard.jsp";
        } else if (role.equalsIgnoreCase("organizer")) {
            dashboardPath = "/View/Organizer/dashboard.jsp";
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <div class="header">
        <h1>My Profile</h1>
    </div>

    <div class="navbar">
        <a href="<%= request.getContextPath() + dashboardPath %>">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ProfileServlet">My Profile</a>
        <a href="<%= request.getContextPath() %>/View/Auth/login.jsp">Logout</a>
    </div>

    <div class="form-box">
        <h2>Update Profile</h2>

        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p class="message"><%= message %></p>
        <%
            }
        %>

        <form action="<%= request.getContextPath() %>/ProfileServlet" method="post">

            <label>Name:</label>
            <input type="text" name="name" value="<%= user.getName() %>" required>

            <label>Email:</label>
            <input type="email" name="email" value="<%= user.getEmail() %>" required>

            <label>Password:</label>
            <input type="password" name="password" value="<%= user.getPassword() %>" required>

            <label>Faculty:</label>
            <input type="text" name="faculty" value="<%= user.getFaculty() %>" required>

            <label>Department:</label>
            <input type="text" name="department" value="<%= user.getDepartment() %>" required>

            <label>Admission Year:</label>
            <input type="number" name="admissionYear" value="<%= user.getAdmissionYear() %>" required>

            <button type="submit">Update Profile</button>
        </form>

        <br>
        <a href="<%= request.getContextPath() + dashboardPath %>">Back to Dashboard</a>
    </div>

</body>
</html>