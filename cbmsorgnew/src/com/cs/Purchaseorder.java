package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;


import com.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.main.MyObject;
import com.hr.utils.MessageConfig;
import com.itextpdf.text.log.SysoLogger;



@Path("purchaselist")

public class Purchaseorder {

	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(Purchaseorder.class);

	/*
	 Insert purchaseorder Return api
	 */
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/PurchaseOrderReturn")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response Purchaseorderreturn(@FormParam("id") String id,@FormParam("purchaseorder_returns_date") String purchaseorder_returns_date,@FormParam("warehouse_name") String warehouse_name,@FormParam("vendor_name")
//	String vendor_name,@FormParam("note_or_reason") String note_or_reason,@FormParam("products") String products,@FormParam("shipping_address") String shipping_address){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			String purchaseorderreturnid= getProductId("PUR");
//			System.out.println(purchaseorderreturnid);
//			org.json.JSONArray jsArray = new org.json.JSONArray(products);
//			for(int i=0;i<jsArray.length();i++){
//				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//				String productname =orgjsObject.getString("productname");
//				String unit =orgjsObject.getString("unit");
//				int qunatity = orgjsObject.getInt("qunatity");
//				String Query="insert into purchase_order_returns(purchaseorder_returns_id,purchaseorder_returns_date,warehouse_name,vendor_id,product_description,product_unit,product_quantity,note_or_reason,shipping_address) values('"+purchaseorderreturnid+"','"+purchaseorder_returns_date+"','"+warehouse_name+"','"+vendor_name+"','"+productname+"','"+unit+"','"+qunatity+"','"+note_or_reason+"','"+shipping_address+"')";
//				
//				int st=statement.executeUpdate(Query);
//				if(st>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
//				}
//			}
//		}catch (SQLException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		} catch (Exception e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Please try again");
//			 jsonObject.put("error", e.getMessage());
//			 System.out.println();
//		}
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	/*
//	 get purchase Order return api
//	 */
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getPurchaseReturnList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response purchasereturnList(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		String subQuery=new String();
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//			if(role_id==4){
//				subQuery="where purchase_order_returns.warehouse_name=(select warehouse_id from tbl_warehouse where warehouse_contact_email='"+jsObject.get("email")+"')";
//			}else if(role_id==1){
//				subQuery=" ";
//			}
//		}
//		String Query ="select distinct purchase_order_returns.purchaseorder_returns_date,purchase_order_returns.shipping_address,purchase_order_returns.purchaseorder_returns_id,"
//				+ "purchase_order_returns.purchaseorder_referencenumber,purchase_order_returns.warehouse_name as warehouse_id,"
//				+ "tbl_warehouse.warehouse_name,purchase_order_returns.vendor_id,tbl_vendor.vendor_name from purchase_order_returns "
//				+ "inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order_returns.warehouse_name "
//				+ "inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order_returns.vendor_id"+subQuery;
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	/*
//	 Edit purchase order return
//	 */
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editPurchaseOrderReturn")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editPurchaseOrderReturn(@FormParam("id") String id,@FormParam("purchaseorder_returns_id") String purchaseorder_returns_id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || purchaseorder_returns_id==null || purchaseorder_returns_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String Query="select purchase_order_returns.id,purchase_order_returns.shipping_address,purchase_order_returns.purchaseorder_returns_id,purchase_order_returns.purchaseorder_returns_date,purchase_order_returns.note_or_reason,purchase_order_returns.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,purchase_order_returns.vendor_id,tbl_vendor.vendor_name,product_description,product_unit,product_quantity from purchase_order_returns inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order_returns.warehouse_name inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order_returns.vendor_id where purchaseorder_returns_id="+"'"+purchaseorder_returns_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/viewPurchaseReturnList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response viewPurchaseReturnList(@FormParam("id") String id,@FormParam("purchaseorder_returns_id") String purchaseorder_returns_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		String subQuery=new String();
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
////			if(role_id==4){
////				subQuery="where purchase_order_returns.warehouse_name=(select warehouse_id from tbl_warehouse where warehouse_contact_email='"+jsObject.get("email")+"')";
////			}else if(role_id==1){
////				subQuery=" ";
////			}
//		}
//		String Query ="select purchase_order_returns.purchaseorder_returns_id,purchase_order_returns.purchaseorder_returns_date,purchase_order_returns.note_or_reason,purchase_order_returns.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,purchase_order_returns.vendor_id,tbl_vendor.vendor_name,product_description,product_unit,product_quantity from purchase_order_returns inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order_returns.warehouse_name inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order_returns.vendor_id where purchaseorder_returns_id="+"'"+purchaseorder_returns_id+"'";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updatepurchaseOrderReturn")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateProductList(@FormParam("token") String token,@FormParam("purchaseorder_returns_id") String purchaseorder_returns_id,
//			@FormParam("purchaseorder_returns_date") String purchaseorder_returns_date,@FormParam("warehouse_name") String warehouse_name,
//			@FormParam("warehouse_id") String warehouse_id,@FormParam("vendor_id") String vendor_id,
//			@FormParam("vendor_name") String vendor_name,@FormParam("note_or_reason") String note_or_reason,
//			@FormParam("customer_name") String customer_name,@FormParam("products") String products){
//		JSONObject jsonObject=new JSONObject();
//		
////		if(warehouse_name==null || warehouse_name.isEmpty()|| vendor_name==null || vendor_name.isEmpty()|| product_description==null || product_description.isEmpty()
////				|| product_unit==null || product_unit.isEmpty()|| product_quantity==null || product_quantity.isEmpty()|| note_or_reason==null || note_or_reason.isEmpty()
////				|| customer_name==null || customer_name.isEmpty()) {
////			jsonObject.clear();
////			jsonObject.put("status", "Failed");
////			jsonObject.put("message",  "Fields are empty");
////			return Response.ok()
////					.entity(jsonObject)
////					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
////		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(token);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Origin", "*")
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();		
//		
//			try {
//				
//				org.json.JSONArray jsArray = new org.json.JSONArray(products);
//				for(int i=0;i<jsArray.length();i++){
//					org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//					String product_description =orgjsObject.getString("product_description");
//					String product_unit        =orgjsObject.getString("product_unit");
//					int product_quantity       =orgjsObject.getInt("product_quantity");
//					int id                     =orgjsObject.getInt("idr");
//				
//				String Query="UPDATE purchase_order_returns SET purchaseorder_returns_date='"+purchaseorder_returns_date+"',product_description='"+product_description+"',product_unit='"+product_unit+"',product_quantity='"+product_quantity+"',note_or_reason='"+note_or_reason+"',customer_name='"+customer_name+"' where id='"+id+"'";
//				
////				warehouse_name='"+ warehouse_id+"',"
////						+ "vendor_id='"+vendor_id+"',
//				int st=statement.executeUpdate(Query);
//				if(st>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	/*
//	 Delete Purchase Order Api
//	 */
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/deletepurchaseorderreturn")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response deletepurchaseorderreturn(@FormParam("id") String id,@FormParam("purchaseorder_returns_id") String purchaseorder_returns_id){
//		JSONObject jsonObject=new JSONObject();
//		if(purchaseorder_returns_id==null || purchaseorder_returns_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Origin", "*")
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();		
//			try {
//				String Query="DELETE from purchase_order_returns where purchaseorder_returns_id='"+purchaseorder_returns_id+"'";
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
	/*
	 Insert purchase list api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/generatePurcaseOrderId")
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
				String purchaseorderid= Customer.getGenerateId("PUR",8,connection);
				System.out.println("the value is :"+purchaseorderid);
				if(purchaseorderid!=null&&!purchaseorderid.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", purchaseorderid);
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
	@Path("/generatePurcaseReturnOrderId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatePurcaseReturnOrderId(@FormParam("id") String id){
//		,@FormParam("warehouse_name") String warehouse_name
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
//			if(role_id==4){
//				
//			}
			System.out.println(role_id);	
		}
		try {
			SqlConnection();
			try {
				String ProductReturnId=Customer.getGenerateId("PURET",8,connection);
				System.out.println("the value is :"+ProductReturnId);
				if(ProductReturnId!=null&&!ProductReturnId.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", ProductReturnId);
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

//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/PurchaseOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response Purchaselist(@FormParam("id") String id,@FormParam("purchaseorder_date") String purchaseorder_date,@FormParam("warehouse_name") String warehouse_name,@FormParam("vendor_name")
//	String vendor_name,@FormParam("shipping_address") String shipping_address,@FormParam("products") String products,@FormParam("delivery_date") String delivery_date,
//	@FormParam("purchaseorder_id") String purchaseorder_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			//String purchaseorderid= getProductId("PUR");
//			String purchaseorder_referenceid= getReferenceId("REF");
//			org.json.JSONArray jsArray = new org.json.JSONArray(products);
//			for(int i=0;i<jsArray.length();i++){
//				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//				String productname =orgjsObject.getString("productname");
//				
//				String[] parts = productname.split("-");
//				//String lastpart = parts[4];
//				//String[] bits = one.split("-");
//				String lastpart = parts[parts.length-1];
//				
//				System.out.println("the value is:" +lastpart);
//
//				String productUnit =orgjsObject.getString("unit");
//				int productQuantity = orgjsObject.getInt("qunatity");
//				String Query="insert into purchase_order(purchaseorder_date,warehouse_name,purchaseorder_id,vendor_id,shipping_address,product_description,product_unit,product_quantity,purchaseorder_referencenumber,delivery_date,productid)"
//						+ " values('"+purchaseorder_date+"','"+warehouse_name+"','"+purchaseorder_id+"','"+vendor_name+"','"+shipping_address+"','"+productname+"','"+productUnit+"','"+productQuantity+"','"+purchaseorder_referenceid+"','"+delivery_date+"','"+lastpart+"')";
//				int st=statement.executeUpdate(Query);
//				if(st>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
//				}
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Please try again");
//			 jsonObject.put("error", e.getMessage());
//		}
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/warehousePurchaseOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response warehousePurchaseOrder(@FormParam("id") String id,@FormParam("purchaseorder_date") String purchaseorder_date,@FormParam("warehouse_name") String warehouse_name,@FormParam("vendor_name")
//	String vendor_name,@FormParam("shipping_address") String shipping_address,@FormParam("products") String products,@FormParam("delivery_date") String delivery_date,
//	@FormParam("purchaseorder_id") String purchaseorder_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			//String purchaseorderid= getProductId("PUR");
//			String purchaseorder_referenceid= getReferenceId("REF");
//			org.json.JSONArray jsArray = new org.json.JSONArray(products);
//			for(int i=0;i<jsArray.length();i++){
//				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//				String productname =orgjsObject.getString("productname");
//				
//				String[] parts = productname.split("-");
//				//String lastpart = parts[4];
//				//String[] bits = one.split("-");
//				String lastpart = parts[parts.length-1];
//				
//				System.out.println("the value is:" +lastpart);
//
//				String productUnit =orgjsObject.getString("unit");
//				int productQuantity = orgjsObject.getInt("qunatity");
//				String Query="insert into purchase_order(purchaseorder_date,warehouse_name,purchaseorder_id,vendor_id,shipping_address,product_description,product_unit,product_quantity,purchaseorder_referencenumber,delivery_date,productid)"
//						+ " values('"+purchaseorder_date+"','"+warehouse_name+"','"+purchaseorder_id+"','"+vendor_name+"','"+shipping_address+"','"+productname+"','"+productUnit+"','"+productQuantity+"','"+purchaseorder_referenceid+"','"+delivery_date+"','"+lastpart+"')";
//				int st=statement.executeUpdate(Query);
//				if(st>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
//				}
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Please try again");
//			 jsonObject.put("error", e.getMessage());
//		}
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
	
//	/*
//	 get purchase list api
//	 */
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getPurchaseList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response productsList(@FormParam("token") String id){
////		,@FormParam("warehouse_name") String warehouse_name
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);String subQuery=new String();
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
////			if(role_id==4){
////				
////			}
//			System.out.println(role_id);
//			if(role_id==4){
//				subQuery="where purchase_order.warehouse_name=(select warehouse_id from tbl_warehouse where warehouse_contact_email='"+jsObject.get("email")+"')";
//			}else if(role_id==1){
//				subQuery=" ";
//			}
//			else{
//				jsonObject.clear();
//				jsonObject.put("status", "Failed");
//				jsonObject.put("message", "You don't have access");
//				return Response.ok()
//						.entity(jsonObject)
//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//			}
//		}
//		//String Query ="select * from purchase_order";
//		String Query ="select distinct purchase_order.purchaseorder_date,purchase_order.purchaseorder_id,purchase_order.delivery_date,purchase_order.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,purchase_order.vendor_id,tbl_vendor.vendor_name from purchase_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order.warehouse_name inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order.vendor_id where purchaseorder_status='Pending' order by purchase_order.purchaseorder_date";
//				//+ " where purchase_order.warehouse_name=(select warehouse_id from tbl_warehouse where warehouse_contact_email='"+jsObject.get("email")+"')";
//
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	/*
//	 Edit PurchaseOrder Api
//	 */
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editpurchaseOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editpurchaseorder(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("token") String token){
//		JSONObject jsonObject=new JSONObject();
//		if(purchaseorder_id==null || purchaseorder_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(token);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//	//	String Query="select * from purchase_order where purchaseorder_id="+"'"+purchaseorder_id+"'";
//		String Query="select purchase_order.id,purchase_order.purchaseorder_date,purchase_order.billing_address,"
//				+ "purchase_order.shipping_address,purchase_order.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,"
//				+ "purchase_order.vendor_id,tbl_vendor.vendor_name,purchase_order.product_description,purchase_order.product_unit,purchase_order.product_quantity,"
//				+ "purchase_status from purchase_order "
//				+ "inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order.warehouse_name "
//				+ "inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order.vendor_id "
//				+ "where purchase_order.purchaseorder_id="+"'"+purchaseorder_id+"'";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/viewPurchaseOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response viewPurchaseOrder(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id){
//		JSONObject jsonObject=new JSONObject();
//		if(purchaseorder_id==null || purchaseorder_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//	//	String Query="select * from purchase_order where purchaseorder_id="+"'"+purchaseorder_id+"'";
//		String Query="select purchase_order.id,purchase_order.purchaseorder_date,purchase_order.billing_address,purchase_order.shipping_address,purchase_order.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,purchase_order.vendor_id,tbl_vendor.vendor_name,product_description,product_unit,product_quantity,purchase_status from purchase_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order.warehouse_name inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order.vendor_id where purchaseorder_id="+"'"+purchaseorder_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
	/*
	 Update Purchase Order Api
	 */
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updatepurchaseorder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updatepurchase(@FormParam("token") String token,@FormParam("products") String products,@FormParam("purchaseorder_date") String purchaseorder_date,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("warehouse_id") String warehouse_id
//			,@FormParam("vendor_id")String vendor_id,@FormParam("billing_address") String billing_address,@FormParam("shipping_address") String shipping_address,@FormParam("warehouse_name")String warehouse_name,@FormParam("vendor_name")String vendor_name){
//		JSONObject jsonObject=new JSONObject();
////		if(purchaseorder_id==null || purchaseorder_id.isEmpty()|| purchaseorder_date==null || purchaseorder_date.isEmpty()|| warehouse_name==null || warehouse_name.isEmpty()
////				|| vendor_name==null || vendor_name.isEmpty()|| billing_address==null || billing_address.isEmpty()|| products==null || products.isEmpty()) {
////			jsonObject.clear();
////			jsonObject.put("status", "Failed");
////			jsonObject.put("message",  "Fields are empty");
////			return Response.ok()                                             
//		
////		@FormParam("id") String id,
//
////					.entity(jsonObject)
////					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
////		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(token);
//		System.out.println("jsObject : "+jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		try {
//			SqlConnection();
//			try {
//				org.json.JSONArray jsArray = new org.json.JSONArray(products);
//				for(int i=0;i<jsArray.length();i++){
//					org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//					String product_description =orgjsObject.getString("product_description");
//					String product_unit        =orgjsObject.getString("product_unit");
//					int product_quantity       =orgjsObject.getInt("product_quantity");
//					int id                     =orgjsObject.getInt("idr");
////					String purchaseorder_date  =orgjsObject.getString("purchaseorder_date");
////					String purchaseorder_id =orgjsObject.getString("purchaseorder_id");
////					String warehouse_name = orgjsObject.getString("warehouse_name");
////					String vendor_name = orgjsObject.getString("vendor_name");
////					String billing_address = orgjsObject.getString("billing_address");
//					
//		
//				String Query="UPDATE purchase_order SET purchaseorder_date='"+purchaseorder_date+"',"
//						+ "billing_address='"+billing_address+"',shipping_address='"+shipping_address+"',product_description='"+product_description+"',product_unit='"+product_unit+"',product_quantity='"+product_quantity+"' where id='"+id+"'";
//				int st=statement.executeUpdate(Query);
////				,warehouse_name='"+ warehouse_id+"',"
////+ "vendor_id='"+vendor_id+"'
//				if(st>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	/*
//	 Delete Purchase Order Api
//	 */
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/deletepurchaseorder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response deletepurchaseorder(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id){
//		JSONObject jsonObject=new JSONObject();
//		if(purchaseorder_id==null || purchaseorder_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Origin", "*")
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();		
//			try {
//				String Query="DELETE from purchase_order where purchaseorder_id='"+purchaseorder_id+"'";
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	

	/*
	  ware house dropdow api in purchase
	 
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/purchasewarehousedropdown")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchasewarehouseDropdown(@FormParam("id") String id){
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
		String Query ="select warehouse_id,warehouse_name from tbl_warehouse";
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
	  Vendore Name dropdow api in purchase
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/purchasevendorNamedropdown")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchaseVendorDropdown(@FormParam("id") String id){
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
		String Query ="select vendor_id,vendor_name  from tbl_vendor";
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
	  Search for product Names api in purchase
	 
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/productNamesSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productNamesSearch(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
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
//		String Query ="select tbl_products.product_name,tbl_category.category_name,tbl_subcategory.subcategory_name,"
//				+ "tbl_brand.brand_name,tbl_products.product_id,tbl_products.product_unit,"
//				+ "tbl_vendor_pricing_table.product_price,tbl_vendor_pricing_table.product_cgst, "
//				+ "tbl_vendor_pricing_table.product_sgst,tbl_vendor_pricing_table.product_igst from tbl_products "
//				+ "inner join tbl_vendor_pricing_table on tbl_vendor_pricing_table.product_id=tbl_products.product_id "
//				+ "inner join tbl_category on tbl_products.product_category_id= tbl_category.category_id "
//				+ "inner join tbl_subcategory on tbl_products.product_subcategory_id=tbl_subcategory.subcategory_id "
//				+ "inner join tbl_brand on tbl_products.product_brand_id=tbl_brand.brand_id "
//				+ "where tbl_products.mark_for_deletion=0 and tbl_products.vendor_id like '%"+vendor_id+"%'";
		

		String Query="select distinct tbl_vendor_pricing_table.product_id,"
				+ "tbl_products.product_name||'-'||tbl_brand.brand_name||'-'|| "
				+ "product_unit.product_unit||'-'||tbl_vendor_pricing_table.product_id "
				+ "as product_description from tbl_vendor_pricing_table "
				+ "inner join tbl_products on tbl_products.product_id=tbl_vendor_pricing_table.product_id "
				+ "inner join product_unit on product_unit.id=tbl_vendor_pricing_table.product_unit "
				+ "inner join tbl_brand on tbl_vendor_pricing_table.product_brand_id=tbl_brand.brand_id "
				+ "where tbl_vendor_pricing_table.vendor_id='"+vendor_id+"' and "
				+ "tbl_vendor_pricing_table.mark_for_deletion=0";

		System.out.println("the query is:" +Query);
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
					
					
					//System.out.println(arrayList);
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
	
	@POST
	@Path("/purchaseorderreport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchseReport(MyObject json) {
		Response response=null; 
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		Connection conn = null;
		JSONObject object= new JSONObject();
		Object responseObject=new JSONObject();
		String sessionid = json.getSessionid();
		String Warehouse_id=json.getWarehouse_id();
		String vendor_id=json.getVendor_id();
		String purchaseorder_raised_date=json.getPurchaseorder_raised_date();
		String from_date=json.getFrom_date();
		try{
				conn = DBConnection.SqlConnection();
				System.out.println("connection: "+conn);
				JSONObject jsObject=TokenCheck.checkTokenStatus(sessionid);
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
		String query="  select distinct on (twpo.purchaseorder_id,twpo.purchase_requisition_id, twpo.purchaseorder_raised_date) twpo.purchaseorder_id,twpo.purchase_requisition_id, twpo.purchaseorder_raised_date,twpo.created_date,twpo.delivery_date, twpo.purchaseorder_status,tv.vendor_name,tw.wareouse_name from tbl_warehouse_purchase_order twpo inner join tbl_vendor1 tv on tv.vendor_id= twpo.vendor_id inner join tbl_warehouse1 tw on tw.warehouse_id= twpo.warehouse_id where(tw.Warehouse_id='"+ Warehouse_id +"' or tv.vendor_id='"+vendor_id+"') and twpo.purchaseorder_raised_date between '"+purchaseorder_raised_date+"' and '"+from_date+"'";
					PreparedStatement statement = conn.prepareStatement(query);
					System.out.println(statement);
					ResultSet resultset = statement.executeQuery();
			        ArrayList result_Array=convertResultSetIntoArrayList(resultset);
			        System.out.println("result_Array: "+result_Array);
			        if(result_Array.contains("[]")){
			        	responseObject=HelperClass.generateResponce(201, "List is empty", "Failed");
			        }else{
//			        	object.put("Status", 200);
//			        	object.put("Data",result_Array);
			        	responseObject=HelperClass.generateResponce(200, result_Array, "Success");
			        }
			        System.out.println("object: "+responseObject);
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e.getMessage());
        	responseObject=HelperClass.generateResponce(202, "Try again", "Failed");

		}
		finally {
			try {
			conn.close();
				
			} catch (SQLException e) {
				log.error(e.getMessage());
	        	responseObject=HelperClass.generateResponce(202, "Something went wrong", "Failed");

			}
		}
		response=HelperClass.convertObjectToResponce(responseObject,200);
		return response;
}	

	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/productData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productData(@FormParam("id") String id,
			@FormParam("product_description") String product_description,
			@FormParam("vendor_id") String vendor_id){
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
		System.out.println(product_description);
		String[] parts = product_description.split("-");
		String product_id = parts[parts.length-1];
		
		String Query ="select distinct discount,product_price,product_cgst,"
				+ "product_sgst,product_igst from tbl_vendor_pricing_table "
				+ "where tbl_vendor_pricing_table.product_id='"+product_id+"' "
				+ "and vendor_id='"+vendor_id+"' and mark_for_deletion=0";
		
		System.out.println("the query is:" +Query);
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
					
					
					//System.out.println(arrayList);
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
	@Path("/vendorBasedProductSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorBasedProductSearch(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
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
		String Query ="select product_description from tbl_vendor where vendor_id='"+vendor_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				//arrayList.contains("\\");
				arrayList.remove("\\");
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
					//System.out.println(arrayList);
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
	@Path("/viewPurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response viewproductsList(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id){
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
		String Query ="select * from purchase_order where purchaseorder_id='"+purchaseorder_id+"'";
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
	@Path("/approvePurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response approvePurchaseList(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id){
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
		String Query ="select purchase_order.id,tbl_generate_invoice.purchase_id,tbl_generate_invoice.invoice_id,tbl_generate_invoice.total_value,"
				+ "purchase_order.purchaseorder_date,purchase_order.billing_address,purchase_order.shipping_address,purchase_order.product_description,"
				+ "purchase_order.product_unit,purchase_order.product_quantity,purchase_order.purchase_status,purchase_order.product_total,purchase_order.paid,"
				+ "purchase_order.payment_status,purchase_order.cgst,purchase_order.igst,purchase_order.sgst,purchase_order.discount,purchase_order.manufacturingdate,"
				+ "purchase_order.expirydate,purchase_order.price,"
				+ "tbl_generate_invoice.total_value,tbl_generate_invoice.total_discount,tbl_generate_invoice.total_tax,tbl_generate_invoice.total from tbl_generate_invoice "
				+ "inner join purchase_order on tbl_generate_invoice.purchase_id=purchase_order.purchaseorder_id where invoice_id='"+invoice_id+"' and purchase_order.purchaseorder_status='Approved'";
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
	@Path("/warehouseRejectedPurchaseOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response rejectedPurchaseList(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id){
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
			
		}
		String Query ="select tbl_generate_invoice1.purchaseorder_id,tbl_generate_invoice1.invoice_id,tbl_generate_invoice1.total_value,"
				+ "tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.return_qty,"
				+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
				+ "tbl_warehouse_purchase_order.product_description,"
				+ "tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,"
				+ "tbl_warehouse_purchase_order.purchaseorder_status,tbl_warehouse_purchase_order.product_total,tbl_warehouse_purchase_order.cgst,"
				+ "tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.sgst,tbl_warehouse_purchase_order.discount,"
				+ "tbl_warehouse_purchase_order.manufacture_date,"
				+ "tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,"
				+ "tbl_generate_invoice1.total_value,tbl_generate_invoice1.total_discount,tbl_generate_invoice1.total_tax,"
				+ "tbl_generate_invoice1.total from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "where tbl_warehouse_purchase_order.return_qty>0 and tbl_generate_invoice1.return_status='Pending' "
				+ "and tbl_generate_invoice1.invoice_id ='"+invoice_id+"'";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
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
	@Path("/invoiceBasedApprovedWarehousePurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response invoiceBasedApprovedWarehousePurchaseList(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		PreparedStatement statement=null;
		ResultSet resultset=null;
		Connection conn=null;
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
				conn=SqlConnection();
				String Query ="select tbl_warehouse_purchase_order.id,tbl_generate_invoice1.purchaseorder_id,tbl_generate_invoice1.invoice_id,tbl_generate_invoice1.total_value,"
						+"tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,tbl_warehouse_purchase_order.product_description,"
						+"tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,tbl_warehouse_purchase_order.approved_qty,tbl_warehouse_purchase_order.product_total,"
						+"tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.sgst,tbl_warehouse_purchase_order.discount,tbl_warehouse_purchase_order.manufacture_date,"
						+"tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,tbl_warehouse_purchase_order.product_id,tbl_warehouse_purchase_order.isbarcode_generated,"
						+"tbl_generate_invoice1.total_value,tbl_generate_invoice1.total_discount,tbl_generate_invoice1.total_tax,tbl_generate_invoice1.total from tbl_generate_invoice1 "
						+"inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id where tbl_warehouse_purchase_order.invoice_id='"+invoice_id+"'";
				statement=conn.prepareStatement(Query);
				resultset=statement.executeQuery();
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				System.out.println("arrayList: "+arrayList);
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
			} 
//			catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
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
	@Path("/completeApprovedWarehousePurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response completeApprovedPurchaseList(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
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
		String Query ="select tbl_warehouse_purchase_order.id,tbl_warehouse_purchase_order.purchase_requisition_id,tbl_generate_invoice1.purchaseorder_id,"
				+ "tbl_generate_invoice1.invoice_id,tbl_generate_invoice1.total_value,"
				+ "tbl_warehouse_purchase_order.purchaseorder_raised_date,"
				+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.approved_qty,tbl_warehouse_purchase_order.shipping_address,"
				+ "tbl_warehouse_purchase_order.product_description,"
				+ "tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,"
				+ "tbl_warehouse_purchase_order.product_total, tbl_vendor1.vendor_name, "
				+ "tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.igst,"
				+ "tbl_warehouse_purchase_order.sgst,tbl_warehouse_purchase_order.discount,"
				+ "tbl_warehouse_purchase_order.manufacture_date,"
				+ "tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,"
				+ "tbl_warehouse_purchase_order.product_id,tbl_warehouse_purchase_order.isbarcode_generated,"
				+ "tbl_generate_invoice1.total_value,tbl_generate_invoice1.total_discount,"
				+ "tbl_generate_invoice1.total_tax,tbl_generate_invoice1.total from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id = tbl_warehouse_purchase_order.vendor_id "
				+ "where tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println("Query "+Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
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
		//		statement.close();
			//	resultset.close();
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
	@Path("/editCompleteApprovedWarehousePurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editCompleteApprovedPurchaseList(@FormParam("token") String id,@FormParam("product_id") String product_id){
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
		String Query ="select tbl_warehouse_purchase_order.id,tbl_warehouse_purchase_order.purchase_requisition_id,tbl_warehouse_purchase_order.product_id,"
				+ "tbl_generate_invoice1.purchaseorder_id,tbl_generate_invoice1.invoice_id,"
				+ "tbl_generate_invoice1.total_value,tbl_warehouse_purchase_order.vendor_id, "
				+ "tbl_warehouse_purchase_order.purchaseorder_raised_date,"
				+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
				+ "tbl_warehouse_purchase_order.product_description,"
				+ "tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,"
				+ "tbl_warehouse_purchase_order.product_total,tbl_warehouse_purchase_order.cgst,"
				+ "tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.sgst,"
				+ "tbl_warehouse_purchase_order.discount,tbl_warehouse_purchase_order.manufacture_date,"
				+ "tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,"
				+ "tbl_generate_invoice1.total_value,tbl_generate_invoice1.total_discount,"
				+ "tbl_generate_invoice1.total_tax,tbl_generate_invoice1.total from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "where tbl_warehouse_purchase_order.product_id='"+product_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				
				System.out.println("Query "+Query);
				@SuppressWarnings("rawtypes")
			ArrayList arrayList=convertResultSetIntoArrayLists(resultset);	
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
				statement.close();
				resultset.close();
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
	@Path("/monthWisePurchaseOrderSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response monthWisePurchaseOrderSearch(@FormParam("id") String id,@FormParam("month") String month,
			@FormParam("warehouse_id") String warehouse_id){
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
		
		String first =month.substring(0, Math.min(month.length(), 3));
		String cap = first.substring(0, 1).toUpperCase() + first.substring(1);
		System.out.println("the letter is "+cap);
		
		String Query ="select distinct purchaseorder_id,purchaseorder_raised_date "
				+ "from tbl_warehouse_purchase_order "
				+ "where to_char(created_date::timestamp,'Mon')='"+cap+"' "
						+ "and purchaseorder_status='Approved' "
						+ "and warehouse_id='"+warehouse_id+"'";
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
				statement.close();
				resultset.close();
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
	@Path("/purchaseOrderInBetweenDates")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchaseOrderInBetweenDates(@FormParam("id") String id,@FormParam("start_date") String start_date,
			@FormParam("end_date") String end_date,@FormParam("warehouse_id") String warehouse_id){
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
		
		String Query ="select distinct purchaseorder_id,purchaseorder_raised_date "
				+ "from tbl_warehouse_purchase_order "
				+ "where purchaseorder_raised_date >= '"+start_date+"' "
				+ "AND purchaseorder_raised_date <  '"+end_date+"' and "
				+ " warehouse_id='"+warehouse_id+"'";
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
				statement.close();
				resultset.close();
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
				
				//Object columnValue = resultSet.getObject(i + 1);
				map.put(columnName,columnValue);
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayList1(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("cgst")||columnName.contains("sgst")||columnName.contains("igst")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("%")){
						columnValue=columnValue.replace("%","");
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("product_price")||columnName.contains("total_value")||columnName.contains("product_total")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}
				else if(columnName.contains("cgst")||columnName.contains("sgst")||columnName.contains("igst")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("%")){
						columnValue=columnValue.replace("%","");
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
	
	
	private static Connection SqlConnection() throws IOException {
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
		return connection;  
		
	}
	/*
	 * public static String getProductId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_product_id_numberrange_function('"+id+"')";
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
	 * }
	 * 
	 * }
	 */
	/*
	 * public static String getProductReturnId(String id){
	 * 
	 * String numrange_function="select fn_number_range_function('"+id+"')";
	 * 
	 * int Rid=0;
	 * 
	 * try{ System.out.println(numrange_function); CallableStatement
	 * CALLABLESTATMENT=connection.prepareCall(numrange_function);
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
	 * }
	 * 
	 * }
	 */
	
	/*
	 * public static String getReferenceId(String id){
	 * 
	 * String
	 * numberrange_function="select purordreference_id_numberrange_function('"+id+
	 * "')";
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
	 * }
	 * 
	 * }
	 */	
}
