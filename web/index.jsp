<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String systemName = (String) application.getAttribute("systemName");

    if (systemName == null) {
        systemName = "Campus Event Management System";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= systemName %></title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

    <div class="header">
        <h1><%= systemName %></h1>
        <p>Welcome to the system</p>
    </div>

    <div class="container" style="text-align:center;">
        <h2>Welcome</h2>

        <p>
            Discover campus events, reserve tickets, track attendance,
            and manage university activities.
        </p>

        <br>

        <a class="btn-link" href="<%= request.getContextPath() %>/View/Auth/login.jsp">
            Login
        </a>

        <a class="btn-link green" href="<%= request.getContextPath() %>/View/Auth/register.jsp">
            Register
        </a>
    </div>

</body>
</html>