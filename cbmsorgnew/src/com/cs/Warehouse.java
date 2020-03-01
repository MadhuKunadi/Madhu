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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("warehouse")

public class Warehouse {
	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Warehouse.class);
	@Context private static HttpServletRequest request;
	@Context private static HttpServletResponse response;
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/addStockwarehouse")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response addStock(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("name") String name,@FormParam("active") String active,@FormParam("company_id") String company_id,@FormParam("partner_id")
//	String partner_id,@FormParam("view_location_id") String view_location_id,@FormParam("lot_stock_id") String lot_stock_id,@FormParam("wh_input_stock_loc_id") String wh_input_stock_loc_id,@FormParam("wh_qc_stock_loc_id") String wh_qc_stock_loc_id,@FormParam("wh_output_stock_loc_id") String wh_output_stock_loc_id,
//	@FormParam("wh_pack_stock_loc_id") String wh_pack_stock_loc_id,@FormParam("mto_pull_id") String mto_pull_id,@FormParam("pick_type_id") String pick_type_id,@FormParam("out_type_id") String out_type_id,@FormParam("in_type_id") String in_type_id,@FormParam("pack_type_id") String pack_type_id
//	,@FormParam("int_type_id") String int_type_id,@FormParam("crossdock_route_id") String crossdock_route_id,@FormParam("reception_route_id") String reception_route_id,@FormParam("delivery_route_id") String delivery_route_id,@FormParam("default_resupply_wh_id") String default_resupply_wh_id
//	,@FormParam("buy_to_resupply") String buy_to_resupply,@FormParam("buy_pull_id") String buy_pull_id,@FormParam("code") String code,@FormParam("reception_steps") String reception_steps,@FormParam("delivery_steps") String delivery_steps){
//		JSONObject jsonObject=new JSONObject();
//		if(name==null || name.isEmpty()|| active==null || active.isEmpty()
//				|| view_location_id==null || view_location_id.isEmpty()|| buy_to_resupply==null || buy_to_resupply.isEmpty()|| buy_pull_id==null || buy_pull_id.isEmpty()|| wh_input_stock_loc_id==null || wh_input_stock_loc_id.isEmpty()
//				|| wh_pack_stock_loc_id==null || wh_pack_stock_loc_id.isEmpty()|| lot_stock_id==null || lot_stock_id.isEmpty()|| reception_route_id==null || reception_route_id.isEmpty()|| pick_type_id==null || pick_type_id.isEmpty()|| pack_type_id==null || pack_type_id.isEmpty()
//				||int_type_id.isEmpty()|| int_type_id==null|| mto_pull_id==null || mto_pull_id.isEmpty()|| crossdock_route_id==null || crossdock_route_id.isEmpty()|| code==null || code.isEmpty()|| company_id==null || company_id.isEmpty()
//				||reception_steps==null || reception_steps.isEmpty()|| delivery_route_id==null || delivery_route_id.isEmpty()|| in_type_id==null || in_type_id.isEmpty()|| default_resupply_wh_id==null || default_resupply_wh_id.isEmpty()|| partner_id==null || partner_id.isEmpty()
//				|| delivery_steps==null || delivery_steps.isEmpty()|| out_type_id==null || out_type_id.isEmpty()|| wh_qc_stock_loc_id==null || wh_qc_stock_loc_id.isEmpty()|| wh_output_stock_loc_id==null || wh_output_stock_loc_id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//				
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			String warehouseid=getId("VENDOR");
//			try {
//				String Query="insert into stock_warehouse(warehouse_id,name,active,company_id,partner_id,view_location_id,lot_stock_id,code,reception_steps,delivery_steps,wh_input_stock_loc_id,wh_qc_stock_loc_id,wh_output_stock_loc_id,wh_pack_stock_loc_id,mto_pull_id,pick_type_id,pack_type_id,out_type_id,in_type_id,int_type_id,crossdock_route_id,reception_route_id,delivery_route_id,default_resupply_wh_id,buy_to_resupply,buy_pull_id) "
//						+ "values('"+warehouseid+"','"+name+"','"+active+"','"+company_id+"','"+partner_id+"','"+view_location_id+"','"+lot_stock_id+"','"+code+"','"+reception_steps+"','"+delivery_steps+"','"+wh_input_stock_loc_id+"','"+wh_qc_stock_loc_id+"','"+wh_output_stock_loc_id+"','"+wh_pack_stock_loc_id+"','"+mto_pull_id+"','"+pick_type_id+"','"+pack_type_id+"','"+out_type_id+"','"+in_type_id+"','"+int_type_id+"','"+crossdock_route_id+"','"+reception_route_id+"','"+delivery_route_id+"','"+default_resupply_wh_id+"','"+buy_to_resupply+"','"+buy_pull_id+"')";
//				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getStockwarehouse")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getstockwarehouse
//	(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
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
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String Query ="select * from stock_warehouse";
////		String Query="select customer_master.customer_id,customer_master.customer_first_name,customer_master.customer_last_name,customer_master.type,customer_master.phone_no,customer_master.customer_email,customer_master.transaction_type,customer_master.status,customer_credit.bank_account_no,customer_credit.ifsc_code,customer_credit.credit_limit,customer_credit.credit_consumed,customer_credit.credit_available from customer_master,customer_credit  ";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	@Path("/editStockwarehouse")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editStockwarehouse(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || warehouse_id==null || warehouse_id.isEmpty()) {
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from stock_warehouse where warehouse_id="+"'"+warehouse_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	@Path("/updateStockwarehouse")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateStockwarehouse(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("name") String warehouse_name,@FormParam("view_location_id") String view_location,@FormParam("company_id") String company_id,
//	@FormParam("out_type_id") String out_type_id,@FormParam("in_type_id") String in_type_id,
//	@FormParam("receipt_route_id") String receipt_route_id,@FormParam("delivery_route_id") String delivery_route_id,@FormParam("buy_pull_id") String buy_pull_id){
//		JSONObject jsonObject=new JSONObject();
////		if(warehouse_name==null || warehouse_name.isEmpty()
////				|| view_location==null || view_location.isEmpty()|| company_id==null || company_id.isEmpty()|| out_type_id==null || out_type_id.isEmpty()
////				||in_type_id==null || in_type_id.isEmpty() || receipt_route_id==null || receipt_route_id.isEmpty()
////				|| delivery_route_id==null || delivery_route_id.isEmpty()|| buy_pull_id==null || buy_pull_id.isEmpty()) {
////			jsonObject.clear();
////			jsonObject.put("status", "Failed");
////			jsonObject.put("message",  "Fields are empty");
////			return Response.ok()
////					.entity(jsonObject)
////					
////					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
////		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
////		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
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
//				String Query="UPDATE stock_warehouse SET name='"+warehouse_name+"',view_location_id='"+ view_location+"',"
//						+ "company_id='"+company_id+"',out_type_id='"+out_type_id+"',in_type_id='"+in_type_id+"',reception_route_id='"+receipt_route_id+"',delivery_route_id='"+delivery_route_id+"',buy_pull_id='"+buy_pull_id+"' where warehouse_id='"+warehouse_id+"'";
//				
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/addStockorderpoint")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response addStockOrder(@FormParam("id") String id,@FormParam("warehouse_orderpoint_id") String warehouse_orderpoint_id,@FormParam("name") String name,@FormParam("active") String active,@FormParam("warehouse_id") String warehouse_id,@FormParam("company_id")
//	String company_id,@FormParam("location_id") String location_id,@FormParam("product_id") String product_id,@FormParam("product_min_qty") String product_min_qty,@FormParam("product_max_qty") String product_max_qty,@FormParam("qty_multiple") String qty_multiple,
//	@FormParam("group_id") String group_id,@FormParam("lead_type") String lead_type,@FormParam("lead_days") String lead_days){
//		JSONObject jsonObject=new JSONObject();
////		if(name==null || name.isEmpty()|| active==null || active.isEmpty()
////				|| warehouse_id==null || warehouse_id.isEmpty()|| location_id==null || location_id.isEmpty()|| product_id==null || product_id.isEmpty()|| product_max_qty==null || product_max_qty.isEmpty()
////				|| group_id==null || group_id.isEmpty()|| lead_type==null || lead_type.isEmpty()|| lead_days==null || lead_days.isEmpty()|| qty_multiple==null || qty_multiple.isEmpty()|| company_id==null || company_id.isEmpty()
////				|| product_min_qty==null || product_min_qty.isEmpty()){
////			jsonObject.clear();
////			jsonObject.put("status", "Failed");
////			jsonObject.put("message",  "Fields are empty");
////			return Response.ok()
////					.entity(jsonObject)
////				
////					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
////		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//				
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
//				String Query="insert into stock_warehouse_orderpoint(warehouse_orderpoint_id,name,active,company_id,warehouse_id,location_id,product_id,product_min_qty,product_max_qty,qty_multiple,group_id,lead_days,lead_type) "
//						+ "values('"+warehouse_orderpoint_id+"','"+name+"','"+active+"','"+company_id+"','"+warehouse_id+"','"+location_id+"','"+product_id+"','"+product_min_qty+"','"+product_max_qty+"','"+qty_multiple+"','"+group_id+"','"+lead_days+"','"+lead_type+"')";
//				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getStockorderpoint")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getstoctorderline(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
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
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String Query ="select * from stock_warehouse_orderpoint";
////		String Query="select customer_master.customer_id,customer_master.customer_first_name,customer_master.customer_last_name,customer_master.type,customer_master.phone_no,customer_master.customer_email,customer_master.transaction_type,customer_master.status,customer_credit.bank_account_no,customer_credit.ifsc_code,customer_credit.credit_limit,customer_credit.credit_consumed,customer_credit.credit_available from customer_master,customer_credit  ";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editStockOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editStockOrder(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
//		JSONObject jsonObject=new JSONObject();
//		if(warehouse_id==null || warehouse_id.isEmpty()) {
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from stock_warehouse_orderpoint where warehouse_id="+"'"+warehouse_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	@Path("/updateStockOrder")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateStockOrder(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("name") String name,@FormParam("location_id") String location_id,@FormParam("company_id") String company_id,
//	@FormParam("product_id") String product_id,@FormParam("lead_days") String lead_days,
//	@FormParam("qty_multiple") String qty_multiple){
//		JSONObject jsonObject=new JSONObject();
//		if(name==null ||name.isEmpty()
//				|| product_id==null || product_id.isEmpty()|| company_id==null || company_id.isEmpty()|| location_id==null || location_id.isEmpty()
//				||lead_days==null || lead_days.isEmpty() || qty_multiple==null || qty_multiple.isEmpty()
//				) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
////		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
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
//				String Query="UPDATE stock_warehouse_orderpoint SET product_id='"+product_id+"',lead_days='"+lead_days+"',qty_multiple='"+qty_multiple+"',name='"+name+"',location_id='"+location_id+"',company_id='"+company_id+"' where warehouse_id='"+warehouse_id+"'";
//				
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/stockScrap")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response stockScrap(@FormParam("id") String id,@FormParam("product_id") String product_id,@FormParam("location_id") String location_id,@FormParam("scrap_id") String scrap_id){
//		JSONObject jsonObject=new JSONObject();
//		if(product_id==null || product_id.isEmpty()
//				|| location_id==null || location_id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//				
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			@SuppressWarnings("unused")
//			String scrapid = getId("");
//			System.out.println(scrapid);
//			try {
//				String Query="insert into stock_warn_insufficient_qty_scrap(scrap_id,product_id,location_id) "
//						+ "values('"+scrapid+"','"+product_id+"','"+location_id+"')";
//				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getLocationid")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getLocationid(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select view_location_id from stock_warehouse";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", arrayList);
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getStockscrap")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getStockscrap(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
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
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String Query ="select * from stock_warn_insufficient_qty_scrap";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editStockscrap")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editStockscrap(@FormParam("id") String id,@FormParam("scrap_id") String scrap_id){
//		JSONObject jsonObject=new JSONObject();
//		if(scrap_id==null || scrap_id.isEmpty()) {
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from stock_warn_insufficient_qty_scrap where scrap_id="+"'"+scrap_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updateStockscrap")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateStockscrap(@FormParam("id") String id,@FormParam("scrap_id") String scrap_id,@FormParam("product_id") String product_id,@FormParam("location_id") String location_id){
//		if(scrap_id==null || scrap_id.isEmpty()
//				|| product_id==null || product_id.isEmpty()|| location_id==null || location_id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
////		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
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
//				String Query="UPDATE stock_warn_insufficient_qty_scrap SET product_id='"+ product_id+"',"
//						+ "location_id='"+location_id+"' where scrap_id='"+scrap_id+"'";
//				
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
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
//	
//	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
//	public static String getId(String id){
//
//		String numberrange_function="select customer_numberrange_function('"+id+"')";
//
//		int Rid=0;
//
//		try{
//			System.out.println(numberrange_function);
//			CallableStatement CALLABLESTATMENT=connection.prepareCall(numberrange_function);
//
//			CALLABLESTATMENT.execute();
//
//			ResultSet resultset=CALLABLESTATMENT.getResultSet();
//
//			while(resultset.next()){
//
//				Rid=resultset.getInt(1);
//
//			}if(Rid<0){
//
//				return "Failed 0";
//
//			}
//
//			System.out.println("Rid :"+Rid);
//
//			String ReqId=id+"000";
//
//			String s=String.valueOf(Rid);
//
//			ReqId=ReqId.substring(0,ReqId.length()-s.length());
//
//			ReqId=ReqId.concat(String.valueOf(Rid));
//
//			return ReqId;
//
//		}catch(Exception exc){
//
//			System.out.println(exc.getMessage());
//
//			return "Failed";
//
//		}
//
//	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addWarehouse(@FormParam("id") String id,@FormParam("warehouse_name") String warehouse_name,@FormParam("warehouse_manager_name") String warehouse_manager_name,@FormParam("warehouse_address1") String warehouse_address1,@FormParam("warehouse_address2")
	String warehouse_address2,@FormParam("warehouse_contact_number1") String warehouse_contact_number1,@FormParam("warehouse_contact_number2") String warehouse_contact_number2,@FormParam("warehouse_mailaddress1") String warehouse_mailaddress1,
	@FormParam("warehouse_mailaddress2") String warehouse_mailaddress2,@FormParam("warehouse_city") String warehouse_city,@FormParam("warehouse_state") String warehouse_state,@FormParam("warehouse_country") String warehouse_country,
	@FormParam("gst_number") String gst_number,@FormParam("bank_details") String bank_details){
		JSONObject jsonObject=new JSONObject();
//		if(warehouse_name==null || warehouse_name.isEmpty()|| wh_branch_name==null || wh_branch_name.isEmpty()
//				|| wh_manager_name==null || wh_manager_name.isEmpty()|| wh_contact_number==null || wh_contact_number.isEmpty()|| wh_contact_email==null || wh_contact_email.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
		
//					.entity(jsonObject)
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
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
		try {
			SqlConnection();
			String warehouseid=Customer.getGenerateId("WAREHOUSE", 5,connection);
                 
			try {
				
				String userInsertQuery= "insert into tbl_user(username,password,roleid,emailid,status,auth_id) "
						+ "values('"+warehouse_name+"','123456',4,'"+warehouse_mailaddress1+"','A','"+warehouseid+"')";
				
	            int insertStatus=statement.executeUpdate(userInsertQuery);
	            if(insertStatus>0){
	            	   jsonObject.clear();
	                   jsonObject.put("Status", "Success");
	                   jsonObject.put("Message", "Warehouse User Added Successfully");
	            }else{
	           jsonObject.clear();
	           jsonObject.put("Status", "Failed");
	           jsonObject.put("Message", "Failed to add Warehouse");
	           return Response.ok()
			                  .entity(jsonObject)
			                  .header("Access-Control-Allow-Methods", "POST").build();
	                 }
				
				String email=(String) jsObject.get("email");
				String Query="insert into tbl_warehouse1(warehouse_id,wareouse_name,warehouse_manager_name,warehouse_address1,warehouse_address2,warehouse_contact_number1,warehouse_contact_number2,warehouse_mailaddress1,warehouse_mailaddress2,warehouse_city,warehouse_state,warehouse_country,created_by,gst_number,bank_details) "
						+ "values('"+warehouseid+"','"+warehouse_name+"','"+warehouse_manager_name+"','"+warehouse_address1+"','"+warehouse_address2+"','"+warehouse_contact_number1+"','"+warehouse_contact_number2+"','"+warehouse_mailaddress1+"','"+warehouse_mailaddress2+"','"+warehouse_city+"','"+warehouse_state+"','"+warehouse_country+"','"+email+"','"+gst_number+"','"+bank_details+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Warehouse Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add Warehouse");
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
	@Path("/warehouseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseList(@FormParam("id") String id){
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
		String Query ="select * from tbl_warehouse1 where mark_for_deletion=0";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Edit warehouse Api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/editWarehouseData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response editwarehouseData(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		if(warehouse_id==null || warehouse_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		
		String Query="select * from tbl_warehouse1 where warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("warehouseSearch1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response warehouseSearch1(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		if(jsonObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		
		String Query="select distinct product_id,product_description from tbl_warehouse_purchase_order where warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("warehouseSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response warehouseSearch(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
		if(warehouse_id==null || warehouse_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		
		String Query="  select distinct ws.total_stock,twp.cgst,twp.sgst,twp.igst,twp.product_description,twp.product_price from tbl_warehouse_purchase_order twp "
   +" inner join warehouse_stockdetails ws on ws.product_id= twp.product_id where twp.warehouse_id='"+warehouse_id+"' and twp.product_id='"+product_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("storeSearch1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response storeSearch1(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		if(jsonObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		
		String Query="SELECT distinct twp.product_description,twp.product_id FROM public.tbl_store_indent tsi "+
		" inner join tbl_warehouse_purchase_order twp on twp.warehouse_id= tsi.warehouse_id " +
        "  inner join tbl_store_indent_products tsip on tsip.store_id= tsi.store_id "+
        "  where tsi.store_id='"+store_id+"' and tsip.status='Approved'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("storeSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response storeSearch(@FormParam("id") String id,@FormParam("store_id") String store_id,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
		if(jsonObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		
		String Query="SELECT distinct twp.product_description,twp.cgst,twp.sgst,twp.igst,twp.product_price,tsip.issued_qty as qty"
+	" FROM public.tbl_store_indent tsi inner join tbl_warehouse_purchase_order twp on twp.warehouse_id= tsi.warehouse_id"
 +   " inner join tbl_store_indent_products tsip on tsip.store_id= tsi.store_id "
  + " where tsip.store_id='"+store_id+"' and tsip.product_id='"+product_id+"' and twp.product_id='"+product_id+"' and tsip.status='Approved'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	 Update Warehouse Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateWarehouseData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatewarehouseData(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("warehouse_name") String warehouse_name,@FormParam("warehouse_manager_name") String warehouse_manager_name,@FormParam("warehouse_address1") String warehouse_address1,@FormParam("warehouse_address2")
	String warehouse_address2,@FormParam("warehouse_contact_number1") String warehouse_contact_number1,@FormParam("warehouse_contact_number2") String warehouse_contact_number2,@FormParam("warehouse_mailaddress1") String warehouse_mailaddress1,
	@FormParam("warehouse_mailaddress2") String warehouse_mailaddress2,@FormParam("warehouse_city") String warehouse_city,@FormParam("warehouse_state") String warehouse_state,@FormParam("warehouse_country") String warehouse_country,
	@FormParam("gst_number") String gst_number,@FormParam("bank_details") String bank_details){
		JSONObject jsonObject=new JSONObject();
//		if(warehouse_id==null || warehouse_id.isEmpty()|| warehouse_name==null || warehouse_name.isEmpty()|| warehouse_branch_name==null || warehouse_branch_name.isEmpty()
//				|| warehouse_managername==null || warehouse_managername.isEmpty()|| warehouse_contact_number==null || warehouse_contact_number.isEmpty()|| warehouse_contact_email==null || warehouse_contact_email.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Origin", "*")
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
				String email=(String) jsObject.get("email");
				String Query="UPDATE tbl_warehouse1 SET wareouse_name='"+warehouse_name+"',warehouse_manager_name='"+ warehouse_manager_name+"',warehouse_address1='"+ warehouse_address1+"',warehouse_address2='"+ warehouse_address2+"',warehouse_contact_number1='"+ warehouse_contact_number1+"',"
						+ "warehouse_contact_number2='"+warehouse_contact_number2+"',warehouse_mailaddress1='"+warehouse_mailaddress1+"',warehouse_mailaddress2='"+warehouse_mailaddress2+"',warehouse_city='"+warehouse_city+"',warehouse_state='"+warehouse_state+"',warehouse_country='"+warehouse_country+"',"
								+ "updated_date=current_date,updated_by='"+email+"',gst_number='"+gst_number+"',bank_details='"+bank_details+"' where warehouse_id='"+warehouse_id+"'";
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
	@Path("/deleteWarehouseData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deleteWarehouseData(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		
		if(warehouse_id==null || warehouse_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Origin", "*")
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
				String Query="update tbl_warehouse1 set mark_for_deletion=1 where warehouse_id='"+warehouse_id+"'";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Warehouse Deleted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Delete");
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
	@Path("/viewWarehouse_data")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editpurchaseorder(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id){
		JSONObject jsonObject=new JSONObject();
//		if(purchaseorder_id==null || purchaseorder_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
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
		
	//	String Query="select * from purchase_order where purchaseorder_id="+"'"+purchaseorder_id+"'";
//		String Query="select purchase_order.id,tbl_generate_invoice.purchase_id,tbl_generate_invoice.invoice_id,tbl_generate_invoice.total_value,"
//				+ "purchase_order.purchaseorder_date,purchase_order.billing_address,purchase_order.shipping_address,purchase_order.product_description,"
//				+ "purchase_order.product_unit,purchase_order.product_quantity,purchase_order.purchase_status,purchase_order.product_total,purchase_order.paid,"
//				+ "purchase_order.payment_status,purchase_order.cgst,purchase_order.igst,purchase_order.sgst,purchase_order.discount,purchase_order.manufacturingdate,"
//				+ "purchase_order.expirydate,purchase_order.price,"
//				+ "tbl_generate_invoice.total_value,tbl_generate_invoice.total_discount,tbl_generate_invoice.total_tax,tbl_generate_invoice.total from tbl_generate_invoice "
//				+ "inner join purchase_order on tbl_generate_invoice.purchase_id=purchase_order.purchaseorder_id where invoice_id='"+invoice_id+"' and purchase_order.purchaseorder_status='Pending'";
		String Query="select tbl_warehouse_purchase_order.id,tbl_generate_invoice1.purchaseorder_id,tbl_generate_invoice1.invoice_id, "
				+ " tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.warehouse_id,tbl_warehouse_purchase_order.vendor_id, "
				+ " tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
				+ " tbl_warehouse_purchase_order.product_description,tbl_warehouse_purchase_order."
				+ " product_quantity,tbl_warehouse_purchase_order.purchaseorder_status,tbl_warehouse_purchase_order.product_total,tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.igst,"
				+ " tbl_warehouse_purchase_order.sgst,tbl_warehouse_purchase_order.discount,tbl_warehouse_purchase_order.manufacture_date,tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,"
				+ " tbl_generate_invoice1.total_discount,tbl_generate_invoice1.total_tax,tbl_generate_invoice1.total from tbl_generate_invoice1"
				+ " inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id where tbl_generate_invoice1.invoice_id='"+invoice_id+"' and tbl_warehouse_purchase_order.purchaseorder_status='Pending'";
		
		//System.out.println("the query is:"+ Query);
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
					jsonObject.put("Status", "0");
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/approveorreject")
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

	//if(status.equals("1")){
	//	
//		}
	try {
	SqlConnection();

	try {
	String pcoId=new String();
	System.out.println(data);
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
	jsArray = new org.json.JSONArray(data);
	for(int i=0;i<jsArray.length();i++){
	org.json.JSONObject jsObject1 = jsArray.getJSONObject(i);
	String purchase_order_id = jsObject1.getString("purchaseorder_id");
	String product_description = jsObject1.getString("product_description");

	String[] parts = product_description.split("-");
	//String lastpart = parts[4];
	//String[] bits = one.split("-");
	String product_id = parts[parts.length-1];
	String warehouse_id = jsObject1.getString("warehouse_id");
	String vendor_id = jsObject1.getString("warehouse_id");
	int quantity = jsObject1.getInt("product_quantity");
	int qty = jsObject1.getInt("quantity");
	
	String Query ="select fn_warehouse_stock_indetails('"+purchase_order_id+"','"+product_id+"','"+warehouse_id+"','"+vendor_id+"','"+email+"','"+qty+"')";
	System.out.println("In Details : "+Query);
	CallableStatement csStatement=connection.prepareCall(Query);
	csStatement.execute();
	resultset=csStatement.getResultSet();
	String getStatus="";
	while(resultset.next()){
	getStatus=resultset.getString(1);
	}
	int return_qunt=quantity-qty;
	System.out.println("total quantity: "+quantity);
	System.out.println("approved quantity: "+qty);
	System.out.println("rejected quantity: "+return_qunt);
	if(return_qunt==0){
		Query="update tbl_warehouse_purchase_order set purchaseorder_status='"+status+"',approved_qty='"+qty+"' where  product_id='"+product_id+"'   and purchaseorder_id='"+purchase_order_id+"' ";
	}else{
	String returnid=Customer.getGenerateId("PURET",8,connection);
	Query="update tbl_warehouse_purchase_order set purchaseorder_status='"+status+"',approved_qty='"+qty+"',return_qty='"+return_qunt+"',purchaseorder_returns_id='"+returnid+"' where  product_id='"+product_id+"'   and purchaseorder_id='"+purchase_order_id+"' ";
	}
	
	System.out.println(Query);
	i=statement.executeUpdate(Query);
	jsonObject.clear();
	jsonObject.put("Status", "Success");
	jsonObject.put("Message", getStatus);
	}
	} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
//		} 

	}else if(status.equals("0")){
	status="Rejected";
	String email=(String) jsObject.get("email");
	org.json.JSONArray jsArray;
	try {
	jsArray = new org.json.JSONArray(data);
	for(int i=0;i<jsArray.length();i++){
	org.json.JSONObject jsObject1 = jsArray.getJSONObject(i);
	String purchase_order_id = jsObject1.getString("purchaseorder_id");
	String product_description = jsObject1.getString("product_description");
	String[] parts = product_description.split("-");
	//String lastpart = parts[4];
	//String[] bits = one.split("-");
	String product_id = parts[parts.length-1];
	String warehouse_id = jsObject1.getString("warehouse_id");
	String vendor_id = jsObject1.getString("warehouse_id");
	int quantity = jsObject1.getInt("product_quantity");
	int qty = jsObject1.getInt("quantity");
	String ProductReturnId=Customer.getGenerateId("PURET",8,connection);
	String Query ="select fn_warehouse_stock_indetails('"+purchase_order_id+"','"+product_id+"','"+warehouse_id+"','"+vendor_id+"','"+email+"','"+(quantity-qty)+"')";
	System.out.println("In Details : "+Query);
	CallableStatement csStatement=connection.prepareCall(Query);
	csStatement.execute();
	resultset=csStatement.getResultSet();
	String getStatus="";
	while(resultset.next()){
	getStatus=resultset.getString(1);
	}
int approve_qunt=quantity-qty;
System.out.println("total product quantity: "+quantity);
System.out.println("approve_qunt: "+approve_qunt);
System.out.println("rejected_qunt: "+qty);

	
Query="update tbl_warehouse_purchase_order set purchaseorder_status='"+status+"',approved_qty='"+approve_qunt+"',return_qty='"+qty+"',purchaseorder_returns_id='"+ProductReturnId+"' where product_id='"+product_id+"' and purchaseorder_id='"+purchase_order_id+"'  ";
	System.out.println(Query);
	i=statement.executeUpdate(Query);
	jsonObject.clear();
	jsonObject.put("Status", "Success");
	jsonObject.put("Message", getStatus);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
//		} 
	}else{
	jsonObject.clear();
	jsonObject.put("Status", "Failed");
	jsonObject.put("Message", "Invalid action");
	return Response.ok()
	.entity(jsonObject)
	.header("Access-Control-Allow-Methods", "POST").build();
	}
	jsonObject.clear();
	jsonObject.put("Status", "Success");
	jsonObject.put("Message", "Updated Successfully");

	} catch (Exception e) {
	e.printStackTrace();
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
	.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();

	}


	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/warehouseProductStock")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseProductStock(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
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
		String Query ="SELECT tbl_products.product_name||'-'||tbl_brand.brand_name as product_description,"
				+ "tbl_products.product_unit,tbl_warehouse1.wareouse_name, "
				+ "warehouse_stockdetails.total_stock, warehouse_stockdetails.in_stock, "
				+ "warehouse_stockdetails.out_stock FROM warehouse_stockdetails "
				+ "inner join  tbl_products on tbl_products.product_id=warehouse_stockdetails.product_id "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=warehouse_stockdetails.warehouse_id "
				+ "inner join tbl_brand on tbl_brand.brand_id=tbl_products.product_brand_id "
				+ "where warehouse_stockdetails.warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/warehouseShippingAddress")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseShippingAddress(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
			System.out.println(role_id);
		}
		String auth_id=(String) jsObject.get("auth_id");
		int role_id=(int) jsObject.get("role_id");
		String Query="";
		if(role_id ==4){
			Query ="select warehouse_address1,warehouse_address2 from tbl_warehouse1 where warehouse_id='"+auth_id+"'";

		}else{
			Query ="select * from tbl_warehouse1";
		}
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/totalWarehouseProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response totalWarehouseProductsList(@FormParam("id") String id,
			@FormParam("product_description") String product_description,
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
			 if(role_id==1){
				 
			 }else{
				 warehouse_id=auth_id;
			 }
			System.out.println(role_id);
		}
		
		String[] parts = product_description.split("-");
		String product_id=parts[parts.length-1];
		String auth_id=(String) jsObject.get("auth_id");
		String Query ="select distinct tbl_warehouse_purchase_order.product_description, "
				+ "warehouse_stockdetails.total_stock,tbl_products.product_cgst,"
				+ "tbl_products.product_sgst,tbl_products.product_igst,"
				+ "tbl_products.price,tbl_products.product_unit "
				+ "from warehouse_stockdetails "
				+ "inner join tbl_products on tbl_products.product_id=warehouse_stockdetails.product_id "
				+ "inner join tbl_warehouse_purchase_order on warehouse_stockdetails.product_id=tbl_warehouse_purchase_order.product_id "
				+ "where warehouse_stockdetails.product_id='"+product_id+"' and "
						+ "warehouse_stockdetails.warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		//String Query ="select  product_id, product_code, product_name,COALESCE((select sum(product_quantity) from purchase_order where purchase_order.productid=tbl_addproduct.product_id and purchaseorder_status='Approved'),0) as Qty	FROM public.tbl_addproduct";
		String Query="select  product_id, product_code, product_brand,product_name,COALESCE((select sum(product_quantity) from purchase_order where "
				+ "purchase_order.productid=tbl_addproduct.product_id and purchaseorder_status='Approved'),0) as "
				+ "total_stock,coalesce((select product_quantity as closing_stock from complete_inventoryorder where "
				+ "complete_inventoryorder.product_id=tbl_addproduct.product_id and  product_status='Approved'),0) as closing_stock,(COALESCE((select sum(product_quantity) from purchase_order where "
				+ "purchase_order.productid=tbl_addproduct.product_id and purchaseorder_status='Approved'),0)-coalesce((select product_quantity as closing_stock from complete_inventoryorder where "
				+ "complete_inventoryorder.product_id=tbl_addproduct.product_id and  product_status='Approved'),0)) as remaining_stock FROM public.tbl_addproduct";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("/productNamesSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productNamesSearch(@FormParam("id") String id,@FormParam("product_name") String product_name){
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
		String Query ="select product_description,product_unit,product_quantity,price,discount,cgst,igst,sgst,product_total from tbl_warehouse_purchase_order where LOWER(product_description) like LOWER('%"+product_name+"%') and purchaseorder_status='Approved'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/warehouseandStoreDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseStoreDetails(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("store_id") String store_id ,@FormParam("product_id") String product_id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date ){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		String Query= new String();
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
		if(warehouse_id==null||warehouse_id.isEmpty()||store_id!=null){	Query ="SELECT ssf.in_stock,ssf.out_stock,(select wareouse_name from tbl_warehouse1 tw where tw.warehouse_id=ssf.warehouse_id )"
				+",(select product_name from tbl_products tp where tp.product_id=ssf.product_id),created_date from store_stock_flow ssf"
				+" where (ssf.warehouse_id='"+warehouse_id+"'or ssf.store_id='"+store_id+"' ) and ssf.created_date::date between '"+from_date+"' and '"+to_date+"'";
		System.out.println("the query is:" +Query);
		}else if(warehouse_id!=null){	 Query ="SELECT wsf.in_stock,wsf.out_stock,(select wareouse_name from tbl_warehouse1 tw where tw.warehouse_id=wsf.warehouse_id )"
				+",(select product_name from tbl_products tp where tp.product_id=wsf.product_id),created_date from warehouse_stock_flow wsf"
			+" where (wsf.store_id='"+store_id+"'or wsf.warehouse_id='"+warehouse_id+"' or wsf.product_id='"+product_id+"') and wsf.created_date::date between '"+from_date+"'and '"+to_date+"'";
		System.out.println("the query is:" +Query); 
				}
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 * public static String getWarehouseId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_wareouse_id_numberrange_function('"+id+"')";
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("product_price")||columnName.contains("product_total")){
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
	
	
		
}
