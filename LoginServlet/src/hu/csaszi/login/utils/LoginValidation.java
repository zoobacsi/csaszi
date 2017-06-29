package hu.csaszi.login.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.csaszi.login.model.User;


public class LoginValidation {
		
	public static User loginValidate(String login, String pass) {
		
		User user = null;
		
		try{
			boolean st = false;
			
			DatabaseConnection dbcon = new DatabaseConnection();
			Connection conn = dbcon.getDatabaseConnection();
			
			boolean db = conn.isValid(1000);
			
			System.out.println(db);
			
			
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
			pst.setString(1, login);
			pst.setString(2, pass);
			
			ResultSet rs = pst.executeQuery();
			st = rs.next();
			user = new User(rs.getLong(1), rs.getString(2), rs.getString(3));
			
			System.out.println(st);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return user;
		
	
	}
}
