package com.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONObject;

import com.helper.DBConnection;

public class TokenCheck {

	
	@SuppressWarnings({ "unchecked", "unused" })
	public static JSONObject checkTokenStatus(String id){
		JSONObject jsonObject=new JSONObject();
		String token_check="select tbl_user.username,tbl_user.emailid,tbl_user.roleid,tbl_role.role,tbl_user.auth_id from sessiondetails "
				+ "  inner join tbl_user on tbl_user.emailid=sessiondetails.username   inner join tbl_role on tbl_role.roleid=tbl_user.roleid "
				+ "  where sessiondetails.isactive=0 and sessiondetails.status='Success' and sessiondetails.sessionid=?";
		Connection connection=null;
		try {
			 connection=DBConnection.SqlConnection();
			try{
				PreparedStatement statement=connection.prepareStatement(token_check);
				statement.setString(1, id);
				System.out.println(statement);
				ResultSet result = statement.executeQuery();
				int role_id=0;String email=new String();String role=new String();String auth_id=new String();
				while(result.next()){
					role_id=result.getInt("roleid");
					role=result.getString("role");
					email=result.getString("emailid");
					auth_id=result.getString("auth_id");
				}

				if(!email.isEmpty()&&email!=null&&role!=null&&role_id>0&&auth_id!=null&&!auth_id.isEmpty()){
					jsonObject.put("role_id", role_id);
					jsonObject.put("role", role);
					jsonObject.put("email", email);
					jsonObject.put("auth_id", auth_id);
				}else{
					jsonObject.put("status", "Your Session has been expired. Please logout and try again later");
				}
			}catch (SQLException e) {
				e.printStackTrace();
				jsonObject.put("status", "Server issues occured");
			}
		}catch(Exception exc){
			System.out.println(exc.getMessage());
			jsonObject.put("status", "connection failed ");
		}finally{
			if(connection!=null){
				try {
					connection.close();
					System.out.println("Database connection is closed "+connection.isClosed());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return jsonObject;
	}
	/*get mail from valid token*/
	@SuppressWarnings("resource")
	public static String getmail(String token){
		String mail_Query="select email from token_master where token=? and flag=0 order by createdon desc limit 1";
		String email=new String();
		String token_org=new String();
		Connection connection=null;
		ResultSet resultset=null;
		PreparedStatement statement=null;
		try{
			 connection=DBConnection.SqlConnection();
			try{
				 statement=connection.prepareStatement(mail_Query);
				statement.setString(1, token);
				
				 resultset=statement.executeQuery();
				while(resultset.next()){
					email=resultset.getString("email");
				}
				if(email!=null){
					String query_token="select token from token_master where email='"+email+"'"
							+ "and flag=0 order by createdon desc limit 3";
					resultset=statement.executeQuery(query_token);
					while(resultset.next()){
						token_org=resultset.getString("token");
						if(token.equals(token_org)){
							return email;
						}
					}	return null;
				}else{
					return null;
				}
				
			}catch (SQLException Sexc) {
				return null;
			}
		}catch(Exception Exc){
			return null;
		}finally{
			if(connection!=null){
				try {
					connection.close();
					System.out.println("connection closed");
				} catch (SQLException Ecls) {
//					log.error("connection closing exception "+Ecls.getMessage());
				}
			}if(resultset!=null){
				try {
					resultset.close();
				} catch (SQLException Eres) {
//					log.error("resultset closing exception "+Eres.getMessage());
				}
			}if(statement!=null){
				try {
					statement.close();
				} catch (SQLException Esta) {
//					log.error("statement closing exception "+Esta.getMessage());
				}
			}
		}
	}
	
}
