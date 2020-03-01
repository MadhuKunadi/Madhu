/**
 * 
 */
package com.intl.aoc.dbconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author SB TECHNOLOGY
 *
 */
public class dbConnection {
	public static Connection sqlConnectionDB() throws IOException, ClassNotFoundException, SQLException{
		Connection conn=null;
		Properties prop=new Properties();
		String filename="dbconnection.properties";
		InputStream input=dbConnection.class.getClassLoader().getResourceAsStream(filename);
		prop.load(input);	
			Class.forName(prop.getProperty("db.classname"));
			conn = DriverManager.getConnection(prop.getProperty("db.drivername"),prop.getProperty("db.username"),prop.getProperty("db.password")); 
			return conn;
	}
		public static Connection sqlConnection() throws IOException, ClassNotFoundException, SQLException{
			Connection conn=null;
			Properties prop=new Properties();
			String filename="dbconnection.properties";
			InputStream input=dbConnection.class.getClassLoader().getResourceAsStream(filename);
			prop.load(input);	
				Class.forName(prop.getProperty("db.classnamedb"));
				conn = DriverManager.getConnection(prop.getProperty("db.drivernamedb"),prop.getProperty("db.usernamedb"),prop.getProperty("db.passworddb")); 
			return conn;
		}
		
}
