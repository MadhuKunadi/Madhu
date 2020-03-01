package com.cs;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import com.helper.DBConnection;
import com.utils.APIResponse;
import com.utils.IndentDetails;
import com.utils.Ingrediants;

@SuppressWarnings({ "unchecked" ,"unused"})
@Path("/indent")
public class IndentAPI {
	
	@POST
	@Path("/generateIndentId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generateExtIndentId(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		String subQuery=new String();
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			String role_id=(String) jsObject.get("auth_id");
			System.out.println(role_id);
//			if(role_id==4){
//				
//			}
			System.out.println(role_id);	
		}	Connection connection=null;
		try {
			connection=DBConnection.SqlConnection();
			try {
				String purchaseorderid= "";
				String Query="select * from fn_indent_id_generation('IND','"+jsObject.get("email").toString()+"')";
				System.out.println(Query);
				CallableStatement csStatement=connection.prepareCall(Query);
				csStatement.execute();
				ResultSet result=csStatement.getResultSet();
				while(result.next()){
					purchaseorderid=result.getString(1);
				}if(purchaseorderid!=null&&!purchaseorderid.isEmpty()&&purchaseorderid.contains("IND")){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", purchaseorderid);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "List is empty");
				}
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
		} catch (ClassNotFoundException | SQLException e1) {
			jsonObject.clear();
			jsonObject.put("Status", "Failed");
			jsonObject.put("Message", "Something went wrong");
			jsonObject.put("error", e1.getMessage());
		}finally {
			try {
				DBConnection.closeConnection(connection);
			} catch (SQLException e) {
			}
		}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@POST
	@Path("/createIndent")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createIndent(IndentDetails indent) throws ClassNotFoundException, IOException, SQLException{
		System.out.println("Create Indent");
		APIResponse response=new APIResponse();
		List<Object> listOfData=new ArrayList<Object>();
		String message="";
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(indent.getId());
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			message=(String) jsObject.get("status");
			response.setMessage(message);
			response.setStatus("Failed");
			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
		}else{
			String email=(String) jsObject.get("email");
			String role_id=(String) jsObject.get("auth_id");
			System.out.println(role_id);
		}Connection connection=null;
		String role_id=(String) jsObject.get("auth_id");
		try{
			List<Ingrediants> ingrediants = indent.getListOfIngrediants();
			connection=DBConnection.SqlConnection();
			connection.setAutoCommit(false);
			String query="insert into tbl_store_indent (indent_id,store_id,warehouse_id,created_by)values (?,?,?,?)";
			PreparedStatement statement=connection.prepareStatement(query);
			statement.setString(1, indent.getIndentId());
			statement.setString(2, role_id);
			statement.setString(3, indent.getWarehouse_id());
			statement.setString(4, (String) jsObject.get("email"));
			statement.executeUpdate();
			for(Ingrediants ingrediant:ingrediants){
				query="insert into tbl_store_indent_products (indent_id,store_id,warehouse_id,product_id,raised_qty,created_by) values (?,?,?,?,?,?)";
				String product_id=ingrediant.getProductId();
				statement=connection.prepareStatement(query);
				statement.setString(1, indent.getIndentId());
				statement.setString(2, role_id);
				statement.setString(3, indent.getWarehouse_id());
			//	statement.setString(4, product_id.substring(product_id.lastIndexOf("-")+1));
				statement.setString(4, String.valueOf(ingrediant.getProductId()));
				statement.setDouble(5, Double.valueOf(ingrediant.getQuantity()));
				statement.setString(6, (String)jsObject.get("email"));
				statement.executeUpdate();
			}
			message="Indent Created Successfully";
			connection.setAutoCommit(true);
			response.setMessage(message);
			response.setStatus("SUCCESS");
			return Response.status(Status.OK).entity(response).build();
			
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().contains("already exists")){
				message="Indent id already exists";
				response.setMessage(message);
				response.setStatus("ERROR");
			}else{
				message="Indent not Created";
				response.setMessage(message);
				response.setStatus("ERROR");
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}finally {
			try {
				
				DBConnection.closeConnection(connection);
			} catch (SQLException e) {
			}
		}
	}
	
//	@POST
//	@Path("/updateIndent")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateIndent(IndentDetails indent) {
//		System.out.println("Update Indent");
//		APIResponse response=new APIResponse();
//		List<Object> listOfData=new ArrayList<Object>();
//		String message="";
//		JSONObject jsObject=TokenCheck.checkTokenStatus(indent.getId());
//		if(jsObject.containsKey("status")){
//			message=(String) jsObject.get("status");
//			response.setMessage(message);
//			response.setStatus("Failed");
//			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//		}Connection connection=null;
//		try{
//			List<Ingrediants> ingrediants = indent.getListOfIngrediants();
//			connection=DBConnection.SqlConnection();
//			connection.setAutoCommit(false);
//			for(Ingrediants ingrediant:ingrediants){
//				String query="insert into tbl_store_indent_products (indent_id,department_id,product_id,raised_qty,created_by) values (?,?,?,?,?)"
//						+ " ON CONFLICT (indent_id,product_id)  DO  update  set raised_qty=?,updated_by=?,updated_on=current_timestamp where "
//						+ " tbl_department_indent_ingrediants.indent_id=? and tbl_department_indent_ingrediants.product_id=?";
//				PreparedStatement statement=connection.prepareStatement(query);
//				String product_id=ingrediant.getProductId();
//				statement=connection.prepareStatement(query);
//				statement.setString(1, indent.getIndentId());
//				statement.setInt(2, (Integer)jsObject.get("departmentid"));
//				statement.setString(3, product_id.substring(product_id.lastIndexOf("-")+1));
//				statement.setDouble(4, Double.valueOf(ingrediant.getQuantity()));
//				statement.setString(5, (String)jsObject.get("email"));
//				statement.setDouble(6, Double.valueOf(ingrediant.getQuantity()));
//				statement.setString(7, (String)jsObject.get("email"));
//				statement.setString(8, indent.getIndentId());
//				statement.setString(9, product_id.substring(product_id.lastIndexOf("-")+1));
//				System.out.println(statement);
//				statement.executeUpdate();
//			}
//			message="Indent Updated Successfully";
//			connection.setAutoCommit(true);
//			response.setMessage(message);
//			response.setStatus("SUCCESS");
//			return Response.status(Status.OK).entity(response).build();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			if(e.getMessage().contains("already exists")){
//				message="Indent id already exists";
//				response.setMessage(message);
//				response.setStatus("ERROR");
//			}else{
//				message="Indent not Created";
//				response.setMessage(message);
//				response.setStatus("ERROR");
//			}
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
//		}finally {
//			try {
//				
//				DBConnection.closeConnection(connection);
//			} catch (SQLException e) {
//			}
//		}
//	}
//	
	@POST
	@Path("/viewIndent")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response viewIndent(@FormParam("id") String id,@FormParam("store_id") int store_id) {
		APIResponse response=new APIResponse();
		List<Object> listOfData=new ArrayList<Object>();
		String message="";
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		String subQuery="";
		if(jsObject.containsKey("status")){
			message=(String) jsObject.get("status");
			response.setMessage(message);
			response.setStatus("Failed");
			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id=(String) jsObject.get("auth_id");
			//String dept_name=(String) jsObject.get("departmentname");
//			System.out.println(departmsent_id);
//			if(store_id<=0 && dept_name.toLowerCase().contains("raw material warehouse") ){
//				subQuery=" ";
//			}else{
//				if(store_id>0){
//					subQuery=" where store_id='"+store_id+"'";
//				}else{
//					subQuery=" where store_id='"+role_id+"'";
//				}
//				
//			}
//			
//		}
			Connection connection=null;
		try{
			String Query="";
			//String auth_id=(String) jsObject.get("auth_id");
			connection=DBConnection.SqlConnection();
			if(role_id==3){
//			String Query="select id,indent_id,store_id,created_on::date,isissued,status,created_by as indent_raised_by,tbl_store.store_name from tbl_store_indent"
//					+ " inner join tbl_store on tbl_store.store_id=tbl_store_indent.store_id "+subQuery+" order by created_on desc";
			Query="select tbl_store_indent.id ,tbl_store_indent.indent_id,tbl_store_indent.store_id,"
					+ "tbl_store_indent.created_on::date,tbl_store_indent.isissued,tbl_store_indent.status,"
					+ "tbl_store_indent.created_by as indent_raised_by,tbl_store.store_name from tbl_store_indent "
					+ "inner join tbl_store on tbl_store_indent.store_id=tbl_store.store_id "
					+ " where tbl_store_indent.store_id='"+auth_id+"' order by created_on desc";
			}else{
				Query="select tbl_store_indent.id ,tbl_store_indent.indent_id,tbl_store_indent.store_id,"
						+ "tbl_store_indent.created_on::date,tbl_store_indent.isissued,tbl_store_indent.status,"
						+ "tbl_store_indent.created_by as indent_raised_by,tbl_store.store_name from tbl_store_indent "
						+ "inner join tbl_store on tbl_store_indent.store_id=tbl_store.store_id "
						+ " where tbl_store_indent.warehouse_id='"+auth_id+"' and status='pending' order by created_on desc";
			}
			System.out.println("view indent: "+Query);
			ResultSet res_exeQuery=connection.createStatement().executeQuery(Query);
			while(res_exeQuery.next()){
				IndentDetails iDetails=new IndentDetails();
				iDetails.setIndent_id((int)res_exeQuery.getInt("id"));
				iDetails.setIndentId((String)res_exeQuery.getString("indent_id"));
				iDetails.setDeptName((String)res_exeQuery.getString("store_name"));
				iDetails.setCreatedBy((String)res_exeQuery.getString("indent_raised_by"));
				iDetails.setStatus((String)res_exeQuery.getString("status"));
				iDetails.setIs_issued((int)res_exeQuery.getInt("isissued"));
				iDetails.setCreated_on((String)res_exeQuery.getString("created_on"));
				listOfData.add(iDetails);
			}if(listOfData==null||listOfData.isEmpty()){
				response.setMessage("List is empty");
				response.setStatus("SUCCESS");
				return Response.status(Status.OK).entity(response).build();
			}else{
				response.setListOfData(listOfData);
				response.setMessage("");
				response.setStatus("SUCCESS");
				return Response.status(Status.OK).entity(response).build();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			response.setMessage("Please try again");
			response.setStatus("ERROR");
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}finally {
			try {
				
				DBConnection.closeConnection(connection);
			} catch (SQLException e) {
			}
		}
	}
	}
	
	@POST
	@Path("/viewIndentById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response viewIndentById(@FormParam("id") String id,@FormParam("indent_id") String indent_id,
			@FormParam("warehouse_id") String warehouse_id) {
		APIResponse response=new APIResponse();
		List<Object> listOfData=new ArrayList<Object>();
		String message="";
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			message=(String) jsObject.get("status");
			response.setMessage(message);
			response.setStatus("Failed");
			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id=(String) jsObject.get("auth_id");
			//String dept_name=(String) jsObject.get("departmentname");
//			System.out.println(departmsent_id);
			
			
		}Connection connection=null;
		try{
			int role_id=(int) jsObject.get("role_id");
			String auth_id=(String) jsObject.get("auth_id");
			if(role_id==1){
				warehouse_id=warehouse_id;
			}else{
				warehouse_id=auth_id;
			}
			connection=DBConnection.SqlConnection();
//			String Query="select tbl_store_indent_products.product_id,tbl_store_indent_products.indent_id,tbl_store_indent_products.product_id,tbl_store_indent_products.status, "
//					+ "tbl_store_indent_products.raised_qty,tbl_store_indent_products.issued_qty,tbl_store_indent_products.remaining_qty,tbl_store_indent_products.isissued, "
//					+ "tbl_products.product_name,tbl_products.product_name||'-'||tbl_store_indent_products.product_id as product_description,"
//					+ "tbl_store_indent_products.created_on as raised_date,tbl_warehouse1.wareouse_name as warehouse_name "
//					+ "from tbl_store_indent_products "
//					+ "inner join tbl_products on tbl_products.product_id=tbl_store_indent_products.product_id "
//					+ "inner join tbl_warehouse1 on tbl_store_indent_products.warehouse_id=tbl_warehouse1.warehouse_id "
//					+ "where tbl_store_indent_products.indent_id='"+indent_id+"'";
			
		String Query="select tbl_store_indent_products.product_id,tbl_store_indent_products.indent_id,"
				+ "tbl_store_indent_products.product_id,tbl_store_indent_products.status,tbl_store_indent_products.store_id, "
				+ "tbl_store_indent_products.raised_qty,tbl_store_indent_products.issued_qty,tbl_store_indent_products.warehouse_id,"
				+ "tbl_store_indent_products.remaining_qty,tbl_store_indent_products.isissued,"
				+ "tbl_store_indent_products.created_on as raised_date,tbl_store.store_address1 as shipping_address, "
				+ "tbl_warehouse1.wareouse_name as warehouse_name,tbl_store.store_name,COALESCE(warehouse_stockdetails.total_stock,0) as remaining_stock, "
				+ "tbl_products.product_name,tbl_products.product_name||'-'||product_unit.product_unit||'-'||"
				+ "tbl_store_indent_products.product_id as product_description "
				+ "from tbl_store_indent_products "
				+ "inner join tbl_products on tbl_products.product_id=tbl_store_indent_products.product_id "
				+ "inner join tbl_warehouse1 on tbl_store_indent_products.warehouse_id=tbl_warehouse1.warehouse_id "
				+ "inner join tbl_store on tbl_store_indent_products.store_id=tbl_store.store_id "
				+ "inner join product_unit on tbl_products.product_unit=product_unit.id "
				//+ "inner join warehouse_stockdetails on warehouse_stockdetails.warehouse_id=tbl_store_indent_products.warehouse_id "
				+ "inner join warehouse_stockdetails on warehouse_stockdetails.product_id=tbl_store_indent_products.product_id "
				+ "where tbl_store_indent_products.indent_id='"+indent_id+"' and warehouse_stockdetails.warehouse_id='"+warehouse_id+"'";	
			
			System.out.println(Query);
			ResultSet res_exeQuery=connection.createStatement().executeQuery(Query);
			while(res_exeQuery.next()){
				Ingrediants iDetails=new Ingrediants();
				//iDetails.setIngrediant_id((int)res_exeQuery.getInt("ingrediant_id"));
				iDetails.setIndentId((String)res_exeQuery.getString("indent_id"));
				iDetails.setStatus((String)res_exeQuery.getString("status"));
				iDetails.setProduct_description((String)res_exeQuery.getString("product_description"));
				iDetails.setProductId((String)res_exeQuery.getString("product_id"));
				iDetails.setProduct_name((String)res_exeQuery.getString("product_name"));
				iDetails.setIs_issued((int)res_exeQuery.getInt("isissued"));
				iDetails.setQuantity((String)res_exeQuery.getString("raised_qty"));
				iDetails.setStore_name((String)res_exeQuery.getString("store_name"));
				iDetails.setWarehouse_name((String)res_exeQuery.getString("warehouse_name"));
				iDetails.setRaised_date((Date)res_exeQuery.getDate("raised_date"));
				iDetails.setShipping_address((String)res_exeQuery.getString("shipping_address"));
				iDetails.setRemaining_stock((int)res_exeQuery.getInt("remaining_stock"));
				iDetails.setWarehouse_id((String)res_exeQuery.getString("warehouse_id"));
				iDetails.setStore_id((String)res_exeQuery.getString("store_id"));

				listOfData.add(iDetails);
			}if(listOfData==null||listOfData.isEmpty()){
				response.setMessage("List is empty");
				response.setStatus("SUCCESS");
				return Response.status(Status.OK).entity(response).build();
			}else{
				response.setListOfData(listOfData);
				response.setMessage("");
				response.setStatus("SUCCESS");
				return Response.status(Status.OK).entity(response).build();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			response.setMessage("Please try again");
			response.setStatus("ERROR");
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}finally {
			try {
				
				DBConnection.closeConnection(connection);
			} catch (SQLException e) {
			}
		}
	}
	
//	@POST
//	@Path("/viewIndentsForIssued")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response viewIndentsForIssue(@FormParam("id") String id,@FormParam("department_id") int department_id) {
//		APIResponse response=new APIResponse();
//		List<Object> listOfData=new ArrayList<Object>();
//		String message="";
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			message=(String) jsObject.get("status");
//			response.setMessage(message);
//			response.setStatus("Failed");
//			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			String dep_id=(String) jsObject.get("auth_id");
//			String dept_name=(String) jsObject.get("departmentname");
////			System.out.println(departmsent_id);
//			
//			
//		}Connection connection=null;
//		try{
//			connection=DBConnection.SqlConnection();
//			String Query="select '' as project_id, tbl_department_indent.indent_id,tbl_department_indent.department_id::varchar,tbl_department_indent.status,tbl_department_indent.isissued,"
//					+ " tbl_department_indent.created_on::date,tbl_department_indent.created_by as indent_raised_by,mstdepartment.department_name from tbl_department_indent"
//					+ " inner join mstdepartment on mstdepartment.departmentid=tbl_department_indent.department_id where tbl_department_indent.status!='completed' "
//					+ " union select tbl_indent.project_id,tbl_indent.indent_id,tbl_indent.project_id,tbl_indent.status,tbl_indent.isissued,tbl_indent.created_on::date,tbl_indent.created_by,tbl_project.department_type from tbl_indent "
//					+ " inner join tbl_project on tbl_project.project_id=tbl_indent.project_id where lower(tbl_project.department_type)!='production' and tbl_indent.status!='completed'";
////			System.out.println(Query);
//			if(department_id>0){
//				Query="select '' as project_id,tbl_department_indent.indent_id,tbl_department_indent.department_id::varchar,tbl_department_indent.status,tbl_department_indent.isissued,"
//						+ " tbl_department_indent.created_on::date,tbl_department_indent.created_by as indent_raised_by,mstdepartment.department_name from tbl_department_indent"
//						+ " inner join mstdepartment on mstdepartment.departmentid=tbl_department_indent.department_id where tbl_department_indent.status!='completed' and tbl_department_indent.department_id='"+department_id+"'"
//						+ " union select tbl_indent.project_id, tbl_indent.indent_id,tbl_indent.project_id,tbl_indent.status,tbl_indent.isissued,tbl_indent.created_on::date,tbl_indent.created_by,tbl_project.department_type from tbl_indent "
//						+ " inner join tbl_project on tbl_project.project_id=tbl_indent.project_id where lower(tbl_project.department_type)!='production' and tbl_indent.status!='completed' and tbl_indent.department_id='"+department_id+"'";
//			}
//			
//			System.out.println("viewIndentsForIssued : "+Query);
//			ResultSet res_exeQuery=connection.createStatement().executeQuery(Query);
//			while(res_exeQuery.next()){
//				IndentDetails iDetails=new IndentDetails();
////				iDetails.setIndent_id((int)res_exeQuery.getInt("id"));
//				iDetails.setProjectId((String)res_exeQuery.getString("project_id"));
//				iDetails.setIndentId((String)res_exeQuery.getString("indent_id"));
//				iDetails.setDeptName((String)res_exeQuery.getString("department_name"));
//				iDetails.setCreatedBy((String)res_exeQuery.getString("indent_raised_by"));
//				iDetails.setStatus((String)res_exeQuery.getString("status"));
//				iDetails.setIs_issued((int)res_exeQuery.getInt("isissued"));
//				iDetails.setCreated_on((String)res_exeQuery.getString("created_on"));
//				listOfData.add(iDetails);
//			}if(listOfData==null||listOfData.isEmpty()){
//				response.setMessage("List is empty");
//				response.setStatus("SUCCESS");
//				return Response.status(Status.OK).entity(response).build();
//			}else{
//				response.setListOfData(listOfData);
//				response.setMessage("");
//				response.setStatus("SUCCESS");
//				return Response.status(Status.OK).entity(response).build();
//			}
//			
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			response.setMessage("Please try again");
//			response.setStatus("ERROR");
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
//		}finally {
//			try {
//				
//				DBConnection.closeConnection(connection);
//			} catch (SQLException e) {
//			}
//		}
//	}
//	
//	@POST
//	@Path("/viewIndentsForIssuedByIndentId")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response viewIndentsForIssuedByIndentId(@FormParam("id") String id,@FormParam("indent_id") String indent_id) {
//		APIResponse response=new APIResponse();
//		List<Object> listOfData=new ArrayList<Object>();
//		String message="";
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus_V1(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			message=(String) jsObject.get("status");
//			response.setMessage(message);
//			response.setStatus("Failed");
//			return Response.ok().status(Status.UNAUTHORIZED).entity(response).build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			
//			String dept_name=(String) jsObject.get("departmentname");
//			
//			
//		}Connection connection=null;
//		try{
//			connection=DBConnection.SqlConnection();
//			String dep_id=(String) jsObject.get("auth_id");
//			String Query="";
//			if(indent_id.contains("EIND")){
//				Query="select '' as project_id,tdii.ingrediant_id as ingrediant_id,tdii.indent_id,'' as project_code,tdii.product_id,tdii.remaining_qty as remaining_quantity,tdii.raised_qty as total_quantity,(select coalesce((select sum(total_stock) from tbl_current_stock where product_id=tdii.product_id and dep_id='"+dep_id+"'),0 ))stock_availability, tp.product_code as product_description,tp.product_name,tdii.remaining_qty as issued_quantity from tbl_department_indent_ingrediants as tdii inner join tbl_department_indent as tdi  on tdi.indent_id=tdii.indent_id "
//					 +" inner join tbl_products as tp on tp.product_id=tdii.product_id"
//					+ " where tdi.indent_id='"+indent_id+"' ";
//			}else{
//				Query="select tii.id as ingrediant_id,(select project_id from tbl_indent where indent_id='"+indent_id+"')as project_id,tii.indent_id, tii.product_id,tp.product_code as product_description,tp.product_name,  " + 
//						"    tii.raised_quantity as total_quantity ,tii.remaining_quantity,tii.issued_quantity,(select coalesce((select sum(total_stock) from tbl_current_stock where product_id=tii.product_id and dep_id='"+dep_id+"'),0 ))stock_availability,remaining_quantity as issued_quantity " + 
//						"    from tbl_indent_ingrediants  as tii " + 
//						"    inner join tbl_products  as tp on tp.product_id= tii.product_id " + 
//						"    where indent_id='"+indent_id+"' ";
////				ch_id='PB0001' and tbl_project_batch.stage_name='A' and tbl_project_batch_item.project_id='PRJ0000001'";
//			}	System.out.println("viewIndentsForIssuedByIndentId: "+Query);
//			ResultSet res_exeQuery=connection.createStatement().executeQuery(Query);
//			while(res_exeQuery.next()){
//				Ingrediants iDetails=new Ingrediants();
//				iDetails.setProjectId((String)res_exeQuery.getString("project_id"));
//				iDetails.setIngrediant_id((int)res_exeQuery.getInt("ingrediant_id"));
//				iDetails.setIndentId((String)res_exeQuery.getString("indent_id"));
////				iDetails.setStatus((String)res_exeQuery.getString("status"));
//				iDetails.setProduct_description((String)res_exeQuery.getString("product_description"));
//				iDetails.setProductId((String)res_exeQuery.getString("product_id"));
//				iDetails.setProductName((String)res_exeQuery.getString("product_name"));
////				iDetails.setIs_issued((int)res_exeQuery.getInt("isissued"));
//				iDetails.setStock_availability((String)res_exeQuery.getString("stock_availability"));
//				iDetails.setRemaining_quantity((String)res_exeQuery.getString("remaining_quantity"));
//				iDetails.setTotal_quantity((String)res_exeQuery.getString("total_quantity"));
//				iDetails.setIssued_quantity((String)res_exeQuery.getString("issued_quantity"));
//				listOfData.add(iDetails);
//			}if(listOfData==null||listOfData.isEmpty()){
//				response.setMessage("List is empty");
//				response.setStatus("SUCCESS");
//				return Response.status(Status.OK).entity(response).build();
//			}else{
//				response.setListOfData(listOfData);
//				response.setMessage("");
//				response.setStatus("SUCCESS");
//				return Response.status(Status.OK).entity(response).build();
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			response.setMessage("Please try again");
//			response.setStatus("ERROR");
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
//		}finally {
//			try {
//				
//				DBConnection.closeConnection(connection);
//			} catch (SQLException e) {
//			}
//		}
//	}
	
}

