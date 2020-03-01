package com.cs;


import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@Path("price")
public class PricingHistory {
	
	static JSONArray jsonArray = new JSONArray();
	static JSONObject jsonObject = new JSONObject();
	static Connection connection;
	static ResultSet resultset;
	static Statement statement;
	private static Log log=LogFactory.getLog(PricingHistory.class);
	
	@POST
	@Path("/pricingList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response  prisingList(@FormParam("id") String id){
JSONObject jsObject = TokenCheck.checkTokenStatus(id);
if(jsObject.containsKey("status")){
	jsonObject.clear();
	jsonObject.put("status","Failed");
	jsonObject.put("message", jsObject.get("status"));
	return Response.ok()
			.entity(jsonObject)
			.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
}else{
	String email = (String) jsObject.get("email");
	//int roll_id= (int) jsObject.get("roll_id");
	//System.out.println(rollid);
}
try{
	SqlConnection();
	try{
		
		String Query="select id,product_description,product_unit,product_quantity from purchase_order where purchaseorder_status='Approved'";

resultset=statement.executeQuery(Query);
		ArrayList arrayList = convertResultSetIntoArrayList(resultset);
		if(arrayList!=null&&!arrayList.isEmpty()){
			jsonObject.clear();
			jsonObject.put("Status", "Success");
			jsonObject.put("Data", arrayList);	
		}else{
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("message", "List is emplty");		
		}
	}
		catch(SQLException e){
		jsonObject.clear();
		jsonObject.put("Status", "Failed");
		jsonObject.put("message", "Something went Wrong");
	}catch (Exception e) {
		jsonObject.clear();
		jsonObject.put("Status", "Failed");
		jsonObject.put("message", "Please Try Again");
	}
}catch (IOException e) {
jsonObject.clear();
jsonObject.put("Status", "Failed");
jsonObject.put("message", "Something Went Wrong");
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
		.entity(jsonObject)
		.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();

	}
	
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryPricingList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response inventoryPricingList(@FormParam("id") String id){
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status","Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{
				
				String Query="SELECT pricing_table.barcode_number, pricing_table.product_id, pricing_table.product_name, "
						+ "pricing_table.product_unit, pricing_table.manufactur_date, pricing_table.expiry_date,"
						+ "pricing_table.price,barcode.discount FROM pricing_table "
						+ "inner join barcode on pricing_table.product_id=barcode.product_id";

		resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList = convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);	
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
					e.printStackTrace();
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("message", "Something went Wrong");
			}catch (Exception e) {
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
			e.printStackTrace();
		jsonObject.clear();
		jsonObject.put("Status", "Failed");
		jsonObject.put("message", "Something Went Wrong");
		}finally{
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
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryPricingHistoryList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response inventoryPricingHistoryList(@FormParam("id") String id,@FormParam("product_id") String product_id){
		
		JSONObject jsObject = TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status","Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email = (String) jsObject.get("email");
			//int roll_id= (int) jsObject.get("roll_id");
			//System.out.println(rollid);
		}
		try{
			SqlConnection();
			try{ 
				
				String Query="SELECT pricinghistory_table.barcode_number, pricinghistory_table.product_id, pricinghistory_table.product_name, "
						+ "pricinghistory_table.product_unit, pricinghistory_table.from_date, "
						+ "case when pricinghistory_table.to_date is null then 'none' else  to_char(pricinghistory_table.to_date,'DD-MM-YYYY') end as to_date,"
						+ "barcode.discount,pricinghistory_table.current_price "
						+ "FROM pricinghistory_table "
						+ "inner join barcode on pricinghistory_table.product_id=barcode.product_id where pricinghistory_table.product_id='"+product_id+"'";

		resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList = convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);	
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("message", "List is emplty");		
				}
			}
				catch(SQLException e){
					e.printStackTrace();
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("message", "Something went Wrong");
			}catch (Exception e) {
				e.printStackTrace();
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("message", "Please Try Again");
			}
		}catch (IOException e) {
			e.printStackTrace();
		jsonObject.clear();
		jsonObject.put("Status", "Failed");
		jsonObject.put("message", "Something Went Wrong");
		}finally{
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
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("price")||columnName.contains("current_price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}
				else{
					Object columnValue = resultSet.getObject(i + 1);
					map.put(columnName,columnValue);
				}
				//Object columnValue = resultSet.getObject(i + 1);
				
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}
	
	
}
 