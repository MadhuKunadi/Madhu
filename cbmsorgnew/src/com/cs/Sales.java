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
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hr.helper.HelperClass;
import com.hr.main.MyObject;

@Path("sales")

public class Sales {

	
	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Sales.class);
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createSalesOrderline")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response createSalesOrderline(@FormParam("id") String id,@FormParam("order_id") String order_id,@FormParam("name") String name,@FormParam("invoice_status") String invoice_status,@FormParam("price_unit")
	String price_unit,@FormParam("price_subtotal") String price_subtotal,@FormParam("price_tax") String price_tax,@FormParam("price_reduce") String price_reduce,@FormParam("price_reduce_taxinc") String price_reduce_taxinc,@FormParam("price_reduce_taxexcl") String price_reduce_taxexcl,
	@FormParam("product_id") String product_id,@FormParam("product_uom_qty") String product_uom_qty,@FormParam("product_uom") String product_uom,@FormParam("qty_delivered") String qty_delivered,@FormParam("qty_to_invoice") String qty_to_invoice,
	@FormParam("qty_invoiced") String qty_invoiced,@FormParam("salesman_id") String salesman_id,@FormParam("currency_id") String currency_id,@FormParam("company_id") String company_id,@FormParam("state") String state,
	@FormParam("price_total") String price_total,@FormParam("is_downpayment") String is_downpayment,@FormParam("order_partner_id") String order_partner_id,@FormParam("customer_lead") String customer_lead,@FormParam("amt_to_invoice") String amt_to_invoice,
	@FormParam("amt_invoiced") String amt_invoiced,@FormParam("sequence") String sequence,@FormParam("layout_category_sequence") String layout_category_sequence,@FormParam("product_packaging") String product_packaging,@FormParam("discount") String discount,
	@FormParam("route_id") String route_id,@FormParam("layout_category_id") String layout_category_id){
		//JSONObject jsonObject=new JSONObject();
		if(order_id==null || order_id.isEmpty()||
				name==null || name.isEmpty()|| invoice_status==null || invoice_status.isEmpty()
				|| price_unit==null || price_unit.isEmpty()|| price_subtotal==null || price_subtotal.isEmpty()|| name==null || name.isEmpty()
				|| product_id==null || product_id.isEmpty()|| route_id==null || route_id.isEmpty()|| sequence==null || sequence.isEmpty()
				|| qty_invoiced==null || qty_invoiced.isEmpty()|| price_tax==null || price_tax.isEmpty() || price_reduce==null || price_reduce.isEmpty()|| layout_category_sequence==null || layout_category_sequence.isEmpty()
				|| price_total==null || price_total.isEmpty()|| product_uom_qty==null || product_uom_qty.isEmpty()|| product_uom==null || product_uom.isEmpty()|| price_reduce_taxinc==null || price_reduce_taxinc.isEmpty()|| product_packaging==null || product_packaging.isEmpty()|| discount==null || discount.isEmpty()
				|| invoice_status==null || invoice_status.isEmpty()|| salesman_id==null || salesman_id.isEmpty()|| currency_id==null || currency_id.isEmpty()|| qty_delivered==null || qty_delivered.isEmpty()|| customer_lead==null || customer_lead.isEmpty()|| qty_to_invoice==null || qty_to_invoice.isEmpty()|| price_reduce_taxexcl==null || price_reduce_taxexcl.isEmpty()
				|| layout_category_id==null || layout_category_id.isEmpty()|| amt_invoiced==null || amt_invoiced.isEmpty()|| amt_invoiced==null || amt_invoiced.isEmpty()|| is_downpayment==null || is_downpayment.isEmpty()|| order_partner_id==null || order_partner_id.isEmpty()|| company_id==null || company_id.isEmpty()|| state==null || state.isEmpty()|| amt_to_invoice==null || amt_to_invoice.isEmpty()){
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
				String Query="insert into sale_order_line(layout_category_id,name,price_unit,order_id,price_subtotal,product_id,qty_invoiced,price_total,amt_invoiced,route_id,price_tax,product_uom_qty,"
						+ "salesman_id,is_downpayment,sequence,price_reduce,product_uom,currency_id,order_partner_id,layout_category_sequence,price_reduce_taxinc,qty_delivered,company_id,customer_lead,product_packaging,price_reduce_taxexcl,qty_to_invoice,state,amt_to_invoice,discount) "
						+ "values('"+layout_category_id+"','"+name+"','"+order_id+"','"+price_unit+"','"+price_subtotal+"','"+product_id+"','"+qty_invoiced+"','"+price_total+"','"+amt_invoiced+"','"+route_id+"','"+price_tax+"','"+product_uom_qty+"',"
						+ "'"+salesman_id+"','"+is_downpayment+"','"+sequence+"','"+price_reduce+"','"+product_uom+"','"+currency_id+"','"+order_partner_id+"','"+layout_category_sequence+"','"+price_reduce_taxinc+"','"+qty_delivered+"','"+company_id+"','"+customer_lead+"','"+product_packaging+"',"
						+ "'"+price_reduce_taxexcl+"','"+qty_to_invoice+"','"+state+"','"+amt_to_invoice+"','"+discount+"')";
				
				 System.out.println(Query);
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
	@Path("/getSalesOrderline")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getCustomer(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from sale_order_line";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
				
			}catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createSalesOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response createSalesOrder(@FormParam("id") String id,@FormParam("order_id") String order_id,@FormParam("name") String name,@FormParam("order_date")String order_date,@FormParam("discount") String discount,
			@FormParam("payment_type") String payment_type,@FormParam("origin") String origin,@FormParam("state") String state,@FormParam("confirmation_date") String confirmation_date,@FormParam("invoice_status") String invoice_status,
	@FormParam("note") String note,@FormParam("untaxed_amount") String untaxed_amount,@FormParam("tax_amount") String tax_amount,@FormParam("total_amount") String total_amount){
		JSONObject jsonObject=new JSONObject();
//		if(order_id==null || order_id.isEmpty()||
//				name==null || name.isEmpty()|| invoice_status==null || invoice_status.isEmpty()
//				|| price_unit==null || price_unit.isEmpty()|| price_subtotal==null || price_subtotal.isEmpty()|| name==null || name.isEmpty()
//				|| product_id==null || product_id.isEmpty()|| route_id==null || route_id.isEmpty()|| sequence==null || sequence.isEmpty()
//				|| qty_invoiced==null || qty_invoiced.isEmpty()|| price_tax==null || price_tax.isEmpty() || price_reduce==null || price_reduce.isEmpty()|| layout_category_sequence==null || layout_category_sequence.isEmpty()
//				|| price_total==null || price_total.isEmpty()|| product_uom_qty==null || product_uom_qty.isEmpty()|| product_uom==null || product_uom.isEmpty()|| price_reduce_taxinc==null || price_reduce_taxinc.isEmpty()|| product_packaging==null || product_packaging.isEmpty()|| discount==null || discount.isEmpty()
//				|| invoice_status==null || invoice_status.isEmpty()|| salesman_id==null || salesman_id.isEmpty()|| currency_id==null || currency_id.isEmpty()|| qty_delivered==null || qty_delivered.isEmpty()|| customer_lead==null || customer_lead.isEmpty()|| qty_to_invoice==null || qty_to_invoice.isEmpty()|| price_reduce_taxexcl==null || price_reduce_taxexcl.isEmpty()
//				|| layout_category_id==null || layout_category_id.isEmpty()|| amt_invoiced==null || amt_invoiced.isEmpty()|| amt_invoiced==null || amt_invoiced.isEmpty()|| is_downpayment==null || is_downpayment.isEmpty()|| order_partner_id==null || order_partner_id.isEmpty()|| company_id==null || company_id.isEmpty()|| state==null || state.isEmpty()|| amt_to_invoice==null || amt_to_invoice.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
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
		try {
			SqlConnection();
			String sales_order_id=Customer.getGenerateId("SALE",3,connection);
			try {
				String Query="insert into sale_order(order_id,name,date_order,discount,payment_type,origin,state,confirmation_date,invoice_status,note,amount_untaxed,amount_tax,amount_total) "
						+ "values('"+sales_order_id+"','"+name+"','"+order_date+"','"+discount+"','"+payment_type+"','"+origin+"','"+state+"','"+confirmation_date+"','"+invoice_status+"','"+note+"',"
						+ "'"+untaxed_amount+"','"+tax_amount+"','"+total_amount+"')";
				
				 System.out.println(Query);
				int i=statement.executeUpdate(Query);
				
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
	@Path("/getSalesOrderlist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getSalesOrderlist(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}String Query="select * from sale_order";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@POST
	@Path("/storeAddress")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response storeAddress(@FormParam("id") String id){
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
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id = (String) jsObject.get("auth_id");
						}
		String auth_id = (String) jsObject.get("auth_id");

		String Query ="select  store_address1,store_address2 from tbl_store where store_id='"+auth_id+"'";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
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
				connection.close();
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
//	@SuppressWarnings("unused")
//	@Path("salesReport")
//	public class SalesReport {
//	static JSONArray jsonArray = new JSONArray();
//	static JSONObject jsonObject = new JSONObject();
//
////	@Context private static HttpServletRequest resquest;
////	@Context private static HttpServletResponse response;
//
//	private static Log log=LogFactory.getLog(SalesReport.class);

	@SuppressWarnings({"unchecked", "rawtypes"})
	@POST
	@Path("returnSalesReport")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response salesReport(@FormParam("id") String id,@FormParam("store_id") String store_id,@FormParam("customer_id") String customer_id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date) throws IOException{
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
	if(store_id==null || store_id.isEmpty()){
	jsonObject.clear();
	jsonObject.put("status", "Value");
	jsonObject.put("Messasge"," Store_id Field is empty.");	
	}else{
	try{
	SqlConnection();
	try{
	String Query="select salesorder_id,created_date,customer_name,discount_amount,product_total from tbl_sales where (store_id='"+store_id+"' or customer_id='"+customer_id+"') and created_date::date between '"+from_date+"' and '"+to_date+"'order by created_date ";
	ResultSet resultSet =statement.executeQuery(Query);
	System.out.println("resultSet :"+ resultSet);
	ArrayList arrayList = convertResultSetIntoArrayLists(resultSet);	
	if(arrayList!=null && !arrayList.isEmpty()){
	jsonObject.clear();
	jsonObject.put("Status", "Success");
	jsonObject.put("message", "Return sales report. ");
	jsonObject.put("Data", arrayList);	
	}else{
	jsonObject.clear();
	jsonObject.put("Status", "Failed");
	jsonObject.put("message", "List is empty or Invalid vendor id");	
	}
	}catch(SQLException e){
	e.printStackTrace();
	jsonObject.clear();
	jsonObject.put("Status", "Failed");
	jsonObject.put("message", "Something went Wrong");
	}catch (Exception e) {
	e.printStackTrace();
	jsonObject.clear();
	jsonObject.put("Status", "Failed");
	jsonObject.put("message", "Please Try Again");
	}
	}finally{
	try{
	connection.close();	
	}catch(SQLException e){
	e.printStackTrace();
	}	
	}
	}
	return Response.ok().entity(jsonObject)
	.header("Access-Control-Allow-Methods", "POST").build();
	}


	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/barcodeIdSearch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response barcodeIdSearch(@FormParam("id") String id,@FormParam("barcodeNumber") String barcodeNumber,@FormParam("store_id") String store_id){
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
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id = (String) jsObject.get("auth_id");
			if(role_id==1){
				
			}else{
				store_id=auth_id;
			}
					}
		String Query ="select barcode.id,barcode.barcode_number,barcode.product_name,barcode.manufactur_date,"
				+ "COALESCE(store_stockdetails.total_stock,0) as stock_availability, "
				+ "barcode.expiry_date,barcode.price,tbl_products.product_cgst,tbl_products.product_sgst,"
				+ "tbl_products.product_igst,product_unit.product_unit,barcode.discount from barcode "
				+ "inner join tbl_products on barcode.product_id=tbl_products.product_id "
				+ "inner join store_stockdetails on store_stockdetails.product_id=tbl_products.product_id "
				+ "inner join product_unit on tbl_products.product_unit=product_unit.id "
				+ "where barcode.barcode_number='"+barcodeNumber+"' and store_stockdetails.store_id='"+store_id+"'";
		
		System.out.println("the query is:"+Query);
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
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
	@Path("/generateSalesId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generatePurcaseOrderId(@FormParam("id") String id){
//		,@FormParam("warehouse_name") String warehouse_name
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
//			if(role_id==4){
//				
//			}
			System.out.println(role_id);	
		}
		try {
			SqlConnection();
			try {
				String salesReferenceId= Customer.getGenerateId("SALES",8,connection);
				//System.out.println("the value is :"+generateInvoiceid);
				if(salesReferenceId!=null&&!salesReferenceId.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", salesReferenceId);
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
	@Path("/AddSalesOrder") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddSalesOrder(@FormParam("id") String id,@FormParam("salesreference_id") String salesreference_id,@FormParam("barcode_number") String barcode_number,
			@FormParam("products") String products,@FormParam("total_value") String total_value,@FormParam("total_discount") String total_discount,
			@FormParam("total_tax") String total_tax,@FormParam("final_total") String final_total
//			@FormParam("date") String date,@FormParam("customer_name") String customer_name,
//			@FormParam("product_name") String product_name,@FormParam("quantity") String quantity,
//			@FormParam("unit") String unit,@FormParam("price") String price,@FormParam("cgst") String cgst,
//			@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("discount") String discount,
//			@FormParam("total") String total
			){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			
			
			String salesInsertQuery= "insert into tbl_salesorder(reference_id,total_value,total_discount,total_tax,total_amount) "
										+ "values('"+salesreference_id+"','"+total_value+"','"+total_discount+"','"+total_tax+"','"+final_total+"')";
int insertStatus=statement.executeUpdate(salesInsertQuery);
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
				String product_name =orgjsObject.getString("product_name");
				String product_unit =orgjsObject.getString("product_unit");
				int qunatity = orgjsObject.getInt("product_quantity");
				//String date = orgjsObject.getString("date");
				//String customer_name = orgjsObject.getString("customername");
				int price = orgjsObject.getInt("price");
				int CGST = orgjsObject.getInt("cgst");
				int SGST = orgjsObject.getInt("sgst");
				int IGST = orgjsObject.getInt("igst");
				int discount = orgjsObject.getInt("discount");
				//int grandtotal = orgjsObject.getInt("grandtotal");
				//int paid = orgjsObject.getInt("paid");
				//int balance_amount = orgjsObject.getInt("balance_amount");
				//String paymentstatus = orgjsObject.getString("paymentstatus");
				int total = orgjsObject.getInt("total");
				String pr		= Integer.toString(price);
				String cgst		= Integer.toString(CGST);
				String sgst		= Integer.toString(SGST);
				String igst		= Integer.toString(IGST);
				String dis		= Integer.toString(discount);
				String tot		= Integer.toString(total);
				//String paidd = Integer.toString(paid);
				//String ba = Integer.toString(balance_amount);
				
				String Query="insert into sales(reference_number,barcode_number,product_name,quantity,unit,price,cgst,sgst,igst,discount,product_total) "
						+ "values('"+salesreference_id+"','"+barcode_number+"','"+product_name+"','"+qunatity+"','"+product_unit+"','"+pr+"','"+cgst+"',"
								+ "'"+sgst+"','"+igst+"','"+dis+"','"+tot+"')";			
				
				//String Query="insert into sales,tbl_salesorder(sales.reference_number,sales.barcode_number,sales.product_name,sales.quantity,sales.unit,sales.price,sales.cgst,sales.sgst,sales.igst,sales.discount,sales.grand_total,tbl_salesorder.reference_id,tbl_salesorder.total_value,tbl_salesorder.total_discount,tbl_salesorder.total_tax,tbl_salesorder.final_total) values('"+salesReferenceId+"','"+barcode_number+"','"+product_name+"','"+qunatity+"','"+product_unit+"','"+pr+"','"+cgst+"','"+sgst+"','"+igst+"','"+dis+"','"+tot+"','"+salesReferenceId+"','"+total_value+"','"+total_discount+"','"+total_tax+"','"+final_total+"')";
				
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
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		} catch (Exception e) {
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Please try again");
			 jsonObject.put("error", e.getMessage());
			 System.out.println();
		}finally{
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
	@Path("/salesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response salesList(@FormParam("id") String id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query ="select * from tbl_salesorder";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/salesListView")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response salesListView(@FormParam("id") String id,@FormParam("reference_id") String reference_id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query ="	select * from sales where reference_number='"+reference_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/editSalesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editpurchaseorder(@FormParam("id") String id,@FormParam("reference_id") String reference_id)
	//,@FormParam("token") String token)
	{
		JSONObject jsonObject=new JSONObject();
		if(reference_id==null || reference_id.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		String Query="select * from sales where reference_number="+"'"+reference_id+"'";
//		String Query="select purchase_order.id,purchase_order.purchaseorder_date,purchase_order.billing_address,"
//				+ "purchase_order.shipping_address,purchase_order.warehouse_name as warehouse_id,tbl_warehouse.warehouse_name,"
//				+ "purchase_order.vendor_id,tbl_vendor.vendor_name,product_description,product_unit,product_quantity,"
//				+ "purchase_status from purchase_order "
//				+ "inner join tbl_warehouse on tbl_warehouse.warehouse_id=purchase_order.warehouse_name "
//				+ "inner join tbl_vendor on tbl_vendor.vendor_id=purchase_order.vendor_id "
//				+ "where purchase_order.purchaseorder_id="+"'"+reference_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/salesReturn")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response salesreturn(@FormParam("id") String id,@FormParam("reference_id") String reference_id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		String subQuery=new String();
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//					}
//		String Query ="select reference_id,purchased_date,total_amount from tbl_salesorder where reference_id='"+reference_id+"'";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
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
//				jsonObject.clear();
//				
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//		finally{
//			try {
//				statement.close();
//				resultset.close();
//				connection.close();
//				
//			} catch (SQLException e) {
//			
//				e.printStackTrace();
//			}
//			}
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/newSalesreturns")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response newSalesreturns(@FormParam("id") String id,@FormParam("salesorder_id") String salesorder_id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query=null;
		if(salesorder_id.contains("SALES")||salesorder_id!=null){
		 Query ="select tso.salesorder_id as ref_no,ts.created_date::date,tso.total_value,ts.customer_id,ts.customer_name,"+
				" (select count(product_id) from tbl_sales ts where ts.salesorder_id='"+salesorder_id+"')as no_of_items from tbl_salesorder tso " +
				" inner join tbl_sales ts on (ts.salesorder_id=tso.salesorder_id) where  ts.salesorder_id='"+salesorder_id+"' group by 1,2,3,4,5,6 ";
		}
		else{ Query ="select tso.salesorder_id as ref_no,ts.created_date::date,tso.total_value from tbl_salesorder tso "
                +" inner join tbl_sales ts on ts.salesorder_id=tso.salesorder_id";		
		}
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/salesProductList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response salesProductList(@FormParam("id") String id,@FormParam("salesorder_id") String salesorder_id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query =" select tbl_sales.product_id,tbl_sales.quantity, tbl_products.product_name||'-'||tbl_brand.brand_name||'-'||product_unit.product_unit as product_description from tbl_products"+
				" inner join product_unit on product_unit.id=tbl_products.product_unit "+
				" inner join tbl_brand on tbl_products.product_brand_id=tbl_brand.brand_id "+
                " inner join tbl_sales on tbl_sales.product_id=tbl_products.product_id "
				+ "where salesorder_id='"+salesorder_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/salesProductList2")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response salesProductList2(@FormParam("id") String id,@FormParam("product_id") String product_id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query ="select quantity from tbl_sales where product_id='"+product_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/salesProductList1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response salesProductList1(@FormParam("id") String id,@FormParam("salesorder_id") String salesorder_id,@FormParam("product_id") String product_id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query ="  select ts.product_id,tp.product_name,ts.quantity from tbl_sales ts  inner join tbl_products tp on tp.product_id= ts.product_id  "
				+ "where salesorder_id='"+salesorder_id+"' and ts.product_id='"+product_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/updateReturnQty")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response updateReturnQty(MyObject json) throws JSONException, SQLException{
		String id=json.getId();
		String product_details=json.getProduct_details();
		String salesorder_id=json.getSalesorder_id();
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
;		@SuppressWarnings("unused")
		String subQuery=new String();
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
			Connection conn=sqlConnection();
			int k=0;
			String salesReturnId= Customer.getGenerateId("SRET",8,connection);
		org.json.JSONArray jsArray=new org.json.JSONArray(product_details);
		System.out.println("product_details: "+product_details);
		System.out.println("jsArray: "+jsArray);
		for(int i=0;i<jsArray.length();i++) {
		org.json.JSONObject js_Object=jsArray.getJSONObject(i);
		String qty=js_Object.getString("qty");
		String product_id=js_Object.getString("product_id");
		
		if(js_Object.isNull(qty)) {
			jsonObject= HelperClass.generateResponce(201,"Please enter valid quantity..!!", "");
		}
		
		String Query="UPDATE tbl_sales SET return_qty ='"+qty+"',returnorder_id='"+salesReturnId+"'  WHERE  product_id='"+product_id+"'and salesorder_id='"+salesorder_id+"'";	
		PreparedStatement pstmt=conn.prepareStatement(Query);
		k=pstmt.executeUpdate();
		System.out.println("statement: "+statement);
		}
		if(k>0){
			jsonObject.clear();
			
			 jsonObject.put("Status", "Success");
			 jsonObject.put("Message", "inserted");
		}else{
			jsonObject.clear();
			
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Failed to insert");
			}
		}catch (SQLException e) {
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
		finally{
			try {
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
	@Path("/salesreturns")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response salesreturns(@FormParam("id") String id){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		String Query ="select ts.salesorder_id as ref_no,ts.created_date::date as date,ts.customer_name,ts.product_price,ts.returnorder_id,(select count(product_id)" +
  " from tbl_sales ts where ts.salesorder_id=ts.salesorder_id and ts.returnorder_id=ts.returnorder_id) from tbl_sales ts";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	@Path("/salesReturnDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response salesReturnDetails(@FormParam("id") String id,@FormParam("product_price") String product_price,@FormParam("return_qty") String return_qty){
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
					}
		
		String Query ="select ts.product_id,ts.return_qty,ts.product_price,(COALESCE(product_price,0)*COALESCE(return_qty,0)) as product_total,sum((COALESCE(product_price,0)*COALESCE(return_qty,0))) as grand_total, "+
	"(sum((COALESCE(product_price,0)*COALESCE(return_qty,0)))+discount_amount) as total from tbl_sales ts"+
	" where ts.product_id=ts.product_id and ts.salesorder_id=ts.salesorder_id and ts.return_qty!='0' group by 1,2,3,4,discount_amount";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
@Path("/productNameSearch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public static Response productNameSearch(@FormParam("id") String id,@FormParam("reference_id") String reference_id){
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
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}else{
		@SuppressWarnings("unused")
		String email=(String) jsObject.get("email");
		int role_id=(int) jsObject.get("role_id");
		System.out.println(role_id);
				}
	String Query ="select product_name from sales where reference_id='"+reference_id+"'";
	try {
		SqlConnection();
		try {
			resultset=statement.executeQuery(Query);
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
@Path("/productList")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public static Response productList(@FormParam("id") String id,@FormParam("product_name") String product_name){
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
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}else{
		@SuppressWarnings("unused")
		String email=(String) jsObject.get("email");
		int role_id=(int) jsObject.get("role_id");
		System.out.println(role_id);
				}
	String Query ="select * from sales where product_name='"+product_name+"'";
	try {
		SqlConnection();
		try {
			resultset=statement.executeQuery(Query);
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
@Path("/salesReturnInsertion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public static Response salesReturnInsertion(@FormParam("id") String id,@FormParam("reference_id") String reference_id,
		@FormParam("product_name") String product_name,@FormParam("product_unit") String product_unit,
		@FormParam("product_quantity") String product_quantity,@FormParam("cgst") String cgst,@FormParam("product_id") String product_id,
		@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("sales_date") String sales_date,
		@FormParam("discount") String discount,@FormParam("price") String price,@FormParam("total") String total,@FormParam("notes") String notes)
{
	
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
	try {
		SqlConnection();
		try {
			String Query="insert into sales_return(sales_date,reference_number,product_id,product_name,unit,quantity,cgst,sgst,igst,discount,price,total,notes) "
					+ "values('"+sales_date+"','"+reference_id+"','"+product_id+"','"+product_name+"','"+product_unit+"','"+product_quantity+"',"
							+ "'"+cgst+"','"+sgst+"','"+igst+"','"+discount+"','"+price+"','"+total+"','"+notes+"')";
			
			 System.out.println(Query);
			int i=statement.executeUpdate(Query);
			if(i>0){
				jsonObject.clear();
				jsonObject.put("Status", "Success");
				jsonObject.put("Message", "Inserted Successfully");
			}else{
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Failed to insert");
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
			connection.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		}
	
	return Response.ok()
			.entity(jsonObject)
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
}


	public static void SqlConnection() throws IOException{
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
	public static Connection sqlConnection() throws IOException{
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
		return connection;  
	}
	
	/*
	 * public static String getSalesId(String id){
	 * 
	 * String numberrange_function="select sale_id_numberrange_function('"+id+"')";
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
	
	
	/*
	 * public static String getId(String id){
	 * 
	 * String
	 * numberrange_function="select salesorder_id_numberrange_function('"+id+"')";
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList convertResultSetIntoArrayLists(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("price")||columnName.contains("current_price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
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
