<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Date" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Task</title>
    <!-- Add Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="text-center">Delete Task</h1>

        <!-- Display task details for confirmation -->
        <form action="DeleteTaskServlet" method="post">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" class="form-control" id="title" name="title" value="<%= request.getAttribute("title") %>" readonly>
            </div>
            <div class="form-group">
                <label for="dueDate">Due Date:</label>
                <input type="text" class="form-control" id="dueDate" name="dueDate" value="<%= ((Date)request.getAttribute("dueDate")).toString() %>" readonly>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <input type="text" class="form-control" id="status" name="status" value="<%= request.getAttribute("status") %>" readonly>
            </div>
            <div class="form-group">
                <label for="category">Category:</label>
                <input type="text" class="form-control" id="category" name="category" value="<%= request.getAttribute("categoryId") %>" readonly>
            </div>

            <input type="hidden" name="taskId" value="<%= request.getParameter("taskId") %>">

            <button type="submit" class="btn btn-danger">Confirm Deletion</button>
        </form>
    </div>

    <!-- Add Bootstrap JS and Popper.js scripts -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>