

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteTaskServlet
 */
public class DeleteTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  int taskId = Integer.parseInt(request.getParameter("taskId"));

	        List<Object> taskDetails = getTaskDetails(taskId);

	        request.setAttribute("taskId", taskDetails.get(0));
	        request.setAttribute("title", taskDetails.get(1));
	        request.setAttribute("dueDate", taskDetails.get(2));
	        request.setAttribute("status", taskDetails.get(3));
	        request.setAttribute("categoryId", taskDetails.get(4));

	        
	        request.getRequestDispatcher("/DeleteTask.jsp").forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int taskId = Integer.parseInt(request.getParameter("taskId"));

        deleteTaskFromDatabase(taskId);
        response.sendRedirect(request.getContextPath() + "/HomeServlet");
	}
	
    private void deleteTaskFromDatabase(int taskId) {
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE task_id=?")) {

            statement.setInt(1, taskId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
