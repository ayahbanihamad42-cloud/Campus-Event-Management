<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.entity.User"%>

<%
    List<User> users = (List<User>) request.getAttribute("users");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Manage Users</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (users == null || users.isEmpty()) { %>

            <p>No users found.</p>

        <% } else { %>

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

                <% for (User user : users) { %>
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
                            <a class="action-link"
                               href="<%= request.getContextPath() %>/BlockUserServlet?id=<%= user.getId() %>">
                                Block
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/UnblockUserServlet?id=<%= user.getId() %>">
                                Unblock
                            </a>

                            <a class="action-link"
                               href="<%= request.getContextPath() %>/DeleteUserServlet?id=<%= user.getId() %>"
                               onclick="return confirm('Delete this user?');">
                                Delete
                            </a>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>