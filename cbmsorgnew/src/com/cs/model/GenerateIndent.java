///**
// * 
// */
package com.cs.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;

import com.cs.TokenCheck;
import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.utils.MessageConfig;
//
///**
// * @author Admin
// *
// */
@Path("GenerateIndent")
@SuppressWarnings({"unused","unchecked"})
public class GenerateIndent {
	
	private static Log log=LogFactory.getLog(GenerateIndent.class);
	
//	private static  String itemsToRaiseIndent=" with items as(select distinct(product_id) as productid from tbl_project_stage_material as a " + 
//			"                  where  project_id=? and is_stage_output=0 ) " + 
//			"   	select ? as  project_id, items.productid,(select project_quantity from tbl_project where project_id=?)project_quantity, " + 
//			"    (select batch_size from  tbl_project_model_details where  project_id=?)batch_size, " + 
//			"    round(((select project_quantity from tbl_project where project_id=?)/(select batch_size from  tbl_project_model_details where  project_id=?)))batches_count,(select sum(quantity) " + 
//			"                               from tbl_project_stage_material where project_id=? and product_id=items.productid)item_quantity,(select  (round(((select project_quantity from tbl_project where project_id=?)/(select batch_size from  tbl_project_model_details where  project_id=?)))*(select sum(quantity) " + 
//			"                               from tbl_project_stage_material where project_id=? and product_id=items.productid)) )quantity, (select product_name from tbl_products where tbl_products.product_id=items.productid )  " + 
//			"    from items";
	private static  String itemsToRaiseIndent=" with items as(select distinct(product_id) as productid from tbl_project_stage_material as a   " + 
			"			                  where  project_id=? and is_stage_output=0 )   " + 
			"			   	select ? as  project_id, items.productid,(select sum(quantity)   " + 
			"			                               from tbl_project_stage_material where project_id=? and product_id=items.productid)item_quantity,0::numeric as quantity, (select product_name from tbl_products where tbl_products.product_id=items.productid )    " + 
			"			    from items ";
	
