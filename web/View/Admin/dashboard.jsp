<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="header">
        <h1>Admin Dashboard</h1>
    </div>

    <div class="navbar">
        <a href="${pageContext.request.contextPath}/View/Admin/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/ManageUsersServlet">Manage Users</a>
        <a href="${pageContext.request.contextPath}/View/Auth/login.jsp">Logout</a>
    </div>

    <div class="container">
        <h2>Welcome Admin</h2>
        <p>Login successful! You can manage users and control user access from here.</p>

        <div class="card-grid">
            <div class="card">
                <h3>Manage Users</h3>
                <p>View all users, block, unblock, or delete accounts.</p>
                <a href="${pageContext.request.contextPath}/ManageUsersServlet">Open Users</a>
            </div>

            <div class="card">
                <h3>User Roles</h3>
                <p>Manage students, organizers, and admins in the system.</p>
            </div>

            <div class="card">
                <h3>Account Control</h3>
                <p>Control active and blocked user accounts.</p>
            </div>
        </div>
    </div>

</body>
</html>