import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
* Example Code Provided By Dr. Michael Tu For
* ITS553-Secure Java Programming Lab 3
* @author Fan Hu 
* @function If you run the safe_command, you will only get
* 			one result, which is right; 
* 			if you run the dangerous_command,
*           you will get more than one result, which is the result of injection 
*           and it's dangerous
*/
public class SqlUncompliant {
	Connection connection;
	PreparedStatement preStatement = null;
	ResultSet result = null;
	
	String safe_command="select * from user where username='Sky' and password=123";
	String danngerous_command="select * from user where username='Sky' and password=123 or '1'='1'";
	
	/*public static void main(String[] args) {
		Uncompliant uncompliant=new Uncompliant();
		uncompliant.query();
		
	}*/
	public void query(){
		try {
			//String command=safe_command;
			String command=danngerous_command;
			connection=getConnection();			
			preStatement=connection.prepareStatement(command);
			result=preStatement.executeQuery(command);

			while (result.next()) {

					System.out.println("username= "+result.getString(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		try {
			/*you need to change these to your username and password*/
			Class.forName("com.mysql.jdbc.Driver");
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