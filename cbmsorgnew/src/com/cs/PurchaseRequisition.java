package com.cs;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
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

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PGobject;

import com.helper.DBConnection;
import com.hr.main.GenDocument;
import com.hr.main.MyObject;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Path("purchaseRequest")
	public class PurchaseRequisition {
		
		@POST
		@Path("/generatePurcaseRequisitionId")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response generatePurcaseOrderId(@FormParam("id") String id){
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
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
//				if(role_id==4){
//					
//				}
				System.out.println(role_id);	
			}	Connection connection=null;
			try {
				connection=DBConnection.SqlConnection();
				try {
					String purchaseorderid= "";
					String Query="select * from fn_generate_id('PRE',7)";
					System.out.println(Query);
					CallableStatement csStatement=connection.prepareCall(Query);
					csStatement.execute();
					ResultSet result=csStatement.getResultSet();
					while(result.next()){
						purchaseorderid=result.getString(1);
					}
					if(purchaseorderid!=null&&!purchaseorderid.isEmpty()&&purchaseorderid.contains("PRE")){
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
						.header("Access-Control-Allow-Methods", "POST").build();
		}
	
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/raiseRequisition")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response raiseRequisition(@FormParam("id") String id,@FormParam("purchase_requisition_id") String purchase_requisition_id,@FormParam("address") String address,@FormParam("delivery_date") String delievery_date,@FormParam("data") String data,@FormParam("indentno") String indent_id){
			JSONObject jsonObject=new JSONObject();
			System.out.println("First"+id+" data"+data+" prid"+purchase_requisition_id);
			if(id==null){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Fields are empty");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}if(purchase_requisition_id==null||purchase_requisition_id.isEmpty()){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Fields are empty");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			if(data==null||data.isEmpty()){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Please choose any one");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			String auth_id = "";
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				auth_id=(String) jsObject.get("auth_id");
				System.out.println(role_id);
			}
			Connection ConnectionWar=null;
			try {	String result_insertion_data="";
				ConnectionWar=DBConnection.SqlConnection();
				ConnectionWar.setAutoCommit(false);
				try {
					String Query_insert="insert into tbl_purchase_requisition (purchase_requisition_id,warehouse_id,shipping_address,delievery_date,status) values ('"+purchase_requisition_id+"','"+auth_id+"','"+address+"','"+delievery_date+"','pending');";
					int status=ConnectionWar.createStatement().executeUpdate(Query_insert);
					System.out.println("fn Query"+Query_insert);
					org.json.JSONArray jsArray=new org.json.JSONArray(data);
					for(int i=0;i<jsArray.length();i++){
						org.json.JSONObject jObject=jsArray.getJSONObject(i);
						String product_desc=jObject.getString("productname");
						String product_id=product_desc.substring(product_desc.lastIndexOf("-")+1,product_desc.length());
						String issued_stock=jObject.getString("required_stock");
						String Query="select * from fn_stage_requisition_byProduct('"+auth_id+"','"+purchase_requisition_id+"','"+product_id+"','"+issued_stock+"')";
						System.out.println("fn Query"+Query);
						CallableStatement CStatement=ConnectionWar.prepareCall(Query);
						CStatement.execute();
						ResultSet result_insertion=CStatement.getResultSet();
						while(result_insertion.next()){
							result_insertion_data=result_insertion.getString(1);
						}
						System.out.println("result_insertion_data :   "+result_insertion_data);
						if(result_insertion_data.contains("Success")||result_insertion_data.contains("Already Exists")){
							
						}else{
							break;
						}
					}
					if(result_insertion_data.contains("Success")||result_insertion_data.contains("Already Exists")){
						ConnectionWar.setAutoCommit(true);
						String Query_Stage="update tbl_purchase_requisition set is_stage=1 where purchase_requisition_id='"+purchase_requisition_id+"'	";
						int i=ConnectionWar.createStatement().executeUpdate(Query_Stage);
						System.out.println("Query_Stage :   "+Query_Stage);
						//String updateindentQuery="update tbl_indent set purchase_requisition_id='"+purchase_requisition_id+"' where indent_id='"+indentno+"';update tbl_department_indent set purchase_requisition_id='"+purchase_requisition_id+"' where indent_id='"+indentno+"'";
						//ConnectionWar.createStatement().executeUpdate(updateindentQuery);
						
						/*author=siba sankar
						 *date=23.01.2020
						 * I added this insert purchase_requisition_id operation */
						String insert_PRID="update  tbl_store_indent set purchase_requisition_id='"+purchase_requisition_id+"'where indent_id='"+indent_id+"'";
						int j=ConnectionWar.createStatement().executeUpdate(insert_PRID);
						System.out.println("insert_PRID and indent_id:   "+insert_PRID+indent_id);
						

						String getprdetails="select tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.created_date,tbl_purchase_requisition.delievery_date, "
								+ " tbl_purchase_requisition.shipping_address,tbl_purchase_requisition.status,"
								+ " (select coalesce(json_agg(json_build_object('product_id',tbl_purchase_requisition_products.product_id,"
								+ " 'quantity',tbl_purchase_requisition_products.issued_quantity,'product_name',tbl_products.product_name)),'[]'::json) from tbl_purchase_requisition_products "
								+ " inner join tbl_products on tbl_products.product_id=tbl_purchase_requisition_products.product_id "
								+ " where tbl_purchase_requisition_products.purchase_requisition_id=tbl_purchase_requisition.purchase_requisition_id ) as ingrediants"
								+ " from tbl_purchase_requisition where tbl_purchase_requisition.mark_for_deletion=0 and tbl_purchase_requisition.purchase_requisition_id='"+purchase_requisition_id+"' ";
						
						System.out.println(getprdetails);
						ResultSet rset=ConnectionWar.createStatement().executeQuery(getprdetails);
						Object object=convertToJSONObject(rset);
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "PR forwarded to admin for approval");
						jsonObject.put("Data", object);
//						jsonObject.clear();
//						jsonObject.put("Status", "Success");
//						jsonObject.put("Message", "PR created successfully and forwarded to Purchase Department");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to update");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Something went wrong");
					jsonObject.put("error", e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		public static Object convertToJSONObject(ResultSet resultSet) throws SQLException{
			HashMap<String, Object> obj = new HashMap<String, Object>();
			 while (resultSet.next()) {
		            int total_rows = resultSet.getMetaData().getColumnCount();
		            for (int i = 0; i < total_rows; i++) {
		            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
		            	Object columnvalue=null;
		            	 int type = resultSet.getMetaData().getColumnType(i+1);
		            	 if(type==1111) {
		            		 PGobject pgobject= new PGobject();
		            		 pgobject=(PGobject) resultSet.getObject(i + 1);
		            		 String value=pgobject.getValue();
		            		 JSONParser parser= new JSONParser();
		            		 try {
								columnvalue=parser.parse(value);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		 
		            	 }else {
		            		 columnvalue=resultSet.getObject(i + 1);
		            		 
		            	 }
		            	 obj.put(columnname, columnvalue);
		            }
		        }
			return obj;
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/savePRDocument")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response savePRDocument(@FormParam("id") String id,
				@FormParam("purchase_requisition_id") String purchase_requisition_id,
				@FormParam("type") String type,
				@FormParam("doc_path") String doc_path){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			int userid=0;
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				String role_id=(String) jsObject.get("auth_id");
				System.out.println(role_id);
				//userid=(int) jsObject.get("userid");
				/*if(role_id!=1){
					warehouse_id=(String) jsObject.get("auth_id");
				}*/
			}
			Response response=null;
			Connection ConnectionWar=null;
			try {	

			
			MyObject requestobj=new MyObject();
			requestobj.setComments(" new purchase requisition  document of "+purchase_requisition_id+" has generated");
			requestobj.setProcess_id(type);
			requestobj.setDocument(doc_path);
			requestobj.setCreatedby(1);

				JSONObject docobject=GenDocument.initiateWorkflowDocURL(requestobj);
						org.json.JSONObject object= new org.json.JSONObject(docobject.toString());
						int status=object.getInt("Status");
						String documentid=object.getString("Data");
						System.out.println(documentid);
						if(status==200) {
						
							if(!documentid.contains("Failed")) {
							String Query="update tbl_purchase_requisition set pr_docid=? where purchase_requisition_id=? ";
								ConnectionWar=DBConnection.SqlConnection();
								PreparedStatement stmnt=ConnectionWar.prepareStatement(Query);
								stmnt.setString(1, documentid);
								stmnt.setString(2, purchase_requisition_id);
								System.out.println(stmnt);
								int state=stmnt.executeUpdate();
								
									if(state>0){
										jsonObject.clear();
										jsonObject.put("Status", "Success");
										jsonObject.put("Message", "PR forwarded to admin for approval");
									}else{
										jsonObject.clear();
										jsonObject.put("Status", "Failed");
										jsonObject.put("Message", "Failed to save documentid");
									}}
								else {
								jsonObject.clear();
								jsonObject.put("Status", "Failed");
								jsonObject.put("Message", "Failed to save document");
							}
						}else {
							jsonObject.clear();
							jsonObject.put("Status", "Success");
							jsonObject.put("Message", documentid);
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
			} catch (JSONException e) {
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Something went wrong");
				jsonObject.put("error", e.getMessage());
			}finally {
				try {
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@POST
		@Path("/getPurchaseRequisitionListinStage")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getPurchaseRequisitionListinStage(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("type") String type){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			int role_id=0;
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				 role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				if(role_id!=1){
//					warehouse_id=(String) jsObject.get("auth_id");
//					warehouse_id="tbl_purchase_requisition.warehouse_id='"+warehouse_id+"' ";
				}else {
//					warehouse_id="tbl_purchase_requisition.warehouse_id='"+warehouse_id+"' and status='pending' ";
				}
			}
			warehouse_id="";
			String auth_id=(String) jsObject.get("auth_id");
			/*String Query="select tbl_purchase_requisition.id,tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
					+ " tbl_purchase_requisition.delievery_date,tbl_purchase_requisition.status,tbl_purchase_requisition.created_date::date, "
					+ " tbl_warehouse.warehouse_name,tbl_warehouse.warehouse_address from tbl_purchase_requisition	inner join tbl_warehouse on tbl_warehouse.warehouse_id=tbl_purchase_requisition.warehouse_id"
					+ " where delievery_date is not null "+warehouse_id+" order by tbl_purchase_requisition.created_date desc";*/
			String Query=null;
			if(type==null||type.isEmpty()){
				Query="select  tbl_purchase_requisition.id,tbl_purchase_requisition.is_raised,"
					+ " tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
					+ "	tbl_purchase_requisition.delievery_date,case when tbl_purchase_requisition.status='pending' "
					+ " then case when is_stage=1 then 'Pending at Purchase dept' "
					+ " else 'Approval pending' end when tbl_purchase_requisition.status='approved' "
					+ " then case when is_raised=0 then 'Admin approved/PO pending' end else 'PO raised'  "
					+ " end as status ,tbl_purchase_requisition.created_date::date,tbl_warehouse1.wareouse_name as warehouse_name "
					+ "	from tbl_purchase_requisition"
					+ " inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id where tbl_purchase_requisition.warehouse_id='"+auth_id+"' "
					+ "	order by tbl_purchase_requisition.created_date desc";
			}else {
				type=type.toLowerCase();
				if(role_id!=1){
				Query="select  tbl_purchase_requisition.id,tbl_purchase_requisition.is_raised,"
						+ " tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id, "
						+ " tbl_purchase_requisition.delievery_date,case when tbl_purchase_requisition.status='pending' "
						+ " then case when is_stage=1 then 'Pending at Purchase dept' else 'Approval pending' "
						+ " end when tbl_purchase_requisition.status='approved' then case when is_raised=0 "
						+ " then 'Admin approved/PO pending' end else 'PO raised'  end as status ,"
						+ "tbl_purchase_requisition.created_date::date,tbl_warehouse1.wareouse_name as warehouse_name "
						+ " from tbl_purchase_requisition "
						+ "	inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id "
						+ " where  lower(tbl_purchase_requisition.status)='approved'  "
						+ " order by tbl_purchase_requisition.created_date desc ;";
				}else{
					Query="select  tbl_purchase_requisition.id,tbl_purchase_requisition.is_raised,"
							+ "tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
							+ "	tbl_purchase_requisition.delievery_date,case when tbl_purchase_requisition.status='pending' "
							+ " then case when is_stage=1 then 'Pending at Purchase dept' else 'Approval pending' "
							+ " end when tbl_purchase_requisition.status='approved' then case when is_raised=0 "
							+ " then 'Admin approved/PO pending' end else 'PO raised'  end as status ,"
							+ " tbl_purchase_requisition.created_date::date,tbl_warehouse1.wareouse_name as warehouse_name "
							+ "	from tbl_purchase_requisition "
							+ "	inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id "
							+ " where  lower(tbl_purchase_requisition.status)='"+type+"' "
							+ "order by tbl_purchase_requisition.created_date desc";

				}
				}
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					ResultSet result=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(result);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/getReqListbyId")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getReqListbyId(@FormParam("id") String id,@FormParam("purchase_requisition_id") String purchase_requisition_id){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				/*if(role_id!=1){
					warehouse_id=(String) jsObject.get("auth_id");
				}*/
			}String Query="select tbl_purchase_requisition_products.id,tbl_purchase_requisition.status,tbl_purchase_requisition_products.purchase_requisition_id,tbl_purchase_requisition_products.product_id,"
						+ " tbl_purchase_requisition.is_raised,tbl_purchase_requisition.created_date::date as cdate,tbl_purchase_requisition.warehouse_id,tbl_purchase_requisition.shipping_address,"
						+ " case when tbl_purchase_requisition.is_stage=1 then 'Still Inqueue' else case when tbl_purchase_requisition.status='pending' then 'Pending at Purchase dept' when "
						+ " tbl_purchase_requisition.status='approved' then tbl_purchase_requisition.status else 'PO Raised' end end as status,tbl_purchase_requisition_products.issued_quantity as quantity,"
						+ " tbl_products.product_name||'-'||tbl_products.product_id as product_description,product_unit.product_unit "
						+ " from tbl_purchase_requisition_products inner join tbl_products on tbl_products.product_id=tbl_purchase_requisition_products.product_id "
						+ " inner join tbl_purchase_requisition on tbl_purchase_requisition.purchase_requisition_id=tbl_purchase_requisition_products.purchase_requisition_id  "
						+ " inner join product_unit on product_unit.id = tbl_products.product_unit "
						+ " where tbl_purchase_requisition_products.mark_for_deletion=0 and tbl_purchase_requisition_products.purchase_requisition_id='"+purchase_requisition_id+"' order by tbl_purchase_requisition_products.product_id asc";
			
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					ResultSet result=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(result);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/getPurchaseRequisitionList")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getPurchaseRequisitionList(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("type") String type){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				if(role_id!=1){
//					warehouse_id=(String) jsObject.get("auth_id");
//					warehouse_id="tbl_purchase_requisition.warehouse_id='"+warehouse_id+"' ";
				}else {
//					warehouse_id="tbl_purchase_requisition.warehouse_id='"+warehouse_id+"' and status='pending' ";
				}
			}
			warehouse_id="";
			/*String Query="select tbl_purchase_requisition.id,tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
					+ " tbl_purchase_requisition.delievery_date,tbl_purchase_requisition.status,tbl_purchase_requisition.created_date::date, "
					+ " tbl_warehouse.warehouse_name,tbl_warehouse.warehouse_address from tbl_purchase_requisition	inner join tbl_warehouse on tbl_warehouse.warehouse_id=tbl_purchase_requisition.warehouse_id"
					+ " where delievery_date is not null "+warehouse_id+" order by tbl_purchase_requisition.created_date desc";*/
			String Query=null;
			if(type==null||type.isEmpty()){
				 Query="select tbl_purchase_requisition.id,tbl_purchase_requisition.is_raised,"
				 		    + " tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
							+ " tbl_purchase_requisition.delievery_date,tbl_warehouse1.wareouse_name as warehouse_name, "
							+ " case when tbl_purchase_requisition.status='pending' then 'Approval Pending' "
							+ " when tbl_purchase_requisition.status='approved' then 'Admin approved/PO pending' "
							+ " else 'PO Raised' end as status ,tbl_purchase_requisition.created_date::date "
							+ " from tbl_purchase_requisition "
							+ " inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id "
							+ " where delievery_date is not null  order by tbl_purchase_requisition.created_date desc";
				
			}else {
				type=type.toLowerCase();
				 Query="select tbl_purchase_requisition.id,tbl_purchase_requisition.is_raised,"
				 		    + " tbl_purchase_requisition.purchase_requisition_id,tbl_purchase_requisition.warehouse_id,"
							+ " tbl_purchase_requisition.delievery_date,tbl_warehouse1.wareouse_name as warehouse_name, "
							+ " case when tbl_purchase_requisition.status='pending' then 'Approval Pending' "
							+ " when tbl_purchase_requisition.status='approved' then 'Admin approved/PO pending' "
							+ " else 'PO Raised' end as status ,tbl_purchase_requisition.created_date::date "
							+ " from tbl_purchase_requisition "
							+ " inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id "
							+ " where delievery_date is not null and lower(tbl_purchase_requisition.status)='"+type+"' "
							+ "order by tbl_purchase_requisition.created_date desc";
			}
			
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					ResultSet result=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(result);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/getPurchaseRequisitionListbyId")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getPurchaseListbyId(@FormParam("id") String id,@FormParam("purchase_requisition_id") String purchase_requisition_id){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				/*if(role_id!=1){
					warehouse_id=(String) jsObject.get("auth_id");
				}*/
			}String Query="";
			/*if(purchase_requisition_id.contains("PRE")){
				Query="select tbl_stage_purchase_requisition.id,tbl_stage_purchase_requisition.purchase_requisition_id,tbl_stage_purchase_requisition.product_id,tbl_stage_purchase_requisition.vendor_id,"
						+ " tbl_purchase_requisition.created_date::date as cdate,tbl_purchase_requisition.warehouse_id,tbl_warehouse.warehouse_name,tbl_warehouse.warehouse_address as shipping_address,"
						+ " tbl_stage_purchase_requisition.issued_quantity as quantity,tbl_products.product_name||'-'||tbl_unit.unit_value||''||tbl_unit.unit_name as product_description,"
						+ " tbl_vendor.vendor_name,(select tbl_vendor_pricing_table.product_price::numeric::float8 as product_price from tbl_vendor_pricing_table"
						+ " where tbl_vendor_pricing_table.product_id=tbl_stage_purchase_requisition.product_id and tbl_vendor_pricing_table.vendor_id=tbl_stage_purchase_requisition.vendor_id),"
						+ " (select tbl_vendor_pricing_table.product_cgst from tbl_vendor_pricing_table"
						+ " where tbl_vendor_pricing_table.product_id=tbl_stage_purchase_requisition.product_id and tbl_vendor_pricing_table.vendor_id=tbl_stage_purchase_requisition.vendor_id) as cgst,"
						+ " (select tbl_vendor_pricing_table.product_sgst  from tbl_vendor_pricing_table"
						+ " where tbl_vendor_pricing_table.product_id=tbl_stage_purchase_requisition.product_id and tbl_vendor_pricing_table.vendor_id=tbl_stage_purchase_requisition.vendor_id) as sgst,"
						+ " (select tbl_vendor_pricing_table.product_igst  from tbl_vendor_pricing_table"
						+ " where tbl_vendor_pricing_table.product_id=tbl_stage_purchase_requisition.product_id and tbl_vendor_pricing_table.vendor_id=tbl_stage_purchase_requisition.vendor_id) as igst"
						+ "  from tbl_stage_purchase_requisition inner join tbl_products on tbl_products.product_id=tbl_stage_purchase_requisition.product_id "
						+ "  inner join tbl_purchase_requisition on tbl_purchase_requisition.purchase_requisition_id=tbl_stage_purchase_requisition.purchase_requisition_id  inner join tbl_warehouse on tbl_warehouse.warehouse_id=tbl_purchase_requisition.warehouse_id "
						+ "  inner join tbl_vendor on tbl_vendor.vendor_id=tbl_stage_purchase_requisition.vendor_id  inner join tbl_unit on tbl_products.product_unit=tbl_unit.id"
						+ "  where tbl_stage_purchase_requisition.mark_for_deletion=0 and tbl_stage_purchase_requisition.purchase_requisition_id='"+purchase_requisition_id+"' order by tbl_stage_purchase_requisition.product_id asc";
			}else{
				Query="select tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.product_quantity as quantity,tbl_warehouse_purchase_order.purchaseorder_raised_date,"
						+ "tbl_warehouse_purchase_order.product_description,tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.igst,"
						+ "tbl_warehouse_purchase_order.product_price::numeric::float8 as product_price,tbl_warehouse_purchase_order.vendor_id, "
						+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
						+ "tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.sgst,"
						+ "tbl_products.product_cgst,tbl_products.product_sgst,tbl_products.product_igst,tbl_warehouse_purchase_order.discount,"
						+ "tbl_products.product_name,tbl_warehouse.warehouse_name,tbl_vendor.vendor_name,"
						+ "tbl_warehouse_purchase_order.product_id,tbl_warehouse.gst_number as warehouse_gst,"
						+ "tbl_vendor.gst_number as vendor_gst from tbl_warehouse_purchase_order "
						+ "inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id "
						+ "inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id "
						+ "inner join tbl_products on tbl_warehouse_purchase_order.product_id=tbl_products.product_id "
						+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.purchaseorder_id='"+purchase_requisition_id+"'";
			}*/
			
			if(purchase_requisition_id.contains("PRE")){
				/*Query="select tbl_stage_purchase_requisition.id,tbl_purchase_requisition.status,tbl_stage_purchase_requisition.is_raised,tbl_stage_purchase_requisition.purchase_requisition_id,tbl_stage_purchase_requisition.product_id,tbl_stage_purchase_requisition.vendor_id,"
						+ " tbl_purchase_requisition.is_raised,tbl_purchase_requisition.created_date::date as cdate,tbl_purchase_requisition.warehouse_id,tbl_purchase_requisition.shipping_address,"
						+ " case when tbl_purchase_requisition.status='pending' then 'Approval Pending' when tbl_purchase_requisition.status='approved' then tbl_purchase_requisition.status else 'PO Raised' end as status ,tbl_vendor.vendor_name,tbl_stage_purchase_requisition.issued_quantity as quantity,tbl_products.product_name||'-'||tbl_products.product_id as product_description,tbl_purchase_requisition.pr_docid "
						+ "  from tbl_stage_purchase_requisition inner join tbl_products on tbl_products.product_id=tbl_stage_purchase_requisition.product_id "
						+ "  inner join tbl_purchase_requisition on tbl_purchase_requisition.purchase_requisition_id=tbl_stage_purchase_requisition.purchase_requisition_id  "
						+ "  inner join tbl_vendor on tbl_vendor.vendor_id=tbl_stage_purchase_requisition.vendor_id  "
						+ "  where tbl_stage_purchase_requisition.mark_for_deletion=0 and tbl_stage_purchase_requisition.purchase_requisition_id='"+purchase_requisition_id+"' order by tbl_stage_purchase_requisition.product_id asc";*/
				
				Query="select tbl_purchase_requisition_products.id,tbl_purchase_requisition.status,tbl_purchase_requisition_products.purchase_requisition_id,tbl_purchase_requisition_products.product_id,"
						+ " tbl_purchase_requisition.is_raised,tbl_purchase_requisition.created_date::date as cdate,tbl_purchase_requisition.warehouse_id,tbl_purchase_requisition.shipping_address,"
						+ " case when tbl_purchase_requisition.status='pending' then 'Approval Pending' when tbl_purchase_requisition.status='approved' then tbl_purchase_requisition.status else 'PO Raised' end as status,tbl_purchase_requisition_products.issued_quantity as quantity,tbl_products.product_name||'-'||tbl_products.product_id as product_description,tbl_products.product_unit,tbl_purchase_requisition.pr_docid "
						+ "  from tbl_purchase_requisition_products inner join tbl_products on tbl_products.product_id=tbl_purchase_requisition_products.product_id "
						+ "  inner join tbl_purchase_requisition on tbl_purchase_requisition.purchase_requisition_id=tbl_purchase_requisition_products.purchase_requisition_id  "
//						+ "  inner join tbl_vendor on tbl_vendor.vendor_id=tbl_stage_purchase_requisition.vendor_id  "
						+ "  where tbl_purchase_requisition_products.mark_for_deletion=0 and tbl_purchase_requisition_products.purchase_requisition_id='"+purchase_requisition_id+"' order by tbl_purchase_requisition_products.product_id asc";
				
			}else{
				Query="select tbl_warehouse_purchase_order.purchaseorder_raised_date as cdate,tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.product_quantity as quantity,tbl_warehouse_purchase_order.purchaseorder_raised_date,"
						+ "tbl_warehouse_purchase_order.product_description,tbl_warehouse_purchase_order.vendor_id, "
						+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
						+ " case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Invoice Pending' else"
						+ " case when (select 1 from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id and (select sum(q.product_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id) "
						+ " < (select sum(q.issued_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id))=1 "
						+ " then case when is_qc_verified=0 then 'Partially Received/Waiting For Qc Approval' when is_qc_verified=1 then 'Partially Received/Approved by QC'  else 'Partially Received/Rejected by QC' end else "
						+ " case when is_qc_verified=0 then 'Received/Waiting For Qc Approval' when is_qc_verified=1 then 'Received/Approved by QC' else 'Received/Rejected by QC' end "
						+ " end end as status, COALESCE (tbl_warehouse_purchase_order.issued_quantity,0) as issued_quantity,"
//						+ "tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.sgst,"
//						+ "tbl_products.product_cgst,tbl_products.product_sgst,tbl_products.product_igst,tbl_warehouse_purchase_order.discount,"
						+ "tbl_products.product_name,tbl_products.product_unit,tbl_vendor.vendor_name,"
						+ "tbl_warehouse_purchase_order.product_id from tbl_warehouse_purchase_order "
//						+ "inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id "
						+ "inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id "
						+ "inner join tbl_products on tbl_warehouse_purchase_order.product_id=tbl_products.product_id "
						+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.purchaseorder_id='"+purchase_requisition_id+"'";
			}
			
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					ResultSet result=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(result);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@POST
		@Path("/setUpdatePurchaseRequisitionById")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response setUpdatePurchaseRequisitionById(@FormParam("id") String id,@FormParam("status") int status,@FormParam("purchase_requisition_id") String purchase_requisition_id,@FormParam("comments") String comments){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				/*if(role_id!=1){
					warehouse_id=(String) jsObject.get("auth_id");
				}*/
			}if(comments==null){
				comments="No Comments";
			}
			String strStatus="";
			if(status==1){
				strStatus="approved";
			}else{
				strStatus="rejected";
			}
			String Query=" update tbl_purchase_requisition set status='"+strStatus+"',comments='"+comments+"',updated_by='"+jsObject.get("email")+"',updated_date=current_timestamp where purchase_requisition_id='"+purchase_requisition_id+"' ";
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					int upd_Status=ConnectionWar.createStatement().executeUpdate(Query);
					if(upd_Status>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Purchase requisition "+strStatus+ " successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to update");
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@POST
		@Path("/getproductSearch")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getproductSearch(@FormParam("id") String id,@FormParam("pre_id") String pre_id){
			JSONObject jsonObject=new JSONObject();
			System.out.println(id);
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
			}
			
			/*String Query="select distinct tbl_ingrediants.product_id,tbl_products.product_name,tbl_products.product_name||'-'||tbl_ingrediants.product_id as product_description from tbl_ingrediants "
//					+ " inner join warehouse_stockdetails on warehouse_stockdetails.product_id=tbl_ingrediants.product_id"
//					+ " inner join tbl_products on tbl_products.product_id=tbl_ingrediants.product_id  inner join tbl_unit on tbl_unit.id=tbl_products.product_unit  "`
					+ " inner join tbl_products on tbl_products.product_id=tbl_ingrediants.product_id "
					+ " group by tbl_ingrediants.product_id,tbl_products.product_name,tbl_ingrediants.status having tbl_ingrediants.status='pending' and (select project_type from tbl_project where tbl_project.project_id=tbl_ingrediants.project_id)!='Contract'";*/
			
			String Query="select distinct tbl_indent_ingrediants.product_id,tbl_products.product_name,tbl_products.product_name||'-'||tbl_indent_ingrediants.product_id as product_description from tbl_indent_ingrediants"
					+ " inner join tbl_products on tbl_products.product_id=tbl_indent_ingrediants.product_id where tbl_indent_ingrediants.status='pending' and indent_type!='Contract'";
			if(pre_id!=null&&!pre_id.isEmpty()){
				Query="select distinct tbl_purchase_requisition_products.product_id,tbl_products.product_name,tbl_products.product_name||'-'||tbl_purchase_requisition_products.product_id as product_description from tbl_purchase_requisition_products"
						+ " inner join tbl_products on tbl_products.product_id=tbl_purchase_requisition_products.product_id where tbl_purchase_requisition_products.purchase_requisition_id='"+pre_id+"'";
			}
			Connection ConnectionWar=null;
			try {
				ConnectionWar=DBConnection.SqlConnection();
				try {
					System.out.println(Query);
					ResultSet resultset=ConnectionWar.createStatement().executeQuery(Query);
					System.out.println(resultset);
					@SuppressWarnings("rawtypes")
					ArrayList arrayList=convertResultSetIntoArrayList(resultset);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "List is empty");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					jsonObject.clear();
					 jsonObject.put("Status", "Failed");
					 jsonObject.put("Message", "Something went wrong");
					 jsonObject.put("error", e.getMessage());
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/getVendorListByProduct")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getVendorListByProduct(@FormParam("id") String id,@FormParam("product_description") String product_description,@FormParam("issued_quantity") String issued_quantity){
			JSONObject jsonObject=new JSONObject();
			
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
			}
			
			String[] parts = product_description.split("-");
			String product_id = parts[parts.length-1];
			
			String Query="select tbl_vendor_pricing_table.vendor_id,tbl_vendor1.vendor_name,'"+issued_quantity+"' as issued_quantity,tbl_vendor_pricing_table.product_price "
					+ " as product_price,(tbl_vendor_pricing_table.product_cgst::float+tbl_vendor_pricing_table.product_sgst::float) as tax,tbl_vendor_pricing_table.discount from tbl_vendor_pricing_table"
					+ " inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_vendor_pricing_table.vendor_id where tbl_vendor_pricing_table.product_id='"+product_id+"' and tbl_vendor1.mark_for_deletion=0";
			System.out.println(Query);
			Connection ConnectionWar=null;
			try {
				ConnectionWar=DBConnection.SqlConnection();
				try {
					ResultSet resultset=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(resultset);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "List is empty");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					jsonObject.clear();
					 jsonObject.put("Status", "Failed");
					 jsonObject.put("Message", "Something went wrong");
					 jsonObject.put("error", e.getMessage());
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@POST
		@Path("/getproductStockDetails")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getproductStockDetails(@FormParam("id") String id,@FormParam("product_description") String product_description,@FormParam("req_id") String req_id){
			JSONObject jsonObject=new JSONObject();
			
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
			}
			
			String[] parts = product_description.split("-");
			String product_id = parts[parts.length-1];
			
			/*String Query="select distinct tbl_ingrediants.product_id,tbl_products.product_name||'-'||tbl_ingrediants.product_id as product_description, "
					+ " (sum(quantity)-sum(issued_quantity)) as stock_required,warehouse_stockdetails.total_stock  as stock_availability from tbl_ingrediants "
					+ " inner join warehouse_stockdetails on warehouse_stockdetails.product_id=tbl_ingrediants.product_id"
					+ " inner join tbl_products on tbl_products.product_id=tbl_ingrediants.product_id   "
					+ " group by tbl_ingrediants.product_id,warehouse_stockdetails.total_stock,tbl_products.product_name"
					+ " having (sum(quantity)-sum(issued_quantity))>0  and (sum(quantity)-sum(issued_quantity)) > warehouse_stockdetails.total_stock  and tbl_ingrediants.product_id='"+product_id+"'";*/
			
			String Query="select distinct tbl_indent_ingrediants.product_id,tbl_products.product_name||'-'||tbl_indent_ingrediants.product_id as product_description,"
					+ " (sum(raised_quantity)-sum(issued_quantity)) as stock_required,warehouse_stockdetails.total_stock  as stock_availability from tbl_indent_ingrediants "
					+ " inner join warehouse_stockdetails on warehouse_stockdetails.product_id=tbl_indent_ingrediants.product_id "
					+ " inner join tbl_products on tbl_products.product_id=tbl_indent_ingrediants.product_id group by tbl_indent_ingrediants.product_id,tbl_indent_ingrediants.status,indent_type,warehouse_stockdetails.total_stock,tbl_products.product_name having (sum(raised_quantity)-sum(issued_quantity))>0  and"
					+ " (sum(raised_quantity)-sum(issued_quantity)) >= warehouse_stockdetails.total_stock  and tbl_indent_ingrediants.product_id='"+product_id+"'"
					+ " and tbl_indent_ingrediants.status='pending' and indent_type!='Contract'";
			if(req_id!=null&&!req_id.isEmpty()){
				Query="select distinct tbl_purchase_requisition_products.product_id,tbl_products.product_name||'-'||tbl_purchase_requisition_products.product_id as product_description,"
						+ " tbl_purchase_requisition_products.issued_quantity  as stock_required from tbl_purchase_requisition_products "
						+ " inner join tbl_products on tbl_products.product_id=tbl_purchase_requisition_products.product_id  where "
						+ " tbl_purchase_requisition_products.product_id='"+product_id+"' and tbl_purchase_requisition_products.purchase_requisition_id='"+req_id+"'";
			}
			System.out.println(Query);
			Connection ConnectionWar=null;
			try {
				ConnectionWar=DBConnection.SqlConnection();
				try {
					ResultSet resultset=ConnectionWar.createStatement().executeQuery(Query);
					ArrayList arrayList=convertResultSetIntoArrayListIndividual(resultset);
					if(arrayList!=null&&!arrayList.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", arrayList);
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "List is empty");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					jsonObject.clear();
					 jsonObject.put("Status", "Failed");
					 jsonObject.put("Message", "Something went wrong");
					 jsonObject.put("error", e.getMessage());
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/getStageRequisitionList")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response getStageRequisitionList(@FormParam("issued_qty") double issued_qty,@FormParam("id") String id,@FormParam("purchse_requisition_id") String purchase_requisition_id,@FormParam("product_id") String product_id,@FormParam("data") String data,@FormParam("status") int status){
			JSONObject jsonObject=new JSONObject();
			System.out.println(id +" : "+purchase_requisition_id+" : "+product_id+" : "+data);
			if(id==null||product_id==null){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Fields are empty");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}if(purchase_requisition_id==null||purchase_requisition_id.isEmpty()){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Fields are empty");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			if(data==null||data.isEmpty()||data.contains("[]")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Please choose any one");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			String auth_id="";
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				auth_id=(String) jsObject.get("auth_id");
				System.out.println(role_id);
			}
			double qty=getStock(data);
			if(qty<=0){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Issued qunatity is zero");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			if(qty>issued_qty){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Please change the quantity and sum of issued quantity less than required stock");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			
			String Query="select * from fn_stage_requisition('"+auth_id+"','"+purchase_requisition_id+"','"+product_id+"','"+data+"')";
			//String Query="select * from fn_stage_requisition('"+jsObject.get("departmentid").toString()+"','"+purchase_requisition_id+"','"+product_id+"','"+data+"')";
			System.out.println(Query);
			Connection ConnectionWar=null;
			try {	String result_insertion_data="";
				ConnectionWar=DBConnection.SqlConnection();
				try {
					CallableStatement CStatement=ConnectionWar.prepareCall(Query);
					CStatement.execute();
					ResultSet result_insertion=CStatement.getResultSet();
					while(result_insertion.next()){
						result_insertion_data=result_insertion.getString(1);
					}System.out.println("result_insertion_data :   "+result_insertion_data);
					if(result_insertion_data.contains("Success")||result_insertion_data.contains("Already Exists")){
						String retrieve_Query="select tbl_stage_purchase_requisition.id,tbl_stage_purchase_requisition.purchase_requisition_id,tbl_stage_purchase_requisition.product_id,tbl_stage_purchase_requisition.vendor_id,"
								+ " tbl_stage_purchase_requisition.issued_quantity,tbl_products.product_name||'-'||tbl_products.product_id as product_description, "
								+ " tbl_vendor1.vendor_name,(select tbl_vendor_pricing_table.product_price::numeric::float8 as price from tbl_vendor_pricing_table "
								+ " where tbl_vendor_pricing_table.product_id=tbl_stage_purchase_requisition.product_id and tbl_vendor_pricing_table.vendor_id=tbl_stage_purchase_requisition.vendor_id) "
								+ " from tbl_stage_purchase_requisition inner join tbl_products on tbl_products.product_id=tbl_stage_purchase_requisition.product_id"
								+ " inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_stage_purchase_requisition.vendor_id "
								+ " where tbl_stage_purchase_requisition.purchase_requisition_id='"+purchase_requisition_id+"' and tbl_stage_purchase_requisition.mark_for_deletion=0 order by tbl_stage_purchase_requisition.product_id asc";
						System.out.println("retrieve_Query : "+retrieve_Query);
						ResultSet resultset=ConnectionWar.createStatement().executeQuery(retrieve_Query);
						ArrayList arrayList=convertResultSetIntoArrayListIndividual(resultset);
						if(arrayList!=null&&!arrayList.isEmpty()){
							jsonObject.clear();
							jsonObject.put("Status", "Success");
							jsonObject.put("Data", arrayList);
						}else{
							jsonObject.clear();
							jsonObject.put("Status", "Failed");
							jsonObject.put("Message", "List is empty");
						}
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to update");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					jsonObject.clear();
					 jsonObject.put("Status", "Failed");
					 jsonObject.put("Message", "Something went wrong");
					 jsonObject.put("error", e.getMessage());
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/setRaisePurchaseOrderById")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response setRaisePurchaseOrderById(@FormParam("id") String id,
				@FormParam("purchase_requisition_id") String purchase_requisition_id,
				@FormParam("warehouse_id") String warehouse_id){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
				/*if(role_id!=1){
					warehouse_id=(String) jsObject.get("auth_id");
				}*/
			}
			String Query=" select * from fn_raise_purchase_order('"+purchase_requisition_id+"','"+jsObject.get("email").toString()+"','PUR','"+warehouse_id+"') ";
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					String res="";
					System.out.println(Query);
					CallableStatement csStatement=ConnectionWar.prepareCall(Query);
					csStatement.execute();
					ResultSet result=csStatement.getResultSet();
					while(result.next()){
						res=result.getString(1);
					}System.out.println(res);
					if(res.contains("Success")){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Purchase order raised successfully");
					}else if(res.contains("still in pending")){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Purchase requisition is still in pending");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to raise purchase order");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/removeItemInRequisition")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response removeItemInRequisition(@FormParam("id") String id,@FormParam("item_id") String item_id,@FormParam("purchase_requisition_id") String purchase_requisition_id){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
			}
			String Query="update tbl_stage_purchase_requisition set mark_for_deletion=1 where id='"+item_id+"' ";	//and purchase_requisition_id='"+purchase_requisition_id+"'
			System.out.println(Query);
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					int status=ConnectionWar.createStatement().executeUpdate(Query);
					if(status>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Updated successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to update");
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("unchecked")
		@POST
		@Path("/raisePurchaseRequisition")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response raisePurchaseRequisition(@FormParam("id") String id,
				@FormParam("purchase_requisition_id") String purchase_requisition_id,
				@FormParam("delievery_date") String delievery_date,
				@FormParam("shipping_address") String shipping_address,
				@FormParam("warehouse_id") String warehouse_id){
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}else{
				String email=(String) jsObject.get("email");
				int role_id=(int) jsObject.get("role_id");
				System.out.println(role_id);
			}if(delievery_date!=null&&!delievery_date.isEmpty()){
				/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
				Date c_date=new Date();
				Date d_Date = null;
				try {
					d_Date = sdf.parse(delievery_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(d_Date.after(new Date(sdf.format(c_date)))){
					System.out.println("Yes");
				}else{
					System.out.println("No");
				}*/
			}else{
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Please enter delievery date");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "POST").build();
			}
			
			String check_Query="select 1 from tbl_stage_purchase_requisition where purchase_requisition_id='"+purchase_requisition_id+"'";
			
			System.out.println(check_Query);
			String Query="update tbl_purchase_requisition set is_stage=0,updated_date=current_timestamp,delievery_date='"+delievery_date+"',shipping_address='"+shipping_address+"' where purchase_requisition_id='"+purchase_requisition_id+"' ";
			System.out.println(Query);
			Connection ConnectionWar=null;
			try {	
				ConnectionWar=DBConnection.SqlConnection();
				try {
					ResultSet result=ConnectionWar.createStatement().executeQuery(check_Query);
					boolean res_Status=result.next();
					if(!res_Status){
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Please select Products and vendors");
						return Response.ok()
								.entity(jsonObject)
								.header("Access-Control-Allow-Methods", "POST").build();
					}
					
					int status=ConnectionWar.createStatement().executeUpdate(Query);
					if(status>0){
						String getprdetails="select tbl_purchase_requisition.purchase_requisition_id,"
								+ "tbl_purchase_requisition.created_date,tbl_purchase_requisition.delievery_date,\r\n" + 
								" tbl_purchase_requisition.shipping_address,tbl_purchase_requisition.status,\r\n" + 
								"(select coalesce(json_agg(json_build_object('product_id',tbl_stage_purchase_requisition.product_id,"
								+ "'vendor_id',tbl_stage_purchase_requisition.vendor_id,\r\n" + 
								" 'vendor_name',tbl_vendor1.vendor_name,'wareouse_name',tbl_warehouse1.wareouse_name,'quantity',"
								+ "tbl_stage_purchase_requisition.issued_quantity,'product_name',tbl_products.product_name)),'[]'::json) "
								+ "from tbl_stage_purchase_requisition\r\n "
								+ " inner join tbl_warehouse1 on tbl_purchase_requisition.warehouse_id=tbl_warehouse1.warehouse_id " + 
								" inner join tbl_products on tbl_products.product_id=tbl_stage_purchase_requisition.product_id \r\n " + 
								" inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_stage_purchase_requisition.vendor_id where "
								+ "tbl_stage_purchase_requisition.purchase_requisition_id=tbl_purchase_requisition.purchase_requisition_id) as ingrediants\r\n" + 
								" from tbl_purchase_requisition where tbl_purchase_requisition.mark_for_deletion=0 and "
								+ "tbl_purchase_requisition.purchase_requisition_id='"+purchase_requisition_id+"' ";
						System.out.println(getprdetails);
						ResultSet rset=ConnectionWar.createStatement().executeQuery(getprdetails);
						Object object=convertToJSONObject(rset);
						String path_sever=System.getProperty("catalina.base");
						System.out.println("path_sever: "+path_sever);
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Purchase requisition updated successfully");
						jsonObject.put("Data", object);
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to update");
					}
					
				} catch (Exception e) {
					jsonObject.clear();
					 jsonObject.put("Status", "Failed");
					 jsonObject.put("Message", "Please try again");
					 jsonObject.put("error", e.getMessage());
					 e.printStackTrace();
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
					DBConnection.closeConnection(ConnectionWar);
				} catch (SQLException e) {
				}
			}
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
			
		}
		
		@SuppressWarnings("rawtypes")
		public static ArrayList convertResultSetIntoArrayListIndividual(ResultSet resultSet) throws Exception {
			ArrayList resultsetArray=new ArrayList(); 
			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				Map map=new HashMap();
				double totalStock=0;double requiredstock=0;double raisedStock=0;
				for (int i = 0; i <total_rows; i++) {
					String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
					if(columnName.contains("stock_required")){
						requiredstock=resultSet.getDouble("stock_required");
//						System.out.println(requiredstock);
					}if(columnName.contains("stock_availability")){
						totalStock=resultSet.getDouble("stock_availability");
						System.out.println(totalStock);
					}if(totalStock>=0&&requiredstock>0){
						if(totalStock>requiredstock){
							raisedStock=totalStock-requiredstock;
						}else{
							raisedStock=requiredstock-totalStock;
						}
						System.out.println(raisedStock);
						map.put("raised_stock", raisedStock);
					}/*else if(totalStock==0){
						raisedStock=requiredstock;
//						System.out.println(raisedStock);
						map.put("raised_stock", raisedStock);
					}*/
					Object columnValue = resultSet.getObject(i + 1);
					map.put(columnName,columnValue);
					
					//Object columnValue = resultSet.getObject(i + 1);
					
				}
				resultsetArray.add(map);
			}
			System.out.println(resultsetArray);
			return resultsetArray;
		}
		
		public static ArrayList convertResultSetIntoArrayList(ResultSet resultSet) throws Exception {
			ArrayList resultsetArray=new ArrayList(); 
			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				Map map=new HashMap();
				for (int i = 0; i <total_rows; i++) {
					String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
					Object columnValue = resultSet.getObject(i + 1);
					map.put(columnName,columnValue);
				}
				resultsetArray.add(map);
			}
			System.out.println(resultsetArray);
			return resultsetArray;
		}
		
		public static double getStock(String data){
			double qty=0;
			try {
				org.json.JSONArray jsArray=new org.json.JSONArray(data);
				for(int i=0;i<jsArray.length();i++){
					org.json.JSONObject jsObject=jsArray.getJSONObject(i);
					double v_qty=jsObject.getDouble("quantity");
					qty=qty+v_qty;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return -1;
			}
			return qty;
		}
}
