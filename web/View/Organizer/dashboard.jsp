<%@page import="model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Organizer Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Organizer Dashboard</h2>

        <% if (user != null) { %>
            <p>Welcome, <strong><%= user.getName() %></strong>.</p>
        <% } %>

        <div class="card-grid">
            <div class="card">
                <h3>Create Event</h3>
                <p>Create campus events.</p>
                <a href="<%= request.getContextPath() %>/View/Organizer/createEvent.jsp">Create</a>
            </div>

            <div class="card">
                <h3>Manage Events</h3>
                <p>Edit, close, complete, or delete your events.</p>
                <a href="<%= request.getContextPath() %>/ManageEventsServlet">Manage</a>
            </div>

            <div class="card">
                <h3>Profile</h3>
                <p>Update your profile.</p>
                <a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a>
            </div>
        </div>
    </div>

</body>
</html>