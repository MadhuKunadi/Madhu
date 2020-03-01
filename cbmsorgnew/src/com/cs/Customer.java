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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("customer")
public class Customer {
	
	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(Customer.class);
	@Context private static HttpServletRequest request;
	@Context private static HttpServletResponse response;
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/addCustomer")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response addCustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("customer_first_name") String customer_first_name,@FormParam("customer_last_name") String customer_last_name,@FormParam("customer_type")
//	String customer_type,@FormParam("phone_no") String phone_no,@FormParam("customer_email") String customer_email,@FormParam("country") String country,@FormParam("state") String state,@FormParam("city") String city,
//	@FormParam("pin_code") String pin_code,@FormParam("address") String address,@FormParam("transaction_type") String transaction_type,@FormParam("status") String status){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() ||customer_first_name==null || customer_first_name.isEmpty()|| customer_last_name==null || customer_last_name.isEmpty()
//				|| customer_type==null || customer_type.isEmpty()|| phone_no==null || phone_no.isEmpty()|| customer_email==null || customer_email.isEmpty()
//				|| state==null || state.isEmpty()|| country==null || country.isEmpty()
//				|| pin_code==null || pin_code.isEmpty()|| address==null|| status==null || status.isEmpty()){
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
//			String customerid = getId("CUST");
//			System.out.println(customerid);
//			try {
//				String Query="insert into customer_master(customer_id,customer_first_name,customer_last_name,type,phone_no,customer_email,country,state,city,pin_code,address,transaction_type,status) "
//						+ "values('"+customerid+"','"+customer_first_name+"','"+customer_last_name+"','"+customer_type+"','"+phone_no+"','"+customer_email+"','"+country+"','"+state+"','"+city+"','"+pin_code+"','"+address+"','"+transaction_type+"','"+status+"')";
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
//	@Path("/addCredit")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response addCredit(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("bank_account_no") String bank_account_no,@FormParam("ifsc_code") String ifsc_code,@FormParam("credit_limit")
//	String credit_limit,@FormParam("credit_consumed") String credit_consumed,@FormParam("credit_available") String credit_available,@FormParam("status") String status,@FormParam("comments") String comments){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| 
//				bank_account_no==null || bank_account_no.isEmpty()|| ifsc_code==null || ifsc_code.isEmpty()
//				|| credit_consumed==null || credit_consumed.isEmpty()|| credit_available==null || credit_available.isEmpty()|| credit_limit==null || credit_limit.isEmpty()
//				|| comments==null || comments.isEmpty()|| status==null || status.isEmpty()){
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
//			
//			try {
//				String Query="insert into customer_credit(customer_id,bank_account_no,ifsc_code,credit_limit,credit_consumed,credit_available,status,comments) "
//						+ "values('"+customer_id+"','"+bank_account_no+"','"+ifsc_code+"','"+credit_limit+"','"+credit_consumed+"','"+credit_available+"','"+status+"','"+comments+"')";
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
//	@Path("/getCustomerCredit")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getCustomerCreditdetails(@FormParam("id") String id){
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
//		}
//		String Query ="select * from customer_credit";
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
//				
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updateCustomercredit")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateCustomercredit(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("bank_account_no") String bank_account_no,@FormParam("credit_limit") String credit_limit,@FormParam("credit_consumed") String credit_consumed,
//	@FormParam("credit_available") String credit_available,@FormParam("ifsc_code") String ifsc_code){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| 
//				bank_account_no==null || bank_account_no.isEmpty()|| credit_limit==null || credit_limit.isEmpty()
//				|| credit_consumed==null || credit_consumed.isEmpty()|| credit_available==null || credit_available.isEmpty()
//				|| ifsc_code==null || ifsc_code.isEmpty()){
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
//
//			try {
//				String Query ="update customer_credit set bank_account_no='"+bank_account_no+"',ifsc_code='"+ifsc_code+"',credit_available='"+credit_available+"',credit_consumed='"+credit_consumed+"',credit_limit='"+credit_limit+"'";
//				PreparedStatement preparedStatement=connection.prepareStatement(Query);
//			
//				int i=preparedStatement.executeUpdate();
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
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editCustomercredit")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editCustomercredit(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
////		
////		String Query="select customer_master.customer_id,customer_master.customer_first_name,customer_master.customer_last_name,customer_master.type,customer_master.phone_no,customer_master.customer_email,customer_master.transaction_type,customer_master.status,customer_credit.bank_account_no,customer_credit.ifsc_code,customer_credit.credit_limit,customer_credit.credit_consumed,customer_credit.credit_available from customer_master"
////				+ " left join customer_credit on customer_credit.customer_id=customer_master.customer_id "
////				+ " where customer_master.customer_id="+"'"+customer_id+"'";
//		String Query ="select * from customer_credit where  customer_id ="+"'"+customer_id+"'";
//		try {
//			SqlConnection();
//			try {
//			System.out.println(Query);
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("data", arrayList);
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
//		
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getCustomerid")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getCustomerid(@FormParam("id") String id){
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
//		}String Query="select customer_id from customer_master";
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
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getCustomerdetails")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getCustomerdetails(@FormParam("id") String id){
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
//		String Query ="select * from customer_master";
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
//	@Path("/updateCustomer")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateCustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("customer_first_name") String customer_first_name,@FormParam("customer_last_name") String customer_last_name,@FormParam("phone_no") String phone_no,
//	@FormParam("status") String status,@FormParam("type") String type,@FormParam("customer_email") String customer_email){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()|| 
//				customer_first_name==null || customer_first_name.isEmpty()|| customer_last_name==null || customer_last_name.isEmpty()
//				|| phone_no==null || phone_no.isEmpty()|| customer_email==null || customer_email.isEmpty()
//				|| type==null || type.isEmpty()||status==null || status.isEmpty()){
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
//
//			try {
//				String Query ="update customer_master set customer_first_name='"+customer_first_name+"',customer_last_name='"+customer_last_name+"',phone_no='"+phone_no+"',customer_email='"+customer_email+"',status='"+status+"',type='"+type+"' where customer_id='"+customer_id+"'";
////				String Query="UPDATE customer_master SET customer_first_name=?,customer_last_name=?,phone_no=?,customer_email=?,transaction_type=?,status=?,type=? where customer_id=?";
////				String Query1="UPDATE customer_credit SET bank_account_no=?,ifsc_code=?,credit_limit=?,credit_consumed=?,credit_available=? where customer_id=?";
////
//				PreparedStatement preparedStatement=connection.prepareStatement(Query);
////				PreparedStatement preparedStatement1=connection.prepareStatement(Query1);
////				preparedStatement.setString(8,customer_id);
////				preparedStatement1.setString(6,customer_id);
////				preparedStatement.setString(1, customer_first_name);
////				preparedStatement.setString(2, customer_last_name);
////				preparedStatement.setString(3, phone_no);
////				preparedStatement1.setString(1, bank_account_no);
////				preparedStatement1.setString(2, ifsc_code);
////				preparedStatement.setString(4, customer_email);
////				preparedStatement1.setString(3, credit_limit);
////				preparedStatement.setString(5, transaction_type);
////				preparedStatement1.setString(4, credit_consumed);
////				preparedStatement.setString(5, credit_available);
////				preparedStatement.setString(6, status);
////				preparedStatement.setString(7, type);
//				
//				int i=preparedStatement.executeUpdate();
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
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editCustomerProfile")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editCustomerProfile(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || customer_id==null || customer_id.isEmpty()) {
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
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String Query ="select * from customer_master where  customer_id ="+"'"+customer_id+"'";
//
//		
////		String Query="select customer_master.customer_id,customer_master.customer_first_name,customer_master.customer_last_name,customer_master.type,customer_master.phone_no,customer_master.customer_email,customer_master.transaction_type,customer_master.status,customer_credit.bank_account_no,customer_credit.ifsc_code,customer_credit.credit_limit,customer_credit.credit_consumed,customer_credit.credit_available from customer_master"
////				+ " left join customer_credit on customer_credit.customer_id=customer_master.customer_id "
////				+ " where customer_master.customer_id="+"'"+customer_id+"'";
//		try {
//			SqlConnection();
//			try {
//			System.out.println(Query);
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("data", arrayList);
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
	 Insert api for Customer 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addcustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response branch(@FormParam("id") String id,@FormParam("customer_name") String customer_name,@FormParam("customer_email") String customer_email,@FormParam("customer_address") String customer_address
			,@FormParam("customer_phonenum") String customer_phonenum,@FormParam("country") String country,
			@FormParam("state") String state,@FormParam("city") String city){
		JSONObject jsonObject=new JSONObject();
		if(customer_name==null || customer_name.isEmpty()|| customer_email==null || customer_email.isEmpty()|| customer_address==null || customer_address.isEmpty()|| customer_phonenum==null || customer_phonenum.isEmpty()){
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
			String customerid=getProductSubCategoryId("CUSTR");
			try {
				String email=(String) jsObject.get("email");
				String Query="insert into tbl_customer(customer_id,customer_name,customer_email,customer_address,customer_phonenum,country,state,city,createdby) "
						+ "values('"+customerid+"','"+customer_name+"','"+customer_email+"','"+customer_address+"','"+customer_phonenum+"','"+country+"','"+state+"','"+city+"','"+email+"')";
				
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
				
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	/*
	 get customer list api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getCustomerlist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response discountList(@FormParam("id") String id){
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
		String Query ="select * from tbl_customer";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	/*
	 Edit Customer Api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/editcustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editcustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(customer_id==null || customer_id.isEmpty()) {
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
		
		String Query="select * from tbl_customer where customer_id="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update ustomerApi
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatecustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatecustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id,@FormParam("customer_name") String customer_name,@FormParam("customer_email") String customer_email,@FormParam("customer_address")
	String customer_address,@FormParam("customer_phonenum") String customer_phonenum
	,@FormParam("country") String country,
	@FormParam("state") String state,@FormParam("city") String city,@FormParam("credit_limit") double credit_limit){
		JSONObject jsonObject=new JSONObject();
		
		if(customer_id==null || customer_id.isEmpty()|| customer_name==null || customer_name.isEmpty()|| customer_email==null || customer_email.isEmpty()
				|| customer_address==null || customer_address.isEmpty()|| customer_phonenum==null || customer_phonenum.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
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
				String Query="UPDATE tbl_customer SET customer_name='"+customer_name+"',customer_email='"+ customer_email+"',"
						+ "customer_address='"+customer_address+"',customer_phonenum='"+customer_phonenum+"',"
								+ "country='"+country+"',city='"+city+"',state='"+state+"',updatedon=current_date,updatedby='"+email+"',credit_limit='"+credit_limit+"' "
										+ " where customer_id='"+customer_id+"'";
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
	
	/*
	 Delete Customer Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletecustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletecustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		
		if(customer_id==null || customer_id.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
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
				String Query="DELETE from tbl_customer where customer_id='"+customer_id+"'";
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
	
	
	public static String getProductSubCategoryId(String id){

		String numberrange_function="select productsubcategory_id_numberrange_function('"+id+"')";

		int Rid=0;

		try{
			System.out.println(numberrange_function);
			CallableStatement CALLABLESTATMENT=connection.prepareCall(numberrange_function);

			CALLABLESTATMENT.execute();

			ResultSet resultset=CALLABLESTATMENT.getResultSet();

			while(resultset.next()){

				Rid=resultset.getInt(1);

			}if(Rid<0){

				return "Failed 0";

			}

			System.out.println("Rid :"+Rid);

			String ReqId=id+"000";

			String s=String.valueOf(Rid);

			ReqId=ReqId.substring(0,ReqId.length()-s.length());

			ReqId=ReqId.concat(String.valueOf(Rid));

			return ReqId;

		}catch(Exception exc){

			System.out.println(exc.getMessage());

			return "Failed";

		}

	}
	
//	@POST
//	@Path("getFixedCoustomerId")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public static Response getFixedCoustomerId(@FormParam("id") String id){
//		
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
//		}
//		try {
//			SqlConnection();
//			try {
//				String purchaseorderid= getFixedCustomerId("PUR");
//				System.out.println("the value is :"+purchaseorderid);
//				if(purchaseorderid!=null&&!purchaseorderid.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", purchaseorderid);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
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
//		
//		
//		
//		
//	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addFixedCustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addFixedCoustomer(@FormParam("id") String id,@FormParam("customer_name") String customer_name,@FormParam("customer_email") String customer_email,
			@FormParam("customer_address") String customer_address,@FormParam("customer_phonenum") String customer_phonenum,@FormParam("country") String country,
			@FormParam("state") String state,@FormParam("city") String city,@FormParam("is_true") int is_true,@FormParam("credit_limit") double credit_limit){
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
			int mem_id=0;
			SqlConnection();
			String customerid=getGenerateId("CUSTOMER",5,connection);
			try {
				if(is_true==1){
					@SuppressWarnings("unused")
					String insertAccounts = "insert into member_table(member_type_id,member_name)"
							+ "values(2,'"+customer_name+"') returning member_id ";
					
					ResultSet resultSet = connection.createStatement().executeQuery(insertAccounts);
					while(resultSet.next()){
						mem_id=resultSet.getInt("member_id");
					}
				}
				
				String userInsertQuery= "insert into tbl_user(username,password,roleid,emailid,status,auth_id) "
						+ "values('"+customer_name+"','123456',5,'"+customer_email+"','A','"+customerid+"')";
                int insertStatus=statement.executeUpdate(userInsertQuery);
                if(insertStatus>0){
                	   jsonObject.clear();
                       jsonObject.put("Status", "Success");
                       jsonObject.put("Message", "Customer User Added Successfully");
                }else{
               jsonObject.clear();
               jsonObject.put("Status", "Failed");
               jsonObject.put("Message", "Failed to add Customer");
               return Response.ok()
			                  .entity(jsonObject)
			                  .header("Access-Control-Allow-Methods", "POST").build();
                     }
				
				String email=(String) jsObject.get("email");
				String Query="insert into fixed_customer_details(customer_id,customer_name,customer_email,customer_address,customer_contact_number,country,state,city,createdby,member_id,credit_limit) "
						+ "values('"+customerid+"','"+customer_name+"','"+customer_email+"','"+customer_address+"','"+customer_phonenum+"','"+country+"','"+state+"','"+city+"','"+email+"','"+mem_id+"','"+credit_limit+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Customer Added Successfully");
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
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getStoreCustomerList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response inventoryCustomerlist(@FormParam("id") String id,@FormParam("customer_type") String customer_type){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		if(customer_type.equals("B2B")){
		 Query ="select * from fixed_customer_details";
		}else{
			 Query="select salesorder_id as customer_id,customer_name,customer_email,customer_contact_number,customer_address FROM sales_customer_details";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	
	@POST
	@Path("/editInventoryCustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editInventoryCustomerList(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		if(customer_id==null || customer_id.isEmpty()) {
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
		
		String Query="select * from fixed_customer_details where customer_id="+"'"+customer_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update ustomerApi
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateInventoryCustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateInventoryCustomerList(@FormParam("id") String id,@FormParam("customer_id") String customer_id, 
			@FormParam("customer_name") String customer_name,@FormParam("customer_email") String customer_email,
			@FormParam("customer_address") String customer_address,@FormParam("customer_phonenum") String customer_phonenum,
			@FormParam("country") String country,
			@FormParam("state") String state,@FormParam("city") String city){
		JSONObject jsonObject=new JSONObject();
		
		if(customer_name==null || customer_name.isEmpty()|| customer_email==null || customer_email.isEmpty()
				|| customer_address==null || customer_address.isEmpty()|| customer_phonenum==null || customer_phonenum.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
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
				String Query="UPDATE fixed_customer_details SET customer_name='"+customer_name+"',customer_email='"+ customer_email+"',"
						+ "customer_address='"+customer_address+"',customer_contact_number='"+customer_phonenum+"',"
								+ "country='"+country+"',city='"+city+"',state='"+state+"',updatedon=current_date,updatedby='"+email+"'"
										+ " where customer_id='"+customer_id+"'";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete Customer Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deleteInventoryCustomer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deleteInventoryCustomer(@FormParam("id") String id,@FormParam("customer_id") String customer_id){
		JSONObject jsonObject=new JSONObject();
		
		if(customer_id==null || customer_id.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
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
				String Query="DELETE from fixed_customer_details where customer_id='"+customer_id+"'";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	
	
	
	
	
	public static String getGenerateId(String id,int length,Connection conn){

		String numberrange_function="select fn_generate_id('"+id+"',"+length+")";

		String Rid=null;

		try{
			System.out.println(numberrange_function);
			CallableStatement CALLABLESTATMENT=conn.prepareCall(numberrange_function);

			CALLABLESTATMENT.execute();

			ResultSet resultset=CALLABLESTATMENT.getResultSet();

			while(resultset.next()){

				Rid=resultset.getString(1);

			}
			
			if(Rid==null){

				return "Failed 0";

			}

			System.out.println("Rid :"+Rid);

			return Rid;

		}catch(Exception exc){

			System.out.println(exc.getMessage());

			return "Failed";

		}

	}
	
	
	
	
	
}
