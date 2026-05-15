<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String eventId = request.getParameter("eventId");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rate Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="form-box">
        <h2>Rate Event</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/RateEventServlet" method="post">
            <input type="hidden" name="eventId" value="<%= eventId %>">

            <label>Rating:</label>
            <select name="ratingValue" required>
                <option value="5">5 - Excellent</option>
                <option value="4">4 - Very Good</option>
                <option value="3">3 - Good</option>
                <option value="2">2 - Fair</option>
                <option value="1">1 - Poor</option>
            </select>

            <label>Comment:</label>
            <textarea name="comment" rows="5" required></textarea>

            <button type="submit">Submit Rating</button>
        </form>
    </div>

</body>
</html>