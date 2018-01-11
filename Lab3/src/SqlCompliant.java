import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Example Code Provided By Dr. Michael Tu For
 * ITS553-Secure Java Programming Lab 3
 * @author Fan Hu
 * @function 
 */
public class SqlCompliant {
	Connection connection;
	PreparedStatement preStatement = null;
	ResultSet result = null;

	/*public static void main(String[] args) {
		Compliant compliant = new Compliant();
		compliant.query();
	}*/

	/*This method provides a defend way*/
	public void query() {
		String query = "select * from user where username=? and password=?";
		try {
			connection = getConnection();
			preStatement = connection.prepareStatement(query);
			preStatement.setString(1, "'Sky'");
			/*This will set '123' or '1'='1' as the value of passwd, which will prevent the injection*/
			preStatement.setString(2, "123 or '1'='1'");
			result = preStatement.executeQuery();

			while (result.next()) {
				System.out.println("username= " + result.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			/*you need to change these to your username and password*/
			String url = "jdbc:mysql://localhost/test";
			String username = "viewonly";
			String passwd = "12345";
			connection = DriverManager.getConnection(url, username, passwd);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}

