package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

@Path("Store")
public class Store {

	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	static PreparedStatement preparestatement;
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/warehouseProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productNamesSearch(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
	            if(role_id==1||role_id==3){
	            	
	            	System.out.println("warehouse_id is "+warehouse_id);
	            	
	            }
	            else{
	            	
	            	warehouse_id=auth_id;
	            }
			System.out.println(role_id);
		}
		String Query ="select distinct product_description,product_id from tbl_warehouse_purchase_order "
				+ "where purchaseorder_status='Approved' and warehouse_id='"+warehouse_id+"' "
						+ "and isbarcode_generated='true' and mark_for_deletion=0 and tbl_products.item_prohibited=0 and tbl_products.item_hide=0";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "List is empty");
				}
			} catch (SQLException e) {
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Something went wrong");
				 jsonObject.put("error", e.getMessage());
			} catch (Exception e) {
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}
		finally{
			try {
				
				connection.close();
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
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
	/*
	 * This API is the functionality of Stores where they can update the product price according to their need of market strategy.
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateProductPrice")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateProductPrice(@FormParam("id") String id,@FormParam("price") double price,@FormParam("barcode_number") String barcode_number,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		String store_id=null;
		String email=null;
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
	            if(role_id==1||role_id==3){      	
	            	System.out.println("warehouse_id is "+store_id);
	            }
	            else{	            	
	            	store_id=auth_id;
	            }
			System.out.println(role_id);
		}
		String Query ="update barcode set price=?,updated_by=?,updaatedon=current_timestamp where barcode_number=? and product_id=?";
		try {
			SqlConnection();
			try {
				preparestatement=connection.prepareStatement(Query);
					preparestatement.setDouble(1, price);
					preparestatement.setString(2, email);
					preparestatement.setString(3, barcode_number);
					preparestatement.setString(4, barcode_number);
					
					int status=preparestatement.executeUpdate();
					System.out.println("Query3"+status);
					if(status>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message","Data Updated sucssesfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed");
					}
			} catch (SQLException e) {
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Something went wrong");
				 jsonObject.put("error", e.getMessage());
			} catch (Exception e) {
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}
		finally{
			try {
				
				connection.close();
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
}
