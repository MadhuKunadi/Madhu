package com.cs.account;

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
@Path("NewAccounts")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NewAccount {
	static ResultSet  resultset;
	static Connection connection;
	static Statement statement;
	static JSONArray  jsonArray   =new JSONArray();
	static JSONObject jsonObject  =new JSONObject();
	static JSONArray  subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String     sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Accounts.class);
	/*
	 * Add Expense category API 
	 */
	@POST
	@Path("/AddExpenseCategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddExpenseCategory(@FormParam("category_name") String expensecategory_name,
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
			Query ="insert into expense_category_master(category_name,description)"
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
	 * get Expenses Category
	 */
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
		String Query ="select * from expense_category_master";
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
	/*
	 * Expanse category drop down api
		 */
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
						
					String Query="select category_name from expense_category_master";
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
