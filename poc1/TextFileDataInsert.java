package poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextFileDataInsert {

	public static void main(String[] args) {
		int insertedCount=0;
		  ResultSet rs = null;
		  int Score = 0;
		try {
			FileInputStream fstream = new FileInputStream("D:\\Cricket_Players.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			ArrayList list = new ArrayList();
			List<String> data = new <String>ArrayList();
			while ((strLine = br.readLine()) != null) {
				list.add(strLine);
			}
			int tableDrop=DbConnection.getUpdates("drop table ipl_teams;");
			int createTable=DbConnection.getUpdates( "CREATE TABLE IPL_Teams" + "(Team_ID VARCHAR(99)," + "Team_Name VARCHAR(99),"+ "Player_Name VARCHAR(99),"+"Player_Score INTEGER);");

			if(tableDrop>0 && createTable >0) {
				System.out.println("DROPPED Exixting Table And Created New Table ");
			}

			Iterator itr;
			for (itr = list.iterator(); itr.hasNext();) {
				String str = itr.next().toString();
				String[] splitSt = str.split(" ");
				String Team_ID = "", Team_Name = "", Player_Name = "";
				for (int i = 0; i < splitSt.length; i++) {
					Team_ID = splitSt[0];
					Team_Name = splitSt[1];
					Player_Name = splitSt[2];
					
				}
				insertedCount = DbConnection
						.getUpdates("insert into IPL_Teams(Team_ID,Team_Name,Player_Name) values('" + Team_ID
								+ "','" + Team_Name + "','" + Player_Name + "');");
				
			}
			if (insertedCount > 0) {
				rs=DbConnection.getResultSet("select * from ipl_teams Order By TeamName asc,Score asc;");
				System.out.println("Data successfully Inserted");
				while (rs.next()) {
					 
	                String teamId = rs.getString(1);
	                String TeamName = rs.getString(2);
	                String playerName = rs.getString(3);
	                System.out.println(teamId + "\t\t" + TeamName
	                                   + "\t\t" + playerName);
	                Scanner sc= new Scanner(System.in); 
	                System.out.print("Enter "+playerName+"s Score : ");
	                int PlayerScore= sc.nextInt();
	                System.out.println("PlayerScore="+PlayerScore);
	                Score=DbConnection.getUpdates("update ipl_teams set Player_Score="+PlayerScore+" where Team_ID='"+teamId+"' AND Team_Name='"+TeamName+"' and Player_Name='"+playerName+"';");
	                data.add(teamId + " " + TeamName + " " + playerName + " " + PlayerScore);
				}
				writeToFile(data, "D:\\Employee.txt");
				rs.close();
				//st.close();
				
			}
		} catch (Exception e) {
		}
		
	}
	private static void writeToFile(List<String> list, String path) {
		BufferedWriter out = null;
		try {
			File file = new File(path);
			out = new BufferedWriter(new FileWriter(file, true));
			for (String s : list) {
				out.write(s);
				out.newLine();

			}
			out.close();
		} catch (IOException e) {
		}
	}
}
