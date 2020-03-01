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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.enterprise.inject.New;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.websocket.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.filter.TokenFilterContext;
import com.helper.Helper;

@SuppressWarnings("unused")
@Path("hr")
public class login {

	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(login.class);
	@Context private static HttpServletRequest request;
	@Context private static HttpServletResponse response;
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@POST
	@Path("/hrLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response login(@FormParam("username") String username,@FormParam("password") String password,@FormParam("description") String description)
	{
		
        
		System.out.println(username+" "+password);
		if(username == null || username.isEmpty() ||(password==null || password.isEmpty())){
			jsonObject.clear();
			jsonArray.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Messaage", "Fields are empty");
			jsonArray.add(jsonObject);
			return Response.ok()
					.entity(jsonArray)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		else	
            if (!Helper.emailCheck(username)) {
            	jsonObject.put("message", "Incorrect email");
            }
		
            else {
		String user_name=username.toLowerCase();
		String Query="select 1 from tbl_user where emailid='"+user_name+"'";
		String login_Query="select 1 from tbl_user where emailid='"+user_name+"' and password='"+password+"'";
		try{
			SqlConnection();
			try{
				resultset=statement.executeQuery(Query);
				boolean b=resultset.next();
				System.out.println("b: "+b);
				if(!b){
					jsonObject.clear();
					jsonArray.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Messaage", "Username is not exists");
					jsonArray.add(jsonObject);
				}else{
					System.out.println(login_Query);
					ResultSet login_resultset=statement.executeQuery(login_Query);
					boolean result=login_resultset.next();
					if(result){
						ArrayList resArrayList=getResponse(username);
						if(resArrayList!=null){
							UUID uuid=UUID.randomUUID();
							String sessionid=uuid.toString();
						    System.out.println(sessionid);
						    String insrtQuery="insert into sessiondetails(username,sessionid,status) values('"+user_name+"','"+sessionid+"','Success')";
						    int i=statement.executeUpdate(insrtQuery);
						    jsonObject.clear();
							jsonObject.put("Status", "Success");
							jsonObject.put("content", resArrayList);
							jsonObject.put("sessionid", sessionid);
						}else{
							jsonObject.clear();
							jsonObject.put("Status", "Failed");
							jsonObject.put("Messaage", "Something went wrong");
						}
						
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Messaage", "Password entered is invalid");
					}
				}
			
			}catch(Exception exc){
				System.out.println(exc.getMessage());
				jsonObject.clear();
	        	jsonObject.put("Status", "Failed");
	        	jsonObject.put("Messaage", "Some server issues occurred");
			}
		
		
		}catch(Exception exc){
			jsonObject.clear();
        	jsonObject.put("Status", "Failed");
        	jsonObject.put("Messaage", "Server connection failed");
		
		}finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
            }
		System.out.println(jsonObject);
		JSONArray jsArray=new JSONArray();
		jsArray.add(jsonObject);
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();

	}

	@SuppressWarnings("rawtypes")
	public static ArrayList getResponse(String username){
		JSONObject jsonObject=new JSONObject();
		
		String Query="select tbl_role.roleid,tbl_role.role,tbl_user.emailid,tbl_user.username,tbl_user.auth_id from tbl_user inner join tbl_role on tbl_role.roleid=tbl_user.roleid where emailid='"+username+"'";

		
		try {
			System.out.println(Query);
			ResultSet resultSet=statement.executeQuery(Query);
			ArrayList resarray=convertResultSetIntoJSON(resultSet);
			return resarray;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	@POST
	@Path("/hrlogout")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response logout(@FormParam("id") String id)
	{
		if(id==null||id.isEmpty()){
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Messaage", "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
				
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		try{
			SqlConnection();
			try{
				String query="update sessiondetails set isactive=1,logout=current_timestamp where sessionid='"+id+"'";
				int update=statement.executeUpdate(query);
				if(update>0){
					jsonObject.clear();
		        	jsonObject.put("Status", "Success");
		        	jsonObject.put("Messaage", "logout successfully");
				}else{
					jsonObject.clear();
		        	jsonObject.put("Status", "Failed");
		        	jsonObject.put("Messaage", "Please try agin");
				}
			}catch(Exception exc){
				System.out.println(exc.getMessage());
				jsonObject.clear();
	        	jsonObject.put("Status", "Failed");
	        	jsonObject.put("Messaage", "Some server issues occurred");
			}
		}catch(Exception exc){
			jsonObject.clear();
        	jsonObject.put("Status", "Failed");
        	jsonObject.put("Messaage", "Server connection failed");
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
	@Path("/createEmployee")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response createEmployee(@FormParam("id") String id,@FormParam("firstName") String firstName,@FormParam("middleName") String middleName,@FormParam("lastName") String lastName,@FormParam("dob") String dob,@FormParam("email") String email,
			@FormParam("contactNumber") String contactNumber,@FormParam("gender") String gender,@FormParam("presentAddress") String presentAddress,@FormParam("permanentAddress") String permanentAddress,@FormParam("pincode") String pincode,
			@FormParam("alternateContactPerson") String alternateContactPerson,@FormParam("alternateContactNumber") String alternateContactNumber,@FormParam("aadharNumber") String aadharNumber,@FormParam("panNumber") String panNumber,
			@FormParam("passportNumber") String passportNumber,@FormParam("joiningDate") String joiningDate,@FormParam("employementStatus") String employementStatus,@FormParam("workStatus") String workStatus,@FormParam("fatherName") String fatherName,
			@FormParam("motherName") String motherName,@FormParam("fatherOccupation") String fatherOccupation,@FormParam("motherOccupation") String motherOccupation,@FormParam("maritalStatus") String maritalStatus,@FormParam("spouseName") String spouseName,
			@FormParam("dateOfMarriage") String dateOfMarriage,@FormParam("childrenCount") String childrenCount,@FormParam("nomineeName") String nomineeName,@FormParam("nomineeRelation") String nomineeRelation,@FormParam("nomineeContactNumber") String nomineeContactNumber,
			@FormParam("state") String state,@FormParam("country") String country,@FormParam("motherTongue") String motherTongue,@FormParam("department") String department,@FormParam("designation") String designation,@FormParam("grade") String grade,
			@FormParam("empbloodGroup") String empbloodGroup,@FormParam("empid") String empid,@FormParam("emprelievingdate") String emprelievingdate){
		
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
			String email1=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		try{
			SqlConnection();
			try{
				String empId=getId("EMP");
				String query="select * from createEmployee_Function('"+firstName+"','"+middleName+"','"+lastName+"','"+dob+"','"+email+"','"+contactNumber+"',"
						+ "'"+gender+"','"+presentAddress+"','"+permanentAddress+"','"+state+"','"+country+"','"+pincode+"','"+alternateContactPerson+"',"
						+ "'"+alternateContactNumber+"','"+motherTongue+"','"+aadharNumber+"','"+panNumber+"','"+passportNumber+"','"+department+"','"+designation+"',"
						+ "'"+grade+"','"+joiningDate+"','"+employementStatus+"','"+workStatus+"','"+fatherName+"','"+motherName+"','"+fatherOccupation+"','"+motherOccupation+"',"
						+ "'"+maritalStatus+"','"+spouseName+"','"+nomineeName+"','"+nomineeRelation+"','"+nomineeContactNumber+"','"+empId+"','"+empbloodGroup+"')";
				System.out.println(query);
				CallableStatement callableStatement=connection.prepareCall(query);
				callableStatement.execute();
				ResultSet resultSet=callableStatement.getResultSet();
				String result=new String();
				while(resultSet.next()){
					result=resultSet.getString(1);
				}
				
				if(result.contains("Success")){
					if(emprelievingdate!=null){
						int i=statement.executeUpdate("update emp_master set emp_relieving_date='"+emprelievingdate+"' where emp_id='"+empId+"';"
								+ "update emp_details set emp_relieving_date='"+emprelievingdate+"' where emp_id='"+empId+"';");
					}if(dateOfMarriage!=null){
						int k=statement.executeUpdate("update emp_family_details set emp_date_of_marriage ='"+dateOfMarriage+"'  where emp_id='"+empId+"'");
					}if(childrenCount!=null){
						int k=statement.executeUpdate("update emp_family_details set emp_num_of_children ='"+childrenCount+"'  where emp_id='"+empId+"'");
					}
					jsonObject.clear();
					jsonObject.put("status", "Success");
					jsonObject.put("message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message", result);
				}
			}catch(Exception exc){
				jsonObject.clear();
				jsonObject.put("status", "Failed");
				jsonObject.put("message", "Something went wrong");
				jsonObject.put("error", exc.getMessage());
			}
		}catch(Exception exc){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Please try again later");
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
	@Path("/updateEmployee")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateEmployee(@FormParam("id") String id,@FormParam("emp_id") String emp_id,@FormParam("emp_first_name") String emp_first_name,@FormParam("emp_last_name") String emp_last_name,@FormParam("emp_email_id")
	String emp_email_id,@FormParam("emp_phone_num") String emp_phone_num,@FormParam("emp_department") String emp_department,@FormParam("emp_designation") String emp_designation,@FormParam("grade") String grade,@FormParam("org_id") String org_id){
		JSONObject jsonObject=new JSONObject();
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
				String Query="UPDATE emp_master SET emp_first_name=?,emp_last_name=?,"
						+ "emp_email_id=?,emp_phone_num=?,emp_department=?,emp_designation=?,grade=?,org_id=? where emp_id=?";
				
				PreparedStatement preparedStatement=connection.prepareStatement(Query);
//				int cID=Integer.parseInt(emp_id);
				preparedStatement.setString(9,emp_id);
				preparedStatement.setString(1, emp_first_name);
				preparedStatement.setString(2, emp_last_name);
				preparedStatement.setString(3, emp_email_id);
				preparedStatement.setString(4, emp_phone_num);
				preparedStatement.setString(5, emp_department);
				preparedStatement.setString(6, emp_designation);
				preparedStatement.setString(7, grade);
				preparedStatement.setString(8, org_id);
				
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
	@Path("/editEmployeeProfile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editProfile(@FormParam("id") String id,@FormParam("emp_id") String emp_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || emp_id==null || emp_id.isEmpty()) {
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
		
		String Query="select * from emp_master where emp_id="+"'"+emp_id+"'";
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
	
	
		public static String getId(String id){
		String numberrange_function="select numberrange_function('"+id+"')";
		int Rid=0;
		try{
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getEmployeeProfile")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProfile(@FormParam("id") String id){
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
		}String Query="select * from emp_master";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getEmployeeId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getEmployeeid(@FormParam("id") String id){
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
		}String Query="select emp_id from emp_master";
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/getDesignationList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getDesignationList(@FormParam("id") String id){
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
		}String Query="select * from designation";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getDepartmentList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getDepartment(@FormParam("id") String id){
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
		}String Query="select * from department";
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
	@Path("/getOrganization")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getOrganizationList(@FormParam("id") String id){
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
		}String Query="select * from organization_hierarchy";
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
	@Path("/getGrade")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getGradeList(@FormParam("id") String id){
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
		}String Query="select * from pay_grade_master";
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
	@Path("/generatePaySlip")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response gstPurchase(@FormParam("id") String id,@FormParam("emp_id") String empid ,@FormParam("grade") String grade,@FormParam("date_of_payment") String date_of_payment,@FormParam("month") String month){
		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || empid==null || empid.isEmpty()|| grade==null || grade.isEmpty()|| date_of_payment==null || date_of_payment.isEmpty()
//				|| month==null || month.isEmpty()) {
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
			
//		}String Query="insert into payslip,pay_allowance(grade,net_salary,emp_cpf,emp_id,designation,department,pay_allowance.house_rent_allowance,"
//				+ "pay_allowance.d_allowance,pay_allowance.transport_allowance"	+ ") "
//				+ "values('"+grade+"','"+netSalary+"','"+employee_cpf+"','"+empid+"','"+designation+"','"+department+"','"+transport_allowance+"','"+d_allowance+"','"+house_rent_allowance+"')";
//			
		} String Query = "insert into payslip(emp_id,grade,date_of_payment,month) values('"+empid+"','"+grade+"','"+date_of_payment+"','"+month+"');";
				                             
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
				 System.out.println(e.getMessage());
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
	@Path("/editGeneratedPayslipList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editGeneratedPayslipList(@FormParam("id") String id,@FormParam("emp_id") String emp_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || emp_id==null || emp_id.isEmpty()) {
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
		
		String Query="select * from payslip where emp_id="+"'"+emp_id+"'";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateGeneratedPayslipList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateGeneratedPayslipList(@FormParam("id") String id,@FormParam("emp_id") String emp_id,@FormParam("grade") String grade,@FormParam("date_of_payment") String date_of_payment,@FormParam("month") String month){
		JSONObject jsonObject=new JSONObject();
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
				String Query="UPDATE payslip SET grade='"+grade+"',date_of_payment='"+date_of_payment+"',"
						+ "month='"+month+"' where emp_id='"+emp_id+"'";
				
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
				 System.out.println("the value is"+e.getMessage());
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
	@Path("/getPaySlipList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response paysliplist(@FormParam("id") String id){
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
		
		}String Query="select payslip.emp_id,payslip.modified_on,payslip.net_salary,payslip.allowance_amount,payslip.salary,payslip.employee,payslip.created_by,payslip.emp_cpf,payslip.allowance_type,payslip.month,payslip.deduction,payslip.emp_cpf_rate,payslip.created_on,payslip.date_of_payment,payslip.grade,payslip.modified_by,payslip.designation,payslip.department,pay_allowance.house_rent_allowance,pay_allowance.d_allowance,pay_allowance.transport_allowance from payslip,pay_allowance";
		System.out.println(Query);
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
				 System.out.println(e.getMessage());
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
	@Path("/editPayslipList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editPayslipList(@FormParam("id") String id,@FormParam("emp_id") String emp_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || emp_id==null || emp_id.isEmpty()) {
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
		
		String Query="select * from payslip where emp_id="+"'"+emp_id+"'";
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatePayslipList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatePayslipList(@FormParam("id") String id,@FormParam("emp_id") String emp_id,@FormParam("net_salary") String net_salary,@FormParam("allowance_amount") String allowance_amount,@FormParam("salary")
	String salary,@FormParam("emp_cpf") String emp_cpf,@FormParam("allowance_type") String allowance_type,@FormParam("month") String month,@FormParam("deduction") String deduction,@FormParam("emp_cpf_rate") String emp_cpf_rate,@FormParam("grade") String grade
	,@FormParam("designation") String designation,@FormParam("department") String department){
		JSONObject jsonObject=new JSONObject();
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
				String Query="UPDATE payslip SET net_salary='"+net_salary+"',allowance_amount='"+allowance_amount+"',"
						+ "salary='"+salary+"',emp_cpf='"+emp_cpf+"',allowance_type='"+allowance_type+"',month='"+month+"',deduction='"+deduction+"'"
						+ ",emp_cpf_rate='"+emp_cpf_rate+"',grade='"+grade+"',designation='"+designation+"',department='"+department+"' where emp_id='"+emp_id+"'";
				
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
				 System.out.println("the value is"+e.getMessage());
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
	@Path("/paySlipTemplate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatepaysliptemplate(@FormParam("id") String id,@FormParam("desig_id") String desig_id,@FormParam("designation") String designation,@FormParam("department") String department,@FormParam("basic_salary") String basic_salary,
			@FormParam("grade") String grade,@FormParam("house_rent_allowance") String house_rent_allowance,@FormParam("d_allowance") String d_allowance,@FormParam("transport_allowance") String transport_allowance
			,@FormParam("net_salary") String net_salary,@FormParam("employeepf") String employeepf){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()|| desig_id==null || desig_id.isEmpty() || designation==null || designation.isEmpty()|| department==null || department.isEmpty()|| basic_salary==null || basic_salary.isEmpty()
				||house_rent_allowance==null || house_rent_allowance.isEmpty() ||d_allowance==null || d_allowance.isEmpty() ||transport_allowance==null || transport_allowance.isEmpty()
				||net_salary==null || net_salary.isEmpty()||employeepf==null || employeepf.isEmpty()) {
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
		}String Query="insert into pay_basic(grade,designation,department,basic_salary,desig_id) values('"+grade+"','"+designation+"','"+department+"','"+basic_salary+"','"+desig_id+"');"
	    +"insert into pay_allowance(house_rent_allowance,d_allowance,transport_allowance,desig_id)values('"+house_rent_allowance+"','"+d_allowance+"','"+transport_allowance+"','"+desig_id+"');"
        +"insert into payslip(net_salary,employeepf,desig_id) values('"+net_salary+"','"+employeepf+"','"+desig_id+"')";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
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
				 System.out.println(e.getMessage());
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
	@Path("/getPaySlipTemplate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getpaysliptemplate(@FormParam("id") String id){
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
		}String Query="select pay_basic.desig_id,pay_basic.modified_on,pay_basic.created_on,pay_basic.grade,pay_basic.basic_salary,pay_basic.modified_by,pay_basic.designation,pay_basic.department,pay_basic.created_by,pay_allowance.house_rent_allowance,pay_allowance.d_allowance,pay_allowance.transport_allowance,pay_allowance.oid,payslip.net_salary,payslip.employeepf from pay_basic,pay_allowance,payslip";
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
				 System.out.println(e.getMessage());
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
	@Path("/editPaySlipTemplate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editPaySlipTemplate(@FormParam("id") String id,@FormParam("desig_id") String desig_id){
		JSONObject jsonObject=new JSONObject();
		System.out.println(id+" "+desig_id);
		if(id==null || id.isEmpty() || desig_id==null || desig_id.isEmpty()) {
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
		
//		String Query="select  pay_basic.desig_id,pay_basic.grade,pay_basic.basic_salary,pay_basic.department,pay_basic.designation,payslip.net_salary,payslip.employeepf,pay_allowance.house_rent_allowance,pay_allowance.d_allowance,pay_allowance.transport_allowance"
//				+ " inner join pay_basic on pay_basic.desig_id=pay_allowance.desig_id "
//				+ " where pay_basic.desig_id="+"'"+desig_id+"'";;
		String Query = "select pay_basic.desig_id,pay_basic.grade,pay_basic.basic_salary,pay_basic.department,pay_basic.designation,payslip.net_salary,payslip.employeepf,house_rent_allowance,d_allowance,transport_allowance from pay_basic  "
				+ "inner join pay_allowance on pay_allowance.desig_id = pay_basic.desig_id inner join payslip on pay_allowance.desig_id = pay_basic.desig_id where pay_basic.desig_id='"+desig_id+"'";
		
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatePaySlipTemplate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatePaySlipTemplate(@FormParam("id") String id,@FormParam("desig_id") String desig_id,@FormParam("employeepf") String employeepf,@FormParam("net_salary") String net_salary,@FormParam("house_rent_allowance")
	String house_rent_allowance,@FormParam("d_allowance") String d_allowance,@FormParam("grade") String grade,@FormParam("basic_salary") String basic_salary,@FormParam("designation") String designation,@FormParam("department") String department,@FormParam("transport_allowance") String transport_allowance
	){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || desig_id==null || desig_id.isEmpty()|| employeepf==null || employeepf.isEmpty()|| net_salary==null || net_salary.isEmpty()|| house_rent_allowance==null || house_rent_allowance.isEmpty()|| transport_allowance==null || transport_allowance.isEmpty()
				|| d_allowance==null || d_allowance.isEmpty()||grade==null || grade.isEmpty()||basic_salary==null || basic_salary.isEmpty()||designation==null || designation.isEmpty()||department==null || department.isEmpty()) {
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
				String Query="UPDATE pay_basic SET grade='"+grade+"',basic_salary='"+basic_salary+"',department='"+department+"',designation='"+designation+"' where desig_id='"+desig_id+"';"
						+ "	UPDATE pay_allowance SET house_rent_allowance='"+house_rent_allowance+"',d_allowance='"+d_allowance+"',transport_allowance='"+transport_allowance+"' where desig_id='"+desig_id+"'; "
						+ "UPDATE payslip SET employeepf='"+employeepf+"',net_salary='"+net_salary+"' where desig_id='"+desig_id+"'";
//				String Query1="";
//				String Query2="
				
//				String Query="UPDATE pay_basic SET grade='"+grade+"',basic_salary='"+basic_salary+"',department='"+department+"',designation='"+designation+"' where desig_id='"+desig_id+"'";
//				String Query1="UPDATE pay_allowance SET house_rent_allowance='"+house_rent_allowance+"',d_allowance='"+d_allowance+"',transport_allowance='"+transport_allowance+"' where desig_id='"+desig_id+"'";
//				String Query2="UPDATE payslip SET employeepf='"+employeepf+"',net_salary='"+net_salary+"' where desig_id='"+desig_id+"'";

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
				 System.out.println("the value is"+e.getMessage());
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
	/* Converting Resultset into Json format method  */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				if(columnName.contains("purchaseorder_date")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Timestamp ts = resultSet.getTimestamp("purchaseorder_date");
					map.put(columnName, sdf.format(ts));
				}else{
				map.put(columnName,columnValue);
				}
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}		
}
