package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.helper.Config;
import com.helper.DBConnection;
import com.helper.Helper;
import com.ibm.wsdl.util.StringUtils;
//import com.sun.jmx.snmp.Timestamp;
import com.utils.Product;
import com.utils.Products;

@SuppressWarnings("unused")
@Path("products")

public class products {

	static Statement statement;
	static ResultSet resultset;
	static Connection connection;
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject(); 
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(products.class);
	@Context private static HttpServletRequest request;
	@Context private static HttpServletResponse response;

/*
 * author - siba sankar
 * i commented this and created another api wit h
 */
//	@POST
//	@Path("/addProductApi")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response addProduct(Products products){
//		String id=products.getId();
//		String product_id=products.getProduct_id();
//		String product_name=products.getProduct_name();
//		String product_description=products.getProduct_description();
//		String product_code=products.getProduct_code();
//		String product_category_id=products.getProduct_category_id();
//		String product_subcategory_id=products.getProduct_subcategory_id();
//		String product_brand_id=products.getProduct_brand_id();
//		int product_unit=products.getProduct_unit();
//		String product_colour=products.getProduct_colour();
//		int product_size=products.getProduct_size();
//		String product_image=products.getProduct_image();
//		String barcode_number=products.getBarcode_number();
//		String barcode_image=products.getBarcode_image(); 
//		int alert_quantity=products.getAlert_quantity();
//		double product_cgst=products.getProduct_cgst();
//		double product_sgst=products.getProduct_sgst();
//		double products_igst=products.getProduct_igst();
//		String product_taxable=products.getProduct_taxable();
//		String gst = products.getGst();
//		String vendor_id=products.getVendor_id();
//		String product_status =products.getProduct_status();
//		String product_substitution = products.getProduct_substitution();
//		String batch_id=products.getBatch_id();
//		String product_type=products.getProduct_type();
//		String hsn_code=products.getHsn_code();
//		String product_gst=products.getProduct_gst();	
//		double price=products.getPrice();
//		/*these were marked commented before.*/
//		//String nontaxable=products.getProduct_nontaxable();
//		//String productunit_value=products.getProductunit_value();
////		JSONObject product_image=products.getProduct_icon();
//		JSONObject jsonObject=new JSONObject();
//		System.out.println(id);
//		if(id==null||id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Id value is empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}
//		
////		if(id==null || id.isEmpty() || product_name==null || product_name.isEmpty() || product_description==null || product_description.isEmpty() ||product_code==null || product_code.isEmpty()
////		||product_category_id==null || product_category_id.isEmpty()||product_subcategory_id==null || product_subcategory_id.isEmpty()
////		||product_brand_id==null || product_brand_id.isEmpty()||price<0||vendor_id==null){
////			jsonObject.clear();
////			jsonObject.put("status", "Failed");
////			jsonObject.put("message",  "Fields are empty");
////			return Response.ok()
////					.entity(jsonObject)
////					.header("Access-Control-Allow-Methods", "POST").build();
////		}
//		
//		//String produnitvalue=productunit_value+product_unit;
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		//i commentedt this
//		/*if(product_gst==null&&gst.isEmpty()){
//			product_gst="0";
//		}
//		
//		//String ssv="12%";
//		String toRemove = "";
//		if(product_gst.contains("%")){
//			toRemove=product_gst.replace("%", "");
//		}else{
//			toRemove=product_gst;
//		}
//		
//		System.out.println(toRemove);
//		
//		//String gstdiv = Integer.parseInt(toRemove)/2+"%";
//		double gstdiv = Double.parseDouble(toRemove)/2;
//		
//		System.out.println(gstdiv);
//
//		String gstWithPercentage=product_gst;		
//		System.out.println(gstWithPercentage);
//		*/
//		
//		
//		/*already commented*/
////		vendor_id=vendor_id.substring(1,vendor_id.length()-1);
////		System.out.println(vendor_id);
////		String toRemove1 = "";
////		
////		if(vendor_id.contains("\\")){
////			toRemove1=vendor_id.replace("\\(.)", "");
////			System.out.println("the final string is:" + vendor_id);
////			
////		}else{
////			
////		}
//		try {
//			SqlConnection();
//			String productid=Customer.getGenerateId("PRODUCT",7,connection);
//			
//			try {
//				String email=(String) jsObject.get("email");
////				String Query="select fn_add_product('"+productid+"','"+product_name+"','"+product_description+"',"
////						+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
////								+ "'"+product_unit+"','"+product_colour+"','"+product_size+"','"+product_image+"',"
////								+ "'"+alert_quantity+"','"+gstdiv+"','"+gstdiv+"','"+gstWithPercentage+"','"+product_taxable+"','"+price+"',"
////										+ "'"+vendor_id+"','"+email+"','"+product_status+"','"+product_substitution+"')";
//				
//				/* i commented these code*/
//			/*	String Query="select fn_add_product('"+productid+"','"+product_name+"','"+product_description+"',"
//						+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
//								+ "'"+product_unit+"','"+product_size+"','"+product_image+"','"+hsn_code+"','"+product_type+"','"+batch_id+"','"+product_gst+"'"
//								+ "'"+alert_quantity+"','"+gstdiv+"','"+gstdiv+"','0','"+product_taxable+"','"+price+"',"
//										+ "'"+vendor_id+"','"+email+"','"+product_status+"','"+product_substitution+"')";
//				*/
//				/*i am using insert query instead of functions*/
//				String Query="insert into tbl_products(product_id,product_code,product_name,product_description,product_category_id,"
//						+ "product_subcategory_id,product_brand_id,product_unit,product_size,product_image,alert_quantity,product_taxable,"
//						+ "vendor_id,product_type,hsn_code,product_status,batch_id,product_substitution,price)"
//						+ "values('"+productid+"','"+product_code+"','"+product_name+"','"+product_description+"','"+product_category_id+"','"+product_subcategory_id+"',"
//						+ "'"+product_brand_id+"','"+product_unit+"','"+product_size+"','"+product_image+"','"+alert_quantity+"','"+product_taxable+"',"
//						+ "'"+vendor_id+"','"+product_type+"','"+hsn_code+"','"+product_status+"','"+batch_id+"','"+product_substitution+"','"+price+"')";
//				
//				
//				System.out.println("Add product query:" +Query);
//				
////				CallableStatement callableStatement=connection.prepareCall(Query);
////				callableStatement.execute();
////				ResultSet resultSet=callableStatement.getResultSet();
//				int st=statement.executeUpdate(Query);
//
////				String result=new String();
////				while(resultSet.next()){
////					result=resultSet.getString(1);
////				}
//				/*----------------------     Add Vendor -------------------------------*/
//					/*vendor_id=vendor_id.substring(1,vendor_id.length()-1);
//				    String[] parts = vendor_id.split(",");			
//				    System.out.println(parts);
//					for(int i=0;i<parts.length;i++){
//					System.out.println(parts[i]);
//					
//						String vendorPriceQuery="insert into tbl_vendor_pricing_table(product_id,product_name,product_description,"
//								+ "product_code,product_category_id,product_subcategory_id,product_brand_id,product_unit,product_colour,"
//								+ "product_size,product_image,alert_quantity,product_cgst,product_sgst,product_igst,product_taxable,"
//								+ "product_price, vendor_id,created_by,product_status,product_substitute,hsn_code,product_type,batch_id,product_gst)"
//						+ "	values('"+productid+"','"+product_name+"','"+product_description+"',"
//										+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
//										+ "'"+product_unit+"','"+product_colour+"','"+product_size+"','"+product_image+"',"
//										+ "'"+alert_quantity+"','"+gstdiv+"','"+gstdiv+"','"+gstWithPercentage+"','"+product_taxable+"','"+price+"',"
//										+ "'"+parts[i]+"','"+email+"','"+product_status+"','"+product_substitution+"','"+hsn_code+"','"+product_type+"','"+batch_id+"','"+product_gst+"')";
//						
//						System.out.println("the vendor pricing table is:" +vendorPriceQuery);
//						
//						int st=statement.executeUpdate(vendorPriceQuery);
//						*/
//						if(st>0){
//							jsonObject.clear();
//							jsonObject.put("Status", "Success");
//							jsonObject.put("Message", "Added Successfully");
//						}else{
//							jsonObject.clear();
//							jsonObject.put("Status", "Failed");
//							jsonObject.put("Message", "Failed to Add");
//						}
//				 
//				/*if(result.contains("Success")){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Product Added Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to Add Product");
//				}*/
//						
//			}catch (SQLException e) {
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
//		}finally{
//			try {
//				connection.close();
//				
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//			}
//			}
//						
//		 return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//	
//	}

	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getProductsListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProductsList(@FormParam("id") String id){
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
			System.out.println(role_id);
		}
		String Query ="select tbl_products.product_id,tbl_products.product_name,tbl_products.product_description,"
				+ "tbl_products.product_status,tbl_products.product_substitution,product_unit.product_unit as product_value,"
				+ "tbl_products.product_code,tbl_products.product_image,tbl_brand.brand_name,tbl_products.product_unit,"
				+"tbl_products.hsn_code,tbl_products.batch_id,tbl_products.product_type,"
				+ "tbl_category.category_name,tbl_subcategory.subcategory_name from tbl_products "
				+ "inner join product_unit on product_unit.id=tbl_products.product_unit "
				+ "inner join tbl_brand on tbl_products.product_brand_id=tbl_brand.brand_id "
				+ "inner join tbl_category on tbl_products.product_category_id=tbl_category.category_id "
				+ "inner join tbl_subcategory on tbl_products.product_subcategory_id=tbl_subcategory.subcategory_id "
				+ "where tbl_products.mark_for_deletion=0 and tbl_products.item_prohibited=0 and tbl_products.item_hide=0 ";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println("Query "+ Query);
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
	@Path("/getProductsApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProducts(@FormParam("id") String id){
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
			String auth_id = (String)jsObject.get("auth_id");
			System.out.println(role_id);
		}
		Connection connectionProducts=null;
		try{
			String email=(String) jsObject.get("email");
			connectionProducts = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionProducts.prepareStatement(Config.get_products);
		    ResultSet resultSet =preparedStatement.executeQuery();
			@SuppressWarnings("rawtypes")
			ArrayList arrayList = Helper.convertResultSetIntoJSON(resultSet);
			if(arrayList!=null&&!arrayList.isEmpty()){
				jsonObject.clear();
				jsonObject.put("Status", "Success");
				jsonObject.put("Data", arrayList);
			}else{
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "List is empty");
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Something Went Wrong");
		}finally{
			try {
			//	
				//
				connectionProducts.close();		
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
	@Path("/deleteproductApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deleteproduct(@FormParam("id") String id,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
		
		if(product_id==null || product_id.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				//String Query="DELETE from tbl_addproduct where product_id='"+product_id+"'";
				String Query="update tbl_products set mark_for_deletion=1 where product_id='"+product_id+"'";
				
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
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	
	
//	//Get product list  started
//	
//		@SuppressWarnings("unchecked")
//		@Context
//		@POST
//		@Path("/getproductlist")
//		@Produces(MediaType.APPLICATION_JSON)
//		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//		public  Response productlist(@FormParam("id") String id) {
//			JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//			System.out.println(jsObject);
//			if(jsObject.containsKey("status")){
//				jsonObject.clear();
//				jsonObject.put("status", "Failed");
//				jsonObject.put("message", jsObject.get("status"));
//				return Response.ok()
//						.entity(jsonObject)
//						
//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//			}else{
//				@SuppressWarnings("unused")
//				String email=(String) jsObject.get("email");
//				int role_id=(int) jsObject.get("role_id");
//				System.out.println(role_id);
//			}
//			String query = "select * from product_master";
//		try {
//			SqlConnection();
//		}
//		catch(Exception e) {
//
//			if(connection!=null){
//				try {
//
//					connection.close();
//
//					System.out.println("Connection closed");
//
//					log.error("connection closed");
//
//				} catch (SQLException exc) {
//
//					log.error(exc.getMessage());
//
//				}
//
//			}
//
//			}
//		try {
//
//			 System.out.println(query);
//			resultset=statement.executeQuery(query);
//
//			//System.out.println("query2");
//
//		} catch (SQLException e) {					
//
//			//return Errors;
//
//			
//
//			System.out.println(e.getMessage());
//
//		}finally{
//
//			if(connection!=null){
//
//				try {
//
//					connection.close();
//
//					System.out.println("Connection closed");
//
//					log.error("connection closed");
//
//				} catch (SQLException exc) {
//
//					log.error(exc.getMessage());
//
//				}
//
//			}
//
//		}
//
//		if(resultset!=null){
//
//			 try {
//				 @SuppressWarnings("rawtypes")
//				ArrayList arrayList = convertResultSetIntoJSON(resultset);
//				 jsonObject.clear();
//				 jsonObject.put("Status","Success");
//				 jsonObject.put("Data", arrayList);
//
//			} catch (Exception e) {
//
//			//	return Errors;
//				jsonObject.clear();
//				jsonObject.put("Status","Failed");
//				System.out.println(e.getMessage());
//			}		 
//
//		}else {
//			
//		}
//
//		System.out.println("jsonArray :"+jsonArray);
//
//			return Response.ok()
//			.entity(jsonObject)
//			
//		    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		
//
//		//Get product list End
//		
		
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editProductProfile")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editProductProfile(@FormParam("id") String id,@FormParam("product_id") String product_id){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || product_id==null || product_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from product_master where product_id="+"'"+product_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updateProduct")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateProduct(Product product){
//		String id=product.getId();
//		String product_name=product.getProduct_name();
//		String product_type=product.getProduct_type();
//		String product_category_id=product.getProduct_category_id();
//		String product_subcategory_id=product.getProduct_subcategory_id();
//		String product_description=product.getProduct_description();
//		String product_image=product.getProduct_image();
//		String product_price=product.getProduct_price();
//		String product_discount=product.getProduct_discount();
//		String status=product.getStatus();
//		String  produc_id = product.getProduct_id();
//		String vendor_id=product.getVendor_id();
//		JSONObject jsonObject=new JSONObject();
//
//		
//		if(product_name==null || product_name.isEmpty()||product_type==null || product_type.isEmpty()
//		||product_category_id==null || product_category_id.isEmpty()||product_subcategory_id==null || product_subcategory_id.isEmpty()||product_description==null || product_description.isEmpty()||produc_id==null || produc_id.isEmpty()
//		||product_price==null || product_price.isEmpty()||product_image==null || product_image.isEmpty()
//		||status==null || status.isEmpty()||vendor_id==null || vendor_id.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
////		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Al																					low-Origin", "*")
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			try {
//				String Query="UPDATE product_master SET product_name='"+product_name+"',product_image='"+product_image+"',product_price='"+ product_price+"',"
//						+ "product_subcategory_id='"+product_subcategory_id+"',product_category_id='"+product_category_id+"',product_type='"+product_type+"',status='"+status+"',vendor_id='"+vendor_id+"',product_image='"+product_image+"' where product_id='"+produc_id+"'";
//				
//				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
	//Get product pricing started
	
//	@SuppressWarnings("unchecked")
//	@Context
//	@POST
//	@Path("/getproductpricinglist")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public  Response productpricinglist(@FormParam("id") String id) {
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		String query = "select * from product_pricing";
//	try {
//		SqlConnection();
//	}
//	catch(Exception e) {
//
//		if(connection!=null){
//			try {
//
//				connection.close();
//
//				System.out.println("Connection closed");
//
//				log.error("connection closed");
//
//			} catch (SQLException exc) {
//
//				log.error(exc.getMessage());
//
//			}
//
//		}
//
//		}
//	try {
//
//		 System.out.println(query);
//		resultset=statement.executeQuery(query);
//
//		//System.out.println("query2");
//
//	} catch (SQLException e) {					
//
//		//return Errors;
// 
//		
//
//		System.out.println(e.getMessage());
//
//	}finally{
//
//		if(connection!=null){
//
//			try {
//
//				connection.close();
//
//				System.out.println("Connection closed");
//
//				log.error("connection closed");
//
//			} catch (SQLException exc) {
//
//				log.error(exc.getMessage());
//
//			}
//
//		}
//
//	}
//
//	if(resultset!=null){
//
//		 try {
//			 @SuppressWarnings("rawtypes")
//			ArrayList arrayList = convertResultSetIntoJSON(resultset);
//			 jsonObject.clear();
//			 jsonObject.put("Status","Success");
//			 jsonObject.put("Data", arrayList);
//
//		} catch (Exception e) {
//
//		//	return Errors;
//			jsonObject.clear();
//			jsonObject.put("Status","Failed");
//			System.out.println(e.getMessage());
//		}		 
//
//	}else {
//		
//	}
//
//	System.out.println("jsonArray :"+jsonArray);
//
//		return Response.ok()
//		.entity(jsonObject)
//		
//	    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	//Get product pricing End
	
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editproductpricinglist")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editproductpricinglist(@FormParam("id") String id,@FormParam("product_id") String product_id){
//		JSONObject jsonObject=new JSONObject();
//		if(product_id==null || product_id.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from product_pricing where product_id="+"'"+product_id+"'";
//		try {
//			SqlConnection();
//			try {
//			
//				resultset=statement.executeQuery(Query);
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updateproductpricinglist")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateproductpricinglist(@FormParam("id") String id,@FormParam("product_id") String product_id,@FormParam("end_date") String end_date,@FormParam("customer_price") String customer_price,@FormParam("batch_id")
//	String batch_id,@FormParam("vendor_price") String vendor_price,@FormParam("barcode_id") String barcode_id,@FormParam("start_date") String start_date,@FormParam("status") String status){
//		JSONObject jsonObject=new JSONObject();
//		
//		if(product_id==null || product_id.isEmpty()|| end_date==null || end_date.isEmpty()|| customer_price==null || customer_price.isEmpty()
//				|| batch_id==null || batch_id.isEmpty()|| vendor_price==null || vendor_price.isEmpty()|| barcode_id==null || barcode_id.isEmpty()
//				|| start_date==null || start_date.isEmpty()|| status==null || status.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Al																					low-Origin", "*")
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();		
//		
//			try {
//				String Query="UPDATE product_pricing SET end_date='"+end_date+"',customer_price='"+ customer_price+"',"
//						+ "batch_id='"+batch_id+"',vendor_price='"+vendor_price+"',barcode_id='"+barcode_id+"',start_date='"+start_date+"',status='"+status+"' where product_id='"+product_id+"'";
//				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getProductType")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getProductType(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select product_type_name from product_type";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getProductCategory")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getProductCategory(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select product_category_id from product_category";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getProductSubcategory")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getProductSubcategory(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select product_subcategory_id from product_category";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
//	//INSERTION API:
//	@SuppressWarnings("unchecked")
//		@POST
//	    @Path("/productPricing")
//		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//
//		@Produces(MediaType.APPLICATION_JSON)
//
//		public static Response addInsert(@FormParam("id")String id,@FormParam("product_id") 
//		String product_id,@FormParam("barcode_id") String barcode_id,@FormParam("batch_id") 
//		String batch_id,@FormParam("vendor_price")String vendor_price,@FormParam("customer_price") String customer_price
//		,@FormParam("start_date") String start_date,@FormParam("end_date") String end_date,@FormParam("status") String status,@FormParam("discount") String discount){
//
//		JSONObject jsonObject=new JSONObject();
//		if(product_id==null||product_id.isEmpty()||barcode_id==null||barcode_id.isEmpty()||batch_id==null||batch_id.isEmpty()
//				||vendor_price==null||vendor_price.isEmpty()||customer_price==null||customer_price.isEmpty()||start_date==null||start_date.isEmpty()
//				||end_date==null||end_date.isEmpty()||status==null||status.isEmpty()||discount==null||discount.isEmpty()){
//			jsonObject.clear();
//			jsonObject.put("status","Failed");
//			jsonObject.put("message","Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//
//			System.out.println(jsObject);
//
//			if(jsObject.containsKey("status")){
//
//				jsonObject.clear();
//
//				jsonObject.put("status", "Failed");
//
//				jsonObject.put("Message", jsObject.get("status"));
//
//				return Response.ok()
//
//						.entity(jsonObject)
//
//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//
//			}else{
//
//					@SuppressWarnings("unused")
//					String email=(String) jsObject.get("email");
//					int role_id=(int) jsObject.get("role_id");
//					System.out.println(role_id);
//
//
//			}
//			try {
//
//				SqlConnection();
//		
//				try {
//					String Query="insert into product_pricing(product_id,barcode_id,batch_id,vendor_price,customer_price,start_date,end_date,status,discount) "
//
//					+ "values('"+product_id+"','"+barcode_id+"','"+batch_id+"','"+vendor_price+"','"+customer_price+"','"+start_date+"','"+end_date+"','"+status+"','"+discount+"')";
//
//
//					int i=statement.executeUpdate(Query);
//					if(i>0){
//						jsonObject.clear();
//						jsonObject.put("Status", "Success");
//						jsonObject.put("Message", "Inserted Successfully");
//					}else{
//						jsonObject.clear();
//						jsonObject.put("Status", "Failed");
//						jsonObject.put("Message", "Failed to insert");
//					}
//
//				} catch (SQLException e) {
//
//					jsonObject.clear();
//
//					 jsonObject.put("Status", "Failed");
//
//					 jsonObject.put("Message", "Something went wrong");
//
//					 jsonObject.put("error", e.getMessage());
//					 
//					 System.out.println(e.getMessage());
//
//				} catch (Exception e) {
//
//					jsonObject.clear();
//
//					 jsonObject.put("Status", "Failed");
//
//					 jsonObject.put("Message", "Please try again");
//
//					 jsonObject.put("error", e.getMessage());
//
//				}
//
//			} catch (IOException e) {
//
//				jsonObject.clear();
//
//				 jsonObject.put("Status", "Failed");
//
//				 jsonObject.put("Message", "Something went wrong");
//
//				 jsonObject.put("error", e.getMessage());
//
//			}
//			 			return Response.ok()
//						.entity(jsonObject)
//						
//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//			 }
//	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getbarcodeid")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getbarcodeid(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select barcode_id from product_type";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getproductid")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getproductid(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select product_id from product_master";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getbatchid")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getbatchid(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select batch_id from product_type";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}	
	
	
	//Product Attribute 
	
//	@SuppressWarnings("unchecked")
//	@POST
//    @Path("/productAttributeInsert")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//	public static Response productAttributeInsert(@FormParam("id") String id
//	,@FormParam("product_main") String product_main,@FormParam("product_sub") 
//	String product_sub,@FormParam("product_addition")String product_addition){
//	JSONObject jsonObject=new JSONObject();
//	JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("Message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//				@SuppressWarnings("unused")
//				String email=(String) jsObject.get("email");
//				int role_id=(int) jsObject.get("role_id");
//				System.out.println(role_id);
//		}
//
//		try {
//			SqlConnection();
//			try {
//
//				String Query="insert into product_attribute(product_main,product_sub,product_addition) "
//				+ "values('"+product_main+"','"+product_sub+"','"+product_addition+"')";
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//
//				}else{
//
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to insert");
//
//				}
//
//
//
//			} catch (SQLException e) {
//
//				 jsonObject.clear();
//		         jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//				 System.out.println(e.getMessage());
//			} catch (Exception e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Please try again");
//				 jsonObject.put("error", e.getMessage());
//			}
//
//		} catch (IOException e) {
//			jsonObject.clear();
//			 jsonObject.put("Status", "Failed");
//			 jsonObject.put("Message", "Something went wrong");
//			 jsonObject.put("error", e.getMessage());
//		}
//
//		 			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//
//		 }

//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/getproductAttributes")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response getCustomerdetails(@FormParam("id") String id){
//		JSONObject jsonObject=new JSONObject();
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}String Query="select * from product_attribute";
//		try {
//			SqlConnection();
//			try {
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
	
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/editproductAttributes")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response editproductAttributes(@FormParam("id") String id,@FormParam("product_id") String productid){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || productid==null || productid.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		
//		String Query="select * from product_attribute where product_id = "+"'"+productid+"'";
//		try {
//			SqlConnection();
//			try {
//			System.out.println(Query);
//				resultset=statement.executeQuery(Query);
//				@SuppressWarnings("rawtypes")
//				ArrayList arrayList=convertResultSetIntoJSON(resultset);
//				if(arrayList!=null&&!arrayList.isEmpty()){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("data", arrayList);
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "List is empty");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/updateproductAttributes")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response updateproductAttributes(@FormParam("id") String id,@FormParam("product_id") String productid,@FormParam("product_main") String product_main,@FormParam("product_sub") String product_sub,@FormParam("product_addition")
//	String product_addition){
//		JSONObject jsonObject=new JSONObject();
//		if(id==null || id.isEmpty() || productid==null || productid.isEmpty()|| product_main==null || product_main.isEmpty()|| product_sub==null || product_sub.isEmpty()|| product_addition==null || product_addition.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
////		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			int role_id=(int) jsObject.get("role_id");
//			System.out.println(role_id);
//		}
//		try {
//			SqlConnection();
//			
//			try {
//				String Query="UPDATE product_attribute SET product_main='"+product_main+"',product_sub='"+product_sub+"',"
//						+ "product_addition='"+product_addition+"' where product_id='"+productid+"'";
//				
//				PreparedStatement preparedStatement=connection.prepareStatement(Query);
//				
//				int i=preparedStatement.executeUpdate();
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Updated Successfully");
//				}else{
//					jsonObject.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Message", "Failed to update");
//				}
//			} catch (SQLException e) {
//				jsonObject.clear();
//				 jsonObject.put("Status", "Failed");
//				 jsonObject.put("Message", "Something went wrong");
//				 jsonObject.put("error", e.getMessage());
//				 System.out.println("the value is"+e.getMessage());
//			} catch (Exception e) {
//			
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
//		
//		 return Response.ok()
//					.entity(jsonObject)
//					
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//	
//	}
	
	
	/* Converting Resultset into Json format method  */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
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
	
	
	
	
		
	/*
	 * public static String getId(String id){
	 * 
	 * String numberrange_function="select numberrange_function('"+id+"')";
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
	@Path("/productCategoryApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productcategory(@FormParam("id") String id,@FormParam("product_category_name") String product_category_name,@FormParam("product_category_description") String product_category_description){
		JSONObject jsonObject=new JSONObject();
		if(product_category_name==null || product_category_name.isEmpty()){
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
		try {
			SqlConnection();
			String categoryid=Customer.getGenerateId("CATEGORY",7,connection);
			try {
				String email=(String) jsObject.get("email");
				String Query="insert into tbl_category(category_id,category_name,category_description,created_by) "
						+ "values('"+categoryid+"','"+product_category_name+"','"+product_category_description+"','"+email+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Category Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add");
				}
			} catch (SQLException e) {
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
	
	/*
	 Edit Category Api
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editcategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editcategory(@FormParam("id") String id,@FormParam("product_category_id") String product_category_id){
		JSONObject jsonObject=new JSONObject();
		if(product_category_id==null || product_category_id.isEmpty()) {
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
		
		//String Query="select * from tbl_productcategory where product_category_id="+"'"+product_category_id+"'";
		String Query="select * from tbl_category where category_id="+"'"+product_category_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update category Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatecategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatecategory(@FormParam("id") String id,@FormParam("product_category_id") String product_category_id,@FormParam("product_category_name") String product_category_name,@FormParam("product_category_description") String product_category_description){
		JSONObject jsonObject=new JSONObject();
		if(product_category_id==null || product_category_id.isEmpty()|| product_category_name==null || product_category_name.isEmpty()|| product_category_description==null || product_category_description.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String email=(String) jsObject.get("email");
				//String Query="UPDATE tbl_productcategory SET product_category_name='"+product_category_name+"',product_category_description='"+ product_category_description+"' where product_category_id='"+product_category_id+"'";
				String Query="UPDATE tbl_category SET category_name='"+product_category_name+"',category_description='"+ product_category_description+"',updated_by='"+email+"',updated_date=current_date where category_id='"+product_category_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete Category Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletecategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletecategory(@FormParam("id") String id,@FormParam("product_category_id") String product_category_id){
		JSONObject jsonObject=new JSONObject();
		
		if(product_category_id==null || product_category_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				//String Query="DELETE from tbl_productcategory where product_category_id='"+product_category_id+"'";
				String Query="update tbl_category set mark_for_deletion=1 where category_id='"+product_category_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Category Deleted Successfully");
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
	@Path("/productSubCategoryApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productsubcategory(@FormParam("id") String id,@FormParam("productcategory_id") String productcategory_id,@FormParam("productsubcategory_name") String productsubcategory_name,@FormParam("productsubcategory_description") String productsubcategory_description){
		JSONObject jsonObject=new JSONObject();
		if(productcategory_id==null || productcategory_id.isEmpty()|| productsubcategory_name==null || productsubcategory_name.isEmpty()){
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
		try {
			SqlConnection();
			String subcategoryid=Customer.getGenerateId("SUBCATEGORY",7,connection);
			try {
				
				String email=(String) jsObject.get("email");
//				String Query="insert into tbl_productsubcategory(productsubcategory_id,product_category_id,productsubcategory_name,productsubcategory_description) "
//						+ "values('"+subcategoryid+"','"+productcategory_id+"','"+productsubcategory_name+"','"+productsubcategory_description+"')";
				
				String Query="insert into tbl_subcategory(subcategory_id,category_id,subcategory_name,subcategory_description,created_by) "
						+ "values('"+subcategoryid+"','"+productcategory_id+"','"+productsubcategory_name+"','"+productsubcategory_description+"','"+email+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "SubCategory Added Successfully");
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
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getProductSubCategoryDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productSubCategoryDetails(@FormParam("id") String id){
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
		}
//		String Query ="select tbl_productcategory.product_category_name,tbl_productsubcategory.productsubcategory_name,tbl_productsubcategory.productsubcategory_id,tbl_productsubcategory.productsubcategory_description from tbl_productsubcategory"
//			
//				+" inner join tbl_productcategory on tbl_productsubcategory.product_category_id = tbl_productcategory.product_category_id";
		String Query ="select tbl_category.category_name,tbl_subcategory.subcategory_name,tbl_subcategory.subcategory_description,tbl_subcategory.subcategory_id  from tbl_subcategory "
				+ "inner join tbl_category on tbl_subcategory.category_id=tbl_category.category_id where tbl_subcategory.mark_for_deletion=0";
		
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	/*
	 Edit SubCategory Api
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editsubcategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editsubcategory(@FormParam("id") String id,@FormParam("productsubcategory_id") String productsubcategory_id){
		JSONObject jsonObject=new JSONObject();
		if(productsubcategory_id==null || productsubcategory_id.isEmpty()) {
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
		
		//String Query="select * from tbl_productsubcategory where productsubcategory_id='"+productsubcategory_id+"'";
		String Query="select tbl_category.category_name,tbl_subcategory.subcategory_name,tbl_subcategory.subcategory_description,tbl_subcategory.subcategory_id  from tbl_subcategory "
				+ "inner join tbl_category on tbl_subcategory.category_id=tbl_category.category_id where tbl_subcategory.mark_for_deletion=0 and tbl_subcategory.subcategory_id='"+productsubcategory_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update subcategory Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatesubcategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatesubcategory(@FormParam("id") String id,@FormParam("productcategory_id") String productcategory_id,@FormParam("productsubcategory_id") String productsubcategory_id,@FormParam("productsubcategory_name") String productsubcategory_name,@FormParam("productsubcategory_description") String productsubcategory_description){
		JSONObject jsonObject=new JSONObject();
		if(productcategory_id==null || productcategory_id.isEmpty()||productsubcategory_id==null || productsubcategory_id.isEmpty()|| productsubcategory_name==null || productsubcategory_name.isEmpty()|| productsubcategory_description==null || productsubcategory_description.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String email=(String) jsObject.get("email");
				//String Query="UPDATE tbl_productsubcategory SET product_category_id='"+productcategory_id+"',productsubcategory_name='"+productsubcategory_name+"',productsubcategory_description='"+ productsubcategory_description+"' where productsubcategory_id='"+productsubcategory_id+"'";
				String Query="UPDATE tbl_subcategory SET category_id='"+productcategory_id+"',subcategory_name='"+productsubcategory_name+"',subcategory_description='"+ productsubcategory_description+"',updated_date=current_date,updated_by='"+email+"' where subcategory_id='"+productsubcategory_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete subCategory Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletesubcategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletesubcategory(@FormParam("id") String id,@FormParam("productsubcategory_id") String productsubcategory_id){
		JSONObject jsonObject=new JSONObject();
		
		if(productsubcategory_id==null || productsubcategory_id.isEmpty()) {
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				//String Query="DELETE from tbl_productsubcategory where productsubcategory_id='"+productsubcategory_id+"'";
				String Query="update tbl_subcategory set mark_for_deletion=1 where subcategory_id='"+productsubcategory_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Deleted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to delete");
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/HsncodeandgstDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response HsncodeandgstDetails(@FormParam("id") String id,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String Query="select s_no,hsn_code,gst,product_id from tbl_hsn_master where product_id='"+product_id+"'";
				System.out.println("query :"+Query);
				ResultSet resultset=statement.executeQuery(Query);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/Deletehsndetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response Deletehsndetails(@FormParam("id") String id,@FormParam("s_no") String s_no){
		JSONObject jsonObject=new JSONObject();
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String Query="DELETE FROM tbl_hsn_master WHERE s_no='"+s_no+"'";
				System.out.println("query :"+Query);
				int i=statement.executeUpdate(Query);
					if(i>0){
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Deleted Successfully");
					}else{
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Failed to Deleted");
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
		
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/Hsncodeandgst")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response Hsncodeandgst(@FormParam("id") String id){
		JSONObject jsonObject=new JSONObject();
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="select s_no,hsn_code,gst,product_id from tbl_hsn_master ";
				System.out.println("query :"+Query);
				ResultSet resultset=statement.executeQuery(Query);
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
	@Path("/insertHsncodeandgst")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response insertHsncodeandgst(@FormParam("id") String id,@FormParam("hsn_code") String hsn_code,@FormParam("gst") String gst,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			String email=(String) jsObject.get("email");
			try {
				int i=0;
				String Query=null;
				 Query=" insert into tbl_hsn_master ( hsn_code, gst, created_on,created_by,product_id) values ('"+hsn_code+"','"+gst+"',current_timestamp,'"+email+"','"+product_id+"')";
				System.out.println("query :"+Query);
				int j=statement.executeUpdate(Query);
				if(j>0){
				 Query="insert into tbl_hsncode_revised ( hsn_code, gst, created_on,created_by,product_id) values ('"+hsn_code+"','"+gst+"',current_timestamp,'"+email+"','"+product_id+"')";
				 System.out.println("query :"+Query);
				 i=statement.executeUpdate(Query);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");
				}
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "inserted Successfully");
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
	@Path("/updateHsncodeandgst")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateHsncodeandgst(@FormParam("id") String id,@FormParam("hsn_code") String hsn_code,@FormParam("gst") String gst,@FormParam("s_no") String s_no,@FormParam("update") String update,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();	
			String email=(String) jsObject.get("email");
			String Query=null;
			int i=0;
			int j=0;
			try {
				if(hsn_code!=null&&gst!=null){
				Query="insert into tbl_hsn_master ( hsn_code, gst, created_on,created_by,product_id) values ('"+hsn_code+"','"+gst+"',current_timestamp,'"+email+"','"+product_id+"')";
				System.out.println("query :"+Query);
				 j=statement.executeUpdate(Query);
				int k=0;
				if(j>0){
				Query="update tbl_hsncode_revised set effective_till_date=(date(current_timestamp))-1 where product_id='"+product_id+"'";
				System.out.println("query :"+Query);
				 k=statement.executeUpdate(Query);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");	
				}
				if(k>0){
				Query="insert into tbl_hsncode_revised ( hsn_code, gst, created_on,created_by,product_id) values ('"+hsn_code+"','"+gst+"',current_timestamp,'"+email+"','"+product_id+"')";
				System.out.println("query :"+Query);
				i=statement.executeUpdate(Query);
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");
				}
				}else if(hsn_code!=null&&gst==null){
					Query="update tbl_products set hsn_code='"+hsn_code+"',updated_on=current_timestamp,updated_by='"+email+"' where product_id='"+product_id+"'";	
					System.out.println("query :"+Query);
					 j=statement.executeUpdate(Query);
					 if(j>0){
						 Query="update tbl_hsncode_revised set effective_till_date=(date(current_timestamp))-1 where product_id='"+product_id+"'";
							System.out.println("query :"+Query);
							 i=statement.executeUpdate(Query);
					 }
				}else{	
				 Query="update tbl_hsn_master set gst='"+gst+"',updated_on=current_timestamp,updated_by='"+email+"' where hsn_code='"+hsn_code+"'";
				System.out.println("query :"+Query);
				 j=statement.executeUpdate(Query);
				 if(j>0){
				Query="update tbl_hsncode_revised set effective_till_date=(date(current_timestamp))-1 where product_id='"+product_id+"'";
				System.out.println("query :"+Query);
				 i=statement.executeUpdate(Query);
				 }
			}
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "updated Successfully");
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
	@Path("/getProductCategoryDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productCategoryDetails(@FormParam("id") String id){
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
		}
		String Query ="select * from tbl_category where mark_for_deletion=0";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	
	
	
	/*
	 Drop down api's
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/subcategoryNamesdropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productSubCategoryDropdown(@FormParam("id") String id,@FormParam("category_id") String category_id){
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
		}
		//String Query ="select productsubcategory_name,product_id from tbl_productsubcategory";
		String Query ="select subcategory_id,subcategory_name from tbl_subcategory where category_id='"+category_id+"' and mark_for_deletion=0";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	/*
	 category Names drop down api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/categoryNamesdropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productCategoryDropdown(@FormParam("id") String id){
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
		}
		//String Query ="select product_category_name,product_category_id from tbl_productcategory";
		String Query ="select category_id,category_name,category_description from tbl_category where mark_for_deletion=0";
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
	@Path("/addProductBrandApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addProductBrandApi(@FormParam("id") String id,@FormParam("productbrand_name") String productbrand_name,@FormParam("productbrand_description") String productbrand_description){
		JSONObject jsonObject=new JSONObject();
		if(productbrand_name==null || productbrand_name.isEmpty()){
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
		try {
			SqlConnection();
			String productbrandId=Customer.getGenerateId("PBRAND",6,connection);
			try {
				String email=(String) jsObject.get("email");
//				String Query="insert into tbl_productbrand(productbrand_id,productbrand_name,productbrand_description) "
//						+ "values('"+productbrandId+"','"+productbrand_name+"','"+productbrand_description+"')";
//				
				String Query="insert into tbl_brand(brand_id,brand_name,brand_description,created_by) "
						+ "values('"+productbrandId+"','"+productbrand_name+"','"+productbrand_description+"','"+email+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Brand Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to insert");
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
	@Path("/productBrandListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productBrandList(@FormParam("id") String id){
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
		}
		//String Query ="select * from tbl_productbrand";
		
		String Query ="select * from tbl_brand where mark_for_deletion=0";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Edit Brand Api
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editbrand")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editbrand(@FormParam("id") String id,@FormParam("productbrand_id") String productbrand_id){
		JSONObject jsonObject=new JSONObject();
		if(productbrand_id==null || productbrand_id.isEmpty()) {
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
		
	//	String Query="select * from tbl_productbrand where productbrand_id="+"'"+productbrand_id+"'";
		String Query="select * from tbl_brand where brand_id='"+productbrand_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
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
	 Update Brand Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatebrand")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatebrand(@FormParam("id") String id,@FormParam("productbrand_id") String productbrand_id,@FormParam("productbrand_name") String productbrand_name,@FormParam("productbrand_description") String productbrand_description){
		JSONObject jsonObject=new JSONObject();
		if(productbrand_id==null || productbrand_id.isEmpty()|| productbrand_name==null || productbrand_name.isEmpty()|| productbrand_description==null || productbrand_description.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String email=(String) jsObject.get("email");
				//String Query="UPDATE tbl_productbrand SET productbrand_name='"+productbrand_name+"',productbrand_description='"+ productbrand_description+"' where productbrand_id='"+productbrand_id+"'";
				String Query="UPDATE tbl_brand SET brand_name='"+productbrand_name+"',brand_description='"+ productbrand_description+"',updated_date=current_date,updated_by='"+email+"' where brand_id='"+productbrand_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete brand Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletebrand")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletebrand(@FormParam("id") String id,@FormParam("productbrand_id") String productbrand_id){
		JSONObject jsonObject=new JSONObject();
		
		if(productbrand_id==null || productbrand_id.isEmpty()) {
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				//String Query="DELETE from tbl_productbrand where productbrand_id='"+productbrand_id+"'";
				String Query="update tbl_brand set mark_for_deletion=1 where brand_id='"+productbrand_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Brand Deleted Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Delete");
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
	@Path("/branddropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productBrandDropdown(@FormParam("id") String id){
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
		}
		//String Query ="select id,productbrand_name from tbl_productbrand";
		String Query ="select brand_id,brand_name from tbl_brand";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	

@SuppressWarnings({"unchecked", "rawtypes"})

@POST
@Path("productItemStatus")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)


public static Response productItemStatus(@FormParam("product_id") String product_id,@FormParam("product_status") String product_status,@FormParam("from_date") Date from_date,@FormParam("to_date") Date to_date) throws IOException{
if(product_id==null || product_id.isEmpty() & product_status==null || product_status.isEmpty() ){
jsonObject.clear();
jsonObject.put("status", "Value");
jsonObject.put("Messasge","Field is empty.");
}
try{
	SqlConnection();
try {
String Query=null;
if(product_status.equals("Active"))
{
Query="select product_name,product_unit,product_status from tbl_products where product_id ='"+product_id+"' and product_status='"+product_status+"' and created_date::date between '"+from_date+"' and '"+to_date+"'";
}else if(product_status.equals("hide"))
{
Query="select product_name,product_unit,item_hide from tbl_products where product_id ='"+product_id+"' and item_hide='"+product_status+"' and created_date::date between '"+from_date+"' and '"+to_date+"'";
}else if(product_status.equals("Inactive"))
{
Query="select product_name,product_unit,item_inactive from tbl_products where product_id ='"+product_id+"' and item_inactive='"+product_status+"' and created_date::date between '"+from_date+"' and '"+to_date+"'";
}
else
{
Query="select product_name,product_unit,item_prohibited from tbl_products where product_id ='"+product_id+"' and item_prohibited='"+product_status+"' and created_date::date between '"+from_date+"' and '"+to_date+"'";
}
System.out.println(Query);
resultset=statement.executeQuery(Query);
ArrayList arrayList = convertResultSetIntoArrayList(resultset);
if(arrayList!=null &&!arrayList.isEmpty()){
jsonObject.clear();
jsonObject.put("Status", "Success");
jsonObject.put("message", "Return Product Item. ");
jsonObject.put("Data", arrayList);
}else{
jsonObject.clear();
jsonObject.put("Status", "Failed");
jsonObject.put("message", "List is empty or Invalid Product id");
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
return Response.ok().entity(jsonObject)
.header("Access-Control-Allow-Methods", "POST").build();
}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/unitdropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productUnitDropdown(@FormParam("id") String id,@FormParam("type") String type){
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		if(type==null || type.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}
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
		if(type.contains("grams")){
		
			type="g";
		}
		if (type.contains("ml")){
			type="l";
			
		}
		String Query ="select * from product_unit where product_unit like '%"+type+"%'";
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
	@Path("/sizedropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response sizedropDown(@FormParam("id") String id){
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
			System.out.println(role_id);
		}
		String Query ="select id as size_id,productsize from tbl_productsize";
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
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editProductListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editProductList(@FormParam("id") String id,@FormParam("product_id") String product_id){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty() || product_id==null || product_id.isEmpty()) {
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Fields are empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
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
			System.out.println(role_id);
		}
		
		//String Query="select * from tbl_addproduct where product_id="+"'"+product_id+"'";
		String Query="select tbl_products.product_id,tbl_products.product_size,tbl_products.product_name,tbl_products.product_description,"
				+ "tbl_products.product_code,tbl_products.product_image,tbl_brand.brand_name,tbl_products.product_unit, "
				+ "tbl_category.category_id,tbl_subcategory.subcategory_id,tbl_productsize.productsize,"
				+ "tbl_products.product_igst as gst,tbl_products.product_size as size_id,"
				+ "tbl_brand.brand_id,tbl_products.product_status,tbl_products.product_substitution,tbl_products.product_unit,"
				+ "tbl_products.product_cgst,tbl_products.product_sgst,tbl_products.product_igst,tbl_products.alert_quantity,"
				+ "tbl_category.category_name,tbl_subcategory.subcategory_name from tbl_products "
				+ "inner join tbl_brand on tbl_products.product_brand_id=tbl_brand.brand_id "
				+ "inner join tbl_productsize on tbl_productsize.id=tbl_products.product_size "
				+ "inner join tbl_category on tbl_products.product_category_id=tbl_category.category_id "
				+ "inner join tbl_subcategory on tbl_products.product_subcategory_id=tbl_subcategory.subcategory_id "
				+ "where tbl_products.mark_for_deletion=0 and tbl_products.item_prohibited=0 and tbl_products.item_hide=0 and tbl_products.product_id='"+product_id+"'";
		try {
			SqlConnection();
			try {
				//statement=connection.createStatement();
				resultset=statement.executeQuery(Query);
				System.out.println("query= "+Query);
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
	@Path("/updateproductListApi")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateProductList(@FormParam("id") String id,@FormParam("product_id") String product_id,@FormParam("product_code") String product_code,@FormParam("product_name") String product_name,@FormParam("product_category_id") String product_category_id,@FormParam("product_subcategory_id")
	String product_subcategory_id,@FormParam("product_brand_id") String product_brand_id,@FormParam("product_unit") String product_unit,@FormParam("product_size") String product_size,@FormParam("alert_quantity") String alert_quantity,
	@FormParam("product_description") String product_description,@FormParam("product_colour") String product_colour,@FormParam("product_cgst") String product_cgst,@FormParam("product_csgst") String product_sgst,@FormParam("product_igst") String product_igst,
	@FormParam("product_taxable") String product_taxable,@FormParam("gst") String gst,
	@FormParam("product_status") String product_status,@FormParam("product_substitution") String product_substitution){
		JSONObject jsonObject=new JSONObject();
		
//		if(product_id==null || product_id.isEmpty()|product_code==null || product_code.isEmpty()|| product_name==null || product_name.isEmpty()|| product_category_id==null || product_category_id.isEmpty()
//				|| product_subcategory_id==null || product_subcategory_id.isEmpty()|| product_brand_id==null || product_brand_id.isEmpty()|| product_unit==null || product_unit.isEmpty()
//				|| product_size==null || product_size.isEmpty()|| alert_quantity==null || alert_quantity.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					
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
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		String toRemove = "";
		if(gst.contains("%")){
			toRemove=gst.replace("%", "");
		}else{
			toRemove=gst;
		}
		System.out.println(toRemove);
		
		String gstdiv = Integer.parseInt(toRemove)/2+"%";
		System.out.println("gstdiv - "+gstdiv);
		String gstwithPercentage = gst+"%";
		
		
		try {
			SqlConnection();		
			String email=(String) jsObject.get("email");
			try {
				//String Query="UPDATE tbl_addproduct SET product_code='"+product_code+"',product_name='"+ product_name+"',"
				//		+ "product_category='"+product_category+"',product_subcategory='"+product_subcategory+"',product_brand='"+product_brand+"',product_unit='"+product_unit+"',product_size='"+product_size+"',alert_quantity='"+alert_quantity+"' where product_id='"+product_id+"'";
				System.out.println("product_substitute "+product_subcategory_id);
				String Query="UPDATE tbl_products SET product_name='"+ product_name+"',product_code='"+product_code+"',product_description='"+product_description+"',"
						+ "product_category_id='"+product_category_id+"',product_subcategory_id='"+product_subcategory_id+"',"
						+ "product_brand_id='"+product_brand_id+"',product_unit='"+product_unit+"',product_colour='"+product_colour+"',"
						+ "product_size='"+product_size+"',"
						+ "alert_quantity='"+alert_quantity+"',product_cgst='"+gstdiv+"',"
						+ "product_sgst='"+gstdiv+"',product_igst='"+gstwithPercentage+"',"
						+ "product_taxable='"+product_taxable+"',updated_date=current_date,"
						+ "updated_by='"+email+"',product_status='"+product_status+"',product_substitution='"+product_substitution+"' "
						+ " where product_id='"+product_id+"'";
				
				System.out.println(Query);
				int i=statement.executeUpdate(Query);
				System.out.println("i "+i);
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	
	
	/* Store Api's ----------------------------------
	 * ----------------------------------------------
	 * ----------------------------------------------	
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addstore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addstore(@FormParam("id") String id,@FormParam("store_name") String store_name,@FormParam("store_address1") String store_address1,@FormParam("store_address2") String store_address2
			,@FormParam("store_address3") String store_address3,@FormParam("store_contact_number1") String store_contact_number1
			,@FormParam("store_contact_number2") String store_contact_number2,@FormParam("store_mailaddress1") String store_mailaddress1,
			@FormParam("store_mailaddress2") String store_mailaddress2,@FormParam("store_city") String store_city,
			@FormParam("store_state") String store_state,@FormParam("store_country") String store_country){
		JSONObject jsonObject=new JSONObject();
//		if(branch_name==null || branch_name.isEmpty()|| branch_address==null || branch_address.isEmpty()|| branch_city==null || branch_city.isEmpty()|| branch_state==null || branch_state.isEmpty()|| branch_country==null || branch_country.isEmpty()){
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
				
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();
			String storeid=Customer.getGenerateId("STORE",5,connection);
			try {
				
				String userInsertQuery= "insert into tbl_user(username,password,roleid,emailid,status,auth_id) "
						+ "values('"+store_name+"','123456',3,'"+store_mailaddress1+"','A','"+storeid+"')";
                int insertStatus=statement.executeUpdate(userInsertQuery);
                if(insertStatus>0){
                	   jsonObject.clear();
                       jsonObject.put("Status", "Success");
                       jsonObject.put("Message", "Store User Added Successfully");
                }else{
               jsonObject.clear();
               jsonObject.put("Status", "Failed");
               jsonObject.put("Message", "Failed to add store");
               return Response.ok()
			                  .entity(jsonObject)
			                  .header("Access-Control-Allow-Methods", "POST").build();
                     }
				
				String email=(String) jsObject.get("email");
				
//				String Query="insert into tbl_branch(branch_id,branch_name,branch_address,branch_city,branch_state,branch_country,branch_contact_number,branch_contact_email,createdby) "
//						+ "values('"+branchid+"','"+branch_name+"','"+branch_address+"','"+branch_city+"','"+branch_state+"','"+branch_country+"','"+branch_contact_number+"','"+branch_email+"','"+email+"')";
				String Query="insert into tbl_store(store_id,store_name,store_address1,store_address2,store_address3,store_contact_number1,store_contact_number2,store_mailaddress1,store_mailaddress2,store_city,store_state,store_country,created_by) "
						+ "values('"+storeid+"','"+store_name+"','"+store_address1+"','"+store_address2+"','"+store_address3+"','"+store_contact_number1+"','"+store_contact_number2+"','"+store_mailaddress1+"','"+store_mailaddress2+"','"+store_city+"','"+store_state+"','"+store_country+"','"+email+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Store Added Successfully");
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
				
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getStoreList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response storeList(@FormParam("id") String id){
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
		}
		String Query ="select * from tbl_store where mark_for_deletion=0";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editStore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editStore(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		if(store_id==null || store_id.isEmpty()) {
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
		
		String Query="select * from tbl_store where store_id="+"'"+store_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
	@Path("/updateStore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updateStore(@FormParam("id") String id,@FormParam("store_id") String store_id,@FormParam("store_name") String store_name,@FormParam("store_address1") String store_address1,@FormParam("store_address2") String store_address2
			,@FormParam("store_address3") String store_address3,@FormParam("store_contact_number1") String store_contact_number1
			,@FormParam("store_contact_number2") String store_contact_number2,@FormParam("store_mailaddress1") String store_mailaddress1,
			@FormParam("store_mailaddress2") String store_mailaddress2,@FormParam("store_city") String store_city,
			@FormParam("store_state") String store_state,@FormParam("store_country") String store_country){
		JSONObject jsonObject=new JSONObject();
//		if(branch_id==null || branch_id.isEmpty()|| branch_name==null || branch_name.isEmpty()|| branch_address==null || branch_address.isEmpty()
//				|| branch_city==null || branch_city.isEmpty()|| branch_state==null || branch_state.isEmpty()|| branch_country==null || branch_country.isEmpty()) {
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String email=(String) jsObject.get("email");
				String Query="UPDATE tbl_store SET store_name='"+store_name+"',store_address1='"+ store_address1+"',store_address2='"+store_address2+"',store_address3='"+store_address3+"',"
						+ "store_contact_number1='"+store_contact_number1+"',store_contact_number2='"+store_contact_number2+"',store_mailaddress1='"+store_mailaddress1+"',store_mailaddress2='"+store_mailaddress2+"',"
								+ "store_city='"+store_city+"',store_state='"+store_state+"',store_country='"+store_country+"',updated_date=current_date,updated_by='"+email+"' where store_id='"+store_id+"'";
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Store Updated Successfully");
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
	 Delete Branch Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deleteStore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletestore(@FormParam("id") String id,@FormParam("store_id") String store_id){
		JSONObject jsonObject=new JSONObject();
		if(store_id==null || store_id.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="update tbl_store set mark_for_deletion=1 where store_id='"+store_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	
	/*
	 Insert api for branch 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/branch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response branch(@FormParam("id") String id,@FormParam("branch_name") String branch_name,@FormParam("branch_address") String branch_address,@FormParam("branch_city") String branch_city
			,@FormParam("branch_state") String branch_state,@FormParam("branch_country") String branch_country
			,@FormParam("branch_contact_number") String branch_contact_number,@FormParam("branch_email") String branch_email){
		JSONObject jsonObject=new JSONObject();
		if(branch_name==null || branch_name.isEmpty()|| branch_address==null || branch_address.isEmpty()|| branch_city==null || branch_city.isEmpty()|| branch_state==null || branch_state.isEmpty()|| branch_country==null || branch_country.isEmpty()){
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
		try {
			SqlConnection();
			String branchid=Customer.getGenerateId("BRANCH",3,connection);
			try {
				String email=(String) jsObject.get("email");
				
				String Query="insert into tbl_branch(branch_id,branch_name,branch_address,branch_city,branch_state,branch_country,branch_contact_number,branch_contact_email,createdby) "
						+ "values('"+branchid+"','"+branch_name+"','"+branch_address+"','"+branch_city+"','"+branch_state+"','"+branch_country+"','"+branch_contact_number+"','"+branch_email+"','"+email+"')";
				
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Store Added Successfully");
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
		
	/*
	 get branch list api
	 */
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getBranchlist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response branchList(@FormParam("id") String id){
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
		}
		String Query ="select * from tbl_branch";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	/*
	 Edit Branch Api
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editbranch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editbranch(@FormParam("id") String id,@FormParam("branch_id") String branch_id){
		JSONObject jsonObject=new JSONObject();
		if(branch_id==null || branch_id.isEmpty()) {
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
		
		String Query="select * from tbl_branch where branch_id="+"'"+branch_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update Branch Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatebranch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatebranch(@FormParam("id") String id,@FormParam("branch_id") String branch_id,@FormParam("branch_name") String branch_name,@FormParam("branch_address") String branch_address,@FormParam("branch_city")
	String branch_city,@FormParam("branch_state") String branch_state,@FormParam("branch_country") String branch_country
	,@FormParam("branch_contact_number") String branch_contact_number,@FormParam("branch_email") String branch_email){
		JSONObject jsonObject=new JSONObject();
		if(branch_id==null || branch_id.isEmpty()|| branch_name==null || branch_name.isEmpty()|| branch_address==null || branch_address.isEmpty()
				|| branch_city==null || branch_city.isEmpty()|| branch_state==null || branch_state.isEmpty()|| branch_country==null || branch_country.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String email=(String) jsObject.get("email");
				String Query="UPDATE tbl_branch SET branch_name='"+branch_name+"',branch_address='"+ branch_address+"',"
						+ "branch_city='"+branch_city+"',branch_state='"+branch_state+"',branch_country='"+branch_country+"',branch_contact_number='"+branch_contact_number+"',"
								+ "branch_contact_email='"+branch_email+"',updatedon=current_date,updatedby='"+email+"' where branch_id='"+branch_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete Branch Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletebranch")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletebranch(@FormParam("id") String id,@FormParam("branch_id") String branch_id){
		JSONObject jsonObject=new JSONObject();
		if(branch_id==null || branch_id.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="DELETE from tbl_branch where branch_id='"+branch_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Insert api for Discount 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/discount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response discount(@FormParam("id") String id,@FormParam("discount_id") String discount_id,@FormParam("discount_name") String discount_name,@FormParam("discount_value") String discount_value){
		JSONObject jsonObject=new JSONObject();
		if(discount_name==null || discount_name.isEmpty()||discount_value==null || discount_value.isEmpty()){
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
		try {
			SqlConnection();
			String discountid=Customer.getGenerateId("DISOCUNT",5,connection);
			try {
				String Query="insert into tbl_discount(discount_id,discount_name,discount_value) "
						+ "values('"+discountid+"','"+discount_name+"','"+discount_value+"')";
				
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
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getDiscountlist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response discountList(@FormParam("id") String id){
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
		}
		String Query ="select * from tbl_discount";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	/*
	 Edit Discount Api
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/editdiscount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editdiscount(@FormParam("id") String id,@FormParam("discount_id") String discount_id){
		JSONObject jsonObject=new JSONObject();
		if(discount_id==null || discount_id.isEmpty()) {
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
		
		String Query="select * from tbl_discount where discount_id="+"'"+discount_id+"'";
		try {
			SqlConnection();
			try {
			
				resultset=statement.executeQuery(Query);
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/*
	 Update Discount Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatediscount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response updatediscount(@FormParam("id") String id,@FormParam("discount_id") String discount_id,@FormParam("discount_name") String discount_name,@FormParam("discount_value") String discount_value){
		JSONObject jsonObject=new JSONObject();
		if(discount_id==null || discount_id.isEmpty()|| discount_name==null || discount_name.isEmpty()|| discount_value==null || discount_value.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
			try {
				String Query="UPDATE tbl_discount SET discount_name='"+discount_name+"',discount_value='"+ discount_value+"' where discount_id='"+discount_id+"'";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}	
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	/*
	 Delete Discount Api
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletediscount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response deletediscount(@FormParam("id") String id,@FormParam("discount_id") String discount_id){
		JSONObject jsonObject=new JSONObject();
		
		if(discount_id==null || discount_id.isEmpty()) {
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
					.header("Access-Control-Al																					low-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				String Query="DELETE from tbl_discount where discount_id='"+discount_id+"'";
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
	@Path("/productDiscountdropdwn")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response productDiscountdropdwn(@FormParam("id") String id,@FormParam("product_id") String product_id){
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
		}
		String Query ="select discount_id,discount_name from tbl_discount";
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
				
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	

	
	/*
	 * public static String getProductId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_product_id_numberrange_function('"+id+"')";
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
	 * public static String getProductCategoryId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_category_id_numberrange_function('"+id+"')";
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
	 * public static String getStoreId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_store_id_numberrange_function('"+id+"')";
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
	 * public static String getProductSubCategoryId(String id){
	 * 
	 * String
	 * numberrange_function="select fn_subcategory_id_numberrange_function('"+id+
	 * "')";
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

	public static Connection SqlConnection() throws IOException{
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
	
	
	public static String getProductBrandId(String id){

		String numberrange_function="select fn_brand_id_numberrange_function('"+id+"')";

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
	/*
	 * author- siba
	 */
	@POST
	@Path("/addProductApi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addProduct(Products products) throws SQLException{
		String id=products.getId();
		String product_id=products.getProduct_id();
		String product_name=products.getProduct_name();
		String product_description=products.getProduct_description();
		String product_code=products.getProduct_code();
		String product_category_id=products.getProduct_category_id();
		String product_subcategory_id=products.getProduct_subcategory_id();
		String product_brand_id=products.getProduct_brand_id();
		int product_unit=products.getProduct_unit();
		String product_colour=products.getProduct_colour();
		int product_size=products.getProduct_size();
		String product_image=products.getProduct_image();
		String barcode_number=products.getBarcode_number();
		String barcode_image=products.getBarcode_image(); 
		int alert_quantity=products.getAlert_quantity();
		double product_cgst=products.getProduct_cgst();
		double product_sgst=products.getProduct_sgst();
		double product_igst=products.getProduct_igst();
		String product_taxable=products.getProduct_taxable();
		String gst = products.getGst();
		String vendor_id=products.getVendor_id();
		String product_status =products.getProduct_status();
		String product_substitution = products.getProduct_substitution();
		String batch_id=products.getBatch_id();
		String product_type=products.getProduct_type();
		String hsn_code=products.getHsn_code();
		String product_gst=products.getProduct_gst();	
		double price=products.getPrice();
		System.out.println("vendor_id "+vendor_id);
		/*these were marked commented before.*/
		//String nontaxable=products.getProduct_nontaxable();
		//String productunit_value=products.getProductunit_value();
//		JSONObject product_image=products.getProduct_icon();
		JSONObject jsonObject=new JSONObject();
		System.out.println(id);
		if(id==null||id.isEmpty()){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message",  "Id value is empty");
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}
		
//		if(id==null || id.isEmpty() || product_name==null || product_name.isEmpty() || product_description==null || product_description.isEmpty() ||product_code==null || product_code.isEmpty()
//		||product_category_id==null || product_category_id.isEmpty()||product_subcategory_id==null || product_subcategory_id.isEmpty()
//		||product_brand_id==null || product_brand_id.isEmpty()||price<0||vendor_id==null){
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message",  "Fields are empty");
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
//		}
		
		//String produnitvalue=productunit_value+product_unit;
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
			System.out.println(role_id);
		}
		
		/*I write this operation*/
		System.out.println("gst="+ gst+"product_cgst="+ product_cgst+"product_sgst="+product_sgst+"product_igst="+product_igst);
		
		double gstValue=0.0;
		if(gst==null && gst.isEmpty()){
			gstValue=0.0;	
			System.out.println("if-gst="+gst);
			
		}else if(gst.contains("%")){
			String toRemove=gst.replace("%", "");
			gstValue=Double.parseDouble(toRemove);
			System.out.println("else is-gst="+gst);
		}else {
			gstValue=Double.parseDouble(gst);
			System.out.println("else-gst="+gst);
		}
		
		/* I commented these operations!
		if(product_gst==null&&gst.isEmpty()){
			product_gst="0";
			
		}
		
		//String ssv="12%";
		String toRemove = "";
		if(product_gst.contains("%")){
			toRemove=product_gst.replace("%", "");
		}else{
			toRemove=product_gst;
		}
		
		System.out.println(toRemove);
		
		//String gstdiv = Integer.parseInt(toRemove)/2+"%";
		double gstdiv = Double.parseDouble(toRemove)/2;
		
		System.out.println(gstdiv);

		String gstWithPercentage=product_gst;		
		System.out.println(gstWithPercentage);
		/*
		
		
		/*already commented*/
//		vendor_id=vendor_id.substring(1,vendor_id.length()-1);
//		System.out.println(vendor_id);
//		String toRemove1 = "";
//		
//		if(vendor_id.contains("\\")){
//			toRemove1=vendor_id.replace("\\(.)", "");
//			System.out.println("the final string is:" + vendor_id);
//			
//		}else{
//			
//		}
		try {
			System.out.println("vendor_id-- "+vendor_id);
			connection=SqlConnection();
			connection.setAutoCommit(false);
			String productid=Customer.getGenerateId("PRODUCT",7,connection);
			
			try {
				String email=(String) jsObject.get("email");
//				String Query="select fn_add_product('"+productid+"','"+product_name+"','"+product_description+"',"
//						+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
//								+ "'"+product_unit+"','"+product_colour+"','"+product_size+"','"+product_image+"',"
//								+ "'"+alert_quantity+"','"+gstdiv+"','"+gstdiv+"','"+gstWithPercentage+"','"+product_taxable+"','"+price+"',"
//										+ "'"+vendor_id+"','"+email+"','"+product_status+"','"+product_substitution+"')";
				
				/* i commented these code*/
			/*	String Query="select fn_add_product('"+productid+"','"+product_name+"','"+product_description+"',"
						+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
								+ "'"+product_unit+"','"+product_size+"','"+product_image+"','"+hsn_code+"','"+product_type+"','"+batch_id+"','"+product_gst+"'"
								+ "'"+alert_quantity+"','"+gstdiv+"','"+gstdiv+"','0','"+product_taxable+"','"+price+"',"
										+ "'"+vendor_id+"','"+email+"','"+product_status+"','"+product_substitution+"')";
				*/
				/*i am using insert query instead of functions*/
				String Query="insert into tbl_products(product_id,product_code,product_name,product_description,product_category_id,"
						+ "product_subcategory_id,product_brand_id,product_unit,product_size,product_image,alert_quantity,product_cgst,"
						+ "product_sgst,product_taxable,product_igst,"
						+ "vendor_id,product_type,hsn_code,product_status,batch_id,product_substitution,price)"
						+ "values('"+productid+"','"+product_code+"','"+product_name+"','"+product_description+"','"+product_category_id+"','"+product_subcategory_id+"',"
						+ "'"+product_brand_id+"','"+product_unit+"','"+product_size+"','"+product_image+"','"+alert_quantity+"',"
						+ "'"+gstValue+"','"+gstValue+"','"+product_taxable+"','"+gstValue+"',"
						+ "'"+vendor_id+"','"+product_type+"','"+hsn_code+"','"+product_status+"','"+batch_id+"','"+product_substitution+"','"+price+"')";
				
				
				System.out.println("Add product statement:" +statement);
				
//				CallableStatement callableStatement=connection.prepareCall(Query);
//				callableStatement.execute();
//				ResultSet resultSet=callableStatement.getResultSet();	
				int st=statement.executeUpdate(Query);
				System.out.println("Add product statement:" +statement);
				System.out.println("st "+st);
				if(st>0) {
					int hsngst=Integer.parseInt(gst);
					System.out.println("hsngst= "+hsngst);
					 String insertHsnMaster= "insert into tbl_hsn_master (hsn_code,gst,created_on,product_id,created_by)values ('"+hsn_code+"',"+hsngst+",current_timestamp,'"+productid+"','"+email+"')";    
					 
					 int result1=statement.executeUpdate(insertHsnMaster);
					 String insertHSNRevised=" insert into tbl_hsncode_revised (hsn_code,gst,created_on,product_id,created_by)values ('"+hsn_code+"',"+hsngst+",current_timestamp,'"+productid+"','"+email+"')";
					 int result2=statement.executeUpdate(insertHSNRevised);
					
				
//				String result=new String();
//				while(resultSet.next()){
//					result=resultSet.getString(1);
//				}
				/*----------------------     Add Vendor -------------------------------*/
				
				 //I commented this
				 /*vendor_id=vendor_id.substring(1,vendor_id.length()-1);
				    String[] parts = vendor_id.split(",");			
				    System.out.println(parts);
					for(int i=0;i<parts.length;i++){
					System.out.println(parts[i]);
					*/
					 if(result1>0 && result2>0){
						String vendorPriceQuery="insert into tbl_vendor_pricing_table(product_id,product_name,product_description,"
								+ "product_code,product_category_id,product_subcategory_id,product_brand_id,product_unit,product_colour,"
								+ "product_size,product_image,alert_quantity,product_cgst,product_sgst,product_igst,product_taxable,"
								+ "product_price, vendor_id,created_by,product_status,product_substitute,hsn_code,product_type,batch_id)"
						+ "	values('"+productid+"','"+product_name+"','"+product_description+"',"
										+ "'"+product_code+"','"+product_category_id+"','"+product_subcategory_id+"','"+product_brand_id+"',"
										+ "'"+product_unit+"','"+product_colour+"','"+product_size+"','"+product_image+"',"
										+ "'"+alert_quantity+"','"+gstValue+"','"+gstValue+"','"+gstValue+"','"+product_taxable+"','"+price+"',"
										+ "'"+vendor_id+"','"+email+"','"+product_status+"','"+product_substitution+"','"+hsn_code+"','"+product_type+"','"+batch_id+"')";
						
						
						
						int st1=statement.executeUpdate(vendorPriceQuery);
						System.out.println("the vendor statement:" +statement);
						System.out.println("the vendor vendor_id:" +vendor_id);
						connection.commit();
						jsonObject.clear();
						jsonObject.put("Status", "Success");
						jsonObject.put("Message", "Product Added Successfully");
					 }
					 else {
							connection.rollback();
							jsonObject.clear();
							jsonObject.put("Status", "Failed");
							jsonObject.put("Message", "Failed to Add");
						}

				}else {
					connection.rollback();
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add");
				}
				// I commented this
						/*if(st1>0){
							jsonObject.clear();
							jsonObject.put("Status", "Success");
							jsonObject.put("Message", "Added Successfully");
						}else{
							jsonObject.clear();
							jsonObject.put("Status", "Failed");
							jsonObject.put("Message", "Failed to Add");
						}*/
				 
				/*if(result.contains("Success")){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Product Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to Add Product");
				}*/
				
						
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
		} catch (IOException e) {
			e.printStackTrace();
			jsonObject.clear();
			 jsonObject.put("Status", "Failed");
			 jsonObject.put("Message", "Something went wrong");
			 jsonObject.put("error", e.getMessage());
		}finally{
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
	
}
