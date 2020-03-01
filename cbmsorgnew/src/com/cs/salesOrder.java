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

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;

import com.cs.model.Sale;
import com.helper.Config;
import com.helper.DBConnection;

@Path("salesorder")
public class salesOrder {
	
	
	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/generateSalesId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generateSalesOrderId(@FormParam("id") String id){
//		,@FormParam("warehouse_name") String warehouse_name
		JSONObject jsonObject=new JSONObject();
		System.out.println(id);
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
//			if(role_id==4){
//				
//			}
//			System.out.println(role_id);	
		}
		String email=(String) jsObject.get("email");
		Connection connectionSales=null;
		try {
			//connectionSales=DBConnection.SqlConnection();
			SqlConnection();
			try {
				
				String salesReferenceId= Customer.getGenerateId("SALES",8,connection);
				System.out.println("the value is :"+salesReferenceId);
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
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/generateSalesReturnId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response generateSalesReturnId(@FormParam("id") String id){
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
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
//			if(role_id==4){
//				
//			}
//			System.out.println(role_id);	
		}
		String email=(String) jsObject.get("email");
		Connection connectionSales=null;
		try {
			//connectionSales=DBConnection.SqlConnection();
			SqlConnection();
			try {
				
				String salesReturnId= Customer.getGenerateId("SRET",8,connection);
				//System.out.println("the value is :"+generateInvoiceid);
				if(salesReturnId!=null&&!salesReturnId.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Data", salesReturnId);
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
	@Path("/sales_product_calculations")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
//	public Response calculate_invoice(@FormParam("id")String id,@FormParam("purchaseorder_id")
//	String purchaseorder_id,@FormParam("invoice_id")String invoice_id,@FormParam("product_details")String ){
		public Response calculate_sales(Sale sale){
			String id =sale.getId();
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
				
				String sales_id=sale.getSales_id();
				String store_id=sale.getStore_id();
				//String invoiceid = invoice.getInvoice_id();
				String ProductDetails=sale.getProduct_details();
				//String type=invoice.getType();
				org.json.JSONArray product_details=null;
				String auth_id=(String) jsObject.get("auth_id");
				if(role_id==1){
					
				}else{
					store_id=auth_id;
				}
				try {
					product_details = new org.json.JSONArray(ProductDetails);
				} catch (JSONException e1) {
			
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
							String product_description = productObject.getString("product_name");
							String[] parts = product_description.split("-");
							//String lastpart = parts[4];
							//String[] bits = one.split("-");
							String product_id = parts[parts.length-1];
						
							String S_CGST="";
							String S_SGST="";
							String S_IGST="";
							
						//if(type!=null && !type.toLowerCase().equals("v")){
						
							PreparedStatement preparedStatement=connectionWar.prepareStatement(Config.gst_product);
							preparedStatement.setString(1,product_id);
							ResultSet resultSet=preparedStatement.executeQuery();
							System.out.println("from database");

							while(resultSet.next()){
							//	product_id=resultSet.getString("product_id");
								S_CGST=resultSet.getString("product_cgst");
								S_SGST=resultSet.getString("product_sgst");
								S_IGST=resultSet.getString("product_igst");
								
								System.out.println(S_CGST);
								System.out.println(S_SGST);
								System.out.println(S_IGST);
							}
							if(S_CGST==null||S_CGST.isEmpty()){
								S_CGST="0%";
							}if(S_SGST==null||S_SGST.isEmpty()){
								S_SGST="0%";
							}if(S_IGST==null||S_IGST.isEmpty()){
								S_IGST="0%";
							}
							
							if(S_CGST.contains("%")){
								S_CGST=S_CGST.replace("%", "");
							}if(S_IGST.contains("%")){
								S_IGST=S_IGST.replace("%", "");
							}if(S_SGST.contains("%")){
								S_SGST=S_SGST.replace("%", "");
							}
							System.out.println("*******************************************************");
							System.out.println(S_CGST);
							System.out.println(S_SGST);
							System.out.println(S_IGST);
							
							double CGST=Double.parseDouble(S_CGST);
							double SGST=Double.parseDouble(S_SGST);
							double IGST=Double.parseDouble(S_IGST);
							
							System.out.println(CGST);
							System.out.println(SGST);
							System.out.println(IGST);

							
							
							
							
							//}
//						else{
//							
//							
//							 CGST=productObject.getDouble("CGST");
//							 SGST=productObject.getDouble("SGST");
//							 IGST=productObject.getDouble("IGST");
//						}
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
									productObject.put("product_id", product_id);
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
								productObject.put("product_id", product_id);
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
								productObject.put("product_id", product_id);
								product_details.put(i, productObject);	
								
								
								grand_total=net_amount+grand_total;
							    total_discount=discount+total_discount;
							    totalDiscountInAmount=discount_net_amount+totalDiscountInAmount;
							    totalOrginalAmount=origenal_amount+totalOrginalAmount;
							}
							
							
							
						} catch (Exception e) {
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

					PreparedStatement preparedStatement=connectionWar.prepareStatement(Config.calculate_salesorder);
					preparedStatement.setString(1,email);
					preparedStatement.setString(2,sales_id);
					preparedStatement.setObject(3,pGobject);
					preparedStatement.setString(4,store_id);

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
					e.printStackTrace();
					jsonObject.put("status", "failed");
					jsonObject.put("message", e.getMessage()+"");
				}
				
				finally {
					try {
						DBConnection.closeConnection(connectionWar);
					} catch (SQLException e) {
						
					}
				}
		
				}
		
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	/*
	 * use to add b2b customer details
	 * and b2c customer details  and update whether b2c customer are
	 * recurring or not
	 * */
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/addsalesOrderdata")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addsalesOrderdata(@FormParam("id") String id,@FormParam("sales_id") String sales_id,@FormParam("comment") String comment,@FormParam("credit_type") String credit_type,
			@FormParam("store_id") String store_id,@FormParam("customer_id") String customer_id,@FormParam("customer_contact_number") String customer_contact_number,@FormParam("credit_value") String credit_value,
			@FormParam("customer_name") String customer_name,@FormParam("member_id") int member_id,@FormParam("credit") String credit,@FormParam("credit_amount") double credit_amount,
			@FormParam("payment_type") String payment_type,@FormParam("received_amount") double received_amount,@FormParam("customer_address") String customer_address,@FormParam("salesorder_id") String salesorder_id,
			@FormParam("comments") String comments,@FormParam("member_type_id") int member_type_id,@FormParam("customer_email") String customer_email){
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		System.out.println("credit_value :"+ credit_value);
		Connection conn=null;
		if(jsObject.containsKey("status")){
			jsObject.clear();
			jsObject.put("status", "Failed");
			jsObject.put("message", jsObject.get("status"));
			return Response.ok()
					.entity(jsObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			String auth_id=(String) jsObject.get("auth_id");
			if(role_id==1){
				
			}else{
				store_id=auth_id;
			}
		}
		Connection connectionInv=null;
		
		try{
		
			connectionInv=sqlConnection();
		int shopped =0;
		connectionInv.setAutoCommit(false);
		try{
			
			double total_balance=0;
			double opening_balance=0;
			String Query="select balance,opening_balance from gendral_ledger order by ledger_id desc limit 1 ";
			
			ResultSet resultSet1 = statement.executeQuery(Query);
			System.out.println("Query "+Query);
			System.out.println(resultSet1);
			resultSet1.next();
			total_balance=resultSet1.getDouble(1);
			opening_balance=resultSet1.getDouble(2);
			
			if(comments==null || comments.isEmpty()){
				comments=" ";
			}else{
				
			}
			String email=(String) jsObject.get("email");
			connectionInv = DBConnection.SqlConnection();
			//connectionInv.commit();
			PreparedStatement preparedStatement= connectionInv.prepareStatement(Config.add_salesorder);
			preparedStatement.setString(1,store_id);
			preparedStatement.setString(2,sales_id);
			preparedStatement.setString(3,email);
			preparedStatement.setString(4,customer_id);
			preparedStatement.setString(5,"");
			preparedStatement.setInt(6, member_id);
			preparedStatement.setString(7, payment_type);
			preparedStatement.setString(8, comments);
			preparedStatement.setDouble(9, received_amount);
			preparedStatement.setDouble(10, total_balance);
			preparedStatement.setDouble(11, opening_balance);
			preparedStatement.setInt(12, member_type_id);
		
			//String Query="select fn_store_stock_outdetails('"+lastpart+"','"+sales_id+"','"+store_id+"','"+email+"','"+quantity+"')";
			
			ResultSet resultSet=preparedStatement.executeQuery();	
			System.out.println(Config.add_salesorder+" and "+preparedStatement);
			String fresult=null;		
			while(resultSet.next()){
				fresult=resultSet.getString(1);
			}
			System.out.println("fresult: "+fresult);
			System.out.println("resultSet: "+resultSet);
		if(fresult.contains("Success")){
		//	connectionInv.commit();
			String query=null;
			int i=0;
			double S_totl_amnt=0.0;
			if(credit.equals("yes")){		
			double total_amnt=0.0;	
				if(credit_type.equals("percentage"))
				{
					query="select sum(quantity*product_total) as total from tbl_sales where salesorder_id='"+salesorder_id+"'";
					System.out.println("query---"+query);
					ResultSet resultset=statement.executeQuery(query);
					
					
					while(resultSet.next()){
						S_totl_amnt=resultSet.getDouble(1);
					}
					System.out.println("total: "+S_totl_amnt);
					double C_amnt=Double.parseDouble(credit_value);
					 total_amnt =(S_totl_amnt * C_amnt)/100;
					
				}else{
					
					double C_amnt=Double.parseDouble(credit_value);
					total_amnt=C_amnt;
					//double = Double.parse(string);
					System.out.println("total_amnt--"+total_amnt);
				}
			  query="insert into sales_customer_details (customer_name,customer_email,customer_address,"+
						 " payment_type,received_amount,comment,salesorder_id,customer_contact_number,createdon,credit,credit_value,credit_amount) values(?,?,?,?,?,?,?,?,current_timestamp,?,?,?)";
				preparedStatement= connectionInv.prepareStatement(query);
				preparedStatement.setString(1, customer_name);
				preparedStatement.setString(2, customer_email);
				preparedStatement.setString(3, customer_address);
				preparedStatement.setString(4, payment_type);
				preparedStatement.setDouble(5, received_amount);
				preparedStatement.setString(6, comment);
				preparedStatement.setString(7, sales_id);
				preparedStatement.setString(8, customer_contact_number);
				preparedStatement.setString(9, credit);
				preparedStatement.setDouble(10, Double.parseDouble(credit_value));
				preparedStatement.setDouble(11, total_amnt);
				 i=preparedStatement.executeUpdate();
				System.out.println("querycustomer insertion:" +i);
			}else{
				query="insert into sales_customer_details (customer_name,customer_email,customer_address,"+
						 " payment_type,received_amount,comment,salesorder_id,customer_contact_number,createdon) values(?,?,?,?,?,?,?,?,current_timestamp)";
				preparedStatement= connectionInv.prepareStatement(query);
				preparedStatement.setString(1, customer_name);
				preparedStatement.setString(2, customer_email);
				preparedStatement.setString(3, customer_address);
				preparedStatement.setString(4, payment_type);
				preparedStatement.setDouble(5, received_amount);
				preparedStatement.setString(6, comment);
				preparedStatement.setString(7, sales_id);
				preparedStatement.setString(8, customer_contact_number);
				 i=preparedStatement.executeUpdate();
				System.out.println("querycustomer insertion:" +i);
			}
				if(i>0){
					//connectionInv.commit();
					String query1="insert into tbl_sales_transactions (customer_name,customer_email,customer_contact_number,createdon)"
								+" values(?,?,?,current_timestamp)";
					preparedStatement= connectionInv.prepareStatement(query1);
					preparedStatement.setString(1, customer_name);
					preparedStatement.setString(2, customer_email);
					preparedStatement.setString(3, customer_contact_number);
					int j=preparedStatement.executeUpdate();
					System.out.println("insert in sales transactions:"+j);	
					String cc_no=null;
					String c_email=null;
				if(j>0){
					//connectionInv.commit();
					String query2 ="SELECT customer_contact_number,customer_email,COUNT(*) as shopped FROM sales_customer_details GROUP BY customer_email,customer_contact_number ";
					ResultSet resultFromQuery=connectionInv.createStatement().executeQuery(query2);
					
					while(resultFromQuery.next()) {				
						shopped=resultFromQuery.getInt("shopped");	
						c_email=resultFromQuery.getString("customer_email");
						cc_no=resultFromQuery.getString("customer_contact_number");
					}	
				
					System.out.println("shopped: "+shopped);
						if(shopped>=0){
							//connectionInv.commit();
							String query5="update tbl_sales_transactions set recurring_customer='yes',no_of_trans='"+shopped+"' where customer_contact_number='"+cc_no+"' and customer_email='"+c_email+"' ";
							System.out.println("last query.."+query5);
							preparedStatement= connectionInv.prepareStatement(query5);
							int k=preparedStatement.executeUpdate();
							System.out.println("successfully updated recurring status :"+k);
						}else{
							connectionInv.rollback();
							jsonObject.clear();
							jsonObject.put("status", "Failed");
							jsonObject.put("message", "Failed To UPDATE RECURRING STATUS");
						}
				}else{
				connectionInv.rollback();
				jsonObject.clear();
				jsonObject.put("status", "Failed");
				jsonObject.put("message", "Failed  ");
				}
				connectionInv.setAutoCommit(true);
			jsonObject.clear();
			jsonObject.put("status", "Success");
			jsonObject.put("message", "SalesOrder Generated Successfully");
				}
		
				else{
					connectionInv.rollback();
					jsonObject.clear();
					jsonObject.put("status", "Failed");
					jsonObject.put("message", "Failed To Generate SalesOrder ");
				}
		}else{
			connectionInv.rollback();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Failed  ");
		}
		}catch(Exception e){
			connectionInv.rollback();
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Failed To Generate SalesOrder ");
		}
		}catch(Exception e){
			try {
				connectionInv.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Failed To Generate SalesOrder ");
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
//	
//	@SuppressWarnings("unchecked")
//	@POST
//	@Path("/salesList")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public static Response salesList(@FormParam("id") String id){
//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
//		System.out.println(jsObject);
//		if(jsObject.containsKey("status")){
//			jsObject.clear();
//			jsObject.put("status", "Failed");
//			jsObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsObject)
//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//		}else{
//			@SuppressWarnings("unused")
//			String email=(String) jsObject.get("email");
//			@SuppressWarnings("unused")
//			int role_id=(int) jsObject.get("role_id");
//		}
//		Connection connectionInv=null;
//		try{
//			String email=(String) jsObject.get("email");
//			connectionInv = DBConnection.SqlConnection();
//			PreparedStatement preparedStatement= connectionInv.prepareStatement(Config.salesList);
////			preparedStatement.setString(1,store_id);
////			preparedStatement.setString(2,sales_id);
////			preparedStatement.setString(3,email);
////			preparedStatement.setString(4,customer_id);
////			preparedStatement.setString(5,customer_name);
//			@SuppressWarnings("unused")
//			ResultSet resultSet=preparedStatement.executeQuery();
//			ArrayList arrayList = convertResultSetIntoJSON(resultSet);
//			jsonObject.clear();
//			jsonObject.put("status", "Success");
//		//	jsonObject.put("message", "");
//			jsonObject.put("data", arrayList);
//		}catch(Exception e){
//			e.printStackTrace();
//			jsonObject.clear();
//			jsonObject.put("status", "Failed");
//			jsonObject.put("message", "List is Empty ");
//		}
//		return Response.ok()
//				.entity(jsonObject)
//				.header("Access-Control-Allow-Methods", "POST").build();
//	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/salesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response salesList(@FormParam("id") String id,@FormParam("store_id") String store_id){
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
			String auth_id=(String) jsObject.get("auth_id");
			int role_id=(int) jsObject.get("role_id");
            if(role_id==1){
				
			}else{
				store_id=auth_id;
			}
			System.out.println("the aiuth id is: "+store_id);
		}
		String Query ="select  distinct tbl_sales.salesorder_id,tbl_salesorder.total_discount, tbl_sales.customer_id,\r\n" + 
				"fixed_customer_details.customer_name,tbl_salesorder.total_amount,\r\n" + 
				"tbl_sales.purchase_time, tbl_salesorder.purchased_date from tbl_sales  \r\n" + 
				"inner join tbl_salesorder on tbl_sales.salesorder_id=tbl_salesorder.salesorder_id \r\n" + 
				"inner join fixed_customer_details on fixed_customer_details.customer_id= tbl_sales.customer_id\r\n" + 
				"where tbl_sales.store_id='"+store_id+"'";
		
		System.out.println(Query);
		try {
			SqlConnection();
			try {
				ResultSet resultset=statement.executeQuery(Query);
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
	@Path("/salesCompleteList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response salesCompleteList(@FormParam("id") String id,@FormParam("sales_id") String sales_id){
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
		
		String Grand_total="select tbl_salesorder.total_value as Total_original_amount,"
				+ "tbl_salesorder.total_discount as Total_discount_in_amount,"
				+ "tbl_salesorder.total_tax as Total_tax_in_amount,"
				+ "tbl_salesorder.t_discount,tbl_salesorder.t_tax,"
				+ "tbl_salesorder.total_amount as Grand_total from tbl_sales "
				+ "inner join tbl_products on tbl_products.product_id = tbl_sales.product_id "
				+ "inner join tbl_salesorder on tbl_salesorder.salesorder_id=tbl_sales.salesorder_id "
				+ "where tbl_salesorder.salesorder_id='"+sales_id+"'";
		
		
		String Query ="select distinct tbl_sales.product_id,tbl_sales.quantity, tbl_warehouse_purchase_order.product_description,"
				+ "tbl_sales.product_price,tbl_sales.cgst,tbl_sales.sgst,tbl_sales.igst,"
				+ "tbl_sales.discount,tbl_sales.cgst_amount,"
				+ "tbl_sales.sgst_amount,tbl_sales.igst_amount,"
				+ "tbl_sales.discount_amount,tbl_sales.product_total,"
				+ "tbl_salesorder.total_value,tbl_salesorder.total_discount,"
				+ "tbl_salesorder.total_tax,tbl_salesorder.t_discount,tbl_salesorder.t_tax,"
				+ "tbl_salesorder.total_amount from tbl_sales "
				+ "inner join tbl_warehouse_purchase_order on tbl_warehouse_purchase_order.product_id= tbl_sales.product_id "
				+ "inner join tbl_products on tbl_products.product_id = tbl_sales.product_id "
				+ "inner join tbl_salesorder on tbl_salesorder.salesorder_id=tbl_sales.salesorder_id "
				+ "inner join tbl_brand on tbl_brand.brand_id=tbl_products.product_brand_id "
				+ "where tbl_sales.salesorder_id='"+sales_id+"'";
		
		System.out.println("The Sales Order Query is:" + Query);
				try {
			SqlConnection();
			try {
				ResultSet resultSet = statement.executeQuery(Grand_total);
				JSONObject resJsonObject=convertResultSetIntoJSONObject(resultSet);
				ResultSet resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayLists(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Grand_total",resJsonObject);
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
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("/salesOrderInBetweenDates")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response purchaseOrderInBetweenDates(@FormParam("id") String id,@FormParam("start_date") String start_date,
			@FormParam("end_date") String end_date,@FormParam("store_id") String store_id){
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
				store_id=auth_id;
			}
			System.out.println(role_id);
		}
		String Query ="select distinct salesorder_id,purchase_date from tbl_sales "
				+ "where purchase_date >='"+start_date+"' and purchase_date<'"+end_date+"' "
				+ "and store_id='"+store_id+"'";
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
	
	
	/*
	 * @SuppressWarnings("null") public static String getSalesId(String id){
	 * 
	 * String numberrange_function="select sale_id_numberrange_function('"+id+"')";
	 * 
	 * int Rid=0; Connection connection=null; try{
	 * System.out.println(numberrange_function); CallableStatement
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
	
	public static void SqlConnection() throws IOException{
		Properties prop=new Properties();
		String filename="dbconnection.properties";
		InputStream input=New.class.getClassLoader().getResourceAsStream(filename);
		prop.load(input);
//		Connection connectionSql=null;
//		Statement statement=null;
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
				map.put(columnName,columnValue);
			}
			resultsetArray.add(map);
		}
		System.out.println(resultsetArray);
		return resultsetArray;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static JSONObject convertResultSetIntoJSONObject(ResultSet resultSet) throws Exception {
		JSONObject jsObject=new JSONObject(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
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
				
				if(columnName.contains("product_price")||columnName.contains("current_price")){
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
	public static Connection sqlConnection() throws IOException{
		Properties prop=new Properties();
		String filename="dbconnection.properties";
		InputStream input=New.class.getClassLoader().getResourceAsStream(filename);
		prop.load(input);
//		Connection connectionSql=null;
//		Statement statement=null;
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
	
}
