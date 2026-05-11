<%-- 
    Document   : eventDetails
    Created on : Apr 14, 2026, 11:37:13 PM
    Author     : user
--%>

<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Event event = (Event) request.getAttribute("event");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Details</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Event Details</h1>

    <%
        if (event == null) {
    %>
        <p>Event not found.</p>
    <%
        } else {
    %>
        <p><strong>Title:</strong> <%= event.getTitle() %></p>
        <p><strong>Type:</strong> <%= event.getEventType() %></p>
        <p><strong>Organizer:</strong> <%= event.getOrganizerName() %></p>
        <p><strong>Description:</strong> <%= event.getDescription() %></p>
        <p><strong>Department/Club:</strong> <%= event.getDepartmentClub() %></p>
        <p><strong>Date & Time:</strong> <%= event.getEventDateTime() %></p>
        <p><strong>Location:</strong> <%= event.getLocation() %></p>
        <p><strong>Capacity:</strong> <%= event.getCapacity() %></p>
        <p><strong>Reserved Seats:</strong> <%= event.getReservedSeats() %></p>
        <p><strong>Available Seats:</strong> <%= event.getAvailableSeats() %></p>
        <p><strong>Category:</strong> <%= event.getCategory() %></p>
        <p><strong>Status:</strong> <%= event.getStatus() %></p>
    <%
        }
    %>
</body>
</html>
