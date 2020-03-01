package com.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.helper.Config;
import com.helper.DBConnection;
import com.helper.Helper;

@Path("count")
public class Count {
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/moduleCount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseList(@FormParam("id") String id){
		JSONObject jsonObject= new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsObject.clear();
			jsObject.put("status", "Failed");
			jsObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connectionModCount=null;
		try{
			String email=(String) jsObject.get("email");
			connectionModCount = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionModCount.prepareStatement(Config.module_count);
		    ResultSet resultSet =preparedStatement.executeQuery();
			@SuppressWarnings("rawtypes")
			ArrayList arrayList = Helper.convertResultSetIntoJSON(resultSet);
			if(arrayList!=null&&!arrayList.isEmpty()){
				jsonObject.clear();
				jsonObject.put("Status", "Success");
				jsonObject.put("Data", arrayList);
			}else{
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "List is empty");
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Something Went Wrong");
		}finally{
			try {
				connectionModCount.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
}
