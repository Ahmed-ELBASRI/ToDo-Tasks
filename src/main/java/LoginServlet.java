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
 * Servlet implementation class Login
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    if (validateUser(email, password)) {
	        int userId = getUserId(email);
	        String username = getUsername(email);
	        HttpSession session = request.getSession(true);
	        session.setAttribute("userID", userId);
	        session.setAttribute("userUSERNAME", username);

	        // Redirect to HomeServlet
	        response.sendRedirect("HomeServlet");
	    } else {
	        response.sendRedirect("Login.jsp");
	    }
	}
//	private List<List<Object>> getTasksFromDatabase(int id_user) {
//	    List<List<Object>> tasks = new ArrayList<>();
//
//	    try (Connection connection = DB.getConnection();
//	         PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE UserID = ?")) {
//
//	        statement.setInt(1, id_user);
//
//	        try (ResultSet resultSet = statement.executeQuery()) {
//	            while (resultSet.next()) {
//	                List<Object> task = new ArrayList<>();
//	                task.add(resultSet.getString("title"));
//	                task.add(resultSet.getDate("due_date"));
//	                task.add(resultSet.getString("status"));
//	                task.add(resultSet.getString("category"));
//	                // Add other task properties as needed
//	                tasks.add(task);
//	            }
//	        }
//
//	    } catch (SQLException e) {
//	        e.printStackTrace(); // Handle the exception appropriately in your application
//	    }
//
//	    return tasks;
//	}
    private boolean validateUser(String email,String password) {
        try (Connection connection = DB.getConnection()) {
            String sql = "SELECT * FROM users WHERE  email = ? and password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()) {
                    	return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    private int getUserId(String email) {

        try (Connection connection = DB.getConnection()) {
            String sql = "SELECT id_user, username FROM users WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id_user");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
    private String getUsername(String email) {

        try (Connection connection = DB.getConnection()) {
            String sql = "SELECT username FROM users WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

}
