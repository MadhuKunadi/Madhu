package com.cs.member;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cs.Accounts;
import com.cs.New;
import com.cs.TokenCheck;

@Path("membertype")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Member {
	static Statement  statement;
	static ResultSet  resultset;
	static Connection connection;
	static JSONArray  jsonArray   =new JSONArray();
	static JSONObject jsonObject  =new JSONObject();
	static JSONArray  subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String     sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Member.class);
	/*
	 * Add member 
	 */
	@POST
	@Path("/AddmemberType")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddExpenseCategory(@FormParam("member_type") String member_type,
			@FormParam("description") String description,@FormParam("mobile_number") String mobile_number,@FormParam("address") String address){
			if(member_type==null || member_type.isEmpty()||description==null || description.isEmpty()
					){
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message",  "Fields are empty");
			return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
			JSONObject jsonObject=new JSONObject();
			String Query = null;
			if(jsonObject.containsKey("status")){
						jsonObject.clear();
						jsonObject.put("status", "Failed");
						jsonObject.put("message", jsonObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}else{
			
			}  
						try {
				SqlConnection();
				try {
					Query ="insert into member_type_master(member_type,description)"
							+ "values('"+member_type+"','"+description+"')";
					int i=statement.executeUpdate(Query);
					System.out.println(Query);
					if(i>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Member Added Successfully");
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
					statement.close();
					resultset.close();
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
	 * Add category List 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/Addmember")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddcategoryList(@FormParam("id") String id,@FormParam("member_type_id") int member_type_id,@FormParam("member_name") String member_name,
			@FormParam("description") String description,@FormParam("phone_number") String phone_number,@FormParam("address") String address){
			if(member_name==null || member_name.isEmpty()||description==null || description.isEmpty()){
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message",  "Fields are empty");
			return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
			JSONObject jsonObject=new JSONObject();
			String Query = null;
			if(jsonObject.containsKey("status")){
						jsonObject.clear();
						jsonObject.put("status", "Failed");
						jsonObject.put("message", jsonObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}else{
			
			}  
				try {
					SqlConnection();
				try {
					Query ="insert into member_table(member_type_id,member_name, description, status, address,mobile_number)"
							+ "values('"+member_type_id+"','"+member_name+"','"+description+"','"+"Active"+"','"+address+"','"+phone_number+"')";
					int i=statement.executeUpdate(Query);
					System.out.println(Query);
					if(i>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Member Name Added Successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to add Member");
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
					
					e.printStackTrace();
				}
				}
			
			 return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		
		}
	/*
	 * get member type
	 */
	@POST
	@Path("/getmemberType")
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
		String Query ="select member_table.member_name,member_type_master.member_type,"
+ "member_table.description,"
+ "member_table.address,member_table.mobile_number,member_table.status,"
+ "member_table.member_id,member_table.member_type_id "
+ " from member_type_master "
+ " inner join member_table on member_type_master.member_type_id=member_table.member_type_id";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
		}finally{
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
	/*
	 * Member type drop down api
		 */
		@POST
		@Path("/membertypeDropdown")
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
					String Query="select distinct member_type,member_type_id from member_type_master ";
					resultset=statement.executeQuery(Query);
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
			}finally{
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
						.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
		}
		
		/*
		 * PAYABLE MEMBER TYPE DROPDOWN 
		 * 
		 */
		@POST
		@Path("/payableMembertypeDropdown")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response payableMembertypeDropdown(@FormParam("id") String id){
				JSONObject jsObject = TokenCheck.checkTokenStatus(id);
				if(jsObject.containsKey("status")){
					jsonObject.clear();
					jsonObject.put("status","Failed");
					jsonObject.put("message", jsObject.get("status"));
				return Response.ok()
							.entity(jsonObject)
							.header("Access-Control-Allow-Methods", "POST").build();
				}else{
					@SuppressWarnings("unused")
					String email = (String) jsObject.get("email");
				}
			try{
					SqlConnection();
				try{		
					String Query="select distinct member_type,member_type_id from member_type_master where is_payable=1";
					resultset=statement.executeQuery(Query);
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
			}finally{
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
						.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
		}
		

		/*
		 * RECEIVABLE MEMBER TYPE DROPDOWN 
		 * 
		 */
		
		@POST
		@Path("/receivableMembertypeDropdown")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response receivableMembertypeDropdown(@FormParam("id") String id){
				JSONObject jsObject = TokenCheck.checkTokenStatus(id);
				if(jsObject.containsKey("status")){
					jsonObject.clear();
					jsonObject.put("status","Failed");
					jsonObject.put("message", jsObject.get("status"));
				return Response.ok()
							.entity(jsonObject)
							.header("Access-Control-Allow-Methods", "POST").build();
				}else{
					@SuppressWarnings("unused")
					String email = (String) jsObject.get("email");
				}
			try{
					SqlConnection();
				try{		
					String Query="select distinct member_type,member_type_id from member_type_master where is_receivable=1";
					resultset=statement.executeQuery(Query);
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
			}finally{
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
						.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
		}
		
		@POST
		@Path("/membertypeIdDropdown")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response memberDropDown(@FormParam("id") String id,@FormParam("member_type_id") int member_type_id){
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
					String Query = "";
					if(member_type_id==1){
					Query="select vendor_id,vendor_name from tbl_vendor1 where master_type_id='"+member_type_id+"'";
					}else if(member_type_id==2){
					Query="select warehouse_id,warehouse_name from tbl_warehouse1 where master_type_id='"+member_type_id+"'";
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Content", "Please Select one of the members");
					}
					resultset=statement.executeQuery(Query);
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
			}finally{
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
						.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
		}
		
		
		
		
		/*
		 * Member type based on member_name drop down api
			 */
			@POST
			@Path("/membertypeBsNamesDropdown")
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
			@Produces(MediaType.APPLICATION_JSON)
			public static Response membertypeBsNamesDropdown(@FormParam("id") String id,@FormParam("member_type_id") String member_type_id){
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
							
						String Query="select member_name,member_id,member_type_id from member_table where member_type_id='"+member_type_id+"'";
						resultset=statement.executeQuery(Query);
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
				}finally{
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
							.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE").build();
			}
			
			
	/*
	 * public static String getFixedMemberId(String id){
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
	 * } }
	 */
}
