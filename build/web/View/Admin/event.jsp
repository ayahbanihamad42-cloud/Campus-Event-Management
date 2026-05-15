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
    <title>Admin Manage Events</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Admin - Manage Events</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (events == null || events.isEmpty()) { %>

            <p>No events found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Organizer</th>
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
                        <td><%= event.getOrganizerName() %></td>
                        <td><%= event.getEventType() %></td>
                        <td><%= event.getEventDateTime() %></td>
                        <td><%= event.getCapacity() %></td>
                        <td><%= event.getReservedSeats() %></td>
                        <td><%= event.getStatus() %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/ManageEventsAdminServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="status">
                                <input type="hidden" name="id" value="<%= event.getId() %>">
                                <input type="hidden" name="status" value="Open">
                                <button type="submit">Open</button>
                            </form>

                            <form action="<%= request.getContextPath() %>/ManageEventsAdminServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="status">
                                <input type="hidden" name="id" value="<%= event.getId() %>">
                                <input type="hidden" name="status" value="Closed">
                                <button type="submit">Close</button>
                            </form>

                            <form action="<%= request.getContextPath() %>/ManageEventsAdminServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="status">
                                <input type="hidden" name="id" value="<%= event.getId() %>">
                                <input type="hidden" name="status" value="Completed">
                                <button type="submit">Complete</button>
                            </form>

                            <form action="<%= request.getContextPath() %>/ManageEventsAdminServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= event.getId() %>">

                                <button type="submit"
                                        onclick="return confirm('Delete this event?');">
                                    Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>