<%@page import="java.util.List"%>
<%@page import="model.entity.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    String message = (String) session.getAttribute("message");

    String selectedStrategy = (String) request.getAttribute("strategy");
    String keyword = (String) request.getAttribute("keyword");

    if (selectedStrategy == null) {
        selectedStrategy = "title";
    }

    if (keyword == null) {
        keyword = "";
    }

    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Browse Events</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <script>
        function changeSearchInput() {
            var strategy = document.getElementById("strategy").value;

            document.getElementById("textInputBox").style.display = "none";
            document.getElementById("dateInputBox").style.display = "none";
            document.getElementById("categoryInputBox").style.display = "none";
            document.getElementById("typeInputBox").style.display = "none";
            document.getElementById("keywordText").disabled = true;
            document.getElementById("keywordDate").disabled = true;
            document.getElementById("keywordCategory").disabled = true;
            document.getElementById("keywordType").disabled = true;

            if (strategy === "title" || strategy === "department") {
                document.getElementById("textInputBox").style.display = "block";
                document.getElementById("keywordText").disabled = false;
            } else if (strategy === "date") {
                document.getElementById("dateInputBox").style.display = "block";
                document.getElementById("keywordDate").disabled = false;
            } else if (strategy === "category") {
                document.getElementById("categoryInputBox").style.display = "block";
                document.getElementById("keywordCategory").disabled = false;
            } else if (strategy === "type") {
                document.getElementById("typeInputBox").style.display = "block";
                document.getElementById("keywordType").disabled = false;
            }
        }
    </script>
</head>

<body onload="changeSearchInput()">

    <jsp:include page="/View/common/header.jsp" />
    <jsp:include page="/View/common/navbar.jsp" />

    <div class="container">
        <h2>Browse Events</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/SearchServlet" method="get">
            <label>Search By:</label>
            <select name="strategy" id="strategy" onchange="changeSearchInput()">
                <option value="title" <%= "title".equals(selectedStrategy) ? "selected" : "" %>>Title</option>
                <option value="department" <%= "department".equals(selectedStrategy) ? "selected" : "" %>>Department/Club</option>
                <option value="date" <%= "date".equals(selectedStrategy) ? "selected" : "" %>>Date</option>
                <option value="category" <%= "category".equals(selectedStrategy) ? "selected" : "" %>>Category</option>
                <option value="type" <%= "type".equals(selectedStrategy) ? "selected" : "" %>>Event Type</option>
                <option value="availability" <%= "availability".equals(selectedStrategy) ? "selected" : "" %>>Availability</option>
            </select>

            <div id="textInputBox">
                <label>Keyword:</label>
                <input type="text" id="keywordText" name="keyword" value="<%= keyword %>">
            </div>

            <div id="dateInputBox">
                <label>Date:</label>
                <input type="date" id="keywordDate" name="keyword" value="<%= keyword %>">
            </div>

            <div id="categoryInputBox">
                <label>Category:</label>
                <select id="keywordCategory" name="keyword">
                    <option value="EDUCATIONAL" <%= "EDUCATIONAL".equalsIgnoreCase(keyword) ? "selected" : "" %>>Educational</option>
                    <option value="SOCIAL" <%= "SOCIAL".equalsIgnoreCase(keyword) ? "selected" : "" %>>Social</option>
                    <option value="SPORTS" <%= "SPORTS".equalsIgnoreCase(keyword) ? "selected" : "" %>>Sports</option>
                    <option value="CULTURAL" <%= "CULTURAL".equalsIgnoreCase(keyword) ? "selected" : "" %>>Cultural</option>
                    <option value="TECHNICAL" <%= "TECHNICAL".equalsIgnoreCase(keyword) ? "selected" : "" %>>Technical</option>
                </select>
            </div>

            <div id="typeInputBox">
                <label>Event Type:</label>
                <select id="keywordType" name="keyword">
                    <option value="Workshop" <%= "Workshop".equalsIgnoreCase(keyword) ? "selected" : "" %>>Workshop</option>
                    <option value="Seminar" <%= "Seminar".equalsIgnoreCase(keyword) ? "selected" : "" %>>Seminar</option>
                    <option value="Club Social Event" <%= "Club Social Event".equalsIgnoreCase(keyword) ? "selected" : "" %>>Club Social Event</option>
                    <option value="Sports Activity" <%= "Sports Activity".equalsIgnoreCase(keyword) ? "selected" : "" %>>Sports Activity</option>
                </select>
            </div>

            <button type="submit">Search</button>
        </form>

        <% if (events == null || events.isEmpty()) { %>

            <p>No events found.</p>

        <% } else { %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Department/Club</th>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Capacity</th>
                    <th>Reserved</th>
                    <th>Available</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <% for (Event event : events) { %>
                    <tr>
                        <td><%= event.getId() %></td>
                        <td><%= event.getTitle() %></td>
                        <td><%= event.getEventType() %></td>
                        <td><%= event.getDepartmentClub() %></td>
                        <td><%= event.getEventDateTime() %></td>
                        <td><%= event.getCategory() %></td>
                        <td><%= event.getCapacity() %></td>
                        <td><%= event.getReservedSeats() %></td>
                        <td><%= event.getAvailableSeats() %></td>
                        <td><%= event.getStatus() %></td>
                        <td>
                            <a class="action-link"
                               href="<%= request.getContextPath() %>/ViewEventsServlet?id=<%= event.getId() %>">
                                Details
                            </a>
                        </td>
                    </tr>
                <% } %>
            </table>

        <% } %>
    </div>

</body>
</html>