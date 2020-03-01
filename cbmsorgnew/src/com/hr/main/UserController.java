/**
 * 
 */
package com.hr.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONObject;

import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.security.Security;
import com.hr.utils.MessageConfig;
import com.hr.utils.RandomString;
import com.hr.utils.ValidatePassword;



/**
 * @author Admin
 *
 */
@SuppressWarnings({"unused","resource"})
@Path("UserController")
public class UserController {
	private static Log log=LogFactory.getLog(UserController.class);
	private static final String userProfile="select mstuser.userid, mstuser.employeeid,mstuser.emailid, mstuser.employeename,mstuser.username,mstuser.roleid, " + 
			"    mstuser.organizationid,mstuser.orgunitid,mstuser.departmentunitid,mstuser.departmentid, mstuser.positionid,mstuser.org_id, " + 
			"    mstemployee.emp_fathername,mstemployee.emp_dob,mstemployee.emp_phone, organization.organizationname, organization_unit.organizationunitname, " + 
			"    mstdepartment.department_name,department_unit.departmentunitname,mstposition.position_name FROM public.mstuser " + 
			"    left join mstemployee on mstemployee.employeeid= mstuser.employeeid " + 
			"    left join organization on organization.organizationid= mstuser.organizationid " + 
			"    left join organization_unit on organization_unit.orgunitid= mstuser.orgunitid " + 
			"    left join mstdepartment on mstdepartment.departmentid= mstuser.departmentid " + 
			"    left join department_unit on department_unit.departmentunitid= mstuser.departmentunitid " + 
			"    left join mstposition on mstposition.positionid= mstuser.positionid where  mstuser.userid=?";
	
	@POST
    @Path("/changePassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(MyObject param) {
		String currentpassword=param.getPassword();
		String newpassword=param.getNewpassword();
		String confirmnewpassword=param.getConfirmnewpassword();
		int id=param.getUserid();
		JSONObject responseObject=new JSONObject();
		Response response=null;
		Connection connection=null;	
		PreparedStatement statement=null;
		ResultSet resultSet = null;
		try {			
			connection=DBConnection.sqlConnection();
			
				if(currentpassword==null||newpassword==null||confirmnewpassword==null||currentpassword.contains("null")||newpassword.contains("null")||confirmnewpassword.contains("null")) {
					responseObject=HelperClass.generateResponce(304,MessageConfig.login_304," Fields are empty ");
					response=HelperClass.convertObjectToResponce(responseObject, 200);			
					return response;	
				}else {
				String validatepwrd=ValidatePassword.validateNewPass(newpassword,confirmnewpassword);
					switch(validatepwrd) {
						
					case "Success":
						String newpword=Security.encrypt(Security.key, Security.initVector, newpassword);
						String oldpword=Security.encrypt(Security.key, Security.initVector, currentpassword);
						String Query="select * from fn_changepassword('"+id+"','"+oldpword+"','"+newpword+"') ";
						
						statement=connection.prepareStatement(Query);
						
						 resultSet = statement.executeQuery();
						 String fresult =null;
								while (resultSet.next()) {
									fresult=resultSet.getString(1);
								}
						switch(fresult) {
							case "Password changed":
								responseObject=HelperClass.generateResponce(200,"Password Successfully Changed",null);
								break;
							case "Password not changed":
								responseObject=HelperClass.generateResponce(201,MessageConfig.API_201,"password reset failed");
								break;
							case "Invalid User":
								responseObject=HelperClass.generateResponce(302,MessageConfig.login_302," Invalid user (or) please try again later ");
								break;
						}
						break;
					default :
						responseObject=HelperClass.generateResponce(302,validatepwrd,MessageConfig.login_303);
	
						break;
					}
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
				DBConnection.closeConnection(connection, statement, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
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
    @Path("/userProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response userProfile(MyObject param) {	
		JSONObject responseObject=new JSONObject();
			
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {			
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(userProfile);
			preparedStmt.setInt(1, param.getUserid());
			resultSet = preparedStmt.executeQuery();
			Object object=HelperClass.convertToJSON(resultSet);
			responseObject=HelperClass.generateResponce(200, object,null);
	
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
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
			Response response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}	
	
}
