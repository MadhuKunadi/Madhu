package com.cs.category;

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

import com.cs.New;
import com.cs.TokenCheck;
import com.cs.member.Member;

@SuppressWarnings("unused" )
@Path("Category")
public class Category {
	static Statement  statement;
	static ResultSet  resultset;
	static Connection connection;
	static JSONArray  jsonArray   =new JSONArray();
	static JSONObject jsonObject  =new JSONObject();
	static JSONArray  subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String     sessionId;
	private static Log log=LogFactory.getLog(Category.class);
	/*
	 * Add category 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/Addcategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddExpenseCategory(@FormParam("category_name") String category_name,
			@FormParam("description") String description){
			if(category_name==null || category_name.isEmpty()||description==null || description.isEmpty()){
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message",  "Fields are empty");
			return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
			}
			JSONObject jsonObject=new JSONObject();
			String Query = null;
			if(jsonObject.containsKey("status")){
						jsonObject.clear();
						jsonObject.put("status", "Failed");
						jsonObject.put("message", jsonObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
			
			}  
				try {
					SqlConnection();
				try {
					Query ="insert into category_master(category_name,description)"
							+ "values('"+category_name+"','"+description+"')";
					int i=statement.executeUpdate(Query);
//					ResultSet rs = statement.executeQuery(Query);
					System.out.println(Query);
//					boolean categoryAdded = false;
//					while(!rs.next())
					if(i>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Category Name Added Successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to add Category");
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
						.header("Access-Control-Allow-Methods", "POST").build();
		
		}
	/*
	 * Add category List 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addSubCategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addSubCategory(@FormParam("id") String id,@FormParam("category_id") String category_id,
			@FormParam("category_sub_name") String category_sub_name,
			@FormParam("description") String description){
			if(category_sub_name==null || category_sub_name.isEmpty()||description==null || description.isEmpty()){
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message",  "Fields are empty");
			return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
			}
			JSONObject jsonObject=new JSONObject();
			String Query = null;
			if(jsonObject.containsKey("status")){
						jsonObject.clear();
						jsonObject.put("status", "Failed");
						jsonObject.put("message", jsonObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
			
			}  
				try {
					SqlConnection();
				try {
					Query ="insert into category_sub_table(category_id,category_sub_name,description)"
							+ "values('"+category_id+"','"+category_sub_name+"','"+description+"')";
					int i=statement.executeUpdate(Query);
					System.out.println(Query);
					if(i>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Sub Category Added Successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to add subCategory");
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
						.header("Access-Control-Allow-Methods", "POST").build();
		
		}
	/*
	 * category drop down api
		 */
		@SuppressWarnings("unchecked")
		@POST
		@Path("/categoryDropdown")
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
						
					String Query="select distinct category_name,category_id from category_master";
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
		 * category sub names  drop down api
			 */
			@SuppressWarnings("unchecked")
			@POST
			@Path("/categorysubNamesDropdown")
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
			@Produces(MediaType.APPLICATION_JSON)
			public static Response membertypeBsNamesDropdown(@FormParam("id") String id,@FormParam("category_id") String category_id){
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
							
						String Query="select category_id,category_sub_id,category_sub_name from category_sub_table where category_id ='"+category_id+"'";
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
