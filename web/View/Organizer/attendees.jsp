<%-- 
    Document   : attendees
    Created on : Apr 14, 2026, 11:40:11 PM
    Author     : user
--%>

<%@page import="java.util.List"%>
<%@page import="model.entity.Reservation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    Object eventId = request.getAttribute("eventId");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attendees</title>
</head>
<body>
    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <h1>Attendees</h1>
    <p><strong>Event ID:</strong> <%= eventId %></p>

    <%
        if (reservations == null || reservations.isEmpty()) {
    %>
        <p>No attendees found.</p>
    <%
        } else {
    %>
    <table border="1" cellpadding="8">
        <tr>
            <th>Reservation ID</th>
            <th>Student ID</th>
            <th>Event ID</th>
            <th>Reservation Status</th>
            <th>Attendance Status</th>
        </tr>

        <%
            for (Reservation reservation : reservations) {
        %>
        <tr>
        <%--  <td><%= reservation.getId() %></td>
            <td><%= reservation.getStudentId() %></td>
            <td><%= reservation.getEventId() %></td>
            <td><%= reservation.getReservationStatus() %></td>
<td><%= reservation.getAttendanceStatus() %></td> ----%>
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