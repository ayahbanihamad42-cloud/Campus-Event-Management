<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Student Dashboard</h2>

        <% if (user != null) { %>
            <p>
                Welcome, <strong><%= user.getName() %></strong>.
                You can browse events, reserve tickets, manage reservations, and rate events.
            </p>
        <% } %>

        <div class="card-grid">
            <div class="card">
                <h3>Browse Events</h3>
                <p>Search and view available campus events.</p>
                <a href="<%= request.getContextPath() %>/ViewEventsServlet">Open Events</a>
            </div>

            <div class="card">
                <h3>My Reservations</h3>
                <p>View or cancel your reservations.</p>
                <a href="<%= request.getContextPath() %>/MyReservationsServlet">Open Reservations</a>
            </div>

            <div class="card">
                <h3>Profile</h3>
                <p>View and update your profile.</p>
                <a href="<%= request.getContextPath() %>/ProfileServlet">Open Profile</a>
            </div>
        </div>
    </div>

</body>
</html>