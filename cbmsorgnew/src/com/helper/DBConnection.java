package com.helper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.cs.New;

public class DBConnection {
	private static Properties prop=new Properties();
	private static String filename="dbconnection.properties";
	
	
	public static Connection SqlConnection() throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		InputStream input=New.class.getClassLoader().getResourceAsStream(filename);
		prop.load(input);
		Connection	connection =null;
			Class.forName(prop.getProperty("db.classname"));
			connection = DriverManager.getConnection(prop.getProperty("db.drivername"),prop.getProperty("db.username"),prop.getProperty("db.password"));
		return connection;

			}  
		
public static void closeConnection(Connection connection) throws SQLException{
	if(connection!=null)
		connection.close();
	
}		
}
