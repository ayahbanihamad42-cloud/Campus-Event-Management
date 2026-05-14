<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="header">
        <h1>Campus Event Management System</h1>
    </div>

    <div class="form-box">
        <h2>Login</h2>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label>Email:</label>
            <input type="email" name="email" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <input type="submit" value="Login">
        </form>

        <p>
            Don't have an account?
            <a href="${pageContext.request.contextPath}/View/Auth/register.jsp">Register</a>
        </p>
    </div>

</body>
</html>