package hu.csaszi.login.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection  {
	private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/markets";
	private final String DB_USER = "csaszi";
	private final String DB_PASS = "password";
	private Connection conn;
	
	public Connection getDatabaseConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(DATABASE_URL, DB_USER, DB_PASS);
		
		return conn;
	
	}
	
}
