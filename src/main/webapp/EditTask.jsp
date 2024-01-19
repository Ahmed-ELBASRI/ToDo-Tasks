<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Task</title>
    <!-- Add Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="text-center">Edit Task</h1>

        <!-- Display task details in a form for editing -->
        <form action="EditTaskServlet" method="post">
        	<%
            List<Object> taskDetails = (List<Object>) request.getAttribute("taskDetails");
            int task_id = (int) taskDetails.get(0);
            String title = (String) taskDetails.get(1);
            java.sql.Date dueDate = (java.sql.Date) taskDetails.get(2);
            String status = (String) taskDetails.get(3);
            int category = (int) taskDetails.get(4);
            
            %>
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" class="form-control" id="title" name="title" value="<%= title %>" required>
            </div>
            <div class="form-group">
                <label for="dueDate">Due Date:</label>
                <input type="date" class="form-control" id="dueDate" name="dueDate" value="<%= dueDate %>" required>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <input type="text" class="form-control" id="status" name="status" value="<%= status %>" required>
            </div>
			<div class="form-group">
			    <label for="category">Category:</label>
			    <select class="form-control" id="category" name="category" required>
			        <%
			            List<String> categoryList = (List<String>) request.getAttribute("categoryList");
			            for (String categoryy : categoryList) {
			        %>
			        <option value="<%= categoryy %>"><%= categoryy %></option>
			        <%
			            }
			        %>
			    </select>
			</div>
            
            <input type="hidden" name="taskId" value="<%= task_id %>">

            <button type="submit" class="btn btn-primary">Save Changes</button>
        </form>
    </div>

    <!-- Add Bootstrap JS and Popper.js scripts -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>