package poc;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

	public class IPL_Teams {
		public static void main(String[] args)throws SQLException{
	        try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/selciya", "root", "Selci@44");
				 Statement stmt = con.createStatement();
				 String ipl = "CREATE TABLE IPL_Teams" + "(Team_ID VARCHAR(99)," + "Team_Name VARCHAR(99),"+ "Player_Name VARCHAR(99),"+"Player_Score INTEGER)";
		                   
				 stmt.execute(ipl);
				 System.out.println("IPL_Teams created");
}

		catch(Exception e){
			System.out.println(e);
		}
	}
}

