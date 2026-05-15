<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />

    <div class="form-box">
        <h2>Login</h2>

        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
            <label>Email:</label>
            <input type="email" name="email" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <button type="submit">Login</button>
        </form>

        <p>
            Don't have an account?
            <a href="<%= request.getContextPath() %>/View/Auth/register.jsp">Register</a>
        </p>
    </div>

</body>
</html>