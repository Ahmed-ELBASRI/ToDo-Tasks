

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
 * Servlet implementation class AddTaskServlet
 */
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        List<String> categoryList = getCategoryList();
        request.setAttribute("categ", categoryList);
        request.getRequestDispatcher("/AddTask.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String title = request.getParameter("title");
	    String dueDateStr = request.getParameter("dueDate");
	    String status = request.getParameter("status");
	    String categoryLibelle = request.getParameter("category");

	    java.sql.Date dueDate = java.sql.Date.valueOf(dueDateStr);

	    HttpSession session = request.getSession(false);
	    int userId = (int) session.getAttribute("userID");

	    int categoryId = getCategoryIdByLibelle(categoryLibelle);
	    
	    addTaskToDatabase(title, dueDate, status,userId, categoryId);

	    response.sendRedirect(request.getContextPath() + "/HomeServlet");	}
	 private void addTaskToDatabase(String title, java.sql.Date dueDate, String status,int userId, int category) {
	        try (Connection connection = DB.getConnection();
	             PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (title, due_date, status, UserID, categoryId ) VALUES (?, ?, ?, ?, ?)")) {

	            statement.setString(1, title);
	            statement.setDate(2, dueDate);
	            statement.setString(3, status);
	            statement.setInt(4, userId);
	            statement.setInt(5, category);       

	            statement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately in your application
	        }
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

}
