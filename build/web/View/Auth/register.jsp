<%-- 
    Document   : register
    Created on : Apr 14, 2026, 11:33:05 PM
    Author     : user
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="header">
    <h1>Campus Event Management System</h1>
</div>

<div class="form-box">
    <h2>Create Account</h2>

    <form action="${pageContext.request.contextPath}/register" method="post">

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

        <button type="submit">Register</button>
    </form>

    <p>
        Already have an account?
        <a href="${pageContext.request.contextPath}/View/Auth/login.jsp">Login</a>
    </p>
</div>

</body>
</html>
