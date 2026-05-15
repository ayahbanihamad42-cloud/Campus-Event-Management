<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="form-box">
        <h2>Create Event</h2>

        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/ManageEventsServlet"
              method="post"
              enctype="multipart/form-data">

            <label>Event Type:</label>
            <select name="eventType" required>
                <option value="Workshop">Workshop</option>
                <option value="Seminar">Seminar</option>
                <option value="Club Social Event">Club Social Event</option>
                <option value="Sports Activity">Sports Activity</option>
            </select>

            <label>Event Title:</label>
            <input type="text" name="title" required>

            <label>Organizer Name:</label>
            <input type="text" name="organizerName" required>

            <label>Description:</label>
            <textarea name="description" rows="5" required></textarea>

            <label>Department/Club:</label>
            <input type="text" name="departmentClub" required>

            <label>Event Date & Time:</label>
            <input type="datetime-local" name="eventDateTime" required>

            <label>Location:</label>
            <input type="text" name="location" required>

            <label>Capacity:</label>
            <input type="number" name="capacity" min="1" required>

            <label>Category:</label>
            <select name="category" required>
                <option value="EDUCATIONAL">Educational</option>
                <option value="SOCIAL">Social</option>
                <option value="SPORTS">Sports</option>
                <option value="CULTURAL">Cultural</option>
                <option value="TECHNICAL">Technical</option>
            </select>

            <label>Event Image:</label>
            <input type="file" name="eventImage" accept="image/*">

            <button type="submit">Create Event</button>
        </form>
    </div>

</body>
</html>