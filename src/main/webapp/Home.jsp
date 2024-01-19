<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
	HttpSession sessions = request.getSession(false);
	String username = (String) sessions.getAttribute("userUSERNAME");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <!-- Add Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
	    <form action="HomeServlet" method="post">
   	        <h1 class="text-center">Hello, <%= username %></h1>
   	        <a href="HomeServlet?action=logout"><button type="button" class="btn btn-danger">Logout</button></a>
	        <h2>Your Todo Tasks</h2>
	
			<%@ page import="java.util.List" %>
			<%@ page import="java.util.ArrayList" %>
			<%@ page import="java.sql.Date" %>
			
			<%
			    List<List<Object>> tasks = (List<List<Object>>) request.getAttribute("tasks");
			    if (tasks != null) {
			%>
			    <table class="table table-bordered">
			        <thead>
			            <tr>
			                <th>Title</th>
			                <th>Due Date</th>
			                <th>Status</th>
			                <th>Category</th>
			                <th>Actions</th>
			            </tr>
			        </thead>
					<tbody>
					    <% for (List<Object> task : tasks) { %>
					        <tr <% if ("Done".equals(task.get(3))) { %>style="text-decoration: line-through;"<% } else { %>style="border: 2px solid red !important;"<% } %>>
					            <td><%= task.get(1) %></td>
					            <td><%= ((Date)task.get(2)).toString() %></td>
					            <td><%= task.get(3) %></td>
					            <td><%= task.get(4) %></td>
					            <td>
					                <a href="EditTaskServlet?taskId=<%= task.get(0) %>"><button type="button" class="btn btn-primary">Edit</button></a>
					                <a href="DeleteTaskServlet?taskId=<%= task.get(0) %>"><button type="button" class="btn btn-danger">Delete</button></a>
					            </td>
					        </tr>
					    <% } %>
					</tbody>
			    </table>
			<%
			    } else {
			%>
			    <p>No tasks available.</p>
			<%
			    }
			%>
	
	        <!-- Add New Task button -->	        
	       		 <a href="AddTaskServlet"><button type="button" class="btn btn-success">Add New Task</button></a>
       		</form>
	   </div>



    <!-- Add Bootstrap JS and Popper.js scripts -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>