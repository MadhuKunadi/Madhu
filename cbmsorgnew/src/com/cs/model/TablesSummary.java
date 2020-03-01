/**
 * 
 */
package com.cs.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
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

import com.cs.TokenCheck;
import com.hr.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.utils.CreateExcelFile;
import com.hr.utils.MessageConfig;

/**
 * @author Admin
 *
 */
@Path("TablesSummary")
@SuppressWarnings({"unused","rawtypes","unchecked","resource"})
public class TablesSummary {
	private static Log log=LogFactory.getLog(TablesSummary.class);
	private static String projectsSummaryToAdmin="select  (select count(*) from tbl_project)total_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='new')new_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='inqueue')inqueue_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='inprocess')inprocess_projects";
	private static String projectsSummaryToOthers="select  (select count(*) from tbl_project)total_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='new' and  ingrediant_count>0  and lower(department_type)=? )new_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='inqueue' and  ingrediant_count>0  and lower(department_type)=? )inqueue_projects, " + 
			"    (select count(*) from tbl_project where lower(project_status)='inprocess' and  ingrediant_count>0  and lower(department_type)=? )inprocess_projects";
	private static String purchaseRquisitionSummaryToAdmin="select (select count(*) from tbl_purchase_requisition)total_requisitions, " + 
			"    (select count(*) from tbl_purchase_requisition where lower(status)='pending' )pending_requisitions, " + 
			"    (select count(*) from tbl_purchase_requisition where lower(status)='approved' )approved_requisitions, " + 
			"    (select count(*) from tbl_purchase_requisition where lower(status)='rejected' )rejected_requisitions";
	private static String extenalIndentsSummaryToAdmin="select (select count(*) from tbl_department_indent)total_external_indents, (select count(*) from tbl_department_indent where lower(status)='pending')pending_external_indents ";
	private static String extenalIndentsSummaryToOthers="select (select count(*) from tbl_department_indent)total_external_indents, (select count(*) from tbl_department_indent where lower(status)='pending' and department_id=? )pending_external_indents ";

	
	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 12/15/2018
	 * @version v1.0.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
	@POST
    @Path("/projectsSummary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response projectsSummary(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			connection=DBConnection.sqlConnection();
			int role_id=(int) jsObject.get("role_id");
			String category=(String) jsObject.get("departmentname");
			category=category.toLowerCase();
			if(role_id==1){
			preparedStmt=connection.prepareStatement(projectsSummaryToAdmin);
			}else {
			preparedStmt=connection.prepareStatement(projectsSummaryToOthers);
			preparedStmt.setString(1, category);
			preparedStmt.setString(2, category);
			preparedStmt.setString(3, category);
			}
			System.out.println("projectsSummaryToAdmin: "+preparedStmt);
			 resultSet = preparedStmt.executeQuery();
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
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 12/15/2018
	 * @version v1.0.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
	@POST
    @Path("/purchaseRquisitionSummary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response purchaseRquisitionSummary(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
		
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			connection=DBConnection.sqlConnection();
			
			preparedStmt=connection.prepareStatement(purchaseRquisitionSummaryToAdmin);
			
			
			System.out.println("purchaseRquisitionSummaryToAdmin: "+preparedStmt);
			 resultSet = preparedStmt.executeQuery();
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
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
	
	
	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 12/15/2018
	 * @version v1.0.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
	@POST
    @Path("/extenalIndentsSummary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response extenalIndentsSummary(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			connection=DBConnection.sqlConnection();
			int role_id=(int) jsObject.get("role_id");
			String category=(String) jsObject.get("departmentname");
			int depid=(int)jsObject.get("auth_id");
			category=category.toLowerCase();
			if(role_id==1){
			preparedStmt=connection.prepareStatement(extenalIndentsSummaryToAdmin);
			}else {
			preparedStmt=connection.prepareStatement(extenalIndentsSummaryToOthers);
			preparedStmt.setInt(1, depid);
			
			}
			System.out.println("projectsSummaryToAdmin: "+preparedStmt);
			 resultSet = preparedStmt.executeQuery();
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
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
	
	
	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 12/25/2018
	 * @version v1.0.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
	@POST
    @Path("/productionProjectViewToAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response productionProjectViewToAdmin(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
		}
		String project_id=param.getProject_id();
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		ArrayList array=new ArrayList();
		try {	
			
			connection=DBConnection.sqlConnection();
//			int role_id=(int) jsObject.get("role_id");
//			String category=(String) jsObject.get("departmentname");
//			int depid=(int)jsObject.get("auth_id");
//			category=category.toLowerCase();
			String getDatesQuery="SELECT day::date FROM   generate_series((select start_date from tbl_project where project_id='"+project_id+"')::date, current_date::date, '1 day') day ";
			preparedStmt=connection.prepareStatement(getDatesQuery);
			
			System.out.println("getDatesQuery: "+preparedStmt);
			 resultSet = preparedStmt.executeQuery();
			Object object=HelperClass.convertToJSON(resultSet);
			String getStagesQuery="select stage_name,'Stage-'||stage_name as stage,case when (select stage_name from tbl_project_stage where project_id='"+project_id+"' order by stage_name desc limit 1)=stage_name then  " + 
					"   1 else 0 end as is_laststage from tbl_project_stage where project_id='"+project_id+"' ";
			preparedStmt=connection.prepareStatement(getStagesQuery);
			System.out.println("getStagesQuery: "+getStagesQuery);
			 resultSet = preparedStmt.executeQuery();
			Object stages_object=HelperClass.convertToJSON(resultSet);
			org.json.JSONArray datesArray= new JSONArray(object.toString());
			String date=null;
			for(int i=0;i<datesArray.length();i++) {
				Map map=new LinkedHashMap();
				
				org.json.JSONObject dateObject=datesArray.getJSONObject(i);
				date=dateObject.getString("day");
				map.put("Date", date);
				
				
				org.json.JSONArray stagesArray= new JSONArray(stages_object.toString());
				String stagename=null,stage=null;
				int is_lastStage=0;
				for(int j=0;j<stagesArray.length();j++) {
					org.json.JSONObject stageObject=stagesArray.getJSONObject(j);
					System.out.println(stageObject);
					stage=stageObject.getString("stage");
					stagename=stageObject.getString("stage_name");
					is_lastStage=stageObject.getInt("is_laststage");	
					String batchInfoQuery="select start_date, tbl_project_batch.output_yield,tbl_project_batch.batch_id,tbl_project_batch.batch_quantity, tbl_project_batch.output_hold_inkgs,case  when ('"+date+"'>=start_date and  end_date is null ) then batch_status else batch_status end as batch_status  from tbl_project_batch where tbl_project_batch.stage_name='"+stagename+"' and tbl_project_batch.project_id='"+project_id+"' and  batch_status in ('Completed','Cancelled')  and end_date<='"+date+"' order by batch_id desc limit 1";
					preparedStmt=connection.prepareStatement(batchInfoQuery);
					System.out.println("batchInfoQuery: "+batchInfoQuery);
					resultSet = preparedStmt.executeQuery();
					HashMap BatchObject= (HashMap) HelperClass.convertToJSONObject(resultSet);
					String batchstatus=null,batchid=null;
					Object output_yeld=0;Object batch_size=0; Object hold_stock=0;
					if(BatchObject.isEmpty()||BatchObject.size()==0) {
						map.put(stage, "");
						if(is_lastStage==1) {
							map.put("Output", "");	
						}else{
							
						}
					}else {
						batchid=(String) BatchObject.get("batch_id");
						batchstatus=(String)BatchObject.get("batch_status");
						output_yeld= BatchObject.get("output_yield");
						batch_size= BatchObject.get("batch_quantity");
						hold_stock=BatchObject.get("output_hold_inkgs");
						map.put(stage, batchid+" :(batch size:"+batch_size+"; batch output:"+output_yeld+")");
						if(is_lastStage==1) {
							map.put("Output", output_yeld);	
						}
						
					}
					
					
				}
				/*
				TreeMap<String, Integer> sorted = new TreeMap<>();   
		        // Copy all data from hashMap into TreeMap 
		        sorted.putAll(map); 
		  
		        // Display the TreeMap which is naturally sorted 
		        for (Map.Entry<String, Integer> entry : sorted.entrySet())  
		  */                 
		    array.add(map);
			}
			String server_path=System.getProperty("catalina.base");
			System.out.println("catalina.base: "+server_path);
			 server_path=System.getProperty("catalina.home");
			System.out.println("catalina.home: "+server_path);
		responseObject=HelperClass.generateResponce(200, array,null);	
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
			
			e.printStackTrace();
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
	
	

	/**
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 01/24/2019 11:03AM
	 * @version v1.0.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	
	@POST
    @Path("/stagesListByProject")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response stagesListByProject(Model param) {	
		JSONObject responseObject=new JSONObject();
		Response response=null;
		JSONObject jsObject=TokenCheck.checkTokenStatus(param.getId());
		if(jsObject.containsKey("status")){
			responseObject=HelperClass.generateResponce(205,MessageConfig.API_205,""+jsObject.get("status"));
			 response=HelperClass.convertObjectToResponce(responseObject, 200);			
			return response;
		}else {
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {	
			connection=DBConnection.sqlConnection();
			int role_id=(int) jsObject.get("role_id");
			String category=(String) jsObject.get("departmentname");
			
			String project_query="SELECT project_id, project_name, project_code,  project_type, start_date,priority,(select coalesce((select json_agg(json_build_object('product_id',ti.product_id,'product_code',tp.product_code,'product_name',tp.product_name,'product_unit',tp.product_unit,'cas_number',tp.cas_number,'quantity', ti.quantity,'used_qty',"
					+ "    (select sum(item_qty_used) from tbl_project_batch_item where project_id=? and product_id=ti.product_id)))  from tbl_ingrediants as ti  inner join tbl_products as tp on tp.product_id=ti.product_id where  project_id=?),'[]'::json) )materials 	FROM public.tbl_project where  project_id=?";
			preparedStmt=connection.prepareStatement(project_query);
			preparedStmt.setString(1, param.getProject_id());
			preparedStmt.setString(2, param.getProject_id());
			preparedStmt.setString(3, param.getProject_id());
			resultSet = preparedStmt.executeQuery();
			Object project_object=HelperClass.convertToJSONObject(resultSet);
			category=category.toLowerCase();
			String getstagesQuery="select stage_name, 'Stage-'||stage_name as stage from tbl_project_stage where project_id=? order by stage_name ";
			preparedStmt=connection.prepareStatement(getstagesQuery);
			preparedStmt.setString(1, param.getProject_id());
			
			System.out.println("stagesListByProject: "+preparedStmt);
			ArrayList array=new ArrayList();
			 resultSet = preparedStmt.executeQuery();
			Object object=HelperClass.convertToJSON(resultSet);
			
			org.json.JSONArray stages_array=new JSONArray(object.toString());
			for(int i=0;i<stages_array.length();i++) {
				Map map=new HashMap();
				org.json.JSONObject stage_object=stages_array.getJSONObject(i);
				String stage=stage_object.getString("stage");
				String stage_name=stage_object.getString("stage_name");
				
				String getbatchsQuery="SELECT  batch_id, to_char(batch_date,'DD/MM/YYYY')::varchar as batch_date, batch_quantity,  created_by,   batch_status, output_yield,  coalesce(to_char(start_date,'DD/MM/YYYY')||' '||to_char(start_time,'hh24:mm'),' ')::varchar as started,coalesce(to_char(end_date,'DD/MM/YYYY')||' '||to_char(end_time,'hh24:mm'),' ')::varchar as ended,  output_qty_used " + 
						"	FROM public.tbl_project_batch where project_id=? and stage_name=? order by batch_id asc";
				preparedStmt=connection.prepareStatement(getbatchsQuery);
				preparedStmt.setString(1, param.getProject_id());
				preparedStmt.setString(2, stage_name);
				resultSet = preparedStmt.executeQuery();
				Object batches=HelperClass.convertToJSON(resultSet);
				map.put("stage", stage);
				map.put("stage_name", stage_name);
				map.put("batches", batches);
				array.add(map);	
			}
			try {
				
				CreateExcelFile.storeExcel(array.toString(),project_object.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Excel File Write Exception: "+e.getMessage());
			}	
		responseObject=HelperClass.generateResponce(200, array,null);	
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
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
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
	
}
