<%@page import="java.util.List"%>
<%@page import="model.entity.Reservation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Reservation> reservations =
            (List<Reservation>) request.getAttribute("reservations");

    Object eventId = request.getAttribute("eventId");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attendees</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Attendees</h2>

        <p><strong>Event ID:</strong> <%= eventId %></p>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (reservations == null || reservations.isEmpty()) { %>

            <p>No attendees found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>Reservation ID</th>
                    <th>Student Name</th>
                    <th>Student ID</th>
                    <th>Reservation Status</th>
                    <th>Attendance Status</th>
                    <th>Mark Attendance</th>
                </tr>

                <% for (Reservation reservation : reservations) { %>
                    <tr>
                        <td><%= reservation.getId() %></td>
                        <td><%= reservation.getStudentName() %></td>
                        <td><%= reservation.getUserId() %></td>
                        <td><%= reservation.getReservationStatus() %></td>
                        <td><%= reservation.getAttendanceStatus() %></td>
                        <td>
                            <% if ("Reserved".equalsIgnoreCase(reservation.getReservationStatus())) { %>

                                <form action="<%= request.getContextPath() %>/AttendanceServlet"
                                      method="post"
                                      style="display:inline;">

                                    <input type="hidden"
                                           name="reservationId"
                                           value="<%= reservation.getId() %>">

                                    <input type="hidden"
                                           name="eventId"
                                           value="<%= eventId %>">

                                    <select name="attendanceStatus">
                                        <option value="Pending">Pending</option>
                                        <option value="Present">Present</option>
                                        <option value="Absent">Absent</option>
                                    </select>

                                    <button type="submit">Save</button>
                                </form>

                            <% } else { %>
                                -
                            <% } %>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>