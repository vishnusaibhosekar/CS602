import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
	public static void main(String[] args) {
		int userID = 101;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/atm?user=root");
			System.out.println(conn.getCatalog());
			
			// Prepare a PreparedStatement object using the SQL query and set the userID parameter
	        stmt = conn.prepareStatement("SELECT * FROM accountinfo");
//	        stmt.setInt(1, userID);

	        // Execute the query using the executeQuery() method of the PreparedStatement object
	        rs = stmt.executeQuery();

	        // Check if the ResultSet returned by the query contains any rows
	        while (rs.next()) {
	        	System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
	        }
		} catch (Exception ex) {
		System.out.println("LocalizedMessage: " +
		ex.getLocalizedMessage());
		System.out.println("Message: " + ex.getMessage());
		System.out.println("Cause: " + ex.getCause());
		System.out.println("Class: " + ex.getClass());
		System.out.println("StackTrace: " + ex.getStackTrace());
		System.exit(0);
		}
		System.out.println("Program terminated with no error.");
	}
}