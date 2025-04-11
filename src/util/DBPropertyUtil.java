package util;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException; 
public class DBPropertyUtil {
public static String getConstr(String filename) throws IOException{
	String constr=null;
	Properties prop=new Properties();
	FileInputStream fis=new FileInputStream(filename);
	prop.load(fis);
	String user=prop.getProperty("user");
	String password=prop.getProperty("password");
	String port=prop.getProperty("port");
	String protocol=prop.getProperty("protocol");
	String system=prop.getProperty("system");
	String database=prop.getProperty("database");
	
	constr=protocol+"//"+system+":"+port+"/"+database+"?"+"user="+user+"&password="+password;
	return constr;
    	  
      } 

}
