<%@page import="java.util.List"%>
<%@page import="model.entity.Reservation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Reservation> reservations =
            (List<Reservation>) request.getAttribute("reservations");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Reservations</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>My Reservations</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (reservations == null || reservations.isEmpty()) { %>

            <p>You have no reservations.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Event</th>
                    <th>Reservation Status</th>
                    <th>Attendance Status</th>
                    <th>Reserved At</th>
                    <th>Action</th>
                </tr>

                <% for (Reservation reservation : reservations) { %>
                    <tr>
                        <td><%= reservation.getId() %></td>
                        <td><%= reservation.getEventTitle() %></td>
                        <td><%= reservation.getReservationStatus() %></td>
                        <td><%= reservation.getAttendanceStatus() %></td>
                        <td><%= reservation.getReservedAt() %></td>
                        <td>
                            <% if ("Reserved".equalsIgnoreCase(reservation.getReservationStatus())) { %>

                                <form action="<%= request.getContextPath() %>/CancelReservationServlet"
                                      method="post"
                                      style="display:inline;">

                                    <input type="hidden"
                                           name="reservationId"
                                           value="<%= reservation.getId() %>">

                                    <button type="submit"
                                            onclick="return confirm('Cancel this reservation?');">
                                        Cancel
                                    </button>
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