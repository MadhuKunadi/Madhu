package com.hr.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.UUID;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;



public class HelperClass {

	@SuppressWarnings("unchecked")
	public static Object convertToJSON(ResultSet resultSet) throws SQLException{
		 JSONArray jsonArray = new JSONArray();
		 while (resultSet.next()) {
             HashMap<String, Object> obj = new HashMap<String, Object>();
	            int total_rows = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < total_rows; i++) {
	            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            	Object columnvalue=null;
	            	 int type = resultSet.getMetaData().getColumnType(i+1);
//	            	 System.out.println("columnname:  "+columnname+"   type : "+type+"    json_type : ");
	            	 if(type==1111) {
	            		 PGobject pgobject= new PGobject();
	            		 pgobject=(PGobject) resultSet.getObject(i + 1);
	            		 String value=pgobject.getValue();
	            		 JSONParser parser= new JSONParser();
	            		 try {
							columnvalue=parser.parse(value);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		 
	            	 }else {
	            		 columnvalue=resultSet.getObject(i + 1);
	            		 if(columnvalue==null) {
	            			 columnvalue=""; 
	            		 }
	            	 }
	            	 obj.put(columnname, columnvalue);
	            }
	            jsonArray.add(obj);
	        }
		return jsonArray;
	}
	
	@SuppressWarnings("unchecked")
	public static Object convertToJSONDocList(ResultSet resultSet) throws SQLException{
		 JSONArray jsonArray = new JSONArray();
		 while (resultSet.next()) {
             HashMap<String, Object> obj = new HashMap<String, Object>();
	            int total_rows = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < total_rows; i++) {
	            	String columnName=resultSet.getMetaData().getColumnLabel(i+1);
	            	if(columnName.contains("filesize")) {
	            		String size=resultSet.getString("filesize");
	            		if(size.contains("bytes")) {
	            			
	            		}
	            	}else {
	            		obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
		                        .toLowerCase(), resultSet.getObject(i + 1));
	            	}
	                
	               
	            }
	            jsonArray.add(obj);
	        }
		return jsonArray;
	}
	
	public static Object convertToJSONObject(ResultSet resultSet) throws SQLException{
		HashMap<String, Object> obj = new HashMap<String, Object>();
		 while (resultSet.next()) {
	            int total_rows = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < total_rows; i++) {
	            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            	Object columnvalue=null;
	            	 int type = resultSet.getMetaData().getColumnType(i+1);
	            	// System.out.println("columnvalue:  "+columnvalue+"   type : "+type+"    json_type : "+resultSet.getMetaData().getColumnTypeName(type));
	            	 if(type==1111) {
	            		 PGobject pgobject= new PGobject();
	            		 pgobject=(PGobject) resultSet.getObject(i + 1);
	            		 String value=pgobject.getValue();
	            		 JSONParser parser= new JSONParser();
	            		 try {
							columnvalue=parser.parse(value);
						} catch (ParseException e) {
							e.printStackTrace();
						}
	            		 
	            	 }else {
	            		 columnvalue=resultSet.getObject(i + 1);
	            		 if(columnvalue==null) {
	            			 columnvalue=""; 
	            		 }
	            		 
	            	 }
	            	 obj.put(columnname, columnvalue);
	            }
	        }
		return obj;
		
	}
	
	public static Object retrieveJSON(ResultSet resultSet) throws SQLException{
		 Object jsonArray = new JSONArray();
		 while (resultSet.next()) {
            jsonArray=resultSet.getObject(1);
	        }
		return jsonArray;
		
	}
	@SuppressWarnings("unchecked")
	public static JSONArray retrieveJSONArray(ResultSet resultSet) throws SQLException{
		 JSONArray jsonArray = new JSONArray();
		 while (resultSet.next()) {
         Object  object=resultSet.getObject(1);
         jsonArray.add(object);
	        }
		return jsonArray;
		
	}
	
	public static String ConvertResultset(ResultSet resultSet,String type) throws SQLException{
		String id=null;
		 while (resultSet.next()) {
			 int count=resultSet.getInt(1);
			     DecimalFormat decimalFormat = new DecimalFormat(MyServletContextListener.properties.getProperty(""));  
			 id=type+decimalFormat.format(count);
	        }
		return id;
		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject generateResponce(int status,Object dataObject,String errorObject){
		JSONObject responseObject=new JSONObject();
		
		if(dataObject!=null)
		responseObject.put("Data",dataObject);
		else if(errorObject==null){
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			hashMap.put("Message", errorObject);
			responseObject.put("Data",hashMap);
			}
		
			JSONArray array=new JSONArray();
			if(errorObject!=null)
			array.add(errorObject);
		responseObject.put("Error",array);
		
		responseObject.put("Status",status);
		
		
		return responseObject;
		
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> generateJSONResponce(int status,Object dataObject,String errorObject){
		HashMap<String, Object> responseObject=new HashMap<String, Object>();
		if(dataObject!=null)
		responseObject.put("Data",dataObject);
		else if(errorObject==null){
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			hashMap.put("Message", errorObject);
			responseObject.put("Data",hashMap);
}
		
			JSONArray array=new JSONArray();
			if(errorObject!=null)
			array.add(errorObject);
		responseObject.put("Error",array);
		
		responseObject.put("Status",status);
		
		
		return responseObject;
		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray generateJSONObjectResponce(int status,Object dataObject,String errorObject){
		HashMap<String, Object> responseObject=new HashMap<String, Object>();
			if(dataObject!=null)
			responseObject.put("Data",dataObject);
			else if(errorObject==null){
				HashMap<String, Object> hashMap=new HashMap<String, Object>();
				hashMap.put("Message", MyServletContextListener.properties.getProperty(""));
				responseObject.put("Data",hashMap);
			}
		
			JSONArray array=new JSONArray();
			if(errorObject!=null)
			array.add(errorObject);
			responseObject.put("Error",array);
			
			responseObject.put("Status",status);
			JSONArray jsonArray=new JSONArray();
			jsonArray.add(responseObject);
		return jsonArray;
		
	}
	
	public static Response convertObjectToResponce(Object responseObject,int status){
		ResponseBuilder builder = Response.ok(responseObject).status(status);
		
		return builder.build();
	}
	
	public static Response convertJSONToResponce(JSONArray responseObject,int status){
		ResponseBuilder builder = Response.ok(responseObject).status(status);
		
		return builder.build();
	}
	
	public static Response convertHashMapToResponce(HashMap<String, Object> responseObject,int status){
		ResponseBuilder builder = Response.ok(responseObject).status(status);
		
		return builder.build();
	}

	/*
	 * Creating a random UUID (Universally unique identifier).
	 * 	
	 */
		public static String generateUUID(){
			 
		        UUID uuid = UUID.randomUUID();
		        String randomUUIDString = uuid.toString();
		
		        return randomUUIDString;
			
		}
		
		
		public static int generateUniqueId() {      
	        UUID idOne = UUID.randomUUID();
	        String str=""+idOne;        
	        int uid=str.hashCode();
	        String filterStr=""+uid;
	        str=filterStr.replaceAll("-", "");
	        return Integer.parseInt(str);
	    }
	
		
	/*
	 * CUrrent Time
	 * 	
	 */
public static Timestamp getTimeStap() {
	java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
	java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
	java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());

	
	return ts;
	
}		
	/*
	 * response return	
	 */
public static Response responce(Object object) {
	
	
	return Response.ok() //200
			.entity(object)
			.header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	
}		
/*
 * DB Connection Close	
 */
public static void closeConnection(Connection connection) throws SQLException {
	if(connection!=null&&!connection.isClosed())
		connection.close();
}
//static String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
//
//
//
//public static boolean emailCheck(String email) {
//	 Pattern pattern = Pattern.compile(regex);
//   Matcher matcher = pattern.matcher(email);	
//   if(!matcher.matches())
//	return false;
//   else
//   	return true;




}
