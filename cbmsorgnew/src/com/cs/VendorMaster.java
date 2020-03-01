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
import org.json.simple.parser.JSONParser;

@Path("vendor")
@SuppressWarnings({"unchecked","unused","rawtypes"})
public class VendorMaster {
	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	 static JSONArray jsonarray=new JSONArray();
	 static JSONObject jsonobject=new JSONObject();
	 
	private static Log log=LogFactory.getLog(VendorMaster.class);

	 /*
	 Insert api for vendor
	 */
	
	@POST
	@Path("/addVendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addVendor(@FormParam("id") String id,@FormParam("vendor_name") String vendor_name,@FormParam("vendor_email") String vendor_email,@FormParam("vendor_address") String vendor_address
			,@FormParam("vendor_phonenum") String vendor_phonenum,@FormParam("product_description") String product_description,@FormParam("status") String status,@FormParam("credit_limit") double credit_limit){
		JSONObject jsonObject=new JSONObject();
		
		if(vendor_name==null || vendor_name.isEmpty()|| vendor_email==null || vendor_email.isEmpty()|| vendor_address==null || vendor_address.isEmpty()|| vendor_phonenum==null || vendor_phonenum.isEmpty()){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
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
			System.out.println(role_id);
		}
		try {
			
			SqlConnection();
			String vendor_id=Customer.getGenerateId("VEN", 5,connection);
			try {
				String Query="insert into tbl_vendor1(vendor_id,vendor_name,vendor_email,vendor_address,vendor_phonenum,product_description,status,credit_limit) "
						+ "values('"+vendor_id+"','"+vendor_name+"','"+vendor_email+"','"+vendor_address+"','"+vendor_phonenum+"','"+product_description+"','"+status+"','"+credit_limit+"')";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Vendor Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add Vendor");
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
				
				e.printStackTrace();
			}
			}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	/*
	 get vendor list api
	 */
	
	
	@POST
	@Path("/getVendorlist")
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String Query ="select * from tbl_vendor";
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
	 Edit Vendor Api
	 */
	
	@POST
	@Path("/editvendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editvendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
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
		
		String Query="select * from tbl_vendor where vendor_id="+"'"+vendor_id+"'";
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
	 Update Vendor Api
	 */
	
	@POST
	@Path("/updatevendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatevendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id,@FormParam("vendor_name") String vendor_name,@FormParam("vendor_email") String vendor_email,@FormParam("vendor_address")
	String vendor_address,@FormParam("vendor_phonenum") String vendor_phonenum,@FormParam("product_description") String product_description,@FormParam("status") String status){
		JSONObject jsonObject=new JSONObject();
		
		if(vendor_id==null || vendor_id.isEmpty()|| vendor_name==null || vendor_name.isEmpty()|| vendor_email==null || vendor_email.isEmpty()
				|| vendor_address==null || vendor_address.isEmpty()|| vendor_phonenum==null || vendor_phonenum.isEmpty()) {
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			try {
				JSONArray dataArray=new JSONArray();
				org.json.JSONArray jsArray = new org.json.JSONArray(product_description);
				for(int i=0;i<jsArray.length();i++){
					org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
					String pn = orgjsObject.getString("product_description");
					JSONObject cnjobj = new JSONObject();
					cnjobj.clear();
					cnjobj.put("obj", pn);
					dataArray.add(cnjobj);
				}JSONObject jsfinal = new JSONObject();
//				jsfinal.clear();
				jsfinal.put("type", "json");
				jsfinal.put("value", dataArray);	
				
				String Query="UPDATE tbl_vendor SET vendor_name='"+vendor_name+"',vendor_email='"+ vendor_email+"',"
						+ "vendor_address='"+vendor_address+"',vendor_phonenum='"+vendor_phonenum+"',"
								+ "product_description='"+dataArray+"',status='"+status+"' "
										+ "where vendor_id='"+vendor_id+"'";
				int st=statement.executeUpdate(Query);
				if(st>0){
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
	 Delete Vendor Api
	 */
	
	@POST
	@Path("/deletevendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletevendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		
		if(vendor_id==null || vendor_id.isEmpty()) {
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="DELETE from tbl_vendor where vendor_id='"+vendor_id+"'";
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
	
	
	
	@POST
	@Path("/getVendorCount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorCount(@FormParam("id") String id){
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
		}
		//String Query ="select count(*),(select count(*) from tbl_vendor where status='Active') as ActiveCount,(select count(*) from tbl_vendor where status='Inactive') as InActiveCount from tbl_vendor";
		//String Query1="select count(*),(select count(*) from tbl_productcategory) as product_category_count,(select count(*) from tbl_addproduct) as productcount,(select count(*) from tbl_productsubcategory) as subcategory_count,(select count(*) from tbl_vendor) as vendor_count,(select count(*) from tbl_vendor where status='Active') as vendor_ActiveCount,(select count(*) from tbl_vendor where status='Inactive') as Vendor_InActiveCount,(select count(*) from tbl_warehouse) as warehouse_count from tbl_productbrand";
		String Query1="select count(*),"
				+ "(select count(*) from tbl_category where mark_for_deletion=0) as category_count, "
				+ "(select count(*) from tbl_products where mark_for_deletion=0) as productcount, "
				+ "(select count(*) from tbl_products where product_status='Active' and mark_for_deletion=0) as product_Active_Count,"
				+ "(select count(*) from tbl_products where product_status='InActive' and mark_for_deletion=0) as product_InActive_Count,"
				+ "(select count(*) from tbl_subcategory where mark_for_deletion=0) as subcategory_count, "
				+ "(select count(*) from tbl_vendor1 where mark_for_deletion=0) as vendor_count, "
				+ "(select count(*) from tbl_vendor1 where status='Active' and mark_for_deletion=0) as vendor_ActiveCount,"
				+ "(select count(*) from tbl_vendor1 where status='Inactive' and mark_for_deletion=0) as Vendor_InActiveCount,"
				+ "(select count(*) from tbl_warehouse1 where mark_for_deletion=0) as warehouse_count from tbl_brand where mark_for_deletion=0";
		try {
			SqlConnection();
			try { 
				resultset=statement.executeQuery(Query1);
				
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	
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
	
	
	public static void SqlConnection() throws IOException {
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
	 * public static String getProductVendorId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_vendor_id_numberrange_function('"+id+"')";
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
	 * }if(Rid<0){ return "Failed 0"; }
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
	
	
	@POST
	@Path("/getPurchaseOrderListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getPurchaseOrderList(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id){
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
		}
		String Query ="select id,product_description,product_unit,product_quantity from purchase_order where purchaseorder_id='"+purchaseorder_id+"'";
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
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}	
	
	 /*
	 Insert api for vendor
	 */
	
	@POST
	@Path("/addVendorApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addVendorApi(@FormParam("id") String id,@FormParam("vendor_name") String vendor_name,@FormParam("vendor_mailid") String vendor_mailid,@FormParam("vendor_contact_number1") String vendor_contact_number1
			,@FormParam("vendor_contact_number2") String vendor_contact_number2,@FormParam("vendor_address1") String vendor_address1,@FormParam("vendor_address2") String vendor_address2,@FormParam("status") String status,
			@FormParam("gst_number") String gst_number,@FormParam("bank_account_details1") String bank_account_details1,@FormParam("bank_account_details2") String bank_account_details2,@FormParam("city") String city,
			@FormParam("state") String state,@FormParam("country") String country,@FormParam("is_true") int is_true,@FormParam("credit_limit") double credit_limit){
		JSONObject jsonObject=new JSONObject();
		
//		product_description = "ssv-sss-sls";
//		String[] values = product_description.split("-");
//		System.out.println(Arrays.toString(values));
		
		
		
//		if(vendor_name==null || vendor_name.isEmpty()|| vendor_email==null || vendor_email.isEmpty()|| vendor_address==null || vendor_address.isEmpty()|| vendor_phonenum==null || vendor_phonenum.isEmpty()){
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			int mem_id=0;
			SqlConnection();
			String vendor_id=Customer.getGenerateId("VENDOR", 5,connection);
			try {
				if(is_true==1){
					
					String insertAccounts = "insert into member_table(member_type_id,member_name)"
							+ "values(1,'"+vendor_name+"') returning member_id ";
					
					ResultSet resultSet = connection.createStatement().executeQuery(insertAccounts);
					while(resultSet.next()){
						mem_id=resultSet.getInt("member_id");
					}
				}
					String userInsertQuery= "insert into tbl_user(username,password,roleid,emailid,status,auth_id) "
							+ "values('"+vendor_name+"','123456',2,'"+vendor_mailid+"','A','"+vendor_id+"')";
	                int insertStatus=statement.executeUpdate(userInsertQuery);
	                if(insertStatus>0){
	                	   jsonObject.clear();
	                       jsonObject.put("Status", "Success");
	                       jsonObject.put("Message", "Vendor User Added Successfully");
	                }else{
	               jsonObject.clear();
	               jsonObject.put("Status", "Failed");
	               jsonObject.put("Message", "Failed to add Vendor");
	               return Response.ok()
				                  .entity(jsonObject)
				                  .header("Access-Control-Allow-Methods", "POST").build();
	                     }
				
				String email=(String) jsObject.get("email");
				String Query="insert into tbl_vendor1(vendor_id,vendor_name,vendor_mailid,vendor_contact_number1,vendor_contact_number2,vendor_address1,vendor_address2,status,gst_number,bank_account_details1,bank_account_details2,city,state,country,created_by,member_id,credit_limit) "
						+ "values('"+vendor_id+"','"+vendor_name+"','"+vendor_mailid+"','"+vendor_contact_number1+"','"+vendor_contact_number2+"','"+vendor_address1+"','"+vendor_address2+"','"+status+"','"+gst_number+"','"+bank_account_details1+"','"+bank_account_details2+"','"+city+"','"+state+"','"+country+"','"+email+"','"+mem_id+"',"+credit_limit+")";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Vendor Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add Vendor");
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
				
				e.printStackTrace();
			}
			}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	/*
	 get vendor list api
	 */
	
	
	@POST
	@Path("/vendorList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorList(@FormParam("id") String id){
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
			System.out.println(role_id);
		}
		String Query ="select * from tbl_vendor1 where mark_for_deletion=0";
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
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	/*
	 Edit Vendor Api
	 */
	
	@POST
	@Path("/editVendorData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editvendorData(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || vendor_id==null || vendor_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
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
			System.out.println(role_id);
		}
		
		String Query="select * from tbl_vendor1 where vendor_id="+"'"+vendor_id+"'";
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
	 Update Vendor Api
	 */
	
	@POST
	@Path("/updateVendorData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateVendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id,@FormParam("vendor_name") String vendor_name,@FormParam("vendor_mailid") String vendor_mailid,@FormParam("vendor_contact_number1") String vendor_contact_number1
			,@FormParam("vendor_contact_number2") String vendor_contact_number2,@FormParam("vendor_address1") String vendor_address1,@FormParam("vendor_address2") String vendor_address2,@FormParam("status") String status,@FormParam("product_description") String product_description,
			@FormParam("gst_number") String gst_number,@FormParam("bank_account_details1") String bank_account_details1,@FormParam("bank_account_details2") String bank_account_details2,@FormParam("city") String city,@FormParam("state") String state,@FormParam("country") String country,@FormParam("credit_limit") double credit_limit){
		JSONObject jsonObject=new JSONObject();
		
//		if(vendor_id==null || vendor_id.isEmpty()|| vendor_name==null || vendor_name.isEmpty()|| vendor_email==null || vendor_email.isEmpty()
//				|| vendor_address==null || vendor_address.isEmpty()|| vendor_phonenum==null || vendor_phonenum.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			try {
				String email=(String) jsObject.get("email");
				
				JSONArray dataArray=new JSONArray();
//				org.json.JSONArray jsArray = new org.json.JSONArray(product_description);
//				for(int i=0;i<jsArray.length();i++){
//					org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
//					String pn = orgjsObject.getString("product_description");
//					JSONObject cnjobj = new JSONObject();
//					cnjobj.clear();
//					cnjobj.put("obj", pn);
//					dataArray.add(cnjobj);
//				}
//				JSONObject jsfinal = new JSONObject();
////				jsfinal.clear();
//				jsfinal.put("type", "json");
//				jsfinal.put("value", dataArray);
				
				String Query="UPDATE tbl_vendor1 SET vendor_name='"+vendor_name+"',vendor_mailid='"+ vendor_mailid+"',"
						+ "vendor_contact_number1='"+vendor_contact_number1+"',vendor_contact_number2='"+vendor_contact_number2+"',"
								+ "vendor_address1='"+vendor_address1+"',vendor_address2='"+vendor_address2+"',gst_number='"+gst_number+"',"
										+ "bank_account_details1='"+bank_account_details1+"',bank_account_details2='"+bank_account_details2+"',"
										+ "status='"+status+"',updated_date=current_date,updated_by='"+email+"',credit_limit='"+credit_limit+"' where vendor_id='"+vendor_id+"'";
				System.out.println("the query is:"+ Query);
				int st=statement.executeUpdate(Query);
				if(st>0){
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
	
	/*
	 Delete Vendor Api
	 */
	
	@POST
	@Path("/deleteVendorData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deleteVendorData(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		
		if(vendor_id==null || vendor_id.isEmpty()) {
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="update tbl_vendor1 set mark_for_deletion=1 where vendor_id='"+vendor_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Vendor Deleted Successfully");
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
	
	
	@POST
	@Path("/vendorPricingList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorPricingList(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
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
				
				vendor_id=auth_id;
			}
			//System.out.println(role_id);
		}
//		String Query ="select tbl_vendor_pricing_table.product_id,tbl_vendor_pricing_table.product_name,tbl_category.category_name, "
//				+ "tbl_subcategory.subcategory_name, product_unit.product_unit as unit_value,tbl_vendor_pricing_table.discount, "
//				+ "tbl_vendor_pricing_table.product_price,tbl_vendor_pricing_table.product_cgst, "
//				+ "tbl_vendor_pricing_table.product_sgst,tbl_vendor_pricing_table.product_igst, "
//				+ "tbl_brand.brand_name from tbl_vendor_pricing_table "
//				+ "inner join product_unit on product_unit.id=tbl_vendor_pricing_table.product_unit "
//				+ "inner join tbl_category on tbl_vendor_pricing_table.product_category_id= tbl_category.category_id "
//				+ "inner join tbl_subcategory on tbl_vendor_pricing_table.product_subcategory_id=tbl_subcategory.subcategory_id "
//				+ "inner join tbl_brand on tbl_vendor_pricing_table.product_brand_id=tbl_brand.brand_id "
//				+ "where tbl_vendor_pricing_table.vendor_id='"+vendor_id+"' and tbl_vendor_pricing_table.mark_for_deletion=0";
		
		String Query="select distinct tbl_vendor_pricing_table.product_id,"
				+ "tbl_products.product_name||'-'||tbl_brand.brand_name||'-'||'-'||product_unit.product_unit "
				+ "as product_description,tbl_vendor_pricing_table.discount, "
				+ "tbl_vendor_pricing_table.vendor_id,"
				+ "tbl_vendor_pricing_table.product_price,tbl_vendor_pricing_table.product_cgst, "
				+ "tbl_vendor_pricing_table.product_sgst,tbl_vendor_pricing_table.product_igst, "
				+ "tbl_brand.brand_name from tbl_vendor_pricing_table "
				+ "inner join tbl_products on tbl_products.product_id=tbl_vendor_pricing_table.product_id "
				+ "inner join product_unit on product_unit.id=tbl_vendor_pricing_table.product_unit "
				+ "inner join tbl_brand on tbl_vendor_pricing_table.product_brand_id=tbl_brand.brand_id "
				+ "where tbl_vendor_pricing_table.vendor_id='"+vendor_id+"' and tbl_vendor_pricing_table.mark_for_deletion=0";

		
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayList1(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
					System.out.println(arrayList);
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
	
	
	@POST
	@Path("/editVendorPricingList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editVendorPricingList(@FormParam("id") String id,@FormParam("product_id") String product_id
			,@FormParam("vendor_id") String vendor_id){
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
				vendor_id=auth_id;
			}
			System.out.println(auth_id);
		}
		try {
			SqlConnection();
			try {
				String auth_id=(String) jsObject.get("auth_id");
				String Query="select distinct tbl_vendor_pricing_table.product_id,"
						+ "tbl_products.product_name||'-'||tbl_brand.brand_name||'-'||'-'||product_unit.product_unit "
						+ "as product_description,tbl_vendor_pricing_table.discount, "
						+ "tbl_vendor_pricing_table.vendor_id,"
						+ "tbl_vendor_pricing_table.product_price,tbl_vendor_pricing_table.product_cgst, "
						+ "tbl_vendor_pricing_table.product_sgst,tbl_vendor_pricing_table.product_igst, "
						+ "tbl_brand.brand_name from tbl_vendor_pricing_table "
						+ "inner join tbl_products on tbl_products.product_id=tbl_vendor_pricing_table.product_id "
						+ "inner join product_unit on product_unit.id=tbl_vendor_pricing_table.product_unit "
						+ "inner join tbl_brand on tbl_vendor_pricing_table.product_brand_id=tbl_brand.brand_id "
						+ "where tbl_vendor_pricing_table.product_id='"+product_id+"' and"
						+ " tbl_vendor_pricing_table.vendor_id='"+vendor_id+"' and tbl_vendor_pricing_table.mark_for_deletion=0";
				
				System.out.println("the query is:"  +Query);
				
				resultset=statement.executeQuery(Query);
				
				ArrayList arrayList=convertResultSetIntoArrayList1(resultset);
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
	
	
	@POST
	@Path("/vendorsBasedOnProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorsBasedOnProduct(@FormParam("id") String id,@FormParam("product_id") String product_id){
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
				
			}
		}
		try {
			SqlConnection();
			try {
				String auth_id=(String) jsObject.get("auth_id");
				String Query="select  tbl_vendor_pricing_table.product_id,"
						+ "tbl_products.product_name||'-'||tbl_brand.brand_name||'-'||'-'||product_unit.product_unit "
						+ "as product_description,tbl_vendor_pricing_table.discount, "
						+ "tbl_vendor_pricing_table.vendor_id,"
						+ "tbl_vendor_pricing_table.product_price, "
						+ "tbl_brand.brand_name,vendor_name, vendor_mailid, vendor_contact_number1 from tbl_vendor_pricing_table "
						+ "inner join tbl_products on tbl_products.product_id=tbl_vendor_pricing_table.product_id "
						+ " inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_vendor_pricing_table.vendor_id "
						+ "left join product_unit on product_unit.id=tbl_vendor_pricing_table.product_unit "
						+ "left join tbl_brand on tbl_vendor_pricing_table.product_brand_id=tbl_brand.brand_id "
						+ "where tbl_vendor_pricing_table.product_id='"+product_id+"' and tbl_vendor_pricing_table.mark_for_deletion=0 ";
				
				System.out.println("the query is:"  +Query);
				
				resultset=statement.executeQuery(Query);
				
				ArrayList arrayList=convertResultSetIntoArrayList1(resultset);
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
	
	
	@POST
	@Path("/updateProductPricing")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateProductPricing(@FormParam("id") String id,@FormParam("product_id") String product_id,
			@FormParam("price") String price,@FormParam("discount") String discount,@FormParam("sgst") String sgst,
			@FormParam("cgst") String cgst,@FormParam("igst") String igst,@FormParam("vendor_id") String vendor_id){
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
			String auth_id=(String) jsonObject.get("auth_id");
			if(role_id==1){
				
			}else{
				vendor_id=auth_id;
			}
		}
		String igst1=null;
		String sgst1=null;
		String cgst1=null;
		String discount1=null;
		
		try {
			SqlConnection();
			try {
				
				if(!igst.contains("%")||!sgst.contains("%")||!cgst.contains("%")||discount.contains("%")){
					igst1=igst+"%";
					sgst1=sgst+"%";
					cgst1=cgst+"%";
					discount1=discount+"%";
				}else{
					
				}
				String email=(String) jsObject.get("email");
				String auth_id = (String) jsObject.get("auth_id");
//				String Query="insert into tbl_vendor_pricing_table(product_id,product_price,discount,product_sgst,product_cgst,product_igst,created_by,vendor_id) "
//						+ "values('"+product_id+"','"+price+"','"+discount+"','"+sgst+"','"+cgst+"','"+igst+"','"+email+"','"+auth_id+"')";
				
				String Query="update tbl_vendor_pricing_table set product_price='"+price+"',"
						+ "discount='"+discount1+"',product_sgst='"+sgst1+"',"
								+ "product_cgst='"+cgst1+"',product_igst='"+igst1+"',"
								+ "updated_by='"+email+"',updated_date=current_date "
								+ "where product_id='"+product_id+"' and vendor_id='"+vendor_id+"'";
				int st=statement.executeUpdate(Query);
				if(st>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Update");
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
	
	@POST
	@Path("/deleteProductPricing")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deleteProductPricing(@FormParam("id") String id,@FormParam("product_id") String product_id,
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id=(String) jsonObject.get("auth_id");
if(role_id==1){
				
			}else{
				vendor_id=auth_id;
			}
		}
		String igst1=null;
		String sgst1=null;
		String cgst1=null;
		String discount1=null;
		
		try {
			SqlConnection();
			try {
				
				
				String email=(String) jsObject.get("email");
				String auth_id = (String) jsObject.get("auth_id");
//				String Query="insert into tbl_vendor_pricing_table(product_id,product_price,discount,product_sgst,product_cgst,product_igst,created_by,vendor_id) "
//						+ "values('"+product_id+"','"+price+"','"+discount+"','"+sgst+"','"+cgst+"','"+igst+"','"+email+"','"+auth_id+"')";
				
				String Query="delete from  tbl_vendor_pricing_table where product_id='"+product_id+"' and vendor_id='"+vendor_id+"'";
				int st=statement.executeUpdate(Query);
				if(st>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Deleted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Update");
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
	
	@POST
	@Path("/linkVendorForProduct1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response linkVendorForProduct1(@FormParam("id") String id,@FormParam("product_id") String product_id,@FormParam("vendor_id") String vendor_id,@FormParam("list") String list){
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
			String auth_id=(String) jsonObject.get("auth_id");
			if(role_id==1){
				
			}else{
				vendor_id=auth_id;
			}
		}
		
		try {
			SqlConnection();
			
			try {
				
				connection.setAutoCommit(false);
				
				String email=(String) jsObject.get("email");
				String auth_id = (String) jsObject.get("auth_id");
//				String Query="insert into tbl_vendor_pricing_table(product_id,product_price,discount,product_sgst,product_cgst,product_igst,created_by,vendor_id) "
//						+ "values('"+product_id+"','"+price+"','"+discount+"','"+sgst+"','"+cgst+"','"+igst+"','"+email+"','"+auth_id+"')";
				
				int insert=0;
				JSONParser parser= new JSONParser();
				ArrayList<HashMap> data= (ArrayList<HashMap>) parser.parse(list);
				
				
				for(HashMap map: data) {
					String vendorid=null;
					String price=null;
					String discount=null;
					vendor_id=(String) map.get("vendor_id");
					price= (String) map.get("price");
					discount=(String) map.get("discount");
					System.out.println("vendor_id "+vendor_id);
					String Query="insert into tbl_vendor_pricing_table(product_id,vendor_id,product_price,discount) values(?,?,?,?) " + 
							"    on conflict (product_id,vendor_id) do update set product_price=?,discount=? " + 
							"    where tbl_vendor_pricing_table.product_id=? and tbl_vendor_pricing_table.vendor_id=? ";
					PreparedStatement statement=connection.prepareCall(Query);
					statement.setString(1, product_id);
					statement.setString(2, vendor_id);
					statement.setDouble(3, Double.valueOf(price));
					statement.setString(4, discount);
					statement.setDouble(5, Double.valueOf(price));
					statement.setString(6, discount);
					statement.setString(7, product_id);
					statement.setString(8, vendor_id);
					int st=statement.executeUpdate();
					System.out.println("Query--- "+Query);
					insert=insert+st;
					System.out.println("Query--- "+statement);
				}
				if(insert==data.size()){
					connection.commit();
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}else{
					connection.rollback();
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Update");
				}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	

	public static ArrayList convertResultSetIntoArrayList1(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("product_price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}
				else if(columnName.contains("product_cgst")||columnName.contains("product_sgst")||columnName.contains("igst")||columnName.contains("discount")){
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
	
	
	/*author: siba sankar
	 * Date: 27/01/2020
	 * */
	@POST
	@Path("/linkVendorForProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response linkVendorForProduct(@FormParam("id") String id,@FormParam("product_id") String product_id,@FormParam("vendor_id") String vendor_id,@FormParam("list") String list){
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
			String auth_id=(String) jsonObject.get("auth_id");
			if(role_id==1){
				
			}else{
				vendor_id=auth_id;
			}
		}
		
		try {
			SqlConnection();
			
			try {
				int st=0;
				
				String email=(String) jsObject.get("email");
				String auth_id = (String) jsObject.get("auth_id");
//				String Query="insert into tbl_vendor_pricing_table(product_id,product_price,discount,product_sgst,product_cgst,product_igst,created_by,vendor_id) "
//						+ "values('"+product_id+"','"+price+"','"+discount+"','"+sgst+"','"+cgst+"','"+igst+"','"+email+"','"+auth_id+"')";
				
				int insert=0;
				JSONParser parser= new JSONParser();
				ArrayList<HashMap> data= (ArrayList<HashMap>) parser.parse(list);
				
				System.out.println("data "+data);
				for(HashMap map: data) {
					String vendorid=null;
					String price=null;
					String discount=null;
					vendor_id=(String) map.get("vendor_id");
					price= (String) map.get("price");
					discount=(String) map.get("discount");
					System.out.println("vendor_id "+vendor_id);
//					String Query1= "select 1 from tbl_vendor_pricing_table where vendor_id='"+vendor_id+"',product_id='"+product_id+"'";
//					resultset=statement.executeQuery(Query1);
//					int result=0;
//					while(resultset.next()) {
//						 result=resultset.getInt(1);
//					}
					String product_name=null;
					String product_code=null;
					String product_category_id=null;
					String product_subcategory_id=null;
					String product_brand_id=null;
					int product_unit=0;
					int product_size=0;
					String product_image=null;
					int alert_quantity=0;
					double product_cgst=0;
					double product_sgst=0;
					double product_igst=0;
					String product_taxable=null;
					String created_by=null;
					String product_substitute=null;
					String product_status=null;
					int mark_for_deletion=0;
					
//					System.out.println("result "+result);
//					if(result==0) {
						String Query2="select product_name,product_code,product_category_id,product_subcategory_id,product_brand_id,product_unit," + 
								"  product_size,product_image,alert_quantity,product_cgst,product_sgst,product_igst,product_taxable,created_by," + 
								"  product_substitute,product_status,mark_for_deletion" + 
								"  from tbl_vendor_pricing_table where product_id='"+product_id+"' ";
						resultset=statement.executeQuery(Query2);
						System.out.println("Query2 "+Query2);
						while(resultset.next()) {
							 product_name=resultset.getString("product_name");
							 product_code=resultset.getString("product_code");
							 product_category_id=resultset.getString("product_category_id");
							 product_subcategory_id=resultset.getString("product_subcategory_id");
							 product_brand_id=resultset.getString("product_brand_id");
							 product_unit=resultset.getInt("product_unit");
							 product_size=resultset.getInt("product_size");
							 product_image=resultset.getString("product_image");
							 alert_quantity=resultset.getInt("alert_quantity");
							 product_cgst=resultset.getDouble("product_cgst");
							 product_sgst=resultset.getDouble("product_sgst");
							 product_igst=resultset.getDouble("product_igst");
							 product_taxable=resultset.getString("product_taxable");
							 created_by=resultset.getString("created_by");
							 product_substitute=resultset.getString("product_substitute");
							 product_status=resultset.getString("product_name");
							 mark_for_deletion=resultset.getInt("mark_for_deletion");

							
						}
						System.out.println("mark_for_deletion --"+mark_for_deletion);
						System.out.println("product_name --"+product_name);
						System.out.println("product_status --"+product_status);
						

						String Query3="insert into tbl_vendor_pricing_table(vendor_id,product_id,product_price,discount," + 
								"  product_name,product_code,product_category_id,product_subcategory_id,\r\n" + 
								"  product_brand_id,product_unit,product_size,product_image,alert_quantity,product_cgst,product_sgst,product_igst," + 
								"  product_taxable,created_by,product_substitute,product_status,mark_for_deletion)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
								+ "on conflict (product_id,vendor_id) do update set product_price=?,discount=? " 
								+ "where tbl_vendor_pricing_table.product_id=? and tbl_vendor_pricing_table.vendor_id=?";
						PreparedStatement prepareStatement=connection.prepareCall(Query3);
						prepareStatement.setString(1, vendor_id);
						prepareStatement.setString(2, product_id);
						prepareStatement.setDouble(3, Double.valueOf(price));
						prepareStatement.setString(4, discount);
						prepareStatement.setString(5, product_name);
						prepareStatement.setString(6, product_code);
						prepareStatement.setString(7, product_category_id);
						prepareStatement.setString(8, product_subcategory_id);
						prepareStatement.setString(9, product_brand_id);
						prepareStatement.setInt(10, product_unit);
						prepareStatement.setInt(11, product_size);
						prepareStatement.setString(12, product_image);
						prepareStatement.setInt(13, alert_quantity);
						prepareStatement.setDouble(14, product_cgst);
						prepareStatement.setDouble(15, product_sgst);
						prepareStatement.setDouble(16, product_igst);
						prepareStatement.setString(17, product_taxable);
						prepareStatement.setString(18, created_by);
						prepareStatement.setString(19, product_substitute);
						prepareStatement.setString(20, product_status);
						prepareStatement.setInt(21, mark_for_deletion);
						prepareStatement.setDouble(22, Double.valueOf(price));
						prepareStatement.setString(23, discount);
						prepareStatement.setString(24, product_id);
						prepareStatement.setString(25, vendor_id);
						
						
						st=prepareStatement.executeUpdate();
						insert=insert+st;
						System.out.println("Query--- "+prepareStatement);
				}
					if(st>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Updated Successfully");
					}else{
//						connection.rollback();
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to Update");
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
	
}
