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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.utils.MessageConfig;

/**
 * @author Admin
 *
 */
@Path("Document")
public class Document {
	
	
	private static Log log=LogFactory.getLog(Document.class);

	private static final String getDocumentByID="select documentid,doc_id,filedata, converted_filedata from document where doc_id=?";
	
	/**
	 * 
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 10/25/2018
	 * @version v1.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 * 
	 */
	@POST
    @Path("/getDocumentByID")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public static Response getDocumentByID(MyObject param) {	
		JSONObject responseObject=new JSONObject();
			
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			// connecting to database
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(getDocumentByID);
			preparedStmt.setString(1, param.getDoc_id());
			// excecuting query to resultset
			 resultSet = preparedStmt.executeQuery();
			 //converting result set to object
			Object object=HelperClass.convertToJSONObject(resultSet);
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
				// closing the database connnections
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
		// now bulid the response
		Response response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
}
