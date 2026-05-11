<%-- 
    Document   : dashboard
    Created on : Apr 14, 2026, 11:39:17 PM
    Author     : user
--%>


<%@page import="java.util.List"%>
<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    String organizerName = (String) request.getAttribute("organizerName");

    if (organizerName == null) {
        organizerName = "Organizer";
    }

    int totalEvents = events != null ? events.size() : 0;
    int openEvents = 0;
    int completedEvents = 0;

    if (events != null) {
        for (Event e : events) {
            if ("Open".equalsIgnoreCase(e.getStatus())) {
                openEvents++;
            } else if ("Completed".equalsIgnoreCase(e.getStatus())) {
                completedEvents++;
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Organizer Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <div class="header">
        <h1>Organizer Dashboard</h1>
    </div>

    <div class="navbar">
        <a href="<%= request.getContextPath() %>/View/Organizer/dashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ProfileServlet">My Profile</a>
        <a href="<%= request.getContextPath() %>/View/Auth/login.jsp">Logout</a>
    </div>

    <div class="container">
        <h2>Welcome, <%= organizerName %></h2>
        <p>Login successful! You can manage your organizer account from here.</p>

        <div class="card-grid">
            <div class="card">
                <h3>Total Events</h3>
                <p><strong><%= totalEvents %></strong></p>
            </div>

            <div class="card">
                <h3>Open Events</h3>
                <p><strong><%= openEvents %></strong></p>
            </div>

            <div class="card">
                <h3>Completed Events</h3>
                <p><strong><%= completedEvents %></strong></p>
            </div>
        </div>

        <div class="card-grid">
            <div class="card">
                <h3>Create Event</h3>
                <p>Add a new campus event for students.</p>
                <a href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">Create Event</a>
            </div>

            <div class="card">
                <h3>Manage Events</h3>
                <p>View, update, and manage your events.</p>
                <a href="<%= request.getContextPath() %>/ManageEventsServlet?organizerName=<%= organizerName %>">Manage Events</a>
            </div>

            <div class="card">
                <h3>My Profile</h3>
                <p>View and update your personal information.</p>
                <a href="<%= request.getContextPath() %>/ProfileServlet">Open Profile</a>
            </div>
        </div>
    </div>

</body>
</html>