<%-- 
    Document   : dashboard
    Created on : Apr 14, 2026, 11:36:35 PM
    Author     : user
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="header">
        <h1>Student Dashboard</h1>
    </div>

    <div class="navbar">
        <a href="${pageContext.request.contextPath}/View/Student/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/ProfileServlet">My Profile</a>
        <a href="${pageContext.request.contextPath}/View/Auth/login.jsp">Logout</a>
    </div>

    <div class="container">
        <h2>Welcome Student</h2>
        <p>Login successful!</p>

        <div class="card-grid">
            <div class="card">
                <h3>My Profile</h3>
                <p>View and update your personal information.</p>
                <a href="${pageContext.request.contextPath}/ProfileServlet">Open Profile</a>
            </div>

            <div class="card">
                <h3>Browse Events</h3>
                <p>View available campus events.</p>
                <a href="${pageContext.request.contextPath}/View/Student/event.jsp">View Events</a>
            </div>

            <div class="card">
                <h3>My Reservations</h3>
                <p>Manage your ticket reservations.</p>
                <a href="${pageContext.request.contextPath}/View/Student/MyReservation.jsp">Reservations</a>
            </div>
        </div>
    </div>

</body>
</html>