/**
 * 
 */
package com.hr.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
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

import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.utils.MessageConfig;
import com.hr.utils.Utils;

/**
 * @author Admin
 *
 */
@Path("Authentication")
@SuppressWarnings({"unchecked"})
public class Authentication {
	
	private static Log log=LogFactory.getLog(Authentication.class);

	private static final String login="select function_login(?,?,?,?,?,?,?)";
	private static final String logout="update sessiondetails set flag=1,logout=current_timestamp where sessionid=?";
	static String SESSIONID;
	@Context private static HttpServletRequest request;
	@Context private static HttpServletResponse response;
	/**
	 * 
	 * @param {userid,name,description}
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 09/18/2018
	 * @version v1.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	@POST
    @Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response login(MyObject param) {	
		
		String data=param.getData();
		
		String[] fields=data.split(":::");
		JSONObject responseObject=new JSONObject();
			
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		String EncPwd=null;
		UUID uid=UUID.randomUUID();
		SESSIONID=uid.toString();
		SESSIONID=SESSIONID+fields[0].length();
		
		String[] agent=Utils.getuserAgent(request);
		
		String browser=null,device=null,browserversion=null;
		if(agent!=null) {
			device=agent[0];
			browser=agent[1];
			browserversion=agent[2];
		}
		try {
			EncPwd=fields[1];
			EncPwd=com.hr.security.Security.encrypt(com.hr.security.Security.key, com.hr.security.Security.initVector, fields[1]);
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(login);
			preparedStmt.setString(1, fields[0]);
			preparedStmt.setString(2, EncPwd);
			preparedStmt.setString(3, SESSIONID);
			preparedStmt.setString(4, device);
			preparedStmt.setString(5, browser);
			preparedStmt.setString(6, browserversion);
			preparedStmt.setString(7, request.getRemoteAddr());
			System.out.println("login: "+preparedStmt);
			ResultSet rs = preparedStmt.executeQuery();
			String login_status=null;
			while(rs.next()) {
				login_status=rs.getString(1);
			}
			switch(login_status) {
				case "USERNAME NOT EXISTS":
					responseObject=HelperClass.generateResponce(301,MessageConfig.login_301,"username entered is invalid");
				break;
				case "PASSWORD NOT EXISTS":
					responseObject=HelperClass.generateResponce(303,MessageConfig.login_303,"password entered is invalid");
				break;
				case "PASSWORD EXPIRED":
					responseObject=HelperClass.generateResponce(306,MessageConfig.login_306,"password has expired");
				break;
				case "Success":
					JSONObject GETUSERDETAILS = null;
					try {
						GETUSERDETAILS = getuserinfo(fields[0],SESSIONID,device,browser,browserversion,connection,request.getRemoteAddr());
					} catch (Exception e) {
						e.printStackTrace();
					}
					responseObject=HelperClass.generateResponce(200,GETUSERDETAILS,null);
					break;
				}
			} catch (SQLException e) {
				log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
		}
		finally {
			try {
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
			Response response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}

private static JSONObject getuserinfo(String username,String sessionid,String device, String browser, String browserversion,Connection connection,String ipAddressRequestCameFrom) throws Exception {
		JSONObject userdetails=new JSONObject();
		String GETROLEQUERY="SELECT mstuser.userid, mstuser.employeeid, mstuser.employeename, mstuser.username, \r\n" + 
				"mstuser.passwordexpiredate, mstuser.roleid, mstuser.departmentid, mstuser.positionid,\r\n" + 
				"mstuser.org_id,  mstuser.emailid, mstuser.organizationid, mstuser.orgunitid, mstuser.departmentunitid,\r\n" + 
				"mstrole.role_name,mstdepartment.department_name,mstposition.positionid\r\n" + 
				"FROM public.mstuser\r\n" + 
				"inner join mstrole on mstrole.roleid= mstuser.roleid\r\n" + 
				"inner join mstdepartment on mstdepartment.departmentid= mstuser.departmentid\r\n" + 
				"inner join mstposition on mstposition.positionid= mstuser.positionid where emailid='"+username+"' or username='"+username+"'";
		try {
			
			ResultSet RESULTSET=connection.createStatement().executeQuery(GETROLEQUERY);
			userdetails=convertResultSetIntoJSONObject(RESULTSET);
				
				userdetails.put("sessionid", sessionid);
				userdetails.put("device", device);
				userdetails.put("browser", browser);
				userdetails.put("browserversion", browserversion);
				userdetails.put("remoteip", ipAddressRequestCameFrom);
				
			
		} catch (SQLException e) {
			log.info(e.getMessage());
			userdetails.put("ERROR", e.getMessage());
		}
		return userdetails;
	}

/**
 * 
 * @param {userid,name,description}
 * @return response
 * @author Akhil Yelakanti
 * @date 09/18/2018
 * @version v1.0
 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
 */
@POST
@Path("/logout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response logout(MyObject param) {	
	JSONObject responseObject=new JSONObject();
		
	Connection connection=null;	
	PreparedStatement preparedStmt=null;
	ResultSet resultSet = null;
	try {			
		connection=DBConnection.sqlConnection();
		preparedStmt=connection.prepareStatement(logout);
		preparedStmt.setString(1, param.getSessionid());
		System.out.println(preparedStmt);
		int status = preparedStmt.executeUpdate();
		if(status>0) 
			responseObject=HelperClass.generateResponce(200,"Succefully logged out",null);
		else 
			responseObject=HelperClass.generateResponce(201,MessageConfig.API_201,"failed to logout");
		} catch (SQLException e) {
			log.error(e.getMessage());
		responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
	} catch (IOException e) {
		log.error(e.getMessage());
		responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
	}
	
	finally {
		try {
			DBConnection.closeConnection(connection, preparedStmt, resultSet);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}
		Response response=HelperClass.convertObjectToResponce(responseObject, 200);			
return response;	
	}
public static JSONObject convertResultSetIntoJSONObject(ResultSet resultSet) throws Exception {
		
		JSONObject jsObject=new JSONObject();
		int j=0;
		while (resultSet.next()) {
			
			int total_rows = resultSet.getMetaData().getColumnCount();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				jsObject.put(columnName,columnValue);
			}
		}
		return jsObject;
	}
}
