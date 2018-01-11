/* ITS553 - Secure Java Programming
 * Lab 6 - Final Project
 * Instructor: Dr. Michael Tu
 * @author: Steve Jia
 * @filename: MySqlClient.java
 * @version: 2017-07-28
 * @description: this class requires the mysql-connector jar
 * 	this class contains two methods that can check user login
 * 	against the local database and save encrypted client info
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlClient {
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/sslusers";
		String username = "sslusersrw";
		String passwd = "12345";
		return DriverManager.getConnection(url, username, passwd);
	}
	
	public int validateLogin(String username, String password) {
		String query = "SELECT user_id FROM sslusers.userinfo WHERE username = ? AND password = ?";
		int id = -1;
		Connection mysqlConnection = null;
		try {
			mysqlConnection = getConnection();
			PreparedStatement statement = mysqlConnection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				id = result.getInt(1);
			}
			mysqlConnection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(mysqlConnection!=null) { mysqlConnection = null; }
		}
		return id;
	}//end validateLogin
	
	
	public int saveClientValue(int userId, String clientValue) {
		if(userId <= 0 || clientValue==null || clientValue.isEmpty()) { return 0; }
		int res = 0;
		String query = "UPDATE sslusers.userinfo SET opnumber = ? WHERE user_id = ?";
		Connection mysqlConnection = null;
		
		try {
			mysqlConnection = getConnection();
			PreparedStatement statement = mysqlConnection.prepareStatement(query);
			statement.setString(1, clientValue);
			statement.setInt(2, userId);
			res = statement.executeUpdate();
			mysqlConnection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(mysqlConnection!=null) { mysqlConnection = null; }
		}
		return res;
	}//end saveClientValue()
}//end classS
