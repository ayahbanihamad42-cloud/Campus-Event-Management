<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />

    <div class="form-box">
        <h2>Create Account</h2>

        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/register" method="post">

            <label>Name:</label>
            <input type="text" name="name" required>

            <label>Email:</label>
            <input type="email" name="email" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <label>Faculty:</label>
            <input type="text" name="faculty" required>

            <label>Department:</label>
            <input type="text" name="department" required>

            <label>Admission Year:</label>
            <input type="number" name="admissionYear" required>

            <label>Role:</label>
            <select name="role" required>
                <option value="student">Student</option>
                <option value="organizer">Organizer</option>
            </select>

            <button type="submit">Register</button>
        </form>

        <p>
            Already have an account?
            <a href="<%= request.getContextPath() %>/View/Auth/login.jsp">Login</a>
        </p>
    </div>

</body>
</html>