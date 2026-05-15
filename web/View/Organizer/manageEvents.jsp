<%@page import="java.util.List"%>
<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Events</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Manage Events</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <a class="btn-link" href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">
            Create New Event
        </a>

        <% if (events == null || events.isEmpty()) { %>

            <p>No events found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Date</th>
                    <th>Capacity</th>
                    <th>Reserved</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>

                <% for (Event event : events) { %>
                    <tr>
                        <td><%= event.getId() %></td>
                        <td><%= event.getTitle() %></td>
                        <td><%= event.getEventType() %></td>
                        <td><%= event.getEventDateTime() %></td>
                        <td><%= event.getCapacity() %></td>
                        <td><%= event.getReservedSeats() %></td>
                        <td><%= event.getStatus() %></td>
                        <td>
                            <a class="action-link"
                               href="<%= request.getContextPath() %>/EditEventServlet?id=<%= event.getId() %>">
                                Edit
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/AttendanceServlet?eventId=<%= event.getId() %>">
                                Attendees
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/CloseRegistrationServlet?id=<%= event.getId() %>">
                                Close
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/MarkCompletedServlet?id=<%= event.getId() %>">
                                Complete
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/DeleteEventServlet?id=<%= event.getId() %>"
                               onclick="return confirm('Delete this event?');">
                                Delete
                            </a>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>