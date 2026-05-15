<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Event event = (Event) request.getAttribute("event");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Event Details</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (event == null) { %>

            <p>Event not found.</p>

        <% } else { %>

            <% if (event.getImagePath() != null && !event.getImagePath().trim().isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/<%= event.getImagePath() %>"
                     alt="Event Image"
                     style="max-width:350px; height:auto; border-radius:8px; margin-bottom:20px;"
                     onerror="this.style.display='none'; document.getElementById('noImageMsg').style.display='block';">

                <p id="noImageMsg" style="display:none;">Image file not found.</p>
            <% } else { %>
                <p>No image available.</p>
            <% } %>

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

            <% if (event.isOpenForRegistration()
                    && event.hasAvailableSeats()
                    && !event.isExpired()) { %>

                <form action="<%= request.getContextPath() %>/ReserveTicketServlet" method="post">
                    <input type="hidden" name="eventId" value="<%= event.getId() %>">
                    <button type="submit">Reserve Ticket</button>
                </form>

            <% } else { %>
                <p class="error">Registration is not available for this event.</p>
            <% } %>

            <br>

            <a class="action-link"
               href="<%= request.getContextPath() %>/View/Student/rateevent.jsp?eventId=<%= event.getId() %>">
                Rate Event
            </a>

        <% } %>

        <br><br>

        <a href="<%= request.getContextPath() %>/ViewEventsServlet">Back to Events</a>
    </div>

</body>
</html>