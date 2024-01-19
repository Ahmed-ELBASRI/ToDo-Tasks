

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
import jakarta.servlet.http.HttpSession;
/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if ("logout".equals(action)) {
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/LoginServlet");
        } else {
        	
            if (session != null) {
                int id_user = (int) session.getAttribute("userID");
                List<List<Object>> tasks = getTasksFromDatabase(id_user);
                request.setAttribute("tasks", tasks);

                request.getRequestDispatcher("/Home.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/Login.jsp");
            }
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	private List<List<Object>> getTasksFromDatabase(int id_user) {
	    List<List<Object>> tasks = new ArrayList<>();

	    try (Connection connection = DB.getConnection();
	         PreparedStatement sql = connection.prepareStatement("SELECT * FROM tasks WHERE UserID = ?")) {

	    	sql.setInt(1, id_user);

	        try (ResultSet resultSet = sql.executeQuery()) {
	            while (resultSet.next()) {
	                List<Object> task = new ArrayList<>();
	                task.add(resultSet.getString("task_id"));
	                task.add(resultSet.getString("title"));
	                task.add(resultSet.getDate("due_date"));
	                task.add(resultSet.getString("status"));
	                task.add(resultSet.getInt("categoryId"));
	                tasks.add(task);
	            }
	        }
	        

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return tasks;
	}
}
