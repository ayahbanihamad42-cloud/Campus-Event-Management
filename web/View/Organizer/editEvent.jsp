<%-- 
    Document   : editEvent
    Created on : Apr 14, 2026, 11:39:38 PM
    Author     : user
--%>

<%@page import="model.entity.Category"%>
<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Event event = (Event) request.getAttribute("event");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Event</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Edit Event</h1>

    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <% if (event != null) { %>
    <form action="<%= request.getContextPath() %>/EditEventServlet" method="post">
        <input type="hidden" name="id" value="<%= event.getId() %>">

        <label>Event Type:</label>
        <input type="text" name="eventType" value="<%= event.getEventType() %>" readonly><br><br>

        <label>Title:</label>
        <input type="text" name="title" value="<%= event.getTitle() %>" required><br><br>

        <label>Organizer Name:</label>
        <input type="text" name="organizerName" value="<%= event.getOrganizerName() %>" required><br><br>

        <label>Description:</label>
        <textarea name="description" required><%= event.getDescription() %></textarea><br><br>

        <label>Department/Club:</label>
        <input type="text" name="departmentClub" value="<%= event.getDepartmentClub() %>" required><br><br>

        <label>Event Date & Time:</label>
        <input type="datetime-local" name="eventDateTime" value="<%= event.getEventDateTime() %>" required><br><br>

        <label>Location:</label>
        <input type="text" name="location" value="<%= event.getLocation() %>" required><br><br>

        <label>Capacity:</label>
        <input type="number" name="capacity" value="<%= event.getCapacity() %>" required><br><br>

        <label>Reserved Seats:</label>
        <input type="number" name="reservedSeats" value="<%= event.getReservedSeats() %>" required><br><br>

        <label>Category:</label>
        <select name="category" required>
            <% for (Category c : Category.values()) { %>
                <option value="<%= c.name() %>" <%= c == event.getCategory() ? "selected" : "" %>>
                    <%= c.toString() %>
                </option>
            <% } %>
        </select><br><br>

        <label>Image Path:</label>
        <input type="text" name="imagePath" value="<%= event.getImagePath() %>"><br><br>

        <label>Status:</label>
        <select name="status">
            <option value="Open" <%= "Open".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Open</option>
            <option value="Closed" <%= "Closed".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Closed</option>
            <option value="Completed" <%= "Completed".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Completed</option>
            <option value="Expired" <%= "Expired".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Expired</option>
        </select><br><br>

        <button type="submit">Update Event</button>
    </form>
    <% } else { %>
        <p>Event not found.</p>
    <% } %>
</body>
</html>