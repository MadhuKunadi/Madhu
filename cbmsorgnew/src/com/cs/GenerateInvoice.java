package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("generateInvoice")
public class GenerateInvoice {
	
	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	 static JSONArray jsonarray=new JSONArray();
	 static JSONObject jsonobject=new JSONObject();
	 @SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(GenerateInvoice.class);

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getPurchaseOrderListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getPurchaseOrderList(@FormParam("id") String id,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);String subQuery=new String();
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
			if(role_id==2){
				subQuery="where purchase_order.vendor_id=(select vendor_id from tbl_vendor where vendor_email='"+jsObject.get("email")+"')";
			}else if(role_id==1){
				subQuery=" ";
			}
			else{
				jsonObject.clear();
				jsonObject.put("status", "Failed");
				jsonObject.put("message", "You don't have permissions");
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
		}
		//String Query ="select id,purchaseorder_date,warehouse_name,purchaseorder_id from purchase_order";
		String Query ="select distinct purchase_order.purchaseorder_id,purchase_order.purchaseorder_date,purchase_order.isinvoice_generated,purchase_order.delivery_date,purchase_order.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,purchase_order.vendor_id,"
				+ " tbl_vendor.vendor_name from purchase_order inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order.warehouse_name "
				+ " inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order.vendor_id "+subQuery;
		 //ORDER BY purchaseorder_date desc
			//	+ " where purchaseorder_id='"+purchaseorder_id+"';
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("/deletPurchaseOrderforVendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletepurchaseorderforvendor(@FormParam("id") String id,@FormParam("roll_id") String roll_id){
		JSONObject jsonObject=new JSONObject();
		
		if(roll_id==null || roll_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="DELETE from purchase_order where id='"+roll_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");
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
	@Path("/getPurchaseListforVendor")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productsListVendor(@FormParam("id") String id,@FormParam("purchaseorder_id") String purchaseorder_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		//String Query ="select product_description,product_unit,product_quantity from purchase_order where purchaseorder_id='"+purchaseorder_id+"'";
		//String Query ="SELECT DISTINCT purchaseorder_id,purchaseorder_date,warehouse_name FROM purchase_order ORDER BY purchaseorder_id";
		
		String Query ="select tbl_warehouse_purchase_order.id,tbl_warehouse_purchase_order.warehouse_id "
				+ "as warehouse_id,tbl_warehouse1.wareouse_name,tbl_warehouse_purchase_order.vendor_id,"
				+ "tbl_vendor1.vendor_name,tbl_warehouse_purchase_order.product_description,"
				+ "tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,"
				+ "tbl_warehouse_purchase_order.purchaseorder_status from tbl_warehouse_purchase_order "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=tbl_warehouse_purchase_order.warehouse_id"
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_warehouse_purchase_order.vendor_id "
				+ "where tbl_warehouse_purchase_order.purchaseorder_id='"+purchaseorder_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/generateInvoiceInsertion")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generateinvoice(@FormParam("token") String token,@FormParam("invoiceid") String invoiceid, @FormParam("products") String products,@FormParam("purchaseorder_id") String purchaseorder_id,@FormParam("total_value") String total_value,@FormParam("total_discount")
	String total_discount,@FormParam("total_tax") String total_tax,@FormParam("invoice_total") String invoice_total,@FormParam("invoice_status") String invoice_status){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(token);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			
			String invoicInsertQuery= "insert into tbl_generate_invoice(invoice_id,purchase_id,total_value,total_discount,total_tax,total,invoice_status) "
										+ "values('"+invoiceid+"','"+purchaseorder_id+"','"+total_value+"','"+total_discount+"','"+total_tax+"','"+invoice_total+"','"+"Completed"+"')";
			int insertStatus=statement.executeUpdate(invoicInsertQuery);
			if(insertStatus>0){
				
			}else{
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Failed to insert invoice");
				 return Response.ok()
							.entity(jsonObject)
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
			}
			org.json.JSONArray jsArray = new org.json.JSONArray(products);
			for(int i=0;i<jsArray.length();i++){
				org.json.JSONObject orgjsObject=jsArray.getJSONObject(i);
				String product_description 	     =orgjsObject.getString("product_description");
				String product_unit 		     =orgjsObject.getString("product_unit");
				int product_quantity 		     = orgjsObject.getInt("product_quantity");
				String edate        			 =orgjsObject.getString("edate");
				String mdate                     =orgjsObject.getString("mdate");
				int price                        = orgjsObject.getInt("price");
				int CGST                         =orgjsObject.getInt("CGST");
				int SGST                         =orgjsObject.getInt("SGST");
				int IGST                         = orgjsObject.getInt("IGST");
				int discount                     =orgjsObject.getInt("discount");
				int total                        =orgjsObject.getInt("total");
				int id                           =orgjsObject.getInt("id");
				String pr		= Integer.toString(price);
				String cgst		= Integer.toString(CGST);
				String sgst		= Integer.toString(SGST);
				String igst		= Integer.toString(IGST);
				String dis		= Integer.toString(discount);
				String tot		= Integer.toString(total);
				
				String Query="UPDATE purchase_order SET product_description='"+product_description+"',product_unit='"+ product_unit+"',product_quantity='"+product_quantity+"',"
						+ "expirydate='"+edate+"',manufacturingdate='"+mdate+"',discount='"+dis+"',price='"+pr+"',"
								+ "cgst='"+cgst+"',sgst='"+sgst+"',igst='"+igst+"',product_total='"+tot+"',isinvoice_generated='true' where id='"+id+"'";
				System.out.println("query is:"+Query);
				int st=statement.executeUpdate(Query);
				if(st>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	
	@SuppressWarnings("unchecked")
		@POST
		@Path("/generateInvoiceId")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.APPLICATION_JSON)
		public static Response generatePurcaseOrderId(@FormParam("id") String id){
	//		,@FormParam("warehouse_name") String warehouse_name
			JSONObject jsonObject=new JSONObject();
			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
			System.out.println(jsObject);String subQuery=new String();
			if(jsObject.containsKey("status")){
				jsonObject.clear();
				jsonObject.put("status", "Failed");
				jsonObject.put("message", jsObject.get("status"));
				return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
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
					String generateInvoiceid= Customer.getGenerateId("INVOICE",7,connection);
					System.out.println("the value is :"+generateInvoiceid);
					if(generateInvoiceid!=null&&!generateInvoiceid.isEmpty()){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Data", generateInvoiceid);
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
				//	statement.close();
					connection.close();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
			 return Response.ok()
						.entity(jsonObject)
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}


	public static void SqlConnection() throws IOException {
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				if(columnName.contains("purchaseorder_date")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Timestamp ts = resultSet.getTimestamp("purchaseorder_date");
					map.put(columnName, sdf.format(ts));
				}else{
					
				map.put(columnName,columnValue);
				}
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}	
	
	
	
	
	
	/*
	 * public static String getInvoiceId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_invoice_id_numberrange_function('"+id+"')";
	 * 
	 * int Rid=0;
	 * 
	 * try{ System.out.println(numberrange_function); CallableStatement
	 * CALLABLESTATMENT=connection.prepareCall(numberrange_function);
	 * 
	 * CALLABLESTATMENT.execute();
	 * 
	 * ResultSet resultset=CALLABLESTATMENT.getResultSet();
	 * 
	 * while(resultset.next()){
	 * 
	 * Rid=resultset.getInt(1);
	 * 
	 * }if(Rid<0){
	 * 
	 * return "Failed 0";
	 * 
	 * }
	 * 
	 * System.out.println("Rid :"+Rid);
	 * 
	 * String ReqId=id+"000";
	 * 
	 * String s=String.valueOf(Rid);
	 * 
	 * ReqId=ReqId.substring(0,ReqId.length()-s.length());
	 * 
	 * ReqId=ReqId.concat(String.valueOf(Rid));
	 * 
	 * return ReqId;
	 * 
	 * }catch(Exception exc){
	 * 
	 * System.out.println(exc.getMessage());
	 * 
	 * return "Failed";
	 * 
	 * }
	 * 
	 * }
	 */
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getPurchaseInvoiceList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchaseInvoiceList(@FormParam("id") String id){
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
			System.out.println(role_id);
		}
		String auth_id=(String) jsObject.get("auth_id");
		//String Query ="select * from purchase_order";
		//String Query ="select distinct tbl_generate_invoice.id,tbl_generate_invoice.purchase_id,tbl_generate_invoice.invoice_id from tbl_generate_invoice ";

		String Query ="select distinct tbl_generate_invoice1.id,tbl_generate_invoice1.purchaseorder_id,"
				+ "tbl_generate_invoice1.invoice_id,tbl_warehouse_purchase_order.warehouse_id as warehouse_id,"
				+ "tbl_warehouse1.wareouse_name,tbl_generate_invoice1.created_date,tbl_vendor1.vendor_name "
				+ "from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=tbl_warehouse_purchase_order.warehouse_id "
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_warehouse_purchase_order.vendor_id "
				+ "where tbl_warehouse_purchase_order.vendor_id='"+auth_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
	@Path("/getPurchaseInvoiceCompleteList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchaseInvoiceCompleteList(@FormParam("id") String id,@FormParam("invoice_id") String invoice_id){
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
			System.out.println(role_id);
		}
		String Grand_total="select purchaseorder_id, invoice_generated_date, total as grand_total, "
				+ "total_tax as t_tax, total_discount as t_discount, totaltaxinamount as total_tax_in_amount, "
				+ "totaldiscountinamount as total_discount_in_amount, "
				+ "total_value from tbl_generate_invoice1 where invoice_id='"+invoice_id+"'";
		
		String Query ="select tbl_generate_invoice1.id,tbl_generate_invoice1.purchaseorder_id,"
				+ "tbl_generate_invoice1.invoice_id,tbl_generate_invoice1.created_date as invoice_generated_date,"
				+ "tbl_warehouse_purchase_order.purchaseorder_raised_date,tbl_warehouse_purchase_order.billing_address,"
				+ "tbl_warehouse_purchase_order.shipping_address,tbl_warehouse_purchase_order.product_description,"
				+ "tbl_warehouse_purchase_order.product_unit,tbl_warehouse_purchase_order.product_quantity,"
				+ "tbl_warehouse_purchase_order.purchaseorder_status,tbl_warehouse_purchase_order.product_total,"
				+ "tbl_warehouse1.gst_number as warehouse_gst,tbl_vendor1.gst_number as vendor_gst,"
				+ "tbl_warehouse_purchase_order.cgst,tbl_warehouse_purchase_order.igst,tbl_warehouse_purchase_order.sgst,"
				+ "tbl_warehouse_purchase_order.discount,tbl_warehouse_purchase_order.manufacture_date,"
				+ "tbl_warehouse_purchase_order.expiry_date,tbl_warehouse_purchase_order.product_price,"
				+ "tbl_generate_invoice1.total from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "inner join tbl_warehouse1 on tbl_warehouse1.warehouse_id=tbl_warehouse_purchase_order.warehouse_id "
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_warehouse_purchase_order.vendor_id "
				+ "where tbl_generate_invoice1.invoice_id='"+invoice_id+"'";
		try {
			SqlConnection();
			try {
				ResultSet resultSet=statement.executeQuery(Grand_total);
				JSONObject resJsonObject=convertResultSetIntoJSONObject(resultSet);
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				System.out.println(arrayList);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Grand_total", resJsonObject);
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

	@SuppressWarnings("unchecked")
	@POST
	@Path("/generatedInvoiceListforWarehouse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatedInvoiceListforWarehouse(@FormParam("id") String id,@FormParam("warehouse_id") String warehouse_id){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		@SuppressWarnings("unused")
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
			 String auth_id=(String) jsObject.get("auth_id");
	            if(role_id==1){
	            	
	            }else{
	            	
	            	warehouse_id=auth_id;
	            }
			System.out.println(role_id);
		}
		
		String Query = "select distinct tbl_generate_invoice1.id,tbl_generate_invoice1.purchaseorder_id,tbl_generate_invoice1.invoice_id,"
				+ "tbl_warehouse_purchase_order.vendor_id,tbl_vendor1.vendor_name,tbl_warehouse_purchase_order.purchaseorder_raised_date,"
				+ "(select count(*) from tbl_warehouse_purchase_order where purchaseorder_id=tbl_generate_invoice1.purchaseorder_id "
				+ "and purchaseorder_status='Pending') as viewProductCount,(select count(*) from tbl_warehouse_purchase_order where purchaseorder_id=tbl_generate_invoice1.purchaseorder_id  "
				+ "and purchaseorder_status='Approved') as approvedCount,(select count(*) from tbl_warehouse_purchase_order where purchaseorder_id=tbl_generate_invoice1.purchaseorder_id "
				+ "and purchaseorder_status='Rejected') as RejectCount from tbl_generate_invoice1 "
				+ "inner join tbl_warehouse_purchase_order on tbl_generate_invoice1.purchaseorder_id=tbl_warehouse_purchase_order.purchaseorder_id "
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id=tbl_warehouse_purchase_order.vendor_id where tbl_warehouse_purchase_order.warehouse_id='"+warehouse_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoJSON(resultset);
				
				System.out.println(arrayList);
				
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", arrayList);
					System.out.println(arrayList);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "List is empty");
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
				statement.close();
				resultset.close();
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}	
	
	public static JSONObject convertResultSetIntoJSONObject(ResultSet resultSet) throws Exception {
		JSONObject jsObject=new JSONObject(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				if(columnName.contains("total_value")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}else{
					
				}
				Object columnValue = resultSet.getObject(i + 1);
				jsObject.put(columnName,columnValue);
				
			}
		}
		return jsObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("product_price")||columnName.contains("product_total")){
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
