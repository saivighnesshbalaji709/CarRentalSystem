package util;
import java.sql.*;
import java.io.*;
public class DBConnUtil {
	private static final String filename="db.properties";
	public static Connection getconnectiondb() {
		Connection con=null;
		String constr=null;
		try {
			constr=DBPropertyUtil.getConstr(filename);
		}
		catch(IOException e){
			System.out.println("connection string creation failed");
			
		}
		if(constr!=null) { 
			   try { 
			    con=DriverManager.getConnection(constr); 
			   }catch (SQLException e) { 
			    System.out.println("Error While Establishing DBConnection........"); 
			    e.printStackTrace(); 
			   } 
			  } 
			  return con; 
	}
	

}