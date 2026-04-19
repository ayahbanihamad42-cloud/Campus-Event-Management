<%-- 
    Document   : login
    Created on : Apr 14, 2026, 11:32:48 PM
    Author     : user
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <button type="submit">Login</button>
    </form>

    <p>Don't have an account? <a href="${pageContext.request.contextPath}/View/Auth/register.jsp">Register</a></p>
</body>
</html>
