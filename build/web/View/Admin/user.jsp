<%-- 
    Document   : user
    Created on : Apr 14, 2026, 11:41:24 PM
    Author     : user
--%>

<%-- 
    Document   : user
    Created on : Apr 14, 2026, 11:41:24 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.entity.User"%>

<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <div class="header">
        <h1>Manage Users</h1>
    </div>

    <div class="navbar">
        <a href="<%= request.getContextPath() %>/View/Admin/dashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ManageUsersServlet">Manage Users</a>
        <a href="<%= request.getContextPath() %>/View/Auth/login.jsp">Logout</a>
    </div>

    <div class="container">
        <h2>Users List</h2>

        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Faculty</th>
                <th>Department</th>
                <th>Admission Year</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <%
                if (users != null) {
                    for (User user : users) {
            %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getName() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getFaculty() %></td>
                <td><%= user.getDepartment() %></td>
                <td><%= user.getAdmissionYear() %></td>
                <td><%= user.getRole() %></td>
                <td><%= user.getStatus() %></td>
                <td>
                    <a class="action-link" href="<%= request.getContextPath() %>/BlockUserServlet?id=<%= user.getId() %>">Block</a>
                    |
                    <a class="action-link" href="<%= request.getContextPath() %>/UnblockUserServlet?id=<%= user.getId() %>">Unblock</a>
                    |
                    <a class="action-link"
                       href="<%= request.getContextPath() %>/DeleteUserServlet?id=<%= user.getId() %>"
                       onclick="return confirm('Are you sure you want to delete this user?');">
                        Delete
                    </a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <br>
        <a href="<%= request.getContextPath() %>/View/Admin/dashboard.jsp">Back to Dashboard</a>
    </div>

</body>
</html>
