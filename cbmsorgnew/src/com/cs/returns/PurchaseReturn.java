package com.cs.returns;

import java.io.IOException;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.cs.TokenCheck;
import com.helper.DBConnection;
import com.hr.helper.HelperClass;
import com.hr.main.MyObject;
import com.hr.utils.MessageConfig;

@SuppressWarnings("unchecked")
@Path("purchasereturn")
public class PurchaseReturn {
	static Response response=null; 
	static PreparedStatement preparedStmt=null;
	static Connection conn = null;
	
	private static Log log=LogFactory.getLog(PurchaseReturn.class);
	@POST
	@Path("/preturn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response preturn(MyObject json) {
		
		Object responseObject=new JSONObject(); 
		String vendor_id=json.getVendor_id();
		try{
			conn = DBConnection.SqlConnection();
			if(vendor_id==null||vendor_id.isEmpty()){
				String query=" select distinct on (tgi.invoice_id,twpo.purchaseorder_id) tgi.invoice_id,tgi.invoice_generated_date,"
						+ "tgi.return_status,twpo.purchaseorder_returns_id,twpo.purchaseorder_id,twpo.purchaseorder_status,tv.vendor_name,"
						+ "tw.wareouse_name,twpo.return_qty,(select count(*) from tbl_warehouse_purchase_order "
						+ "where purchaseorder_status='Rejected' and purchaseorder_id=twpo.purchaseorder_id)no_of_items "
						+ "from tbl_generate_invoice1 tgi "
						+ "inner join tbl_warehouse_purchase_order twpo on tgi.purchaseorder_id = twpo.purchaseorder_id "
						+ "inner join tbl_warehouse1 tw on tw.warehouse_id= twpo.warehouse_id"
						+ " inner join tbl_vendor1 tv on tv.vendor_id= twpo.vendor_id  where  twpo.return_qty>0 and tgi.return_status='Pending' "
						+ "and  twpo.vendor_id = ?";
										preparedStmt=conn.prepareStatement(query);
				}else{
					String query=" select distinct on (tgi.invoice_id,twpo.purchaseorder_id) tgi.invoice_id,tgi.invoice_generated_date,"
							+ "tgi.return_status,twpo.purchaseorder_returns_id,twpo.return_qty,"
					        + "twpo.purchaseorder_id,twpo.purchaseorder_status,tv.vendor_name,tw.wareouse_name,"
					        + "(select count(*) from tbl_warehouse_purchase_order where purchaseorder_status='Rejected' and "
					        + "purchaseorder_id=twpo.purchaseorder_id)no_of_items from tbl_generate_invoice1 tgi"
                            + "inner join tbl_warehouse_purchase_order twpo on tgi.purchaseorder_id = twpo.purchaseorder_id"
                            + "inner join tbl_warehouse1 tw on tw.warehouse_id= twpo.warehouse_id"
                            + "inner join tbl_vendor1 tv on tv.vendor_id= twpo.vendor_id  where  twpo.return_qty>0 and tgi.return_status='Pending' and"
                            + "twpo.vendor_id=?";
										preparedStmt=conn.prepareStatement(query);
										preparedStmt.setString(1, vendor_id);
				}
			
			System.out.println(preparedStmt);
			ResultSet resultset = preparedStmt.executeQuery();
	        ArrayList result_Array=(ArrayList) HelperClass.convertToJSON(resultset);
	        if(result_Array.isEmpty()||result_Array.size()==0){
	            responseObject = HelperClass.generateResponce(201,MessageConfig.empty_list,"Failed");

	        }else {
	       	 responseObject = HelperClass.generateResponce(200,result_Array,null );

	        }
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
		}
		finally {
			try {
			conn.close();
				
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
			}
		}
		response=HelperClass.convertObjectToResponce(responseObject,200);
		return response;
}
	
