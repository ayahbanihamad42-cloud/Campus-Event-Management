<%-- 
    Document   : createEvent
    Created on : Apr 14, 2026, 11:39:28 PM
    Author     : user
--%>
<%@page import="model.entity.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Event</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Create Event</h1>

    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/ManageEventsServlet" method="post">
        <label>Event Type:</label>
        <select name="eventType" required>
            <option value="Workshop">Workshop</option>
            <option value="Seminar">Seminar</option>
            <option value="Club Social Event">Club Social Event</option>
            <option value="Sports Activity">Sports Activity</option>
        </select><br><br>

        <label>Title:</label>
        <input type="text" name="title" required><br><br>

        <label>Organizer Name:</label>
        <input type="text" name="organizerName" required><br><br>

        <label>Description:</label>
        <textarea name="description" required></textarea><br><br>

        <label>Department/Club:</label>
        <input type="text" name="departmentClub" required><br><br>

        <label>Event Date & Time:</label>
        <input type="datetime-local" name="eventDateTime" required><br><br>

        <label>Location:</label>
        <input type="text" name="location" required><br><br>

        <label>Capacity:</label>
        <input type="number" name="capacity" required><br><br>

        <label>Category:</label>
        <select name="category" required>
            <% for (Category c : Category.values()) { %>
                <option value="<%= c.name() %>"><%= c.toString() %></option>
            <% } %>
        </select><br><br>

        <label>Image Path:</label>
        <input type="text" name="imagePath"><br><br>

        <button type="submit">Create Event</button>
    </form>
</body>
</html>
