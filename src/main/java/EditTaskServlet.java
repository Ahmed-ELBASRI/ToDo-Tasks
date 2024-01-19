

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditTaskServlet")
public class EditTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int taskId = Integer.parseInt(request.getParameter("taskId"));

        List<Object> taskDetails = getTaskDetails(taskId);
        List<String> categoryList = getCategoryList();

        request.setAttribute("taskDetails", taskDetails);
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/EditTask.jsp").forward(request, response);
    }
    private List<String> getCategoryList() {
        List<String> categories = new ArrayList<>();

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM category")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(resultSet.getString("libelle"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    private int getCategoryIdByLibelle(String categoryLibelle) {
        int categoryId = -1;

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT category_id FROM category WHERE libelle = ?")) {

            statement.setString(1, categoryLibelle);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    categoryId = resultSet.getInt("category_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryId;
    }

    private List<Object> getTaskDetails(int taskId) {
        List<Object> taskDetails = new ArrayList<>();

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE task_id = ?")) {

            statement.setInt(1, taskId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    taskDetails.add(resultSet.getInt("task_id"));
                    taskDetails.add(resultSet.getString("title"));
                    taskDetails.add(resultSet.getDate("due_date"));
                    taskDetails.add(resultSet.getString("status"));
                    taskDetails.add(resultSet.getInt("categoryId"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskDetails;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String dueDateStr = request.getParameter("dueDate");
        String status = request.getParameter("status");
        String categoryLibelle = request.getParameter("category"); 
        int taskId = Integer.parseInt(request.getParameter("taskId"));

        java.sql.Date dueDate = java.sql.Date.valueOf(dueDateStr);

        int categoryId = getCategoryIdByLibelle(categoryLibelle);

        updateTaskInDatabase(taskId, title, dueDate, status, categoryId);
        response.sendRedirect(request.getContextPath() + "/HomeServlet");
    }
    private void updateTaskInDatabase(int taskId, String title, java.sql.Date dueDate, String status, int category) {
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET title=?, due_date=?, status=?, categoryId=? WHERE task_id=?")) {

            statement.setString(1, title);
            statement.setDate(2, dueDate);
            statement.setString(3, status);
            statement.setInt(4, category);
            statement.setInt(5, taskId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}