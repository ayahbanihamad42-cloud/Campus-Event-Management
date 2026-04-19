<%-- 
    Document   : register
    Created on : Apr 14, 2026, 11:33:05 PM
    Author     : user
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <label>Name:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <label>Faculty:</label><br>
        <input type="text" name="faculty" required><br><br>

        <label>Department:</label><br>
        <input type="text" name="department" required><br><br>

        <label>Admission Year:</label><br>
        <input type="number" name="admissionYear" required><br><br>

        <label>Role:</label><br>
        <select name="role" required>
            <option value="student">Student</option>
            <option value="organizer">Organizer</option>
            <option value="admin">Admin</option>
        </select><br><br>

        <button type="submit">Register</button>
    </form>

    <p>Already have an account? <a href="login.jsp">Login</a></p>
</body>
</html>
