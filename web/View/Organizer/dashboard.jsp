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
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Organizer Dashboard</h1>
    <h3>Welcome, <%= organizerName %></h3>

    <p><strong>Total Events:</strong> <%= totalEvents %></p>
    <p><strong>Open Events:</strong> <%= openEvents %></p>
    <p><strong>Completed Events:</strong> <%= completedEvents %></p>

    <a href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">Create Event</a>
    <br><br>
    <a href="<%= request.getContextPath() %>/ManageEventsServlet?organizerName=<%= organizerName %>">Manage Events</a>
</body>
</html>