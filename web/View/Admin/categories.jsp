<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<String> categories = (List<String>) request.getAttribute("categories");

    String message = (String) session.getAttribute("message");

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Categories</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Manage Categories</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/ManageCategoriesServlet" method="post">
            <input type="hidden" name="action" value="add">

            <label>Category Name:</label>
            <input type="text" name="name" required>

            <button type="submit">Add Category</button>
        </form>

        <% if (categories == null || categories.isEmpty()) { %>

            <p>No categories found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>Category Name</th>
                    <th>Action</th>
                </tr>

                <% for (String category : categories) { %>
                    <tr>
                        <td><%= category %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/ManageCategoriesServlet"
                                  method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="name" value="<%= category %>">

                                <button type="submit"
                                        onclick="return confirm('Delete this category?');">
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