	private static  String createExternalIndent=" select * from fn_generateIndentForproject(?,'Indent Raised','Inprocess',?,?,?)";
	
	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 12/04/2018
	 * @version v1.0.2
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
//	@POST
//    @Path("/itemsToRaiseIndent")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//    public Response itemsToRaiseIndent(Model param) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(param.getId());
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			String role_id=(String) jsObject.get("auth_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			
//			connection=DBConnection.sqlConnection();
//			preparedStmt=connection.prepareStatement(itemsToRaiseIndent);
//			preparedStmt.setString(1, param.getProject_id());
//			preparedStmt.setString(2, param.getProject_id());
//			preparedStmt.setString(3, param.getProject_id());
//			
//			System.out.println("items_to_raise_indent: "+preparedStmt);
//			 resultSet = preparedStmt.executeQuery();
//			Object object=HelperClass.convertToJSON(resultSet);
//		responseObject=HelperClass.generateResponce(200, object,null);
//			
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
//		}
//	/**
//	 * 
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 12/02/2018
//	 * @version v1.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
//	@POST
//    @Path("/createExternalIndent")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//    public Response createExternalIndent(Model param) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(param.getId());
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			
//			connection=DBConnection.sqlConnection();
//			
//			PGobject products= new PGobject(); 
//			products.setType("json");
//			products.setValue(param.getProducts());
//			preparedStmt=connection.prepareStatement("select * from fn_generateIndentForproject('"+param.getProject_id()+"','Indent Raised','Inprocess','"+jsObject.get("username")+"','"+param.getComments()+"','"+products+"')");
//			System.out.println(preparedStmt);
//			 resultSet = preparedStmt.executeQuery();
//			 resultSet.next();
//			String status=resultSet.getString(1);
//			if(status.contains("Success"))
//				responseObject=HelperClass.generateResponce(200, "Indent raised successfully",null);
//			else
//				responseObject=HelperClass.generateResponce(201,status,null);
//			
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
//		}
//	
//
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/05/2019
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
//	@POST
//    @Path("/internalIndentsList")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//    public Response internalIndentsList(Model param) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(param.getId());
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			
//			connection=DBConnection.sqlConnection();
//			
//			String internalIndentsList="select indentid,varindentno,indent_date,tbl_project_batch_indent.project_id,project_code,project_type, 'Stage-'||stage_name::varchar  as stage,stage_name, batch_id,'Material issue pending'::varchar as status" + 
//					" from tbl_project_batch_indent " + 
//					" inner join tbl_project on tbl_project.project_id=tbl_project_batch_indent.project_id where is_indentissued=0  and tbl_project_batch_indent.indent_date=current_date ";
//			preparedStmt=connection.prepareStatement(internalIndentsList);
//			
//			System.out.println("internalIndentsList: "+preparedStmt);
//			 resultSet = preparedStmt.executeQuery();
//			Object object=HelperClass.convertToJSON(resultSet);
//		responseObject=HelperClass.generateResponce(200, object,null);
//			
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
//		}
//	
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/21/2019 12:11PM
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
	@POST
    @Path("/storeWiseIndentsList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response departmentWiseIndentsList(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			String role_id=(String) jsObject.get("auth_id");
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			
			connection=DBConnection.sqlConnection();
			
			String departmentWiseIndentsList=" select indent_id from tbl_store_indent where store_id=?::varchar and isissued=0 "
					+ "and status='pending' and purchase_requisition_id is null";
			preparedStmt=connection.prepareStatement(departmentWiseIndentsList);
			preparedStmt.setString(1, param.getDepartmentid());
			//preparedStmt.setInt(2, param.getDepartmentid());
			System.out.println("storeWiseIndentsList: "+preparedStmt);
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
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
//	
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/21/2019 12:11PM
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
	@POST
    @Path("/productListByIndent")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response productListByIndent(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			String role_id=(String) jsObject.get("auth_id");
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			
			connection=DBConnection.sqlConnection();
			
//			String productListByIndent="SELECT  (select project_code from tbl_project where tbl_project.project_id=tbl_indent_ingrediants.project_id )project, tbl_products.product_name||'-'||tbl_indent_ingrediants.product_id as product_name,tbl_products.product_unit, raised_quantity, 0::numeric as required_stock" + 
//					"	FROM public.tbl_indent_ingrediants" + 
//					"    inner join tbl_products on tbl_products.product_id= tbl_indent_ingrediants.product_id" + 
//					"    where indent_id=? and status='pending' union" + 
//					"    SELECT (select department_name from mstdepartment where departmentid=tbl_department_indent_ingrediants.department_id)project,  tbl_products.product_name||'-'||tbl_department_indent_ingrediants.product_id as product_name,tbl_products.product_unit, raised_qty, 0::numeric as required_stock " + 
//					"    from tbl_department_indent_ingrediants" + 
//					"    inner join tbl_products on tbl_products.product_id= tbl_department_indent_ingrediants.product_id" + 
//					"    where indent_id=? and status='pending' and isissued=0 ";
			String productListByIndent = "select tbl_products.product_name||'-'||tbl_store_indent_products.product_id as product_name,product_unit.product_unit, raised_qty, 0::numeric as required_stock "
					+ "from tbl_store_indent_products "
					+ "inner join tbl_products on tbl_products.product_id= tbl_store_indent_products.product_id "
					+ "inner join product_unit on product_unit.id =tbl_products.product_unit "
					+ "where indent_id=? and status='pending' and isissued=0 ";
			preparedStmt=connection.prepareStatement(productListByIndent);
			preparedStmt.setString(1, param.getIndentno());
			//preparedStmt.setString(2, param.getIndentno());
			System.out.println("productListByIndent: "+preparedStmt);
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
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
	
//	
//	
//	
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/21/2019 12:11PM
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
//
//	@POST
//    @Path("/productListByIndentToIssue")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//    public Response productListByIndentToIssue(Model param) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(param.getId());
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			
//			connection=DBConnection.sqlConnection();
//			
//			String productListByIndent=" select tbl_project_batch_indent_items.product_id,indentqty,is_stageoutput,case  when is_stageoutput=1 then  tbl_project_batch_indent_items.product_id else tbl_products.product_code end as product_code, case  when is_stageoutput=1 then  '' else tbl_products.product_unit end as product_unit\r\n" + 
//					" from tbl_project_batch_indent_items\r\n" + 
//					"    left join tbl_products on tbl_products.product_id= tbl_project_batch_indent_items.product_id\r\n" + 
//					"    where varindentno=? ";
//			preparedStmt=connection.prepareStatement(productListByIndent);
//			preparedStmt.setString(1, param.getIndentno());
//			
//			System.out.println("productListByIndent: "+preparedStmt);
//			 resultSet = preparedStmt.executeQuery();
//			String project_type=param.getProject_type(); 
//			ArrayList array= new ArrayList();
//			project_type=project_type.toLowerCase();
//			 while(resultSet.next()) {
//				 Map map= new HashMap();
//				 String product_code=null;String product_id=null,product_unit=null;
//				 Object indentqty=null; int is_stageoutput=0; 
//				 Object stock_held=null;
//				 
//				 product_code= resultSet.getString("product_code");
//				 product_id= resultSet.getString("product_id");
//				 product_unit= resultSet.getString("product_unit");
//				 indentqty= resultSet.getObject("indentqty");
//				 
//				 is_stageoutput=resultSet.getInt("is_stageoutput");
//				 map.put("product_code", product_code);
//				 map.put("product_id", product_id);
//				 map.put("product_unit", product_unit);
//				 map.put("indentqty", indentqty);
//				 map.put("is_stageoutput", is_stageoutput);
//				 
//				 
//				 
//				 if(is_stageoutput==1) {
//					 String batchid=null,stage=null;
//					 String[] info=product_id.split("/");
//					 batchid=info[0];
//					 stage=info[1];
//					 String getStockQuery="select COALESCE((select remaining_qty  from tbl_transfer_project_batch_outputs where project_id=? and stage_name=? and projectbatch_id=?),0) as total_stock";
//					 preparedStmt=connection.prepareStatement(getStockQuery);
//						preparedStmt.setString(1, param.getProject_id());
//						preparedStmt.setString(2, stage);
//						preparedStmt.setString(3, batchid);
//						 
//					 
//				 }else {
//					 if(project_type.contains("own")||project_type.equals("own")) {
//						 String dep_id= (String)jsObject.get("auth_id");
//						 String getStockQuery="select COALESCE((select sum(total_stock) from tbl_current_stock where dep_id=?::int and product_id=? ),0)";
//						 preparedStmt=connection.prepareStatement(getStockQuery);
//							preparedStmt.setString(1, dep_id);
//							preparedStmt.setString(2, product_id);
//							
//						 
//					 }else {
//						 
//						 String getStockQuery="select COALESCE((select sum(total_stock) from tbl_client_current_stock where project_id=? and product_id=?  and  dep_id=?),0)";
//						 	preparedStmt=connection.prepareStatement(getStockQuery);
//						 	preparedStmt.setString(1, param.getProject_id());
//						 	preparedStmt.setString(2, product_id);
//							preparedStmt.setInt(3, 3);
//					 }
//					
//				 }System.out.println("Client Stock data : "+preparedStmt);
//				 ResultSet rs = preparedStmt.executeQuery();
//				 while(rs.next()) {
//					 stock_held=rs.getObject(1);
//					 map.put("stock_held", stock_held);
//					}
//				 array.add(map); 
//			 }
//			 
//			 
//			
//		responseObject=HelperClass.generateResponce(200, array,null);
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
//		}
//	
//	
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/21/2019 12:11PM
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
//
//	@POST
//    @Path("/loadProductsToIssue")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response loadProductsToIssue(@FormParam("batch_id") String batch_id,@FormParam("id") String id,@FormParam("project_id") String project_id,@FormParam("products") String products,@FormParam("indent_id") String indent_id,@FormParam("stage_name") String stage_name) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(id);
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			String dep_id=(String)jsObject.get("auth_id");
//			connection=DBConnection.sqlConnection();
//		ArrayList batches_out=new ArrayList();	
//		ArrayList products_out=new ArrayList();
//		org.json.JSONArray inproducts= new JSONArray(products);
//		for(int i=0;i<inproducts.length();i++){
//			JSONObject jsObject_stageOut=new JSONObject();
//			String product_id=null,project_type=null,in_house_batch_id=null, ar_number=null,client_id=null;
//			Double total_stock=null,indentqty=null,rem_qty=null;
//					Date exp_date=null;
//			org.json.JSONObject inobject=inproducts.getJSONObject(i);
//			product_id=inobject.getString("product_id");
//			indentqty=inobject.getDouble("indentqty");
//			if(inobject.getInt("is_stageoutput")==1){
//				
//				jsObject_stageOut.put("product_code", inobject.getString("product_code"));
//				jsObject_stageOut.put("indentqty", indentqty);
//				jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//				jsObject_stageOut.put("product_id", inobject.getString("product_id"));
//				jsObject_stageOut.put("product_id",product_id);
//				jsObject_stageOut.put("project_id",project_id);
//				jsObject_stageOut.put("indentno",indent_id);
//				jsObject_stageOut.put("is_stageoutput", 1);
//				batches_out.add(jsObject_stageOut);
//			}else {
//				String Query="select lower(project_type),client_id from tbl_project where project_id='"+project_id+"'  ";
//				preparedStmt=connection.prepareStatement(Query);
//				resultSet = preparedStmt.executeQuery();
//				resultSet.next();
//				project_type=resultSet.getString(1);
//				client_id=resultSet.getString(2);
//				
//				if(project_type.contains("own")) {
//					
//					
//					String getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' order by exp_date asc limit 1";
//					preparedStmt=connection.prepareStatement(getdetails);
//					resultSet = preparedStmt.executeQuery();
//					resultSet.next();
//					in_house_batch_id=resultSet.getString(1);
//					ar_number=resultSet.getString(2);
//					total_stock=resultSet.getDouble(3);
//					exp_date=resultSet.getDate(4);
//					
//					if(total_stock<indentqty) {
//						rem_qty=indentqty;
//						jsObject_stageOut.put("product_code", inobject.getString("product_code"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//						jsObject_stageOut.put("pid", product_id);
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", total_stock);
//						products_out.add(jsObject_stageOut);
//						
//						System.out.println("products_out: "+products_out);
//						rem_qty=indentqty-total_stock;
//						
//						while(rem_qty>0) {
//							JSONObject extra_object=new JSONObject();
//							 getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' and exp_date>'"+exp_date+"' order by exp_date asc limit 1";
//							 preparedStmt=connection.prepareStatement(getdetails);
//							ResultSet rs = preparedStmt.executeQuery();
//							while(rs.next()) {
//							in_house_batch_id=rs.getString(1);
//							ar_number=rs.getString(2);
//							total_stock=rs.getDouble(3);
//							exp_date=rs.getDate(4);
//							}
//							if(total_stock<rem_qty) {
//								extra_object.put("product_code", inobject.getString("product_code"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_held"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch_id", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", total_stock);
//								products_out.add(extra_object);
//								rem_qty=rem_qty-total_stock;
//							}else {
//								extra_object.put("product_code", inobject.getString("product_code"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_held"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch_id", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", rem_qty);
//								products_out.add(extra_object);
//								rem_qty=0.0;
//							}
//						
//							
//							
//						}
//						
//						
//					}else {
//						
//						jsObject_stageOut.put("product_code", inobject.getString("product_code"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//						jsObject_stageOut.put("pid", product_id);
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", indentqty);
//						
//						products_out.add(jsObject_stageOut);
//					}
//				}else {
//					
//
//					String getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_client_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' and project_id='"+project_id+"' and client_id='"+client_id+"' order by exp_date asc limit 1";
//					preparedStmt=connection.prepareStatement(getdetails);
//					resultSet = preparedStmt.executeQuery();
//					resultSet.next();
//					in_house_batch_id=resultSet.getString(1);
//					ar_number=resultSet.getString(2);
//					total_stock=resultSet.getDouble(3);
//					exp_date=resultSet.getDate(4);
//					if(total_stock<indentqty) {
//						rem_qty=indentqty;
//						jsObject_stageOut.put("product_code", inobject.getString("product_code"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//						jsObject_stageOut.put("pid", product_id);
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", total_stock);
//						products_out.add(jsObject_stageOut);
//						
//						System.out.println("products_out: "+products_out);
//						rem_qty=indentqty-total_stock;
//						
//						while(rem_qty>0) {
//							JSONObject extra_object=new JSONObject();
//							 getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' and exp_date>'"+exp_date+"'  and project_id='"+project_id+"' and client_id='"+client_id+"' order by exp_date asc limit 1";
//							 preparedStmt=connection.prepareStatement(getdetails);
//							ResultSet rs = preparedStmt.executeQuery();
//							while(rs.next()) {
//							in_house_batch_id=rs.getString(1);
//							ar_number=rs.getString(2);
//							total_stock=rs.getDouble(3);
//							exp_date=rs.getDate(4);
//							}
//							if(total_stock<rem_qty) {
//								extra_object.put("product_code", inobject.getString("product_code"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_held"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", total_stock);
//								products_out.add(extra_object);
//								rem_qty=rem_qty-total_stock;
//							}else {
//								extra_object.put("product_code", inobject.getString("product_code"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_held"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", rem_qty);
//								products_out.add(extra_object);
//								rem_qty=0.0;
//							}
//						
//							
//							
//						}
//						
//						
//					}else {
//						
//						jsObject_stageOut.put("product_code", inobject.getString("product_code"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//						jsObject_stageOut.put("pid", inobject.getString("product_id"));
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", indentqty);
//						
//						products_out.add(jsObject_stageOut);
//					}
//					
//				}
//			}
//			
//		}
//			
//		JSONObject object =new JSONObject();
//		object.put("batches", batches_out);
//		object.put("products", products_out);
//		responseObject=HelperClass.generateResponce(200, object,null);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
//		}
//	
//	/**
//	 * @param param
//	 * @return response
//	 * @author Akhil Yelakanti
//	 * @date 01/21/2019 12:11PM
//	 * @version v1.0.0
//	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
//	 */
//	
//
//	@POST
//    @Path("/loadProductsToIssueForExternalIndent")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response loadProductsToIssueForExternalIndent(@FormParam("id") String id,@FormParam("project_id") String project_id,@FormParam("products") String products,@FormParam("indent_id") String indent_id) {	
//		JSONObject responseObject=new JSONObject();
//		Response response=null;
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(id);
//		if(jsObject.containsKey("status")){
//			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
//			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
//			return response;
//		}else {
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connection=null;	
//		PreparedStatement preparedStmt=null;
//		ResultSet resultSet = null;
//		try {	
//			String dep_id=(String)jsObject.get("auth_id");
//			connection=DBConnection.sqlConnection();
//		ArrayList batches_out=new ArrayList();	
//		ArrayList products_out=new ArrayList();
//		org.json.JSONArray inproducts= new JSONArray(products);
//		for(int i=0;i<inproducts.length();i++){
//			JSONObject jsObject_stageOut=new JSONObject();
//			String product_id=null,project_type=null,in_house_batch_id=null, ar_number=null,client_id=null;
//			Double total_stock=null,indentqty=null,rem_qty=null;
//					Date exp_date=null;
//			org.json.JSONObject inobject=inproducts.getJSONObject(i);
//			product_id=inobject.getString("productId");
//			indentqty=inobject.getDouble("issued_quantity");
//
//					String getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' order by exp_date asc limit 1";
//					preparedStmt=connection.prepareStatement(getdetails);
//					resultSet = preparedStmt.executeQuery();
//					resultSet.next();
//					in_house_batch_id=resultSet.getString(1);
//					ar_number=resultSet.getString(2);
//					total_stock=resultSet.getDouble(3);
//					exp_date=resultSet.getDate(4);
//					
//					if(total_stock<indentqty) {
//						rem_qty=indentqty;
//						jsObject_stageOut.put("product_code", inobject.getString("productName"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_held"));
//						jsObject_stageOut.put("pid", product_id);
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", total_stock);
//						products_out.add(jsObject_stageOut);
//						
//						System.out.println("products_out: "+products_out);
//						rem_qty=indentqty-total_stock;
//						
//						while(rem_qty>0) {
//							JSONObject extra_object=new JSONObject();
//							 getdetails="select in_house_batch_id, ar_number,total_stock,exp_date from  tbl_current_stock where product_id='"+product_id+"' and dep_id='"+dep_id+"' and exp_date>'"+exp_date+"' order by exp_date asc limit 1";
//							 preparedStmt=connection.prepareStatement(getdetails);
//							ResultSet rs = preparedStmt.executeQuery();
//							while(rs.next()) {
//							in_house_batch_id=rs.getString(1);
//							ar_number=rs.getString(2);
//							total_stock=rs.getDouble(3);
//							exp_date=rs.getDate(4);
//							}
//							if(total_stock<rem_qty) {
//								extra_object.put("product_code", inobject.getString("productName"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_availability"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch_id", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", total_stock);
//								products_out.add(extra_object);
//								rem_qty=rem_qty-total_stock;
//							}else {
//								extra_object.put("product_code", inobject.getString("productName"));
//								extra_object.put("indentqty", indentqty);
//								extra_object.put("stock_held", inobject.getString("stock_availability"));
//								jsObject_stageOut.put("pid", product_id);
//								extra_object.put("project_id",project_id);
//								extra_object.put("indentno",indent_id);
//								extra_object.put("is_stageoutput", 0);
//								extra_object.put("in_house_batch_id", in_house_batch_id);
//								extra_object.put("ar_number", ar_number);
//								extra_object.put("edate",exp_date);
//								extra_object.put("product_quantity", rem_qty);
//								products_out.add(extra_object);
//								rem_qty=0.0;
//							}
//						}
//						
//						
//					}else {
//						
//						jsObject_stageOut.put("product_code", inobject.getString("productName"));
//						jsObject_stageOut.put("indentqty", indentqty);
//						jsObject_stageOut.put("stock_held", inobject.getString("stock_availability"));
//						jsObject_stageOut.put("pid", product_id);
//						jsObject_stageOut.put("project_id",project_id);
//						jsObject_stageOut.put("indentno",indent_id);
//						jsObject_stageOut.put("is_stageoutput", 0);
//						jsObject_stageOut.put("in_house_batch", in_house_batch_id);
//						jsObject_stageOut.put("ar_number", ar_number);
//						jsObject_stageOut.put("edate",exp_date);
//						jsObject_stageOut.put("product_quantity", indentqty);
//						
//						products_out.add(jsObject_stageOut);
//					}
//		}
//		JSONObject object =new JSONObject();
//		object.put("products", products_out);
//		responseObject=HelperClass.generateResponce(200, object,null);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			} catch (ClassNotFoundException e) {
//				log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		finally {
//			try {
//				DBConnection.closeConnection(connection, preparedStmt, resultSet);
//			} catch (SQLException e) {
//				log.error(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
//			}
//		}
//			response=HelperClass.convertObjectToResponce(responseObject, 200);			
//	return response;	
		}
//}
