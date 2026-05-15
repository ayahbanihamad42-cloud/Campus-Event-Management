<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Event event = (Event) request.getAttribute("event");
    String errorMessage = (String) request.getAttribute("errorMessage");

    if (event == null) {
        response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="form-box">
        <h2>Edit Event</h2>

        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/EditEventServlet"
              method="post"
              enctype="multipart/form-data">

            <input type="hidden" name="id" value="<%= event.getId() %>">

            <label>Event Type:</label>
            <select name="eventType" required>
                <option value="Workshop" <%= "Workshop".equals(event.getEventType()) ? "selected" : "" %>>Workshop</option>
                <option value="Seminar" <%= "Seminar".equals(event.getEventType()) ? "selected" : "" %>>Seminar</option>
                <option value="Club Social Event" <%= "Club Social Event".equals(event.getEventType()) ? "selected" : "" %>>Club Social Event</option>
                <option value="Sports Activity" <%= "Sports Activity".equals(event.getEventType()) ? "selected" : "" %>>Sports Activity</option>
            </select>

            <label>Title:</label>
            <input type="text" name="title" value="<%= event.getTitle() %>" required>

            <label>Organizer Name:</label>
            <input type="text" name="organizerName" value="<%= event.getOrganizerName() %>" required>

            <label>Description:</label>
            <textarea name="description" rows="5" required><%= event.getDescription() %></textarea>

            <label>Department/Club:</label>
            <input type="text" name="departmentClub" value="<%= event.getDepartmentClub() %>" required>

            <label>Event Date & Time:</label>
            <input type="datetime-local" name="eventDateTime"
                   value="<%= event.getEventDateTime() != null ? event.getEventDateTime().toString() : "" %>"
                   required>

            <label>Location:</label>
            <input type="text" name="location" value="<%= event.getLocation() %>" required>

            <label>Capacity:</label>
            <input type="number" name="capacity" min="1" value="<%= event.getCapacity() %>" required>

            <label>Reserved Seats:</label>
            <input type="number" name="reservedSeats" min="0" value="<%= event.getReservedSeats() %>" required>

            <label>Category:</label>
            <select name="category" required>
                <option value="EDUCATIONAL" <%= event.getCategory().name().equals("EDUCATIONAL") ? "selected" : "" %>>Educational</option>
                <option value="SOCIAL" <%= event.getCategory().name().equals("SOCIAL") ? "selected" : "" %>>Social</option>
                <option value="SPORTS" <%= event.getCategory().name().equals("SPORTS") ? "selected" : "" %>>Sports</option>
                <option value="CULTURAL" <%= event.getCategory().name().equals("CULTURAL") ? "selected" : "" %>>Cultural</option>
                <option value="TECHNICAL" <%= event.getCategory().name().equals("TECHNICAL") ? "selected" : "" %>>Technical</option>
            </select>

            <label>Status:</label>
            <select name="status" required>
                <option value="Open" <%= "Open".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Open</option>
                <option value="Closed" <%= "Closed".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Closed</option>
                <option value="Completed" <%= "Completed".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Completed</option>
                <option value="Expired" <%= "Expired".equalsIgnoreCase(event.getStatus()) ? "selected" : "" %>>Expired</option>
            </select>

            <label>Change Image:</label>
            <input type="file" name="eventImage" accept="image/*">

            <button type="submit">Update Event</button>
        </form>
    </div>

</body>
</html>