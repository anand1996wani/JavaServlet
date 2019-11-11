package com.TestServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CollabTest
 */
@WebServlet("/CollabTest")
public class CollabTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollabTest() {
        super();
    	try {
			connection = getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.getp
		//response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n");
		try {
			response.getWriter().append(CountConflicts(request.getParameter("filename")).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/OOAD_Project";
		 	String username = "anand";
			String password = "anand";
			Class.forName(driver);
			
			Connection connection  = DriverManager.getConnection(url,username,password);
			System.out.println("Connection Successful");
			return connection;
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			System.out.println("Connection Successfully");
		}
		return null;
	}
	public static Integer CountConflicts(String filename) throws Exception{
		try {
		     
			PreparedStatement postPreparedStatement = connection.prepareStatement("SELECT * FROM conflictMessages where sentNode='"+filename+"'");
			ResultSet resultSet = postPreparedStatement.executeQuery();
			ArrayList<ConflictMessages> arrayList = new ArrayList<>();
			while(resultSet.next()) {
				String sentNode = resultSet.getString("sentNode");
				String message = resultSet.getString("message");
				String collabName = resultSet.getString("collabName");
				ConflictMessages conflictMessages = new ConflictMessages();
				conflictMessages.setSentNode(sentNode);
				conflictMessages.setMessage(message);
				conflictMessages.setCollabName(collabName);
				arrayList.add(conflictMessages);
			}
			System.out.println(arrayList.size());
			return arrayList.size();
			
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			System.out.println("Select Query Successfully Complete");
		}
		return 0;
	}
	
}
