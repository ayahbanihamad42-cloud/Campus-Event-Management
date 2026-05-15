<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.entity.User"%>

<%
    User user = (User) request.getAttribute("user");

    if (user == null) {
        user = (User) session.getAttribute("user");
    }

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
        return;
    }

    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="form-box">
        <h2>Update Profile</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/ProfileServlet" method="post">

            <label>Name:</label>
            <input type="text" name="name" value="<%= user.getName() %>" required>

            <label>Email:</label>
            <input type="email" name="email" value="<%= user.getEmail() %>" required>

           <label>Password:</label>
            <input type="password" name="password" value="<%= user.getPassword() %>" required>

            <label>Faculty:</label>
            <input type="text" name="faculty" value="<%= user.getFaculty() %>" required>

            <label>Department:</label>
            <input type="text" name="department" value="<%= user.getDepartment() %>" required>

            <label>Admission Year:</label>
            <input type="number" name="admissionYear" value="<%= user.getAdmissionYear() %>" required>

            <button type="submit">Update Profile</button>
        </form>
    </div>

</body>
</html>