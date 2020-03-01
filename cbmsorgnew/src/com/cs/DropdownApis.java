package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.helper.Helper;



@Path("dropDown")
public class DropdownApis {
	
	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	
	static JSONArray jsonarray=new JSONArray();
	 static JSONObject jsonobject=new JSONObject();
	 
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(DropdownApis.class);
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/productUnit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response productUnit(@FormParam("id") String id){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonobject.clear();
			jsonobject.put("status","Failed");
			jsonobject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonobject)
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="select id,product_unit from product_unit";

		resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList = convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonobject.clear();
					jsonobject.put("Status", "Success");
					jsonobject.put("Data", arrayList);	
				}else{
					jsonobject.clear();
					jsonobject.put("Status", "Failed");
					jsonobject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Something went Wrong");
			}catch (Exception e) {
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
		jsonobject.clear();
		jsonobject.put("Status", "Failed");
		jsonobject.put("message", "Something Went Wrong");
		}
		finally{
			try {
				
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return Response.ok()
				.entity(jsonobject)
				.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
	}
	
	@POST
	@Path("/storeDropDown")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response storDropDown(@FormParam("id") String id){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonobject.clear();
			jsonobject.put("status","Failed");
			jsonobject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonobject)
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="select branch_id,branch_name from tbl_branch";

		resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList = convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonobject.clear();
					jsonobject.put("Status", "Success");
					jsonobject.put("Data", arrayList);	
				}else{
					jsonobject.clear();
					jsonobject.put("Status", "Failed");
					jsonobject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Something went Wrong");
			}catch (Exception e) {
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
		jsonobject.clear();
		jsonobject.put("Status", "Failed");
		jsonobject.put("message", "Something Went Wrong");
		}
		finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return Response.ok()
				.entity(jsonobject)
				.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
	}
	
	
	@POST
	@Path("vendorDropDown")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorDropDown(@FormParam("id") String id){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonobject.clear();
			jsonobject.put("status","Failed");
			jsonobject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonobject)
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="select vendor_id,vendor_name from tbl_vendor1 where status='Active' and mark_for_deletion=0";

		resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList = convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonobject.clear();
					jsonobject.put("Status", "Success");
					jsonobject.put("Data", arrayList);	
				}else{
					jsonobject.clear();
					jsonobject.put("Status", "Failed");
					jsonobject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Something went Wrong");
			}catch (Exception e) {
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
		jsonobject.clear();
		jsonobject.put("Status", "Failed");
		jsonobject.put("message", "Something Went Wrong");
		}
		finally{
			try {
				
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return Response.ok()
				.entity(jsonobject)
				.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/testDropDown/{type}")
	//@Path("/testDropDown/{type}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response testDropDown(@FormParam("id") String id,@PathParam("type") String type){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonobject.clear();
			jsonobject.put("status","Failed");
			jsonobject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonobject)
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="select fn_store_warehouse_list1('"+type+"')";
				System.out.println("Query "+Query);
		resultset=statement.executeQuery(Query);
		Map map=new HashMap();
		
		while(resultset.next()){
		Object object =resultset.getObject(1);
		map.put("Data", object);
		}
		System.out.println("map "+map);
//				ArrayList arrayList = convertResultSetIntoArrayList(resultset);
				if(map!=null && !map.isEmpty()){

					jsonobject.clear();
					jsonobject.put("Status", "Success");
					jsonobject.putAll(map);
				}else{
					jsonobject.clear();
					jsonobject.put("Status", "Failed");
					jsonobject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
					e.printStackTrace();
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Something went Wrong");
			}catch (Exception e) {
				e.printStackTrace();
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
		jsonobject.clear();
		jsonobject.put("Status", "Failed");
		jsonobject.put("message", "Something Went Wrong");
		}
		finally{
			try {
				
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return Response.ok()
				.entity(jsonobject)
				.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();	
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/testDropDownAddress/{type}")
	//@Path("/testDropDown/{type}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response testDropDownAddress(@FormParam("id") String id,@PathParam("type") String type,@FormParam("moduleid") String moduleid){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonobject.clear();
			jsonobject.put("status","Failed");
			jsonobject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonobject)
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="select fn_store_warehouse_address1('"+type+"','"+moduleid+"')";
		resultset=statement.executeQuery(Query);
		Map map=new HashMap();
		while(resultset.next()){
		Object object =resultset.getObject(1);
		map.put("Data", object);
		}
//				ArrayList arrayList = convertResultSetIntoArrayList(resultset);
				if(map!=null && !map.isEmpty()){

					jsonobject.clear();
					jsonobject.put("Status", "Success");
					jsonobject.putAll(map);
				}else{
					jsonobject.clear();
					jsonobject.put("Status", "Failed");
					jsonobject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
					e.printStackTrace();
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Something went Wrong");
			}catch (Exception e) {
				e.printStackTrace();
				jsonobject.clear();
				jsonobject.put("Status", "Failed");
				jsonobject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
		jsonobject.clear();
		jsonobject.put("Status", "Failed");
		jsonobject.put("message", "Something Went Wrong");
		}
		finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return Response.ok()
				.entity(jsonobject)
				.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
	}
	
	private static void SqlConnection() throws IOException {
		// TODO Auto-generated method stub
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayList(ResultSet resultSet) throws Exception {
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
