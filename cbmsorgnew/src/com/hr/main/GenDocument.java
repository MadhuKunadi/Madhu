/**
 * 
 */
package com.hr.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONObject;

import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.utils.MessageConfig;
import com.hr.utils.Utils;



/**
 * @author Admin
 *
 */
public class GenDocument {
static String URL=Utils.getURI();
	
	private static Log log=LogFactory.getLog(GenDocument.class);
	private static final String initiateWorkflowTemp="select createworkflowtransactionwithdocumenttemp(?,?,?,?,?,?,?,?)";
	

    public static JSONObject initiateWorkflowDocURL(MyObject param) {	
		JSONObject responseObject=new JSONObject();
		String document=param.getDocument();
			String filename=null,filetype=null;
			int filesize=0;
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
	
		try {	
			document=URL+document;
			System.out.println("iondoc   : "+document);
			JSONObject docobj=Utils.getBase64EncodedImage(document);
			document=docobj.toString();
			org.json.JSONObject jsonDoc=new org.json.JSONObject(document);
			System.out.println("length: "+jsonDoc.length()+"  doc: "+jsonDoc);
			if(jsonDoc.length()==0) {
				responseObject=HelperClass.generateResponce(208,MessageConfig.API_208,"No file has selected");
				
			}else {
				filename=jsonDoc.getString("filename");
				filesize=jsonDoc.getInt("filesize");
				filetype=filename.substring(filename.lastIndexOf(".")+1);
				filename=filename.substring(0,filename.lastIndexOf("."));
				
			}
			
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(initiateWorkflowTemp);
			preparedStmt.setString(1, param.getProcess_id());
			preparedStmt.setString(2, document);
			preparedStmt.setString(3, document.toString());
			preparedStmt.setInt(4, param.getCreatedby());
			preparedStmt.setString(5, filename);
			preparedStmt.setString(6, filesize+"bytes");
			preparedStmt.setString(7, filetype);
			preparedStmt.setString(8, param.getComments());
			ResultSet rs = preparedStmt.executeQuery();
				rs.next();
				String status=rs.getString(1);
			if(!status.contains("Failed")) 
				responseObject=HelperClass.generateResponce(200,status,null);
			
			else 
				responseObject=HelperClass.generateResponce(201,MessageConfig.API_201,"Workflow rinitiatation failed");
		} catch (SQLException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
		} catch (JSONException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			responseObject=HelperClass.generateResponce(208,MessageConfig.API_208,"No file has selected");
		}finally {
			try {
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return responseObject;	
		}
	
    public static JSONObject initiateWorkflowDoc(MyObject param) {	
		JSONObject responseObject=new JSONObject();
		String document=param.getDocument();
			String filename=null,filetype=null;
			int filesize=0;
			String base64=null;
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
	
		try {	
			
			
			org.json.JSONObject jsonDoc=new org.json.JSONObject(document);
			System.out.println("length: "+jsonDoc.length()+"  doc: "+jsonDoc);
			if(jsonDoc.length()==0) {
				responseObject=HelperClass.generateResponce(208,MessageConfig.API_208,"No file has selected");
				
			}else {
				filename=jsonDoc.getString("filename");
				base64=jsonDoc.getString("base64");			
				int y=0;
				if(base64.endsWith("==")) {
					y=2;
				}else{
					y=1;
				}
				int X=0;
				
				int n=base64.length();
				filesize = (n * (3/4)) - y;
//				filesize=jsonDoc.getInt("filesize");
				filetype=filename.substring(filename.lastIndexOf(".")+1);
				filename=filename.substring(0,filename.lastIndexOf("."));
				
			}
			
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(initiateWorkflowTemp);
			preparedStmt.setString(1, param.getProcess_id());
			preparedStmt.setString(2, document);
			preparedStmt.setString(3, document.toString());
			preparedStmt.setInt(4, param.getCreatedby());
			preparedStmt.setString(5, filename);
			preparedStmt.setString(6, filesize+"bytes");
			preparedStmt.setString(7, filetype);
			preparedStmt.setString(8, param.getComments());
			ResultSet rs = preparedStmt.executeQuery();
				rs.next();
				String status=rs.getString(1);
			if(!status.contains("Failed")) 
				responseObject=HelperClass.generateResponce(200,status,null);
			
			else 
				responseObject=HelperClass.generateResponce(201,MessageConfig.API_201,"Workflow rinitiatation failed");
		} catch (SQLException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
		} catch (JSONException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			responseObject=HelperClass.generateResponce(208,MessageConfig.API_208,"No file has selected");
		}finally {
			try {
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return responseObject;	
		}
}
