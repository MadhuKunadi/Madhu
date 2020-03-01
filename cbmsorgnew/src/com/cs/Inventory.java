package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.helper.Config;
import com.helper.DBConnection;
import com.utils.Products;

@Path("Inventory")
public class Inventory {
	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(Inventory.class);
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addStockInventory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addStockInventory(@FormParam("id") String id,@FormParam("inventory_id") String inventory_id,@FormParam("name") String name,@FormParam("date") String date,@FormParam("state") String state,@FormParam("company_id")
	String company_id,@FormParam("location_id") String location_id,@FormParam("product_id") String product_id,@FormParam("package_id") String package_id,@FormParam("partner_id") String partner_id,@FormParam("lot_id") String lot_id,
	@FormParam("filter") String filter,@FormParam("category_id") String category_id,@FormParam("exhausted") String exhausted,@FormParam("accounting_date") String accounting_date){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			@SuppressWarnings("unused")
			String inventoryid = Customer.getGenerateId("INVEN", 3,connection);
			System.out.println(inventoryid);
			try {
				String Query="insert into stock_inventory(inventory_id,name,date,state,company_id,location_id,product_id,package_id,partner_id,lot_id,filter,category_id,exhausted,accounting_date) "
						+ "values('"+inventoryid+"','"+name+"','"+date+"','"+state+"','"+company_id+"','"+location_id+"','"+product_id+"','"+package_id+"','"+partner_id+"','"+lot_id+"','"+filter+"','"+category_id+"','"+exhausted+"','"+accounting_date+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getStockInventoryDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getStockInventoryDetails(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select * from stock_inventory";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addStockInventoryLine")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addStockInventoryLine(@FormParam("id") String id,@FormParam("inventory_line_id") String inventory_line_id,@FormParam("inventory_id") String inventory_id,@FormParam("partner_id") String partner_id,@FormParam("product_id") String product_id,@FormParam("product_name") String product_name,@FormParam("product_code")
	String product_code,@FormParam("product_uom_id") String product_uom_id,@FormParam("product_quantity") String product_quantity,@FormParam("location_id") String location_id,@FormParam("location_name") String location_name,@FormParam("package_id") String package_id,
	@FormParam("partner_lot_id") String partner_lot_id,@FormParam("partner_lot_name") String partner_lot_name,@FormParam("company_id") String company_id,@FormParam("theoretical_quantity") String theoretical_quantity){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			try {
				String Query="insert into stock_inventory_line(inventory_line_id,inventory_id,partner_id,product_id,product_name,product_code,product_uom_id,product_qty,location_id,location_name,package_id,prod_lot_id,prodlot_name,company_id,theoretical_qty) "
						+ "values('"+inventory_line_id+"','"+inventory_id+"','"+partner_id+"','"+product_id+"','"+product_name+"','"+product_code+"','"+product_uom_id+"','"+product_quantity+"','"+location_id+"','"+location_name+"','"+package_id+"','"+partner_lot_id+"','"+partner_lot_name+"','"+company_id+"','"+theoretical_quantity+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getStockInventoryLineDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getStockInventoryLineDetails(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select * from stock_inventory_line";
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
	
   	/* ------------------------------------------
   	 * 
   	 * 
   	 * 
   	 */

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
		String Query ="select distinct product_description from tbl_warehouse_purchase_order "
				+ "where purchaseorder_status='Approved' and warehouse_id='"+warehouse_id+"' "
						+ "and isbarcode_generated='true' and mark_for_deletion=0";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/warehouseProductSearchData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseProductSearchData(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,
			@FormParam("product_name") String product_name){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
	            if(role_id==1){
	            	
	            }else{
	            	
	            	warehouse_id=auth_id;
	            }
			System.out.println(role_id);
		}
		
		String[] parts=product_name.split("-");
		String product_id = parts[parts.length-1];
		
		String Query ="select * from tbl_warehouse_purchase_order where product_id='"+product_id+"' and product_description like '"+product_name+"'";
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

	@SuppressWarnings("unchecked")
	@POST
	@Path("/generateInventoryId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatePurcaseOrderId(@FormParam("id") String id){
//		,@FormParam("warehouse_name") String warehouse_name
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);String subQuery=new String();
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
//			if(role_id==4){
//				
//			}
			System.out.println(role_id);	
		}
		try {
			SqlConnection();
			try {
				String email=(String) jsObject.get("email");
				
				String inventoryid=Customer.getGenerateId("INVENTORY",9,connection);
				if(inventoryid!=null&&!inventoryid.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", inventoryid);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "List is empty");
				}
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/requestToWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response requestWarehouse(@FormParam("id") String id,@FormParam("inventory_id") String inventory_id,@FormParam("inventory_date") String inventory_date,
			@FormParam("products") String products,@FormParam("warehouse_id") String warehouse_id,
			@FormParam("inventory_address") String inventory_address,@FormParam("store_id") String store_id){
		
		JSONObject jsonObject=new JSONObject();
		
		System.out.println(id);
		if(id==null||id.isEmpty()){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Id value is empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		
//		if(product_name==null || product_name.isEmpty()||product_code==null || product_code.isEmpty()
//		||product_category==null || product_category.isEmpty()||product_subcategory==null || product_subcategory.isEmpty()||alert_quantity==null || alert_quantity.isEmpty()
//		||product_brand==null || product_brand.isEmpty()||product_unit==null || product_unit.isEmpty()||product_size==null || product_size.isEmpty()||productunit_value==null || productunit_value.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
		
		//String produnitvalue=productunit_value+product_unit;

		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id= (String) jsObject.get("auth_id");
			if(role_id==1){
				
			}else{
				
				store_id=auth_id;
			}
			}
		try {
			SqlConnection();
			try {
				String email=(String) jsObject.get("email");
				org.json.JSONArray jsonArray=new org.json.JSONArray(products);
				for(int i=0;i<jsonArray.length();i++){
					org.json.JSONObject jsobject = jsonArray.getJSONObject(i);
					String productname = jsobject.getString("productname");
					//String unit = jsobject.getString("unit");
					int quantity = jsobject.getInt("quantity");
					
					String[] parts = productname.split("-");
					//String lastpart = parts[4];
					//String[] bits = one.split("-");
					String lastpart = parts[parts.length-1];
					//String unit =parts[parts.length-2];
					
					//System.out.println("the value is:" +lastpart);
					
					
				String Query="insert into inventory_request_to_warehouse(inventory_id,inventory_requested_date,inventory_requested_time,product_name,product_quantity,warehouse_id,inventory_address,product_id,store_id,createdby) "
						+ "values('"+inventory_id+"','"+inventory_date+"',current_time,'"+productname+"','"+quantity+"','"+warehouse_id+"','"+inventory_address+"','"+lastpart+"','"+store_id+"','"+email+"')";
				
				int st=statement.executeUpdate(Query);
				if(st>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
				}
			}
					
			}catch (SQLException e) {
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Something went wrong");
				 jsonObject.put("error", e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject.clear();
				e.printStackTrace();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getRequestedListFromRequestedWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response requestedWArehouseList(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		//System.out.println(jsObject);
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
	            if(role_id==1){
	            	
	            }else{
	            	
	            	warehouse_id=auth_id;
	            }
			//System.out.println(role_id);
		}
//		String Query ="SELECT distinct inventory_order.id, inventory_order.inventory_id, inventory_order.inventory_date, inventory_order.product_name,"
//				+ "inventory_order.product_unit, inventory_order.product_quantity, inventory_order.warehouse_id,"
//				+ "inventory_order.inventory_status, inventory_order.product_status, inventory_order.inventory_address,"
//				+ "tbl_warehouse.warehouse_name FROM inventory_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=inventory_order.warehouse_id";
		String Query = "SELECT distinct inventory_request_to_warehouse.inventory_id,inventory_request_to_warehouse.inventory_status "
				+ "as status, inventory_request_to_warehouse.inventory_requested_date,"
				+ "tbl_warehouse1.wareouse_name FROM inventory_request_to_warehouse "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_request_to_warehouse.warehouse_id "
				//+ "inner join tbl_store on tbl_store.store_id=inventory_request_to_warehouse.store_id"
				+ "where inventory_request_to_warehouse.warehouse_id='"+warehouse_id+"'";
		//String Query = "SELECT distinct inventory_order.inventory_id, inventory_order.inventory_date,tbl_warehouse.warehouse_name FROM inventory_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=inventory_order.warehouse_id";
//		,tbl_store.store_name
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
				e.printStackTrace();
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
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getRequestedListFromRequestedWarehouseForStore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getRequestedListFromRequestedWarehouseForStore(@FormParam("id") String id,
			@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		//System.out.println(jsObject);
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
			String auth_id = (String) jsObject.get("auth_id");
			if(role_id==1){
				
			}else{
				store_id=auth_id;
			}
		}
//		String Query ="SELECT distinct inventory_order.id, inventory_order.inventory_id, inventory_order.inventory_date, inventory_order.product_name,"
//				+ "inventory_order.product_unit, inventory_order.product_quantity, inventory_order.warehouse_id,"
//				+ "inventory_order.inventory_status, inventory_order.product_status, inventory_order.inventory_address,"
//				+ "tbl_warehouse.warehouse_name FROM inventory_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=inventory_order.warehouse_id";
		String Query = "SELECT distinct inventory_request_to_warehouse.inventory_id,inventory_request_to_warehouse.inventory_status "
				+ "as status, inventory_request_to_warehouse.inventory_requested_date,"
				+ "tbl_warehouse1.wareouse_name FROM inventory_request_to_warehouse "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_request_to_warehouse.warehouse_id "
				//+ "inner join tbl_store on tbl_store.store_id=inventory_request_to_warehouse.store_id"
				+ "where inventory_request_to_warehouse.store_id='"+store_id+"'";
		//String Query = "SELECT distinct inventory_order.inventory_id, inventory_order.inventory_date,tbl_warehouse.warehouse_name FROM inventory_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=inventory_order.warehouse_id";
//		,tbl_store.store_name
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
				e.printStackTrace();
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
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryPStock")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseProductStock(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="SELECT tbl_products.product_name||'-'||tbl_brand.brand_name as product_description,"
				+ "tbl_products.product_unit,tbl_store.store_name, "
				+ "store_stockdetails.total_stock, store_stockdetails.in_stock, "
				+ "store_stockdetails.out_stock FROM store_stockdetails "
				+ "inner join  tbl_products on tbl_products.product_id=store_stockdetails.product_id "
				+ "inner join tbl_store on tbl_store.store_id=store_stockdetails.store_id "
				+ "inner join tbl_brand on tbl_brand.brand_id=tbl_products.product_brand_id "
				+ "where store_stockdetails.store_id='"+store_id+"'";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getProductCountInWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProductCountInWarehouse(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select id,product_description,product_quantity from purchase_order";
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
		}finally{
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getrequestedListInWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response requestedListInWarehouse(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
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
	            if(role_id==1){
	            	
	            }else{
	            	
	            	warehouse_id=auth_id;
	            }
			System.out.println(role_id);
		}
		String Query ="select inventory_id,inventory_requested_date,inventory_status "
				+ "from inventory_request_to_warehouse where warehouse_id='"+warehouse_id+"'";
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
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/requestedCompleteProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response requestedCompleteList(@FormParam("id") String id,@FormParam("inventory_id") String inventory_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="SELECT inventory_request_to_warehouse.id, inventory_request_to_warehouse.inventory_id,COALESCE(warehouse_stockdetails.total_stock,0) as remaining_stock, "
				+ "inventory_request_to_warehouse.inventory_requested_date, inventory_request_to_warehouse.product_name,"
				+ "inventory_request_to_warehouse.product_unit, inventory_request_to_warehouse.product_quantity, "
				+ "inventory_request_to_warehouse.warehouse_id,inventory_request_to_warehouse.store_id,"
				+ "inventory_request_to_warehouse.inventory_status, inventory_request_to_warehouse.product_status,"
				+ "inventory_request_to_warehouse.shipping_address,inventory_request_to_warehouse.inventory_address,inventory_request_to_warehouse.product_id,"
				+ "tbl_warehouse1.wareouse_name,tbl_store.store_name FROM inventory_request_to_warehouse "
				+ " inner join warehouse_stockdetails on warehouse_stockdetails.warehouse_id=inventory_request_to_warehouse.warehouse_id "
				+ "inner join tbl_store on inventory_request_to_warehouse.store_id=tbl_store.store_id "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_request_to_warehouse.warehouse_id "
				+ "where inventory_request_to_warehouse.inventory_id='"+inventory_id+"'";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
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
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addRequestedProducts")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateRequestedProductsList(@FormParam("id") String id,@FormParam("inventory_date") String inventory_date,@FormParam("indent_id") String indent_id,
			@FormParam("products") String products,@FormParam("warehouse_id") String warehouse_id,@FormParam("inventory_status") String inventory_status,
			@FormParam("inventory_address") String inventory_address,@FormParam("store_id") String store_id,
			@FormParam("shipping_address") String shipping_address ){
		
		JSONObject jsonObject=new JSONObject();
		
		System.out.println(id);
		if(id==null||id.isEmpty()){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Id value is empty"); 
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
//		if(product_name==null || product_name.isEmpty()||product_code==null || product_code.isEmpty()
//		||product_category==null || product_category.isEmpty()||product_subcategory==null || product_subcategory.isEmpty()||alert_quantity==null || alert_quantity.isEmpty()
//		||product_brand==null || product_brand.isEmpty()||product_unit==null || product_unit.isEmpty()||product_size==null || product_size.isEmpty()||productunit_value==null || productunit_value.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
		
		if(indent_id==null || indent_id.isEmpty()){
			
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
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
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			String email=(String) jsObject.get("email");
			try {
				org.json.JSONArray jsonArray=new org.json.JSONArray(products);
				for(int i=0;i<jsonArray.length();i++){
					org.json.JSONObject jsobject = jsonArray.getJSONObject(i);
					String product_description = jsobject.getString("product_description");
					System.out.println(product_description);
					//String product_unit = jsobject.getString("product_unit");
					int product_quantity = jsobject.getInt("product_quantity");
					String[] parts = product_description.split("-");
					//String lastpart = parts[4];
					String lastpart = parts[parts.length-1];
					
//				String Query="select fn_inventory_received_order"
//						+ "('"+inventory_id+"','"+inventory_date+"','"+product_description+"','"+lastpart+"','"+product_quantity+"','"+warehouse_id+"','"+inventory_address+"','"+shipping_address+"','"+email+"','"+store_id+"')";
				
					String Query="select fn_store_received_order('"+indent_id+"','"+lastpart+"','"+product_quantity+"','"+warehouse_id+"','"+email+"','"+store_id+"')";
					System.out.println("the Inventory query is:" +Query);
				
				//int st=statement.executeUpdate(Query);
				CallableStatement callableStatement=connection.prepareCall(Query);
				callableStatement.execute();
				ResultSet resultSet=callableStatement.getResultSet();
				String result=new String();
				while(resultSet.next()){
					result=resultSet.getString(1);
				}
				if(result.contains("Success")){
					String fnQuery= "select fn_warehouse_stock_outdetails('"+indent_id+"','"+lastpart+"','"+warehouse_id+"','"+store_id+"','"+email+"','"+product_quantity+"')";
					System.out.println(fnQuery);
					CallableStatement callableStatement1=connection.prepareCall(fnQuery);
					callableStatement1.execute();
					ResultSet resultSet1=callableStatement1.getResultSet();
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
				}
			}
					
			}catch (SQLException e) {
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Something went wrong");
				 jsonObject.put("error", e.getMessage());
			} catch (Exception e) {
				jsonObject.clear();
				e.printStackTrace();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}			
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/ReceivedProductsListFromWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatedProductsList(@FormParam("id") String id,@FormParam("store_id") String store_id){
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
			if(role_id==1){
				
			}else{
				
				store_id=auth_id;
			}
			System.out.println(role_id);
		}
//		String Query ="SELECT inventory_received_order_from_warehouse.id, "
//				+ "inventory_received_order_from_warehouse.inventory_id,"
//				+ "inventory_received_order_from_warehouse.product_id,"
//				+ "inventory_received_order_from_warehouse.order_received_date,"
//				+ " inventory_received_order_from_warehouse.product_name, "
//				+ "inventory_received_order_from_warehouse.product_unit, "
//				+ "inventory_received_order_from_warehouse.product_quantity,"
//				+ " inventory_received_order_from_warehouse.warehouse_id, "
//				+ "inventory_received_order_from_warehouse.inventory_status, "
//				+ "inventory_received_order_from_warehouse.product_status, "
//				+ "inventory_received_order_from_warehouse.shipping_address,"
//				+ "inventory_received_order_from_warehouse.inventory_address, "
//				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Pending') as viewProductCount,"
//				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Approved') as ApprovedCount,"
//				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Rejected') as RejectedCount, "
//				+ "tbl_warehouse1.wareouse_name FROM inventory_received_order_from_warehouse "
//				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_received_order_from_warehouse.warehouse_id "
//				+ "where inventory_received_order_from_warehouse.store_id='"+store_id+"'";
		
		String Query="SELECT distinct tbl_store_indent_products.indent_id,"
				+ "tbl_store_indent_products.created_on,"
				+ "tbl_store_indent_products.status "
				+ "FROM tbl_store_indent_products where store_id='"+store_id+"' ";
		
		System.out.println(Query);
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
				e.printStackTrace();
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/sentProductsListFromWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response sendProductsListFromWarehouse(@FormParam("id") String id,@FormParam("inventory_id") String inventory_id){
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
		}
		String Query ="SELECT inventory_received_order_from_warehouse.id, "
				+ "inventory_received_order_from_warehouse.inventory_id,"
				+ "inventory_received_order_from_warehouse.product_id,"
				+ "inventory_received_order_from_warehouse.order_received_date,"
				+ " inventory_received_order_from_warehouse.product_name, "
				+ "inventory_received_order_from_warehouse.product_unit, "
				+ "inventory_received_order_from_warehouse.product_quantity,"
				+ " inventory_received_order_from_warehouse.warehouse_id, "
				+ "inventory_received_order_from_warehouse.inventory_status, "
				+ "inventory_received_order_from_warehouse.product_status, "
				+ "inventory_received_order_from_warehouse.shipping_address,"
				+ "inventory_received_order_from_warehouse.inventory_address, "
				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Pending') as viewProductCount,"
				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Approved') as ApprovedCount,"
				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Rejected') as RejectedCount, "
				+ "tbl_warehouse1.wareouse_name FROM inventory_received_order_from_warehouse "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_received_order_from_warehouse.warehouse_id "
				+ "where inventory_received_order_from_warehouse.inventory_id='"+inventory_id+"'";
		
		System.out.println(Query);
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
				e.printStackTrace();
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryProductsCount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response inventoryList(@FormParam("id") String id){
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
			System.out.println(role_id);
		}
		String Query = "select distinct inventory_id,order_received_date,(select count(*) from inventory_received_order_from_warehouse where product_status='Pending') as viewProductCount,"
				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Approved') as ApprovedCount,"
				+ "(select count(*) from inventory_received_order_from_warehouse where product_status='Rejected') as RejectedCount from inventory_received_order_from_warehouse";
		//String Query ="SELECT distinct complete_inventoryorder.inventory_id,complete_inventoryorder.inventory_date FROM complete_inventoryorder where product_status='Pending'";
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
				e.printStackTrace();
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response completeInventoryList(@FormParam("id") String id,@FormParam("indent_id") String indent_id){
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
			System.out.println(role_id);
		}
//		String Query ="SELECT inventory_received_order_from_warehouse.id, inventory_received_order_from_warehouse.inventory_id,"
//				+ "inventory_received_order_from_warehouse.order_received_date, inventory_received_order_from_warehouse.order_received_time,"
//				+ "inventory_received_order_from_warehouse.product_name,inventory_received_order_from_warehouse.inventory_id,"
//				+ "inventory_received_order_from_warehouse.product_unit, inventory_received_order_from_warehouse.store_id,"
//				+ "inventory_received_order_from_warehouse.product_quantity, "
//				+ "inventory_received_order_from_warehouse.warehouse_id,"
//				+ "inventory_received_order_from_warehouse.inventory_status, "
//				+ "inventory_received_order_from_warehouse.product_status, "
//				+ "inventory_received_order_from_warehouse.shipping_address,"
//				+ "inventory_received_order_from_warehouse.inventory_address,"
//				+ "tbl_warehouse1.wareouse_name FROM inventory_received_order_from_warehouse "
//				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=inventory_received_order_from_warehouse.warehouse_id "
//				+ "where inventory_received_order_from_warehouse.inventory_id='"+indent_id+"'";
		
		String Query="SELECT tbl_store_indent_products.sno," 
				+ "tbl_store_indent_products.indent_id,"
				+ "tbl_products.product_name||'-'||product_unit.product_unit as product_description," 
				+ "tbl_store_indent_products.store_id,tbl_store_indent_products.product_id,"
				+ "tbl_store_indent_products.raised_qty,"
				+ "tbl_store_indent_products.warehouse_id,"
				+ "tbl_store_indent_products.issued_qty FROM tbl_store_indent_products "
				+ "inner join tbl_products on tbl_store_indent_products.product_id=tbl_products.product_id "
                + "inner join product_unit on product_unit.id=tbl_products.product_unit "
                + "where tbl_store_indent_products.indent_id='"+indent_id+"'";
		
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println("Query "+Query);
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
				e.printStackTrace();
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryApproveorreject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response acceptorreject(@FormParam("token") String id,@FormParam("data") String data,@FormParam("status") String status){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		if(status==null&&status.isEmpty()){
			
			System.out.println("the status is"   +status);
			
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "status is empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else if(status.equals("1")){
			status="Approved";
			String email=(String) jsObject.get("email");
			org.json.JSONArray jsArray;
			try {
				SqlConnection();
				jsArray = new org.json.JSONArray(data);
				for(int i=0;i<jsArray.length();i++){
					org.json.JSONObject jsObject1 = jsArray.getJSONObject(i);
					String inventory = jsObject1.getString("indent_id");
					String product_description = jsObject1.getString("product_description");
					
					String[] parts = product_description.split("-");
					//String lastpart = parts[4];
					//String[] bits = one.split("-");
					//String product_id = parts[parts.length-1];
					String product_id = jsObject1.getString("product_id");
					String warehouse_id = jsObject1.getString("warehouse_id");
					String store_id = jsObject1.getString("store_id");
					int quantity = jsObject1.getInt("product_quantity");
					String Query ="select fn_store_stock_indetails('"+inventory+"','"+product_id+"',"
							+ "'"+warehouse_id+"','"+store_id+"','"+email+"','"+quantity+"')";
					System.out.println("In Details : "+Query);
					CallableStatement csStatement=connection.prepareCall(Query);
					csStatement.execute();
					resultset=csStatement.getResultSet();
					String getStatus="";
					while(resultset.next()){
						getStatus=resultset.getString(1);
					}
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", getStatus);
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		}else if(status.equals("0")){
			status="Rejected";
		}else{
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Invalid action");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		
		try {
			SqlConnection();
		
			try {
				String pcoId=new String();
				System.out.println(data);
				org.json.JSONArray jsArray=new org.json.JSONArray(data);
				for(int i=0;i<jsArray.length();i++){
					org.json.JSONObject jsObj=jsArray.getJSONObject(i);
					if(pcoId!=null&&!pcoId.isEmpty()){
						pcoId=pcoId+","+jsObj.getInt("id");
					}else{
						int pcId=jsObj.getInt("id");
						pcoId=Integer.toString(pcId);
					}
				}
				
				String Query="update tbl_store_indent_products set "
						+ "status='"+status+"' where sno in ("+pcoId+")";
				System.out.println(Query);
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");
				}
			} catch (SQLException e) {
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please Select The Check Box");
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
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	/*
	 * Approved status API
	 */
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getInvntoryApprovedstatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getInvntoryApprovedstatus(@FormParam("id") String id,@FormParam("indent_id") String indent_id){
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
			
			System.out.println(role_id);
		}
//		String Query ="select id,product_name,product_unit,product_quantity "
//				+ "from inventory_received_order_from_warehouse where product_status='Approved' "
//				+ "and inventory_id='"+inventory_id+"'";
		
		String Query="select tbl_store_indent_products.sno,tbl_products.product_name,"
        + "product_unit.product_unit,tbl_store_indent_products.issued_qty as quantity "
		+ "from tbl_store_indent_products "
        + "inner join tbl_products on tbl_store_indent_products.product_id=tbl_products.product_id "
        + "inner join product_unit on product_unit.id=tbl_products.product_unit "
        + "where status='Approved' "
	    + "and indent_id='"+indent_id+"'";
		
		
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/inventoryProductStock")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response inventoryProductStock(@FormParam("id") String id,@FormParam("store_id") String store_id){
		 JSONObject jsonObject=new JSONObject();
		 /*Just for testing purpose I am commenting these session code */
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
		 if(role_id==1){

		 }else{

		 store_id=auth_id;
		 }
		 }
		 
//		String Query ="SELECT tbl_products.product_name||'-'||tbl_brand.brand_name as product_description,tbl_products.product_unit, "
//				+ " tbl_store.store_name, store_stockdetails.total_stock, store_stockdetails.in_stock, store_stockdetails.out_stock "
//				+ " FROM tbl_store  inner join store_stockdetails on store_stockdetails.store_id=tbl_store.store_id "
//				+ " inner join tbl_products on store_stockdetails.product_id=tbl_products.product_id  "
//				+ " inner join tbl_brand on tbl_brand.brand_id=tbl_products.product_brand_id  "
//				+ " where tbl_store.store_id='"+store_id+"'";
		
//		String Query ="select distinct tbl_store_indent_products.indent_id,tbl_store.store_name,"
//				+ "tbl_warehouse1.wareouse_name as warehouse_name,"
//    + "tbl_products.product_name||'-'||product_unit.product_unit as product_description,"
//    + "tbl_store_indent_products.raised_qty as total_stock,tbl_store_indent_products.remaining_qty as out_stock "
//    + "from tbl_store_indent_products "
//    + "inner join tbl_store on tbl_store_indent_products.store_id=tbl_store.store_id "
//    + "inner join tbl_warehouse1 on tbl_store_indent_products.warehouse_id=tbl_warehouse1.warehouse_id "
//    + "inner join tbl_products on tbl_store_indent_products.product_id=tbl_products.product_id "
//    + "inner join product_unit on tbl_products.product_unit=product_unit.id "
//    + "where tbl_store_indent_products.store_id='"+store_id+"'";
		
		
		String Query ="select tbl_store.store_name,tbl_products.product_name||'-'||product_unit.product_unit as product_description,"
    + " COALESCE(store_stockdetails.total_stock,0)as remaining_stock,(select coalesce( (select total_stock from store_stockdetails  as ss where ss.product_id=store_stockdetails.product_id "
              + " and ss.store_id=store_stockdetails.store_id and ss.createdon::date < current_date order by createdon desc limit 1) ,"
              + "  0 ))as openingbalance,"
              + "  (select coalesce( (select sum(out_stock) from store_stock_flow  as ss where ss.product_id=store_stockdetails.product_id "
              + "  and ss.store_id=store_stockdetails.store_id and ss.created_date::date=current_date ),"
              + " 0 ))as issued_stock,"
              + " (select coalesce( (select sum(in_stock) from store_stock_flow  as ss where ss.product_id=store_stockdetails.product_id "
              + "   and ss.store_id=store_stockdetails.store_id and ss.created_date::date=current_date ) ,"
              + " 0 ))as received_stock "
              + "from store_stockdetails "
              + "inner join tbl_store on tbl_store.store_id=store_stockdetails.store_id "
              + "inner join tbl_products on tbl_products.product_id=store_stockdetails.product_id "
              + " inner join product_unit on product_unit.id=tbl_products.product_unit "
              + "where store_stockdetails.store_id='"+store_id+"'";
		
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
				boolean b=resultset != null;
				System.out.println("resultset is"+ b);
	
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null && !arrayList.isEmpty()){
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
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	/*
	 * Rejected status API
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getInvntoryRejectedstatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getInvntoryRejectedstatus(@FormParam("id") String id,@FormParam("indent_id") String indent_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		//String Query ="select id,product_name,product_unit,product_quantity from inventory_received_order_from_warehouse where product_status='Rejected' and inventory_id='"+inventory_id+"'";
		
		String Query="select tbl_store_indent_products.sno,tbl_products.product_name,"
		        + "product_unit.product_unit,tbl_store_indent_products.issued_qty as quantity "
				+ "from tbl_store_indent_products "
		        + "inner join tbl_products on tbl_store_indent_products.product_id=tbl_products.product_id "
		        + "inner join product_unit on product_unit.id=tbl_products.product_unit "
		        + "where status='Rejected' "
			    + "and indent_id='"+indent_id+"'";
		
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
	/*
	 * total Approved List status API
	 */
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/TotalApprovedList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response TotalApprovedList(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)

					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select id,product_name,product_unit,product_quantity from inventory_received_order_from_warehouse where product_status='Approved' and store_id='"+store_id+"'";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/storeProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response ProductUnitdropdwn(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		Connection connectionPu=null;
		try{
			String email=(String) jsObject.get("email");
			connectionPu = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionPu.prepareStatement(Config.store_products_list);
		preparedStatement.setString(1,store_id);
			@SuppressWarnings("unused")
			ResultSet resultSet=preparedStatement.executeQuery();
			ArrayList arrayList = convertResultSetIntoArrayList(resultSet);
			jsonObject.clear();
			jsonObject.put("status", "Success");
			jsonObject.put("Data", arrayList);
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "List is Empty ");
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
				.header("Access-Control-Allow-Methods", "POST").build();
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
	 * public static String getId(String id){
	 * 
	 * String numberrange_function="select numberrange_function('"+id+"')";
	 * 
	 * int Rid=0;
	 * 
	 * try{ System.out.println(numberrange_function); CallableStatement
	 * CALLABLESTATMENT=connection.prepareCall(numberrange_function);
	 * 
	 * CALLABLESTATMENT.execute();
	 * 
	 * ResultSet resultset=CALLABLESTATMENT.getResultSet();
	 * 
	 * while(resultset.next()){
	 * 
	 * Rid=resultset.getInt(1);
	 * 
	 * }if(Rid<0){
	 * 
	 * return "Failed 0";
	 * 
	 * }
	 * 
	 * System.out.println("Rid :"+Rid);
	 * 
	 * String ReqId=id+"000";
	 * 
	 * String s=String.valueOf(Rid);
	 * 
	 * ReqId=ReqId.substring(0,ReqId.length()-s.length());
	 * 
	 * ReqId=ReqId.concat(String.valueOf(Rid));
	 * 
	 * return ReqId;
	 * 
	 * }catch(Exception exc){
	 * 
	 * System.out.println(exc.getMessage());
	 * 
	 * return "Failed";
	 * 
	 * } }
	 */

	/*
	 * public static String getInventoryId(String id){
	 * 
	 * String
	 * numberrange_function="select inventory_id_numberrange_function('"+id+"')";
	 * 
	 * int Rid=0;
	 * 
	 * try{ System.out.println(numberrange_function); CallableStatement
	 * CALLABLESTATMENT=connection.prepareCall(numberrange_function);
	 * 
	 * CALLABLESTATMENT.execute();
	 * 
	 * ResultSet resultset=CALLABLESTATMENT.getResultSet();
	 * 
	 * while(resultset.next()){
	 * 
	 * Rid=resultset.getInt(1);
	 * 
	 * }if(Rid<0){
	 * 
	 * return "Failed 0";
	 * 
	 * }
	 * 
	 * System.out.println("Rid :"+Rid);
	 * 
	 * String ReqId=id+"000";
	 * 
	 * String s=String.valueOf(Rid);
	 * 
	 * ReqId=ReqId.substring(0,ReqId.length()-s.length());
	 * 
	 * ReqId=ReqId.concat(String.valueOf(Rid));
	 * 
	 * return ReqId;
	 * 
	 * }catch(Exception exc){
	 * 
	 * System.out.println(exc.getMessage());
	 * 
	 * return "Failed";
	 * 
	 * } }
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			System.out.println("Inside convert resultset into arraylist() function total rows "+total_rows);
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("price")||columnName.contains("current_price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					System.out.println("column value-- "+columnValue);
					map.put(columnName,columnValue);
				}
				else{
					Object columnValue = resultSet.getObject(i + 1);
					System.out.println("column value-- "+columnValue);
					map.put(columnName,columnValue);
				}
				//Object columnValue = resultSet.getObject(i + 1);
			}
			resultsetArray.add(map);
			System.out.println("resultsetArray1 "+resultsetArray);
		}
		System.out.println("resultsetArray 2 "+resultsetArray);
		return resultsetArray;
	}
	
	public static ArrayList convertResultSetIntoArrayLists1(ResultSet resultSet) throws Exception {
		ArrayList<Map> resultsetArray=new ArrayList(); 
		Map map=new HashMap();
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			System.out.println("total_rows "+total_rows);
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				map.put(columnName,columnValue);
				System.out.println("column name "+columnName); 
				System.out.println("columnValue "+columnValue);
				}
				//Object columnValue = resultSet.getObject(i + 1);
			resultsetArray.add(map);
			System.out.println("resultsetArray1 "+resultsetArray);
			}
		return resultsetArray;
	}
	
	
}