	@POST
	@Path("/preturnUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response preturnUpdate(MyObject json) {
		
		Response response=null; 
     	int i=0;
		PreparedStatement preparedStmt=null;
		Connection conn = null;
		Object responseObject=new JSONObject();
		String sessionid=json.getSessionid();
		String purchaseorder_returns_date=json.getPurchaseorder_returns_date();
		String invoice_id=json.getInvoice_id();
		String exchange_type=json.getExchange_type();
		String purchase_return_comments=json.getPurchase_return_comments();
		String vendor_id=json.getVendor_id();
		try{
				conn = DBConnection.SqlConnection();
					String query="insert into purchase_order_returns (purchaseorder_returns_date,purchaseorder_referencenumber,exchange_type,purchase_return_comments,vendor_id) values" +
					                      "(?::Date,?,?,?,?)";
										
										preparedStmt=conn.prepareStatement(query);
								
										preparedStmt.setString(1,purchaseorder_returns_date );
										preparedStmt.setString(2,invoice_id );
										preparedStmt.setString(3,exchange_type );
										preparedStmt.setString(4,purchase_return_comments );
										preparedStmt.setString(5,vendor_id);
			
			System.out.println(preparedStmt);
			i = preparedStmt.executeUpdate();
	        if(i>0){
	            responseObject = HelperClass.generateResponce(200,MessageConfig.insert_succes,null);

	        }else {
	       	 responseObject = HelperClass.generateResponce(201,MessageConfig.insert_failed,"Failed to insert" );

	        }
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
		}
		finally {
			try {
			conn.close();
				
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
			}
		}
		response=HelperClass.convertObjectToResponce(responseObject,200);
		return response;
}	
	@POST
	@Path("/purReturnOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purReturnOrders(MyObject json) {		
		Response response=null; 
		PreparedStatement preparedStmt=null;
		Connection conn = null;
		String sessionid=json.getSessionid();
		JSONObject jsObject=TokenCheck.checkTokenStatus(sessionid);
		Object responseObject=new JSONObject();
		JSONObject jsonObject=new JSONObject();
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
				
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		else{
			String vendor_id=(String) jsObject.get("auth_id");
		try{
				conn = DBConnection.SqlConnection();
				
				
				String query1 =" select exchange_type from purchase_order_returns where vendor_id='"+vendor_id+"'";
				ResultSet resultFromQuery=conn.createStatement().executeQuery(query1);
				String exchangetype=null;
				while(resultFromQuery.next()) {
					exchangetype=resultFromQuery.getString("exchange_type");
				}	
				if(exchangetype!=null){
				
					String query="select twpo.invoice_id,twpo.purchaseorder_returns_id,twpo.created_date::date,twpo.purchaseorder_id,twpo.vendor_id,twpo.purchaseorder_status,"
                                  +" tw.wareouse_name,tgi.return_status,(SELECT COUNT(product_id) FROM tbl_warehouse_purchase_order where purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id and purchaseorder_status='Rejected') as no_of_items"
                                  +" from tbl_warehouse_purchase_order twpo  inner join tbl_warehouse1 tw on  tw.warehouse_id= twpo.warehouse_id"
                                  +" inner join tbl_generate_invoice1 tgi on tgi.purchaseorder_id= twpo.purchaseorder_id "
                                  +" where purchaseorder_status='Rejected' and twpo.vendor_id='"+vendor_id+"'";
			preparedStmt=conn.prepareStatement(query);
			System.out.println(preparedStmt);
			ResultSet resultset = preparedStmt.executeQuery();
			  @SuppressWarnings("rawtypes")
			ArrayList result_Array=(ArrayList) HelperClass.convertToJSON(resultset);
			  if(result_Array.isEmpty()||result_Array.size()==0){
		            responseObject = HelperClass.generateResponce(201,MessageConfig.empty_list,"Failed");

		        }else {
		       	 responseObject = HelperClass.generateResponce(200,result_Array,null);
		        }
				}else{
					responseObject = HelperClass.generateResponce(201,MessageConfig.empty_list,"Failed");
				}
		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
		}
		finally {
			try {
			conn.close();
				
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
			}
		}
		response=HelperClass.convertObjectToResponce(responseObject,200);
		return response;
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/rejectproductAapprovedByVendor ")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response rejectProductAapprovedByVendor(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id) throws ClassNotFoundException, SQLException{
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			
		}
		String Query ="";
		
		try {
			conn = DBConnection.SqlConnection();
			try {
				System.out.println(Query);
				preparedStmt=conn.prepareStatement(Query);
				int number=preparedStmt.executeUpdate();
				
				if(number>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
				}
			} catch (SQLException e) {
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
		}
		finally{
			try {
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
}



//	@POST
//		@Path("/warehousereturnlist")
//		@Consumes(MediaType.APPLICATION_JSON)
//		@Produces(MediaType.APPLICATION_JSON)
//		public static Response warehousereturnlist(MyObject json) {
//			Response response=null; 
//			PreparedStatement preparedStmt=null;
//			ResultSet resultSet = null;
//			Connection conn = null;
//			Object responseObject=new JSONObject();
//			String sessionid = json.getSessionid();
//
//	    System.out.println(warehouse_name);
//	    String login_name=json.getRole();
//	    String customer_name=json.getCustomer_name();
//		    String status=json.getStatus();
//	    String exchange_type=json.getExchange_type();
//	    String purchaseorder_returns_id=json.getPurchaseorder_returns_id();
//			try{
//					conn = DBConnection.SqlConnection();
//					System.out.println("connection: "+conn);
//						String query=" select tgi.return_status,twpo.shipping_address,tv.vendor_id,twpo.purchaseorder_id,twpo.return_qty,tv.vendor_name,tw.wareouse_name, twpo.product_description "
//								+"from tbl_generate_invoice1 tgi inner join tbl_warehouse_purchase_order twpo on tgi.purchaseorder_id = twpo.purchaseorder_id"
//                           +" inner join tbl_warehouse1 tw on tw.warehouse_id= twpo.warehouse_id"
//                           +" inner join tbl_vendor1 tv on tv.vendor_id= twpo.vendor_id  where twpo.purchaseorder_status='Rejected'";
//						PreparedStatement statement = conn.prepareStatement(query);
//						System.out.println(statement);
//						ResultSet resultset = statement.executeQuery();
//				        ArrayList result_Array=(ArrayList) HelperClass.convertToJSON(resultset);
//					
//					org.json.JSONArray resultArray = new org.json.JSONArray(result_Array);
//					System.out.println("result array length: "+resultArray.length());
//					for(int i=0;i<resultArray.length();i++){
//						org.json.JSONObject orgjsObject=resultArray.getJSONObject(i);
//						String product_description 	   = orgjsObject.getString("product_description");
//						String purchaseorder_id 	   = orgjsObject.getString("purchaseorder_id");
//						String  product_quantity 	   = orgjsObject.getString("return_qty");
//						String return_status           = orgjsObject.getString("return_status");
//						String shipping_address        = orgjsObject.getString("shipping_address");
//						String vendor_id               = orgjsObject.getString("vendor_id");
//						String vendor_name             = orgjsObject.getString("vendor_name");
//						String wareouse_name           = orgjsObject.getString("wareouse_name");
//					
//						String query1="INSERT INTO public.purchase_order_returns(purchaseorder_returns_id,purchaseorder_returns_date, warehouse_name,vendor_id, product_description, product_quantity, customer_name, createdon, updatedon,shipping_address,status,exchange_type)\r\n" + 
//								"	VALUES ('"+purchaseorder_returns_id+"',current_timestamp,'"+wareouse_name+"','"+vendor_id+"','"+product_description+"','"+product_quantity+"','"+vendor_name+"',current_timestamp,current_timestamp,'"+shipping_address+"','"+return_status+"',?)";
//																		preparedStmt=conn.prepareStatement(query1);
//																		preparedStmt.setString(1, exchange_type);
//																		System.out.println(preparedStmt);
//																		int j= preparedStmt.executeUpdate();
//																		System.out.println("j: "+j);
//																		if(j>0){
//																			System.out.println("inserted sucessfully");
//																		}
//																		else{
//																			System.out.println("failed insertion");
//																		}
//					}
//		        ArrayList result_Array1=(ArrayList) HelperClass.convertToJSON(resultSet);
//		        if(result_Array1.isEmpty()||result_Array1.size()==0){
//		            responseObject = HelperClass.generateResponce(201,MessageConfig.empty_list,"Failed");
//
//		        }else {
//		       	 responseObject = HelperClass.generateResponce(200,result_Array1,null );
//
//		        }
//			} catch (Exception e) {
//				log.error(e.getMessage());
//				System.out.println(e.getMessage());
//				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
//			}
//			finally {
//				try {
//				conn.close();
//					
//				} catch (SQLException e) {
//					log.error(e.getMessage());
//					responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,"Failed");
//				}
//			}
//			response=HelperClass.convertObjectToResponce(responseObject,200);
//			return response;
//	}	
//	}