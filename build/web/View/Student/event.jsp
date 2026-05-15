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
    <title>Browse Events</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Browse Events</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/SearchServlet" method="get">
            <label>Search By:</label>
            <select name="strategy">
                <option value="title">Title</option>
                <option value="department">Department/Club</option>
                <option value="date">Date</option>
                <option value="category">Category</option>
                <option value="type">Event Type</option>
                <option value="availability">Availability</option>
            </select>

            <label>Keyword:</label>
            <input type="text" name="keyword">

            <button type="submit">Search</button>
        </form>

        <% if (events == null || events.isEmpty()) { %>

            <p>No events found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Department/Club</th>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Capacity</th>
                    <th>Reserved</th>
                    <th>Available</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <% for (Event event : events) { %>
                    <tr>
                        <td><%= event.getId() %></td>
                        <td><%= event.getTitle() %></td>
                        <td><%= event.getEventType() %></td>
                        <td><%= event.getDepartmentClub() %></td>
                        <td><%= event.getEventDateTime() %></td>
                        <td><%= event.getCategory() %></td>
                        <td><%= event.getCapacity() %></td>
                        <td><%= event.getReservedSeats() %></td>
                        <td><%= event.getAvailableSeats() %></td>
                        <td><%= event.getStatus() %></td>
                        <td>
                            <a class="action-link"
                               href="<%= request.getContextPath() %>/ViewEventsServlet?id=<%= event.getId() %>">
                                Details
                            </a>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>