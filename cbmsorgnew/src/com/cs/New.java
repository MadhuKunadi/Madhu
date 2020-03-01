package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import sun.rmi.runtime.Log;

/**
 * @author akhil
 *
 */
@Path("Upload")
public class New {
	static Connection connection;
	static Statement statement;
	static ResultSet resultset;
	static CallableStatement callablestatement;
	
	static JSONArray jsonArray = new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	// private static Log log=LogFactory.getLog(New.class);
	private static Log log=LogFactory.getLog(New.class);

	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getRequestId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response getRequestId(@FormParam("applicationid") String applicationid,@FormParam("requestid") String requestid){
		if(applicationid.length()==0||requestid.length()==0){
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Fields Are Empty");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			
		}
		
		String Query="insert into number(applicationid,requestid) values('"+applicationid+"','"+requestid+"')";
		String maxQuery="select max(id) from number where applicationid='"+applicationid+"' and requestid='"+requestid+"'";
		try{
			SqlConnection();
		}catch(Exception e){
			if(connection!=null){
				try {
					connection.close();
					System.out.println("Connection closed");
					log.error("connection closed");
				} catch (SQLException exc) {
					log.error(exc.getMessage());
				}
			}
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Server connection failed");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		try {
			int i=statement.executeUpdate(Query);
			if(i>0){
				resultset=statement.executeQuery(maxQuery);
			}
			else{
				System.out.println(maxQuery);
			}
			
		} catch (SQLException e) {
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Some server issues occurred");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			
			} catch (SQLException e) {
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", e.getMessage());
					jsonArray.add(jsonObject);
			}		
		}
		ArrayList ID;
		if(resultset!=null){
			try {
				int Rid=0;
				while(resultset.next()){
					Rid=resultset.getInt(1);
				}
				System.out.println("Rid :"+Rid);
				String appId=applicationid+"000000";
				String reqId=requestid+"000000";
				System.out.println(requestid);
				String s=String.valueOf(Rid);
				appId=appId.substring(0,appId.length()-s.length());
				appId=appId.concat(String.valueOf(Rid));
				reqId=reqId.substring(0,reqId.length()-s.length());
				reqId=reqId.concat(String.valueOf(Rid));
				JSONObject jobj1=new JSONObject();
				jobj1.put("RequestId", reqId);
				jobj1.put("ApplicationId", appId);
				jobj1.put("Status", "Success");
				jsonArray.clear();
				jsonArray.add(jobj1);
				System.out.println(jsonArray);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			} catch (Exception e) {
				jsonObject.clear();
				jsonArray.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Data conversion failed");
				jsonArray.add(jsonObject);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}finally{
				if(connection!=null){
					try {
						connection.close();
						System.out.println("Connection closed");
						log.error("connection closed");
					} catch (SQLException e) {
						log.error(e.getMessage());
					}
				}
			}		 
		}  
		else{
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Some server issues occurred to get the Data");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Context
	@POST
	@Path("/request")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response uploadRequest(@FormParam("RequestId") String RequestId,@FormParam("ApplicationId") String ApplicationId,@FormParam("FirstName") String FirstName,@FormParam("LastName") String LastName,@FormParam("GaurdianName") String GaurdianName,
			@FormParam("MobileNumber") String MobileNumber,@FormParam("AadharNumber") String AadharNumber,@FormParam("Description") String Description,@FormParam("WhatsappNumber") String WhatsappNumber,@FormParam("Email")String Email,@FormParam("Department") String Department,@FormParam("Priority") String Priority,@FormParam("Assembly_Constituency") String Assembly_Constituency,@FormParam("parliament_constituency") String parliament_constituency,
			@FormParam("HouseNumber") String HouseNumber,@FormParam("StreetName") String StreetName,@FormParam("WardNumber") String WardNumber,  @FormParam("Village") String Village,@FormParam("Mandal") String Mandal,
			@FormParam("District") String District,@FormParam("State") String State,@FormParam("Pincode") String Pincode,@FormParam("CreatedBy") String CreatedBy,@FormParam("References") String References) {
		String InsertQuery="INSERT INTO tj_applicationmaster(RequestId,ApplicationId,FirstName,LastName,GaurdianName,MobileNumber,Whatsapp,AadharNumber,Description,Email,Department,Priority,Assembly_Constituency,parliament_constituency,HouseNumber,StreetName,WardNumber,Village,Mandal,District,State,Pincode,status,count,activity,createdby,referredby) values('"+RequestId+"','"+ApplicationId+"',lower('"+FirstName+"'),lower('"+LastName+"'),lower('"+GaurdianName+"'),'"+MobileNumber+"','"+WhatsappNumber+"','"+AadharNumber+"',lower('"+Description+"'),'"+Email+"','"+Department+"','"+Priority+"','"+Assembly_Constituency+"','"+parliament_constituency+"','"+HouseNumber+"',lower('"+StreetName+"'),'"+WardNumber+"','"+Village+"','"+Mandal+"','"+District+"','"+State+"','"+Pincode+"','new','0','new','"+CreatedBy+"','"+References+"')";
		System.out.println(InsertQuery);
		String ExecuteQuery="select * from tj_applicationmaster where ApplicationId='"+ApplicationId+"'";
		
		try{
			SqlConnection();
		}catch(Exception e){
			System.out.println(e.getMessage());
			if(connection!=null){
				try {
					connection.close();
					System.out.println("Connection closed");
					log.error("connection closed");
				} catch (SQLException exc) {
					log.error(exc.getMessage());
				}
			}
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Server connection failed");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		try {
			int i=0;
			i=statement.executeUpdate(InsertQuery);
			if(i>0){
				callablestatement=connection.prepareCall("select workflow_function"+"('"+RequestId+"','"+"create"+"','create','"+CreatedBy+"','"+CreatedBy+"','"+"No Comments"+"')");
				System.out.println(callablestatement);
				callablestatement.execute();
				resultset=statement.executeQuery(ExecuteQuery);
			}else{
				jsonObject.clear();
				jsonArray.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Some server issues  occurred in updating process");
				jsonArray.add(jsonObject);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
		}catch(Exception resultsetexception){
			log.info(InsertQuery);
			System.out.println(resultsetexception.getMessage());
			log.info(resultsetexception.getMessage());
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Some server issues occurred");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
				} catch (SQLException e) {
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", e.getMessage());
					jsonArray.add(jsonObject);
				
			}
		}
		try {
			ArrayList convertionarray=new ArrayList();
			convertionarray=convertResultSetIntoJSON(resultset);
			if(!convertionarray.isEmpty()&&convertionarray.size()!=0){
				System.out.println("if"+convertionarray);
				JSONObject resultobj=new JSONObject();
				resultobj.put("Status", "Success");
				resultobj.put("Data", convertionarray);
				JSONArray resultjarray=new JSONArray();
				resultjarray.add(resultobj);
				return Response.ok()
						.entity(resultjarray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}else{
				System.out.println("else"+convertionarray);
				jsonObject.clear();
				jsonArray.clear();
				jsonObject.put("Status", "Success");
				jsonObject.put("Data", convertionarray);
				jsonObject.put("Message", "No Data is available");
				jsonArray.add(jsonObject);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
		} catch (Exception convertionException) {
			System.out.println(convertionException.getMessage());
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Data conversion failed");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}finally{
			if(connection!=null){
				try {
					connection.close();
					System.out.println("Connection closed");
					log.error("connection closed");
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
	}	
		
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getRequestuploadCoupontId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response uploadCoupon(@FormParam("user") String user,@FormParam("coupon") String coupon){
		
		String Query="insert into number(user,requestid) values('"+user+"','"+coupon+"')";
		try{
			SqlConnection();
		}catch(Exception e){
			if(connection!=null){
				try {
					connection.close();
					System.out.println("Connection closed");
					log.error("connection closed");
				} catch (SQLException exc) {
					log.error(exc.getMessage());
				}
			}
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Server connection failed");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		try {
			int i=statement.executeUpdate(Query);
			if(i>0){
				jsonObject.clear();
				jsonArray.clear();
				jsonObject.put("Status", "Successs");
				jsonObject.put("Message", "upload Successfully");
				jsonArray.add(jsonObject);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
			else{
				jsonObject.clear();
				jsonArray.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "upload failed");
				jsonArray.add(jsonObject);
				return Response.ok()
						.entity(jsonArray)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
			
		} catch (SQLException e) {
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Some server issues occurred");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}finally{
			if (connection != null) {
				try {
					connection.close();
					System.out.println("connection is closed");
				} catch (SQLException e) {
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", e.getMessage());
					jsonArray.add(jsonObject);
				}
			}
		}
		}
	
	/* Database Connections */
	public static void SqlConnection() throws IOException{
		Properties prop=new Properties();
		String filename="dbconnection.properties";
		InputStream input=New.class.getClassLoader().getResourceAsStream(filename);
		prop.load(input);
		try {
			Class.forName(prop.getProperty("db.classname"));
		} catch (ClassNotFoundException cnfexception) {
			cnfexception.printStackTrace();
		}  
		try {
			connection = DriverManager.getConnection(prop.getProperty("db.drivername"),prop.getProperty("db.username"),prop.getProperty("db.password"));
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}  
		try {
			statement=connection.createStatement();			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}  
	}
	
	/* Converting Resultset into Json format method  */
	@SuppressWarnings("unchecked")
	public static ArrayList convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				map.put(columnName,columnValue);
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}		
	}



