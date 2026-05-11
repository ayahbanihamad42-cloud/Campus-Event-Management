<%-- 
    Document   : manageEvents
    Created on : Apr 14, 2026, 11:39:57 PM
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
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Events</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Manage Events</h1>
    <h3>Organizer: <%= organizerName %></h3>

    <a href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">Create New Event</a>
    <br><br>

    <%
        if (events == null || events.isEmpty()) {
    %>
        <p>No events found.</p>
    <%
        } else {
    %>
    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Type</th>
            <th>Category</th>
            <th>Date</th>
            <th>Location</th>
            <th>Capacity</th>
            <th>Reserved</th>
            <th>Available</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            for (Event event : events) {
        %>
        <tr>
            <td><%= event.getId() %></td>
            <td><%= event.getTitle() %></td>
            <td><%= event.getEventType() %></td>
            <td><%= event.getCategory() %></td>
            <td><%= event.getEventDateTime() %></td>
            <td><%= event.getLocation() %></td>
            <td><%= event.getCapacity() %></td>
            <td><%= event.getReservedSeats() %></td>
            <td><%= event.getAvailableSeats() %></td>
            <td><%= event.getStatus() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/EditEventServlet?id=<%= event.getId() %>">Edit</a> |
                <a href="<%= request.getContextPath() %>/DeleteEventServlet?id=<%= event.getId() %>&organizerName=<%= organizerName %>">Delete</a> |
                <a href="<%= request.getContextPath() %>/CloseRegistrationServlet?id=<%= event.getId() %>&organizerName=<%= organizerName %>">Close Registration</a> |
                <a href="<%= request.getContextPath() %>/MarkCompletedServlet?id=<%= event.getId() %>&organizerName=<%= organizerName %>">Mark Completed</a> |
                <a href="<%= request.getContextPath() %>/AttendanceServlet?eventId=<%= event.getId() %>">Attendees</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>
</body>
</html>