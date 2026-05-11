<%-- 
    Document   : event
    Created on : Apr 14, 2026, 11:36:59 PM
    Author     : user
--%>

<%@page import="java.util.List"%>
<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    String searchBy = (String) request.getAttribute("searchBy");
    String keyword = (String) request.getAttribute("keyword");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Events</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Browse & Search Events</h1>

    <form action="<%= request.getContextPath() %>/SearchServlet" method="get">
        <label>Search By:</label>
        <select name="searchBy">
            <option value="title">Title</option>
            <option value="department">Department/Club</option>
            <option value="date">Event Date</option>
            <option value="category">Category</option>
            <option value="type">Event Type</option>
            <option value="availability">Availability</option>
        </select>

        <label>Keyword:</label>
        <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>">

        <button type="submit">Search</button>
    </form>

    <br>

    <%
        if (events == null || events.isEmpty()) {
    %>
        <p>No events found.</p>
    <%
        } else {
    %>
    <table border="1" cellpadding="8">
        <tr>
            <th>Title</th>
            <th>Type</th>
            <th>Category</th>
            <th>Department/Club</th>
            <th>Date</th>
            <th>Location</th>
            <th>Available Seats</th>
            <th>Status</th>
            <th>Details</th>
        </tr>

        <%
            for (Event event : events) {
        %>
        <tr>
            <td><%= event.getTitle() %></td>
            <td><%= event.getEventType() %></td>
            <td><%= event.getCategory() %></td>
            <td><%= event.getDepartmentClub() %></td>
            <td><%= event.getEventDateTime() %></td>
            <td><%= event.getLocation() %></td>
            <td><%= event.getAvailableSeats() %></td>
            <td><%= event.getStatus() %></td>
            <td><a href="<%= request.getContextPath() %>/ViewEventsServlet?id=<%= event.getId() %>">View Details</a></td>
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
