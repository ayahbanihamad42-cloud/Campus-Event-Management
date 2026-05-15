<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Admin Dashboard</h2>

        <p>
            Admin can manage users, events, departments, and categories.
        </p>

        <div class="card-grid">
            <div class="card">
                <h3>Manage Users</h3>
                <a href="<%= request.getContextPath() %>/ManageUsersServlet">Open Users</a>
            </div>

            <div class="card">
                <h3>Manage Events</h3>
                <a href="<%= request.getContextPath() %>/ManageEventsAdminServlet">Open Events</a>
            </div>

            <div class="card">
                <h3>Departments</h3>
                <a href="<%= request.getContextPath() %>/ManageDepartmentsServlet">Open Departments</a>
            </div>

            <div class="card">
                <h3>Categories</h3>
                <a href="<%= request.getContextPath() %>/ManageCategoriesServlet">Open Categories</a>
            </div>
        </div>
    </div>

</body>
</html>