

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("test");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("Register.jsp");
            return;
        }

//        String hashedPassword = hashPassword(password);

        if (isEmailRegistered(email)) {
            response.sendRedirect("Register.jsp");
            return;
        }

        if (saveUserData(username, email, password)) {
            response.sendRedirect("Login.jsp");
        } else {
            response.sendRedirect("Register.jsp");
        }
	}
//	private String hashPassword(String password) {
//	    try {
//	        MessageDigest md = MessageDigest.getInstance("SHA-256");
//	        byte[] hashedBytes = md.digest(password.getBytes());
//
//	        // Convert the byte array to a hexadecimal string
//	        StringBuilder sb = new StringBuilder();
//	        for (byte b : hashedBytes) {
//	            sb.append(String.format("%02x", b));
//	        }
//	        return sb.toString();
//	    } catch (NoSuchAlgorithmException e) {
//	        e.printStackTrace(); // Handle the exception appropriately
//	        return null;
//	    }
//	}
	private boolean isEmailRegistered(String email) {
	    try (Connection connection = DB.getConnection()) {
	        String sql = "SELECT * FROM users WHERE email = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1,email);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return true; 
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}
    private boolean saveUserData(String username, String email, String hashedPassword) {
        try (Connection connection = DB.getConnection()) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

}
