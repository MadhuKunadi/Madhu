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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("Accounts")
public class Accounts {
	static Statement  statement;
	static ResultSet  resultset;
	static Connection connection;
	static JSONArray  jsonArray   =new JSONArray();
	static JSONObject jsonObject  =new JSONObject();
	static JSONArray  subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String     sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Accounts.class);
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getCustomerid")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getCustomer(@FormParam("id") String id){
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
		}String Query="select customer_id from customer_master";
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
	@Path("/getVendorid")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getVendorid(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select vendor_id from vendor_master";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/getInvoice")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getInvoice(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select invoice from invoice";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/getOrderid")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getorderid(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status","Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select order_id from sale_order";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/getPaidPaymentType")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response paidPaymentType(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/getReceivedPaymentType")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response receivedPaymentType(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/getPaymentsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response paymentsList(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from accounts_payable";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editPaymentList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editPaymentList(@FormParam("id") String id,@FormParam("order_id") String order_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || order_id==null || order_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from accounts_payable where order_id="+"'"+order_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updatePaymentList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatePaymentList(@FormParam("id") String id,@FormParam("order_id") String order_id,@FormParam("payment_type") String payment_type,@FormParam("order_date") String order_date,@FormParam("vendor_id")
	String vendor_id,@FormParam("amount_to_be_paid") String amount_to_be_paid,@FormParam("particulars") String particulars,@FormParam("customer_id") String customer_id,@FormParam("remarks") String remarks
	,@FormParam("payment_date") String payment_date){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || order_id==null || order_id.isEmpty()|| payment_type==null || payment_type.isEmpty()|| order_date==null || order_date.isEmpty()
				|| vendor_id==null || vendor_id.isEmpty()|| amount_to_be_paid==null || amount_to_be_paid.isEmpty()|| particulars==null || particulars.isEmpty()
				|| customer_id==null || customer_id.isEmpty()|| remarks==null || remarks.isEmpty()|| payment_date==null || payment_date.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
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
				String Query="UPDATE accounts_payable SET payment_type='"+payment_type+"',order_date='"+order_date+"',"
						+ "vendor_id='"+vendor_id+"',amount_to_be_paid='"+amount_to_be_paid+"',customer_id='"+customer_id+"',remarks='"+remarks+"',payment_date='"+payment_date+"' where order_id='"+order_id+"'";
				
				PreparedStatement preparedStatement=connection.prepareStatement(Query);

				int i=preparedStatement.executeUpdate();
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
	@Path("/getReceivableList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response receivableList(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from accounts_receivable";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editReceivableList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editReceivableList(@FormParam("id") String id,@FormParam("order_id") String order_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || order_id==null || order_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from accounts_receivable where order_id="+"'"+order_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updateReceivableList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateReceivableList(@FormParam("id") String id,@FormParam("order_id") String order_id,@FormParam("payment_type") String payment_type,@FormParam("order_date") String order_date,@FormParam("vendor_id")
	String vendor_id,@FormParam("amount_to_be_recieved") String amount_to_be_recieved,@FormParam("customer_id") String customer_id,@FormParam("remarks") String remarks
	,@FormParam("payment_date") String payment_date,@FormParam("due_if_any") String due_if_any){
		JSONObject jsonObject=new JSONObject();
		if(order_id==null || order_id.isEmpty()|| payment_type==null || payment_type.isEmpty()|| order_date==null || order_date.isEmpty()
				|| vendor_id==null || vendor_id.isEmpty()||amount_to_be_recieved==null || amount_to_be_recieved.isEmpty()
				||customer_id==null || customer_id.isEmpty()|| remarks==null || remarks.isEmpty()
				|| payment_date==null || payment_date.isEmpty()){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
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
				String Query="UPDATE accounts_receivable SET payment_type='"+payment_type+"',due_if_any='"+due_if_any+"',order_date='"+order_date+"',"
						+ "vendor_id='"+vendor_id+"',amount_to_be_recieved='"+amount_to_be_recieved+"',customer_id='"+customer_id+"',remarks='"+remarks+"',payment_date='"+payment_date+"' where order_id='"+order_id+"'";
				
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
	@Path("/getLedgerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response ledgerList(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from general_ledger";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editLedgerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editLedgerListt(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from general_ledger where customer_id="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updateLedgerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateLedgerList(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("dateof_order") String dateof_order,@FormParam("dateof_payment_received") String dateof_payment_received,@FormParam("balance")
	String balance,@FormParam("received_payment_type") String received_payment_type,@FormParam("invoice_num") String invoice_num,@FormParam("vendor_id") String vendor_id,@FormParam("debit") String debit
	,@FormParam("credit") String credit,@FormParam("order_id") String order_id,@FormParam("remarks") String remarks,@FormParam("paid_payment_type") String paid_payment_type,@FormParam("dateof_payment_done") String dateof_payment_done){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| dateof_order==null || dateof_order.isEmpty()|| dateof_payment_received==null || dateof_payment_received.isEmpty()
				|| balance==null || balance.isEmpty()|| received_payment_type==null || received_payment_type.isEmpty()|| invoice_num==null || invoice_num.isEmpty()
				|| vendor_id==null || vendor_id.isEmpty()|| debit==null || debit.isEmpty() || credit==null || credit.isEmpty()
				|| order_id==null || order_id.isEmpty()|| remarks==null || remarks.isEmpty()|| paid_payment_type==null || paid_payment_type.isEmpty()|| dateof_payment_done==null || dateof_payment_done.isEmpty()) {
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
		try {
			SqlConnection();
			try {
				String Query="UPDATE general_ledger SET dateof_payment_done='"+dateof_payment_done+"',dateof_order='"+dateof_order+"',"
						+ "dateof_payment_received='"+dateof_payment_received+"',balance='"+balance+"',received_payment_type='"+received_payment_type+"'"
						+ ",invoice_num='"+invoice_num+"',vendor_id='"+vendor_id+"',order_id='"+order_id+"',debit='"+debit+"',credit='"+credit+"',remarks='"+remarks+"',paid_payment_type='"+paid_payment_type+"' where customer_id='"+customer_id+"'";
				

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
	@Path("/getGstSalesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response gstSalesList(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from gst_sales";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editGstSalesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editGstSalesList(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from gst_sales where customer_id="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updateGstSalesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateGstSalesList(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("sgst") String sgst,@FormParam("gst_num") String gst_num,@FormParam("date_of_payment_received")
	String date_of_payment_received,@FormParam("hsn_sac_code") String hsn_sac_code,@FormParam("cgst") String cgst,@FormParam("igst") String igst,@FormParam("taxable_value") String taxable_value,@FormParam("gst_percentage") String gst_percentage
	,@FormParam("total") String total,@FormParam("invoice_num") String invoice_num,@FormParam("vendor_id") String vendor_id,@FormParam("date_of_order") String date_of_order,@FormParam("order_id") String order_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| sgst==null || sgst.isEmpty()|| gst_num==null || gst_num.isEmpty()
				|| date_of_payment_received==null || date_of_payment_received.isEmpty()|| hsn_sac_code==null || hsn_sac_code.isEmpty()|| cgst==null || cgst.isEmpty()
				|| igst==null || igst.isEmpty()|| taxable_value==null || taxable_value.isEmpty()|| gst_percentage==null || gst_percentage.isEmpty()
				|| total==null || total.isEmpty()|| invoice_num==null || invoice_num.isEmpty()|| vendor_id==null || vendor_id.isEmpty()
				|| date_of_order==null || date_of_order.isEmpty()|| order_id==null || order_id.isEmpty()) {
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
		try {
			SqlConnection();
			try {
				String Query="UPDATE gst_sales SET sgst='"+sgst+"',gst_num='"+gst_num+"',"
						+ "date_of_payment_received='"+date_of_payment_received+"',hsn_sac_code='"+hsn_sac_code+"',cgst='"+cgst+"'"
						+ ",igst='"+igst+"',taxable_value='"+taxable_value+"',gst_percentage='"+gst_percentage+"'"
						+ ",total='"+total+"',invoice_num='"+invoice_num+"',vendor_id='"+vendor_id+"',date_of_order='"+date_of_order+"',order_id='"+order_id+"' where customer_id='"+customer_id+"'";
				
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
	@Path("/getGstPurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response gstPurchaseList(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from gst_purchases";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editGstPurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editGstPurchaseList(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from gst_purchases where customer_id="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updateGstPurchaseList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateGstPurchaseList(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("sgst") String sgst,@FormParam("gst_num") String gst_num,@FormParam("date_of_payment_done")
	String date_of_payment_done,@FormParam("hsn_sac_code") String hsn_sac_code,@FormParam("cgst") String cgst,@FormParam("igst") String igst,@FormParam("taxable_value") String taxable_value,@FormParam("gst_percentage") String gst_percentage
	,@FormParam("total") String total,@FormParam("invoice_num") String invoice_num,@FormParam("vendor_id") String vendor_id,@FormParam("date_of_order") String date_of_order,@FormParam("order_id") String order_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| sgst==null || sgst.isEmpty()|| gst_num==null || gst_num.isEmpty()
				|| date_of_payment_done==null || date_of_payment_done.isEmpty()|| hsn_sac_code==null || hsn_sac_code.isEmpty()|| cgst==null || cgst.isEmpty()
				|| igst==null || igst.isEmpty()|| taxable_value==null || taxable_value.isEmpty()|| gst_percentage==null || gst_percentage.isEmpty()
				|| total==null || total.isEmpty()|| invoice_num==null || invoice_num.isEmpty()|| vendor_id==null || vendor_id.isEmpty()
				|| date_of_order==null || date_of_order.isEmpty()|| order_id==null || order_id.isEmpty()) {
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
		try {
			SqlConnection();
			try {
				String Query="UPDATE gst_purchases SET sgst='"+sgst+"',gst_num='"+gst_num+"',"
						+ "date_of_payment_done='"+date_of_payment_done+"',hsn_sac_code='"+hsn_sac_code+"',cgst='"+cgst+"'"
						+ ",igst='"+igst+"',taxable_value='"+taxable_value+"',gst_percentage='"+gst_percentage+"'"
						+ ",total='"+total+"',invoice_num='"+invoice_num+"',vendor_id='"+vendor_id+"',date_of_order='"+date_of_order+"',order_id='"+order_id+"' where customer_id='"+customer_id+"'";
				
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
	@Path("/accountsPayable")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response accountsPayable(@FormParam("id") String id,@FormParam("order_id") String orderId,@FormParam("vendor_id") String vendorId,@FormParam("customer_id") String customerId,
			@FormParam("invoice_num") String invoice,@FormParam("payment_type") String paymentType,@FormParam("order_date") String dateOfOrder,
			@FormParam("payment_date") String dateOfPayment,@FormParam("amount_to_be_paid") String amountToPaid,@FormParam("due_if_any") String dueIfAny,@FormParam("remarks") String remarks){
		JSONObject jsonObject=new JSONObject();
		if(orderId==null || orderId.isEmpty()|| vendorId==null || vendorId.isEmpty()|| customerId==null || customerId.isEmpty()
				|| invoice==null || invoice.isEmpty()|| paymentType==null || paymentType.isEmpty()|| dateOfOrder==null || dateOfOrder.isEmpty()|| dateOfPayment==null || dateOfPayment.isEmpty()
				|| amountToPaid==null || amountToPaid.isEmpty()|| dueIfAny==null || dueIfAny.isEmpty()|| remarks==null || remarks.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query="insert into accounts_payable(order_id,vendor_id,customer_id,invoice_num,payment_type,order_date,payment_date,amount_to_be_paid,due_if_any,remarks) values('"+orderId+"','"+vendorId+"','"+customerId+"','"+invoice+"','"+paymentType+"','"+dateOfOrder+"','"+dateOfPayment+"','"+amountToPaid+"','"+dueIfAny+"','"+remarks+"')";
		System.out.println(Query);
		try {
			SqlConnection();
			try {
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
	@Path("/accountsReceivable")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response accountsReceivable(@FormParam("id") String id,@FormParam("order_id") String orderId,@FormParam("vendor_id") String vendorId,@FormParam("customer_id") String customerId,
			@FormParam("invoice_num") String invoice,@FormParam("payment_type") String paymentType,@FormParam("order_date") String dateOfOrder,
			@FormParam("payment_date") String dateOfPayment,@FormParam("amount_to_be_recieved") String amountTobeReceived,@FormParam("due_if_any") String dueIfAny,@FormParam("remarks") String remarks){
		JSONObject jsonObject=new JSONObject();
		if(orderId.isEmpty()|| vendorId==null || vendorId.isEmpty()|| customerId==null || customerId.isEmpty()
				|| invoice==null || invoice.isEmpty()|| paymentType==null || paymentType.isEmpty()|| dateOfOrder==null || dateOfOrder.isEmpty()|| dateOfPayment==null || dateOfPayment.isEmpty()
				|| amountTobeReceived==null || amountTobeReceived.isEmpty()|| dueIfAny==null || dueIfAny.isEmpty()|| remarks==null || remarks.isEmpty()){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query="insert into accounts_receivable(order_id,order_date,vendor_id,customer_id,invoice_num,amount_to_be_recieved,payment_type,due_if_any,remarks,payment_date) values('"+orderId+"','"+dateOfOrder+"','"+vendorId+"','"+customerId+"','"+invoice+"','"+amountTobeReceived+"','"+paymentType+"','"+dueIfAny+"','"+remarks+"','"+dateOfPayment+"')";
		System.out.println(Query);
		try {
			SqlConnection();
			try {
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
			}catch (Exception e) {
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
	@Path("/gstSales")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response gstSales(@FormParam("id") String id,@FormParam("order_id") String orderId,@FormParam("vendor_id") String vendorId,@FormParam("customer_id") String customerId,
			@FormParam("invoice_num") String invoice,@FormParam("date_of_order") String dateOfOrder,@FormParam("hsn_sac_code") String hsn_sac_code,@FormParam("gst_percentage") String gst,@FormParam("taxable_value") String taxable_value,
			@FormParam("gst_num	") String gst_number,@FormParam("cgst") String cgst,@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("total") String total,@FormParam("date_of_payment_received") String date_of_payment_received){
		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || orderId==null || orderId.isEmpty()|| vendorId==null || vendorId.isEmpty()|| customerId==null || customerId.isEmpty()
//				|| invoice==null || invoice.isEmpty()|| dateOfOrder==null || dateOfOrder.isEmpty()|| hsn_sac_code==null || hsn_sac_code.isEmpty()
//				|| gst==null || gst.isEmpty()|| taxable_value==null || taxable_value.isEmpty()|| gst_number==null || gst_number.isEmpty()
//				|| cgst==null || cgst.isEmpty()|| sgst==null || sgst.isEmpty()|| igst==null || igst.isEmpty()|| total==null || total.isEmpty()|| date_of_payment_received==null || date_of_payment_received.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="insert into gst_sales( date_of_order,invoice_num,gst_num,customer_id,vendor_id,hsn_sac_code,gst_percentage,taxable_value,cgst,sgst,igst,total,date_of_payment_received,order_id) "
				+ "values('"+dateOfOrder+"','"+invoice+"','"+gst_number+"','"+customerId+"','"+vendorId+"','"+hsn_sac_code+"','"+gst+"','"+taxable_value+"','"+cgst+"','"+sgst+"','"+igst+"','"+total+"','"+date_of_payment_received+"','"+orderId+"')";
		try {
			SqlConnection();
			try {
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
	@Path("/gstPurchase")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response gstPurchase(@FormParam("id") String id,@FormParam("order_id") String orderId,@FormParam("vendor_id") String vendorId,@FormParam("customer_id") String customerId,
			@FormParam("invoice_num") String invoice_num,@FormParam("date_of_order") String dateOfOrder,@FormParam("hsn_sac_code") String hsn_sac_code,@FormParam("gst_percentage") String gst,@FormParam("taxable_value") String taxable_value,
			@FormParam("gst_num") String gst_number,@FormParam("cgst") String cgst,@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("total") String total,@FormParam("date_of_payment_done") String date_of_payment_done){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || orderId==null || orderId.isEmpty()|| vendorId==null || vendorId.isEmpty()|| customerId==null || customerId.isEmpty()
				|| invoice_num==null || invoice_num.isEmpty()|| dateOfOrder==null || dateOfOrder.isEmpty()|| hsn_sac_code==null || hsn_sac_code.isEmpty()
				|| gst==null || gst.isEmpty()|| taxable_value==null || taxable_value.isEmpty()|| gst_number==null || gst_number.isEmpty()
				|| cgst==null || cgst.isEmpty()|| sgst==null || sgst.isEmpty()|| igst==null || igst.isEmpty()|| total==null || total.isEmpty()|| date_of_payment_done==null || date_of_payment_done.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="insert into gst_purchases(date_of_order,invoice_num,gst_num,customer_id,vendor_id,hsn_sac_code,gst_percentage,taxable_value ,cgst,sgst,igst,total,date_of_payment_done,order_id) "
				+ "values('"+dateOfOrder+"','"+invoice_num+"','"+gst_number+"','"+customerId+"','"+vendorId+"','"+hsn_sac_code+"','"+gst+"','"+taxable_value+"','"+cgst+"','"+sgst+"','"+igst+"','"+total+"','"+date_of_payment_done+"','"+orderId+"')";
		try {
			SqlConnection();
			try {
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
	
	/*
	 * Account payable list 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/DVAccountPaybaleList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getAccountPaybaleList(@FormParam("id") String id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()){
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
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
	
		String Query;
		if(vendor_id!=null){
//		String start_date = null;
//		String end_date = null;
		    Query="SELECT vendor_id,invoice_num,invoice_date,debit,credit,balance  from general_ledger WHERE vendor_id='"+vendor_id+"'";
		}else {
			 Query="SELECT vendor_id,invoice_num,debit,credit,balance,invoice_date from general_ledger WHERE invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
		}
	   try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
			 jsonObject.put("error",e.getMessage());
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
	/*
	 * Account receivable list 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/DVAccountReceivableList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getAccountReceivableList(@FormParam("id") String id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query;
		if(customer_id!=null){
			  Query="SELECT customer_id,invoice_num,order_id,debit,credit,balance,invoice_date from general_ledger WHERE customer_id='"+customer_id+"'";
			  System.out.println(Query);
			  //		String Query="SELECT vendor_id,invoice_num,amount_to_be_recieved,payment_date from accounts_receivable WHERE payment_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
		}else {
//	   String Query="SELECT invoice_num,amount_to_be_recieved,payment_date from accounts_receivable WHERE customer_id='"+customer_id+"'";
			 Query="SELECT customer_id,invoice_num,order_id,debit,credit,balance,invoice_date from general_ledger WHERE invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
			 System.out.println(Query);
		}
			 try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
			 jsonObject.put("error",e.getMessage());
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
	 * Total DCB Ledger List
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/TotalDCBLedgerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response TotalDCBLedgerList(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()||vendor_id==null || vendor_id.isEmpty()){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query;
		if(vendor_id !=null ) {
			  Query="select vendor_id, credit-debit as balance from general_ledger where vendor_id ='"+vendor_id+"'";

		}else {
	  Query="select SUM(debit) as debit,SUM(credit) as credit,SUM(balance) as balance FROM general_ledger";

		}
	   try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
	 *  Opening Balance Ledger List
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/openingBalanceLedgerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response openingBalanceLedgerList(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()){
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
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
	   String Query="select opening_balance,balance,(select sum(with_hold_amount) from gendral_ledger)as with_hold_amount from gendral_ledger order by created_date desc limit 1";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/withholdAmountLedgerList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response withholdAmountLedgerList(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//	    }
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
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//	   String Query="select sum(with_hold_amount) from gendral_ledger";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
//				 jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				 jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			 jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
	/*
	 *  Opening Balance Date Wise ledger list
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/openingBalanceDateWise")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response openingBalanceDateWise(@FormParam("id") String id){
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
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
	   String Query="select SUM(debit) as debit,SUM(credit) as credit,SUM(balance) as balance,\n" + 
	   		"		(select balance from general_ledger order by created_on desc limit 1) as opening_balance\n" + 
	   		"		FROM general_ledger ";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
	 *  CurrentDateAccountPayble
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/CurrentDateAccountPayble")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response CurrentDateAccountPayble(@FormParam("id") String id){
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
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
	   String Query="select * from accounts_payable where payment_date::date = current_date";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
	}
	/*
	 *  CurrentDateReceivable
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/CurrentDateReceivable")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response CurrentDateReceivable(@FormParam("id") String id){
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
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
	   String Query="select * from accounts_receivable where payment_date::date=current_date";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList<Map<String, Object>> arrayList=convertResultSetIntoArrayList(resultset);
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
	 * Add cash transaction
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getaddcashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getaddcashTransaction(@FormParam("id") String id){
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
		String Query ="select * from general_ledger";
//		String Query ="select vendor_id,s_no,invoice_num,remarks,part_name,debit,credit,balance,paid_payment_type from general_ledger";
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
	 * Inser API cash transaction
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/CashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generalLedger(@FormParam("id") String id,@FormParam("vendor_id") String vendorId,
			@FormParam("invoice_num") String invoice_num,@FormParam("paid_payment_type") String paidPaymentType,
			@FormParam("debit") Double amount_debited,@FormParam("credit") Double amount_credited,
			@FormParam("remarks") String remarks,@FormParam("part_name") String part_name
			,@FormParam("balance") Double balance,@FormParam("invoice_date") String invoice_date){
		JSONObject jsonObject=new JSONObject();
		if(vendorId==null || vendorId.isEmpty()
				|| invoice_num==null || invoice_num.isEmpty()|| remarks==null || remarks.isEmpty()|| paidPaymentType==null || paidPaymentType.isEmpty()
				|| invoice_date==null || invoice_date.isEmpty()|| part_name==null || part_name.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		String Query = null;
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		
		}  
		Query ="insert into general_ledger(vendor_id,invoice_num,debit,credit ,paid_payment_type,remarks,part_name,balance,invoice_date)"
				+ "values('"+vendorId+"','"+invoice_num+"','"+amount_debited+"','"+amount_credited+"','"+paidPaymentType+"','"+remarks+"','"+part_name+"',"+(amount_credited-amount_debited)+",'"+invoice_date+"')";
		try {
			SqlConnection();
			try {
				int i=statement.executeUpdate(Query);
				System.out.println(Query);
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
	@Path("/editcashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editCustomercredit(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || vendor_id==null || vendor_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
//		String Query="select customer_master.customer_id,customer_master.customer_first_name,customer_master.customer_last_name,customer_master.type,customer_master.phone_no,customer_master.customer_email,customer_master.transaction_type,customer_master.status,customer_credit.bank_account_no,customer_credit.ifsc_code,customer_credit.credit_limit,customer_credit.credit_consumed,customer_credit.credit_available from customer_master"
//				+ " left join customer_credit on customer_credit.customer_id=customer_master.customer_id "
//				+ " where customer_master.customer_id="+"'"+customer_id+"'";
		String Query ="select * from general_ledger where  vendor_id ="+"'"+vendor_id+"'";
		try {
			SqlConnection();
			try {
			System.out.println(Query);
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("data", arrayList);
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
	@Path("/updateCashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateCustomercredit(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id,@FormParam("part_name") String part_name,@FormParam("debit") Double debit,@FormParam("credit") Double credit,
	@FormParam("paid_payment_type") String paid_payment_type,@FormParam("remarks") String remarks,@FormParam("expense_category") String expense_category
	,@FormParam("invoice_num") String invoice_num,@FormParam("dateof_payment_done") String dateof_payment_done,@FormParam("dateof_order") String dateof_order,@FormParam("invoice_date") String invoice_date,@FormParam("balance") Double balance){
		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || vendor_id==null || vendor_id.isEmpty()|| 
//				part_name==null || part_name.isEmpty()|| debit==null || debit.isEmpty()
//				|| credit==null || credit.isEmpty()|| balance==null || balance.isEmpty()
//				|| paid_payment_type==null || paid_payment_type.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
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
				String Query ="update general_ledger set part_name='"+part_name+"',debit='"+debit+"',credit='"+credit+"',paid_payment_type='"+paid_payment_type+"',remarks='"+remarks+"',invoice_num='"+invoice_num+"',dateof_payment_done='"+dateof_payment_done+"',dateof_order='"+dateof_order+"',invoice_date='"+invoice_date+"',balance="+(debit-credit)+" where vendor_id = '"+vendor_id+"'";
				PreparedStatement preparedStatement=connection.prepareStatement(Query);
				int i=preparedStatement.executeUpdate();
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
	
	
	/*
	 * Inser API Online cash transaction
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/OnlinerCashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response OnlinerCashTransaction(@FormParam("id") String id,@FormParam("customer_id") String customer_id,
			@FormParam("invoice_num") String invoice_num,@FormParam("paid_payment_type") String paidPaymentType,
			@FormParam("debit") Double amount_debited,@FormParam("credit") Double amount_credited,
			@FormParam("remarks") String remarks,@FormParam("part_name") String part_name
			,@FormParam("balance") Double balance,@FormParam("invoice_date") String invoice_date,@FormParam("cheque_number") String cheque_number
			,@FormParam("cheque_date") String cheque_date){
		JSONObject jsonObject=new JSONObject();
		if(customer_id==null || customer_id.isEmpty()||cheque_number==null || cheque_number.isEmpty()||cheque_date==null || cheque_date.isEmpty()
				|| invoice_num==null || invoice_num.isEmpty()|| remarks==null || remarks.isEmpty()|| paidPaymentType==null || paidPaymentType.isEmpty()
				|| invoice_date==null || invoice_date.isEmpty()|| part_name==null || part_name.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		String Query = null;
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}  
//		if (amount_debited>0 && amount_debited<=balance){
			Query ="insert into general_ledger(customer_id,invoice_num,debit,credit ,paid_payment_type,remarks,part_name,balance,invoice_date,cheque_number,cheque_date)"
					+ "values('"+customer_id+"','"+invoice_num+"','"+amount_debited+"','"+amount_credited+"','"+paidPaymentType+"','"+remarks+"','"+part_name+"',"+(amount_credited-amount_debited)+",'"+invoice_date+"','"+cheque_number+"','"+cheque_date+"')";
//		}else {
//			jsonObject.clear();
//			jsonObject.put("Status", "Failed");
//			jsonObject.put("Message", "Failed to insert");
//			 return Response.ok()
//						.entity(jsonObject)
//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
			try {
			SqlConnection();
			try {
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
	@Path("/editOnlinecashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editOnlinecashTransaction(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select * from general_ledger where  customer_id ="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			System.out.println(Query);
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("data", arrayList);
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
	@Path("/updateonlineCashTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateonlineCashTransaction(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("invoice_num") String invoice_num,@FormParam("debit") Double debit,@FormParam("invoice_date") String invoice_date,
	@FormParam("remarks") String remarks,@FormParam("balance") Double balance,@FormParam("cheque_date") String cheque_date
	,@FormParam("paid_payment_type") String paid_payment_type ,@FormParam("credit") Double credit,@FormParam("part_name") String part_name
	,@FormParam("cheque_number") String cheque_number){
		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || vendor_id==null || vendor_id.isEmpty()|| 
//				part_name==null || part_name.isEmpty()|| debit==null || debit.isEmpty()
//				|| credit==null || credit.isEmpty()|| balance==null || balance.isEmpty()
//				|| paid_payment_type==null || paid_payment_type.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
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
				String Query ="update general_ledger set invoice_num='"+invoice_num+"',debit='"+debit+"',remarks='"+remarks+"',balance='"+balance+"',cheque_date='"+cheque_date+"',paid_payment_type='"+paid_payment_type+"',credit='"+credit+"',invoice_date='"+invoice_date+"',part_name='"+part_name+"',cheque_number='"+cheque_number+"',balance="+(debit-credit)+" where customer_id = '"+customer_id+"'";
				PreparedStatement preparedStatement=connection.prepareStatement(Query);
				int i=preparedStatement.executeUpdate();
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
	
	/*
	 * Expanse category drop down api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/expensecategoryDropdown")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
		public static Response storDropDown(@FormParam("id") String id){
			JSONObject jsObject = TokenCheck.checkTokenStatus(id);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("status","Failed");
				jsonObject.put("message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
			}else{
				@SuppressWarnings("unused")
				String email = (String) jsObject.get("email");
			}
			try{
				SqlConnection();
				try{
					
					String Query="select expensecategory_name from expense_category";
			       resultset=statement.executeQuery(Query);
					@SuppressWarnings("rawtypes")
					ArrayList arrayList = convertResultSetIntoArrayList(resultset);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Content", arrayList);	
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
					
					
					connection.close();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
		}
	/*
	 * Add Expense category API 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/AddExpenseCategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddExpenseCategory(@FormParam("expensecategory_name") String expensecategory_name,
			@FormParam("description") String description){
		if(expensecategory_name==null || expensecategory_name.isEmpty()|| 
				description==null || description.isEmpty()){
	jsonObject.clear();
	jsonObject.put("status", "Failed");
	jsonObject.put("message",  "Fields are empty");
	return Response.ok()
			.entity(jsonObject)
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
}
		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		String Query = null;
//		System.out.println(jsObject);
		if(jsonObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsonObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
		
		}  
		Query ="insert into expense_category(expensecategory_name,description)"
				+ "values('"+expensecategory_name+"','"+description+"')";
		try {
			SqlConnection();
			try {
				int i=statement.executeUpdate(Query);
				System.out.println(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Category Added Successfully");
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
	/*
	 * get Expenses Category
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getExpenseCategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getExpenseCategory(@FormParam("id") String id){
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
		String Query ="select * from expense_category";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Content", arrayList);
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
