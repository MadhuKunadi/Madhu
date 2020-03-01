package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;

import com.cs.model.Invoice;
//import com.google.gson.Gson;
import com.helper.Config;
import com.helper.DBConnection;
//import com.itextpdf.text.List;

@Path("purchaseList")
public class WarehousePurchaseOrder {
	
	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(WarehousePurchaseOrder.class);
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/generatePurcaseOrderId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatePurcaseOrderId(@FormParam("id") String id){
//		,@FormParam("warehouse_name") String warehouse_name
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);@SuppressWarnings("unused")
		String subQuery=new String();
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
			System.out.println(role_id);
//			if(role_id==4){
//				
//			}
			System.out.println(role_id);	
		}
		try {
			SqlConnection();
			try {
				String purchaseorderid= Customer.getGenerateId("PUR",8,connection);
				System.out.println("the value is :"+purchaseorderid);
				if(purchaseorderid!=null&&!purchaseorderid.isEmpty()){
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
		}
		finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/warehousePurchaseOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response Purchaselist(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("warehouse_id") String warehouse_id,@FormParam("vendor_id")
	String vendor_id,@FormParam("shipping_address") String shipping_address,@FormParam("delivery_date") String delivery_date,@FormParam("billing_address") String billing_address,
	@FormParam("warehouse_contact_number1") String warehouse_contact_number1,
	@FormParam("warehouse_mailaddress1") String warehouse_mailaddress1,
	@FormParam("products") String products,@FormParam("date") String date){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
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
            String auth_id=(String) jsObject.get("auth_id");
            if(role_id==1){
            	
            }else{
            	warehouse_id=auth_id;
            }
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			String email=(String) jsObject.get("email");
			//String purchaseorderid= getProductId("PUR");
			//String purchaseorder_referenceid= getReferenceId("REF");
			org.json.JSONArray jsArray = new org.json.JSONArray(products);
			for(int i=0;i<jsArray.length();i++){
				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
				//String product_id = orgjsObject.getString("productid");
				String productname =orgjsObject.getString("productname");
				
				String[] parts = productname.split("-");
				//String lastpart = parts[4];
				//String[] bits = one.split("-");
				String product_name = parts[0]; 
				String product_category = parts[1];
				String product_subcategory=parts[2];
				String product_id = parts[parts.length-1];
				//String productUnit =parts[parts.length-2];

//				String productUnit =orgjsObject.getString("unit");
				int productQuantity = orgjsObject.getInt("quantity");
				String cgst = orgjsObject.getString("cgst");
				String sgst = orgjsObject.getString("sgst");
				String igst = orgjsObject.getString("igst");
				double price = orgjsObject.getDouble("price");
				//String discount = orgjsObject.getString("discount");
				//System.out.println("unit is:"+productUnit);
				
				
				String Query="select  Warehouse_Raised_Purchase_Order('"+purchaseorder_id+"','"+date+"','"+warehouse_id+"','"+vendor_id+"','"+shipping_address+"','"+billing_address+"',"
						+ "'"+delivery_date+"','"+warehouse_contact_number1+"','"+warehouse_mailaddress1+"','"+product_id+"','"+productQuantity+"','"+email+"','"+productname+"',"
								+ "'"+price+"','0%','"+cgst+"','"+sgst+"','"+igst+"')";
//System.out.println("the query is"  +Query);
				ResultSet st=statement.executeQuery(Query);
				boolean status=false; 
				while(st.next())
					status=st.getBoolean(1);
				if(status=true){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Purchase Order Raised Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to raise Purchase Order");
				}
			}
		}catch (SQLException e) {
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
		finally{
			try {
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	/*
	 * Admin approved Purchase Order
	 */
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/adminApprovedPO")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AdminApprovedPO(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("warehouse_id") String warehouse_id,@FormParam("vendor_id")
	String vendor_id,@FormParam("shipping_address") String shipping_address,@FormParam("delivery_date") String delivery_date,
	@FormParam("data") String data,@FormParam("status") int status,@FormParam("balance") double balance,@FormParam("grand_total") double grand_total,
	@FormParam("member_id") int member_id,@FormParam("member_type_id") int member_type_id,@FormParam("Opening_Balance") double Opening_Balance,@FormParam("withhold_amount") double withhold_amount){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
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
            String auth_id=(String) jsObject.get("auth_id");
//            if(sta==1){
//            	
//            }else{
//            	warehouse_id=auth_id;
//            }
			System.out.println(role_id);
		}
		
		try {
			SqlConnection();
			String email=(String) jsObject.get("email");
			//String purchaseorderid= getProductId("PUR");
			//String purchaseorder_referenceid= getReferenceId("REF");
			org.json.JSONArray jsArray = new org.json.JSONArray(data);
			for(int i=0;i<jsArray.length();i++){
				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
				//String product_id = orgjsObject.getString("productid");
//				String productname =orgjsObject.getString("productname");
//				
//				String[] parts = productname.split("-");
//				//String lastpart = parts[4];
//				//String[] bits = one.split("-");
//				String product_name = parts[0]; 
//				String product_category = parts[1];
//				String product_subcategory=parts[2];
				//String product_id = parts[parts.length-1];
				String product_id = orgjsObject.getString("product_id");

				//String productUnit =parts[parts.length-2];

//				String productUnit =orgjsObject.getString("unit");
				int productQuantity = orgjsObject.getInt("product_quantity");
				double cgst = orgjsObject.getDouble("product_cgst");
				double sgst = orgjsObject.getDouble("product_sgst");
				double igst = orgjsObject.getDouble("product_igst");
				double price = orgjsObject.getDouble("price");
				String vendor_name = orgjsObject.getString("vendor_name");
				String purchaseorder_raised_date = orgjsObject.getString("purchaseorder_raised_date");
				double remaining_amount=(balance-grand_total);
				//String discount = orgjsObject.getString("discount");
				//System.out.println("unit is:"+productUnit);
				String Query ="";
				if(status==1){
					
					Query="select  fn_admin_approval('"+purchaseorder_id+"','"+member_id+"','"+member_type_id+"','"+purchaseorder_raised_date+"','"+warehouse_id+"','"+vendor_id+"','"+shipping_address+"',"
							+ "'"+product_id+"','"+productQuantity+"','"+email+"','"+price+"','"+cgst+"','"+sgst+"','"+igst+"',"
									+ "'"+balance+"','"+withhold_amount+"','"+remaining_amount+"','"+grand_total+"','"+vendor_name+"','"+Opening_Balance+"')";
					
				}else{
					
//					Query="select  Warehouse_Raised_Purchase_Order('"+purchaseorder_id+"','"+date+"','"+warehouse_id+"','"+vendor_id+"','"+shipping_address+"','"+billing_address+"',"
//							+ "'"+delivery_date+"','"+warehouse_contact_number1+"','"+warehouse_mailaddress1+"','"+product_id+"','"+productQuantity+"','"+email+"','"+productname+"',"
//									+ "'"+price+"','0%','"+cgst+"','"+sgst+"','"+igst+"')";
					
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "PO rejected by admin");
		
				}
				
				ResultSet st=statement.executeQuery(Query);
				boolean status1=false; 
				while(st.next())
					status1=st.getBoolean(1);
				if(status1=true){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "PO approved by admin");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to approve PO");
				}
			}
		}catch (SQLException e) {
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
		finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("raisedPurchaseOrderList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response raisedPurchaseOrderList(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id,@FormParam("level") int level,@FormParam("startdate") String startdate,@FormParam("enddate") String enddate){
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
            String auth_id=(String) jsObject.get("auth_id");
            if(role_id==1){
            	
            }else{
            	
            	warehouse_id=auth_id;
            }
			System.out.println(role_id);
		}
		
		String Query=null;
		if(startdate==null||startdate.isEmpty()||enddate==null||enddate.isEmpty()) {
			 Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse1.wareouse_name as warehouse,tbl_warehouse_purchase_order.purchase_requisition_id,"
					+ "	tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.is_admin_approved as status, "
					+ "	tbl_warehouse_purchase_order.delivery_date,case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Invoice Pending' else"
					+ " case when (select 1 from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id and "
					+ "(select sum(q.product_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  limit 1)"
					+ " < (select sum(q.issued_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id) limit 1)=1"
					+ " then case when (select is_QcVerified from tbl_invoice_details where tbl_invoice_details.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id limit 1)=0 "
					+ " then 'Partially Received/Waiting For Qc Approval' else 'Partially Received/Qc Approval done' end else "
					+ " case when (select is_QcVerified from tbl_invoice_details where tbl_invoice_details.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  limit 1)=0 "
					+ " then 'Received/Waiting For Qc Approval' else 'Received/Qc Approval done' end "
					+ " end end as is_admin_approved,tbl_vendor1.vendor_name from tbl_warehouse_purchase_order"
					+ " inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id "
					+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=tbl_warehouse_purchase_order.warehouse_id"
					+ " where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"'  and "
					+ "	is_order_raised=1 order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
		}else {
			 Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,"
					+ "	tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.purchaseorder_status as status, "
					+ "	tbl_warehouse_purchase_order.delivery_date,case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Invoice Pending' else"
					+ " case when (select 1 from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
					+ " and (select sum(q.product_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id)"
					+ " < (select sum(q.issued_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id) limit 1)=1"
					+ " then case when (select is_QcVerified from tbl_invoice_details where tbl_invoice_details.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id limit 1)=0 "
					+ " then 'Partially Received' else 'Partially Received/Qc Approval done' end else "
					+ " case when (select is_QcVerified from tbl_invoice_details where tbl_invoice_details.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id limit 1)=0 "
					+ " then 'Received' else 'Received/Qc Approval done' end "
					+ " end end as is_admin_approved,tbl_vendor1.vendor_name from tbl_warehouse_purchase_order "
					+ " inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id "
					+ "  where tbl_warehouse_purchase_order.mark_for_deletion=0  and "
					+ "	 is_order_raised=1 and tbl_warehouse_purchase_order.purchaseorder_raised_date between '"+startdate+"' and '"+enddate+"' order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
			
		}
		
//		String Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,"
//				+ " tbl_warehouse_purchase_order.purchaseorder_raised_date, tbl_warehouse.warehouse_name,tbl_warehouse_purchase_order.purchaseorder_status as is_admin_approved, "
//				+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor.vendor_name from tbl_warehouse_purchase_order inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id"
//				+ " inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"'"
//				+ " and lower(tbl_warehouse_purchase_order.is_admin_approved)!='pending' and is_order_raised=1 order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
		
		/*String Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,"
				+ " tbl_warehouse_purchase_order.purchaseorder_raised_date, tbl_warehouse_purchase_order.purchaseorder_status as is_admin_approved, "
				+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor.vendor_name from tbl_warehouse_purchase_order "
				+ " inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0 "
				+ " and lower(tbl_warehouse_purchase_order.is_admin_approved)!='pending' and is_order_raised=1 order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
		*/
		

		/*if(level==0){
			Query ="select  distinct tbl_warehouse_purchase_order.purchase_requisition_id as purchaseorder_id,"
					+ "tbl_warehouse_purchase_order.purchaseorder_raised_date, tbl_warehouse_purchase_order.is_admin_approved,"
					+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor_received_purchase_order.invoice_status,"
					+ "tbl_warehouse.warehouse_name,tbl_vendor.vendor_name,tbl_project.project_name "
					+ "from tbl_warehouse_purchase_order "
					+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  "
					+ "inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id "
					+ "inner join tbl_project on tbl_project.project_id=tbl_warehouse_purchase_order.project_name "
					+ "inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id "
					+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
					+ "tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"'  and is_order_raised=0 "
				    + "order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc"; 
		}else{
			Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,"
					+ "tbl_warehouse_purchase_order.purchaseorder_raised_date, tbl_warehouse_purchase_order.is_admin_approved,"
					+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor_received_purchase_order.invoice_status,"
					+ "tbl_warehouse.warehouse_name,tbl_vendor.vendor_name,tbl_project.project_name "
					+ "from tbl_warehouse_purchase_order "
					+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  "
					+ "inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id "
					+ "inner join tbl_project on tbl_project.project_id=tbl_warehouse_purchase_order.project_name "
					+ "inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id "
					+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
					+ "tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"' and lower(tbl_warehouse_purchase_order.is_admin_approved)!='pending' and is_order_raised=1 "
				    + "order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc"; 
		}*/
		
		System.out.println("the query is:" +Query);
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "List is empty");
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
			 e.printStackTrace();
			 jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}
		finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("raisedPurchaseOrderList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response raisedPurchaseOrderList(@FormParam("id") String id,
//			@FormParam("warehouse_id") String warehouse_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//            String auth_id=(String) jsObject.get("auth_id");
//            if(role_id==1){
//            	
//            }else{
//            	
//            	warehouse_id=auth_id;
//            }
//			System.out.println(role_id);
//		}
//		//String Query ="select product_id,product_brand,product_category,product_subcategory,product_name from tbl_addproduct";
////		String Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchaseorder_raised_date, "
////				+ "tbl_warehouse_purchase_order.delivery_date,tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.product_price,"
////				+ "tbl_vendor_received_purchase_order.invoice_status,tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.sgst,"
////				+ "tbl_warehouse1.wareouse_name,tbl_vendor1.vendor_name,tbl_warehouse_purchase_order.discount "
////				+ "from tbl_warehouse_purchase_order "
////				+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
////				+ "inner join tbl_warehouse1 on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
////				+ "inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0";
//		
//		String Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchaseorder_raised_date, "
//				+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor_received_purchase_order.invoice_status,"
//				+ "tbl_warehouse1.wareouse_name,tbl_vendor1.vendor_name from tbl_warehouse_purchase_order "
//				+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  "
//				+ "inner join tbl_warehouse1 on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
//				+ "inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id "
//				+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
//				+ "tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"' "
//			    + "order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc"; 
//		
//		System.out.println("the query is:" +Query);
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				System.out.println(resultset);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//					
//					
//					//System.out.println(arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//              e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//	}
	
	@POST
	@Path("raisedPurchaseOrderListForVendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response raisedPurchaseOrderListForVendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
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
			
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			 String auth_id=(String) jsObject.get("auth_id");
	            if(role_id==1){
	            	
	            }else{
	            	
	            	vendor_id=auth_id;
	            }
			System.out.println(role_id);
		}
//		if(vendor_id==null||vendor_id.isEmpty()){
//			vendor_id="  ";
//		}else{
//			vendor_id=" tbl_warehouse_purchase_order.vendor_id='"+vendor_id+"'   and ";
//		}
		
		/*String Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchaseorder_raised_date, "
				+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor_received_purchase_order.invoice_status,"
				+ "tbl_warehouse.warehouse_name,tbl_vendor.vendor_name,tbl_project.project_name from tbl_warehouse_purchase_order "
				+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id "
				+ "inner join tbl_project on tbl_project.project_id=tbl_warehouse_purchase_order.project_name "
				+ "inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id "
				+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.is_admin_approved='Completed' and "
				+ "tbl_warehouse_purchase_order.vendor_id='"+vendor_id+"'"
				+ "order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";*/
		
		/*String Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,"
				+ " tbl_warehouse_purchase_order.purchaseorder_raised_date, tbl_warehouse.warehouse_name,tbl_warehouse_purchase_order.purchaseorder_status as is_admin_approved, "
				+ "tbl_warehouse_purchase_order.delivery_date, case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Pending' else 'Completed' end as invoice_status,tbl_vendor.vendor_name from tbl_warehouse_purchase_order inner join tbl_warehouse on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse.warehouse_id"
				+ " inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
				+ "  tbl_warehouse_purchase_order.vendor_id='"+vendor_id+"'   and is_order_raised=1 order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";*/
		
		/*String Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,"
				+ " tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.purchaseorder_status as is_admin_approved, "
				+ "tbl_warehouse_purchase_order.delivery_date, case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Pending' else 'Completed' end as invoice_status,tbl_vendor.vendor_name from tbl_warehouse_purchase_order "
				+ " inner join tbl_vendor on tbl_warehouse_purchase_order.vendor_id=tbl_vendor.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
				+ "  "+vendor_id+"   is_order_raised=1 and lower(tbl_warehouse_purchase_order.purchaseorder_status)='pending' order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
		*/
		
		String Query="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchase_requisition_id,tbl_warehouse_purchase_order.warehouse_id,"
				+ "	tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.purchaseorder_status as is_admin_approved,tbl_warehouse1.wareouse_name, "
				+ "	tbl_warehouse_purchase_order.delivery_date,case when tbl_warehouse_purchase_order.isinvoice_generated=false then 'Pending' else"
				+ " case when (select 1 from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id and (select sum(q.product_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id)"
				+ " < (select sum(q.issued_quantity) from tbl_warehouse_purchase_order as q where q.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id))=1"
				+ " then 'Issued' else 'Partially Issued' end end as invoice_status,tbl_vendor1.vendor_name from tbl_warehouse_purchase_order "
				+ "inner join tbl_warehouse1 on tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
				+ " inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0  and tbl_warehouse_purchase_order.is_admin_approved='Approved' and tbl_warehouse_purchase_order.vendor_id='"+vendor_id+"' and "
				+ "	is_order_raised=1 order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
		
		System.out.println("the query is:" +Query);
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(resultset);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
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
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}
		finally{
			try {
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	
	
	
	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("raisedPurchaseOrderListForVendor")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response raisedPurchaseOrderListForVendor(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			 String auth_id=(String) jsObject.get("auth_id");
//	            if(role_id==1){
//	            	
//	            }else{
//	            	
//	            	vendor_id=auth_id;
//	            }
//			System.out.println(role_id);
//		}
//		//String Query ="select product_id,product_brand,product_category,product_subcategory,product_name from tbl_addproduct";
////		String Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchaseorder_raised_date, "
////				+ "tbl_warehouse_purchase_order.delivery_date,tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.product_price,"
////				+ "tbl_vendor_received_purchase_order.invoice_status,tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.sgst,"
////				+ "tbl_warehouse1.wareouse_name,tbl_vendor1.vendor_name,tbl_warehouse_purchase_order.discount "
////				+ "from tbl_warehouse_purchase_order "
////				+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
////				+ "inner join tbl_warehouse1 on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
////				+ "inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id where tbl_warehouse_purchase_order.mark_for_deletion=0";
//		
//		String Query ="select  distinct tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.purchaseorder_raised_date, "
//				+ "tbl_warehouse_purchase_order.delivery_date,tbl_vendor_received_purchase_order.invoice_status,"
//				+ "tbl_warehouse1.wareouse_name,tbl_vendor1.vendor_name from tbl_warehouse_purchase_order "
//				+ "inner join tbl_vendor_received_purchase_order on tbl_vendor_received_purchase_order.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id  "
//				+ "inner join tbl_warehouse1 on  tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
//				+ "inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id "
//				+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and "
//				+ "tbl_warehouse_purchase_order.vendor_id='"+vendor_id+"'"
//				+ "order by tbl_warehouse_purchase_order.purchaseorder_raised_date desc";
//		
//		System.out.println("the query is:" +Query);
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				System.out.println(resultset);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("editRaisedPurchaseOrderList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response ediRaisedPurchaseOrderList(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("level") int level){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println("JS-OBJECT"+jsObject);
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
			System.out.println("ROLE ID"+role_id);
		}
		
		String Query="select tbl_warehouse_purchase_order.purchaseorder_id,tbl_warehouse_purchase_order.product_quantity,tbl_warehouse_purchase_order.purchaseorder_raised_date,"
				+ "tbl_warehouse_purchase_order.product_description,tbl_warehouse_purchase_order.vendor_id,tbl_warehouse1.wareouse_name,tbl_warehouse_purchase_order.warehouse_id, "
				+ "tbl_warehouse_purchase_order.billing_address,tbl_warehouse_purchase_order.shipping_address,"
				+ "(select product_price from tbl_vendor_pricing_table where vendor_id=tbl_warehouse_purchase_order.vendor_id and "
				+ "product_id=tbl_warehouse_purchase_order.product_id )price,(select discount from tbl_vendor_pricing_table "
				+ "where vendor_id=tbl_warehouse_purchase_order.vendor_id and product_id=tbl_warehouse_purchase_order.product_id )discount,"
				+ "tbl_warehouse_purchase_order.is_admin_approved as status,"
				+ "tbl_products.product_cgst,tbl_products.product_sgst,tbl_products.product_igst,tbl_warehouse_purchase_order.purchaseorder_status,"
				+ "tbl_products.product_name,tbl_vendor1.vendor_name,tbl_vendor1.master_type_id,tbl_vendor1.member_id,tbl_warehouse_purchase_order.product_id from tbl_warehouse_purchase_order "
				+ "inner join tbl_vendor1 on tbl_warehouse_purchase_order.vendor_id=tbl_vendor1.vendor_id "
				+ "inner join tbl_products on tbl_warehouse_purchase_order.product_id=tbl_products.product_id "
				+ "inner join tbl_warehouse1 on tbl_warehouse_purchase_order.warehouse_id=tbl_warehouse1.warehouse_id "
				+ "where tbl_warehouse_purchase_order.mark_for_deletion=0 and tbl_warehouse_purchase_order.purchaseorder_id='"+purchaseorder_id+"'";
		
		System.out.println("the query is:" +Query);
		String Query1="select balance,opening_balance,(select coalesce(sum(grand_total),0) from with_hold where is_pending=0) as hold_amount from gendral_ledger order by created_date desc limit 1";
		try {
			SqlConnection();
			try {
				double grand_total=0,balance=0,withhold_balance=0,opening_balance=0;
				System.out.println("QUERY"+Query);
				ResultSet resultSet=statement.executeQuery(Query);
				ArrayList resultsetArray=new ArrayList(); 
				while (resultSet.next()) {
					int total_rows = resultSet.getMetaData().getColumnCount();
					Map map=new HashMap();
					for (int i = 0; i <total_rows; i++) {
						String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
						if(columnName.contains("product_price")){
							String columnValue = resultSet.getString(i + 1);
							System.out.println("columnValue= "+columnValue);
							if(columnValue.contains("$")){
								columnValue=columnValue.replace("$","");
							}
							map.put(columnName,columnValue);
						}
						else if(columnName.contains("cgst")||columnName.contains("sgst")||columnName.contains("igst")){
							
							String columnValue = resultSet.getString(i+1);
							
							System.out.println("columnValue= "+columnValue);
							if(columnValue.contains("%")){
								columnValue=columnValue.replace("%","");
							}
							map.put(columnName,columnValue);
						}
						else{
							Object columnValue = resultSet.getObject(i + 1);
							map.put(columnName,columnValue);
						}
						//Object columnValue = resultSet.getObject(i + 1);
					}
					
					double totalprice=0;
					double qty=  (double) map.get("product_quantity");
					double cgst=Double.parseDouble( (String) map.get("product_cgst"));
					double sgst=Double.parseDouble((String) map.get("product_sgst"));
					double igst= Double.parseDouble((String) map.get("product_igst"));
					double price= (double) map.get("price");
					totalprice=qty*(((price*(cgst+sgst+igst))/100)+price);
					map.put("product_total",totalprice);
					grand_total=grand_total+totalprice;
					//balance=(double) map.get("balance");
					resultsetArray.add(map);
				}
				
				ResultSet resultSet1 = statement.executeQuery(Query1);
				while(resultSet1.next()){
					balance=resultSet1.getDouble(1);
					opening_balance=resultSet1.getDouble(2);
					withhold_balance=resultSet1.getDouble(3);
				}
				
//				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				if(resultsetArray!=null&&!resultsetArray.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", resultsetArray);
					jsonObject.put("grandtotal", grand_total);
					jsonObject.put("balance", balance);
					jsonObject.put("WithHold_Balance", (grand_total-withhold_balance));
					jsonObject.put("Opening_Balance",opening_balance);
					
					//System.out.println(arrayList);
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
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}
		finally{
			try {
				//resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
	
	
	
	@SuppressWarnings("unchecked")
	@Path("/product_calculations")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
//	public Response calculate_invoice(@FormParam("id")String id,@FormParam("purchaseorder_id")
//	String purchaseorder_id,@FormParam("invoice_id")String invoice_id,@FormParam("product_details")String ){
		public Response calculate_invoice(Invoice invoice){
			String id =invoice.getId();
			HashMap<String, Object> jsonObject=new HashMap<>();
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
				//@SuppressWarnings("unused")
				String email=(String) jsObject.get("email");
				@SuppressWarnings("unused")
				int role_id=(int) jsObject.get("role_id");
				String purchaseorder_id=invoice.getPurchaseorder_id();
				String invoiceid = invoice.getInvoice_id();
				String ProductDetails=invoice.getProduct_details();
				String type=invoice.getType();
				org.json.JSONArray product_details=null;
				try {
					product_details = new org.json.JSONArray(ProductDetails);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Connection connectionWar=null;
				try {
					 connectionWar=DBConnection.SqlConnection();
					double grand_total=0;
					double total_discount=0;
					double total_tax=0;
					double totalDiscountInAmount=0;
					double totalTaxInAmount=0;
					double totalOrginalAmount=0;
					for(int i=0;i<product_details.length();i++){
						try {
							org.json.JSONObject productObject=product_details.getJSONObject(i);
							int product_quantity =productObject.getInt("product_quantity");
							String product_id = productObject.getString("product_id");
							double CGST=0;
							double SGST=0;
							double IGST=0;
							
							
						if(type!=null && !type.toLowerCase().equals("v")){
						
							PreparedStatement preparedStatement=connectionWar.prepareStatement(Config.gst_product);
							preparedStatement.setString(1,product_id);
							ResultSet resultSet=preparedStatement.executeQuery();
							System.out.println("from database");

							while(resultSet.next()){
							 CGST=resultSet.getDouble("product_cgst");
							 SGST=resultSet.getDouble("product_sgst");
							 IGST=resultSet.getDouble("product_igst");
							}
							}
						else{
							 CGST=productObject.getDouble("CGST");
							 SGST=productObject.getDouble("SGST");
							 IGST=productObject.getDouble("IGST");
						}
							double price=productObject.getDouble("price");
							double discount=productObject.getDouble("discount");
							if(CGST!=0 && SGST!=0){
								double origenal_amount=price *product_quantity;
									double cgst_amount =(origenal_amount*CGST)/100;
									double sgst_amount =(origenal_amount*SGST)/100;
									
									double before_discount_net_amount=cgst_amount + sgst_amount+origenal_amount;
									double discount_net_amount =(before_discount_net_amount*discount)/100;
									double net_amount =before_discount_net_amount-discount_net_amount;
									productObject.put("cgst_amount", cgst_amount);
									productObject.put("sgst_amount", sgst_amount);
									productObject.put("gst_type", "s");
									productObject.put("before_discount_net_amount", before_discount_net_amount);
									productObject.put("discount_amount", discount_net_amount);
									productObject.put("net_amount", net_amount);
									productObject.put("origenal_amount", origenal_amount);
									product_details.put(i, productObject);
									grand_total=net_amount+grand_total;
									totalTaxInAmount=(cgst_amount+sgst_amount)+totalTaxInAmount;
									total_tax=(CGST+SGST)+total_tax;
								    total_discount=discount+total_discount;
								    totalDiscountInAmount=discount_net_amount+totalDiscountInAmount;
								    totalOrginalAmount=origenal_amount+totalOrginalAmount;
									
							}
							else if(IGST!=0){
								double origenal_amount=price *product_quantity;
								double igst_amount =(origenal_amount*IGST)/100;
								double before_discount_net_amount=igst_amount+origenal_amount;
								double discount_net_amount =(before_discount_net_amount*discount)/100;
								double net_amount =before_discount_net_amount-discount_net_amount;
								productObject.put("igst_amount", igst_amount);
								productObject.put("gst_type", "c");
								productObject.put("before_discount_net_amount", before_discount_net_amount);
								productObject.put("discount_amount", discount_net_amount);
								productObject.put("net_amount", net_amount);
								productObject.put("origenal_amount", origenal_amount);
								product_details.put(i, productObject);
								grand_total=net_amount+grand_total;
								totalTaxInAmount=igst_amount+totalTaxInAmount;
								total_tax=IGST+total_tax;
							    total_discount=discount+total_discount;
							    totalDiscountInAmount=discount_net_amount+totalDiscountInAmount;
							    totalOrginalAmount=origenal_amount+totalOrginalAmount;
							}
							else{
								double origenal_amount=price *product_quantity;
								double discount_net_amount =(origenal_amount*discount)/100;
								double net_amount =origenal_amount-discount_net_amount;
								productObject.put("before_discount_net_amount", origenal_amount);
								productObject.put("discount_amount", discount_net_amount);
								productObject.put("gst_type", "n");
								productObject.put("net_amount", net_amount);
								productObject.put("origenal_amount", origenal_amount);
								product_details.put(i, productObject);	
								
								grand_total=net_amount+grand_total;
							    total_discount=discount+total_discount;
							    totalDiscountInAmount=discount_net_amount+totalDiscountInAmount;
							    totalOrginalAmount=origenal_amount+totalOrginalAmount;
							}
							
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					JSONObject invoiceData= new JSONObject();
					invoiceData.put("product_details", product_details);
					invoiceData.put("Grand_total", grand_total);
					invoiceData.put("total_tax", total_tax);
					invoiceData.put("totalTaxInAmount", totalTaxInAmount);
					invoiceData.put("total_discount", total_discount);
					invoiceData.put("totalDiscountInAmount", totalDiscountInAmount);
					invoiceData.put("totalOrginalAmount", totalOrginalAmount);
					
					PGobject pGobject = new PGobject();
					pGobject.setType("json");
					pGobject.setValue(invoiceData.toString());

					PreparedStatement preparedStatement=connectionWar.prepareStatement(Config.calculate_invoice);
					preparedStatement.setString(1,email);
					preparedStatement.setString(2,invoiceid);
					preparedStatement.setObject(3,pGobject);
					preparedStatement.setString(4,purchaseorder_id);
					preparedStatement.execute();
					HashMap<String,Object> hashMap=new HashMap<>();
					jsonObject.put("status","success");
					
					hashMap.put("product_details",product_details.toString());
					hashMap.put("grand_total", grand_total);
					hashMap.put("total_tax", total_tax);
					hashMap.put("totalTaxInAmount", totalTaxInAmount);
					hashMap.put("total_discount", total_discount);
					hashMap.put("totalDiscountInAmount", totalDiscountInAmount);
					hashMap.put("totalOrginalAmount", totalOrginalAmount);
//					Gson gson = new Gson(); 
//					String json = gson.toJson(hashMap); 
//					HashMap<String,Object> map=new HashMap<>();
//					map.put("data",hashMap);
					jsonObject.put("data",hashMap);
					//jsonObject.putAll(map);
					
					
					} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObject.put("status", "failed");
					jsonObject.put("message", e.getMessage()+"");
				}
				
				finally {
					try {
						DBConnection.closeConnection(connectionWar);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
		
				}
		
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addInvoicedata")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addInvoicedata(@FormParam("id") String id,
			@FormParam("invoice_id") String invoice_id,
			@FormParam("purchaseorder_id") String purchaseorder_id,
			@FormParam("warehouse_id") String warehouse_id,
			@FormParam("vendor_id") String vendor_id){
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
			@SuppressWarnings("unused")
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connectionInv=null;
		String auth_id=(String) jsObject.get("auth_id");
		int role_id=(int) jsObject.get("role_id");
		if(role_id==2){
			vendor_id=auth_id;
		}
		try{
			String email=(String) jsObject.get("email");
			connectionInv = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionInv.prepareStatement(Config.add_Invoice);
			preparedStatement.setString(1,invoice_id);
			preparedStatement.setString(2,purchaseorder_id);
			preparedStatement.setString(3,email);
			preparedStatement.setString(4,vendor_id);
			preparedStatement.setString(5,warehouse_id);
			@SuppressWarnings("unused")
			ResultSet resultSet=preparedStatement.executeQuery();
			jsonObject.clear();
			jsonObject.put("status", "Success");
			jsonObject.put("message", "Invoice Generated Successfully");
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Generate Invoice Failed");
		}
		finally{
			try {
				connectionInv.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@POST
	@Path("/warehouseStockCount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response warehouseStockCount(@FormParam("id") String id){
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
			@SuppressWarnings("unused")
			int role_id=(int) jsObject.get("role_id");
		}
		Connection connectionStock=null;
		try{
		String email=(String) jsObject.get("email");
		connectionStock=DBConnection.SqlConnection();
		//PreparedStatement preparedStatement = connectionStock.prepareStatement(Config.)
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				connectionStock.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Origin", "POST").build();
	}
	
	public static void SqlConnection() throws IOException {
		// TODO Auto-generated method stub
		Properties prop=new Properties();

		String filename="dbconnection.properties";

		InputStream input=New.class.getClassLoader().getResourceAsStream(filename);

		prop.load(input);

		try {

			Class.forName(prop.getProperty("db.classname"));

		} catch (ClassNotFoundException cnfexception) {

			cnfexception.printStackTrace();

		}  

		try {

			connection = DriverManager.getConnection(prop.getProperty("db.drivername"),prop.getProperty("db.username"),prop.getProperty("db.password"));

		} catch (SQLException sqlException) {

			sqlException.printStackTrace();

		}  

		try {

			statement=connection.createStatement();			

		} catch (SQLException sqlException) {

			sqlException.printStackTrace();

		}  
		
	}	
	
	public static String getProductId(String id){

		String numberrange_function="select fn_purchaseorder_id_numberrange_function('"+id+"')";

		int Rid=0;

		try{
			System.out.println(numberrange_function);
			CallableStatement CALLABLESTATMENT=connection.prepareCall(numberrange_function);

			CALLABLESTATMENT.execute();

			ResultSet resultset=CALLABLESTATMENT.getResultSet();

			while(resultset.next()){

				Rid=resultset.getInt(1);

			}if(Rid<0){

				return "Failed 0";

			}

			System.out.println("Rid :"+Rid);

			String ReqId=id+"000";

			String s=String.valueOf(Rid);

			ReqId=ReqId.substring(0,ReqId.length()-s.length());

			ReqId=ReqId.concat(String.valueOf(Rid));

			return ReqId;

		}catch(Exception exc){

			System.out.println(exc.getMessage());

			return "Failed";

		}

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("product_price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}
				else if(columnName.contains("cgst")||columnName.contains("sgst")||columnName.contains("igst")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("%")){
						columnValue=columnValue.replace("%","");
					}
					map.put(columnName,columnValue);
				}
				else{
					Object columnValue = resultSet.getObject(i + 1);
					map.put(columnName,columnValue);
				}
				//Object columnValue = resultSet.getObject(i + 1);
				
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}
	
	

}
