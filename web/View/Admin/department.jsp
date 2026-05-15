<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<String> departments = (List<String>) request.getAttribute("departments");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Departments</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Manage Departments</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/ManageDepartmentsServlet" method="post">
            <input type="hidden" name="action" value="add">

            <label>Department Name:</label>
            <input type="text" name="name" required>

            <button type="submit">Add Department</button>
        </form>

        <% if (departments == null || departments.isEmpty()) { %>

            <p>No departments found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>Department Name</th>
                    <th>Action</th>
                </tr>

                <% for (String department : departments) { %>
                    <tr>
                        <td><%= department %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/ManageDepartmentsServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="name" value="<%= department %>">

                                <button type="submit"
                                        onclick="return confirm('Delete this department?');">
                                    Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>