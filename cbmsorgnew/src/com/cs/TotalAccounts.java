package com.cs;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.Temporal;
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

import com.helper.Config;
import com.helper.DBConnection;
import com.helper.Helper;
import com.itextpdf.text.Header;

@Path("totalAccounts")
public class TotalAccounts {
	
	static Connection connection;
	static ResultSet resultSet;
	static Statement statement;
	static JSONArray jsonArray = new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static String sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(TotalAccounts.class);
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addAccounts")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response addAccounts(@FormParam("id") String id,@FormParam("party_name") 
	String party_name, @FormParam("created_date") String created_date,@FormParam("created_time") 
	String created_time,
			@FormParam("account_title") String account_title,@FormParam("expense_category")
	String expense_category,@FormParam("debit_amount") String debit_amount,@FormParam("credit_amount")
	String credit_amount,
			@FormParam("payment_method") String payment_method,@FormParam("invoice_number") 
	String invoice_number,@FormParam("total_amount") String total_amount,@FormParam("invoice_date") 
	String invoice_date,
			@FormParam("receipt_upload") String receipt_upload,@FormParam("remarks")
	String remarks,@FormParam("created_by") String created_by) {
		
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
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		
		if(party_name!=null && created_date!=null && created_time !=null  &&
				account_title !=null  && expense_category !=null  && debit_amount !=null  && credit_amount !=null  &&
				 payment_method !=null  &&invoice_number !=null  && invoice_date !=null  &&
				receipt_upload !=null  && remarks !=null  &&created_by !=null){
		Connection connectionAcc=null;
		try{
			connectionAcc=DBConnection.SqlConnection();
			//String created_date1=current_date;
			@SuppressWarnings("unused")
			String general_ledger_id=Customer.getGenerateId("GENLED",8,connectionAcc);
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
		
		PreparedStatement preparedStatement=connectionAcc.prepareStatement(Config.add_Accounts);
		preparedStatement.setString(1, general_ledger_id);
		preparedStatement.setString(1, party_name);
		preparedStatement.setString(3, created_date);
		preparedStatement.setString(4, created_time);
		preparedStatement.setString(5, account_title);
		preparedStatement.setString(6, expense_category);
		preparedStatement.setString(7, debit_amount);
		preparedStatement.setString(8, credit_amount);
		preparedStatement.setString(9, payment_method);
		preparedStatement.setString(10, invoice_number);
		preparedStatement.setString(11, total_amount);
		preparedStatement.setString(12, invoice_date);
		preparedStatement.setString(13, receipt_upload);
		preparedStatement.setString(14, created_by);
		ResultSet resultSet=preparedStatement.executeQuery();
		jsonObject.clear();
		jsonObject.put("status", "Success");
		jsonObject.put("message", "account added Successfully");
	
		}
		catch(Exception e){
			e.printStackTrace();
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", "Failed to add account");		
		}
		
		finally{
			try {
				statement.close();
			//	resultset.close();
				connectionAcc.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
		
	}	
	
	/*
	 * public static String getGeneral_ledgerID(String id){
	 * 
	 * String
	 * numberrange_function="select fn_general_ledger_id_numberrange_function('"+id+
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
	

	}  */
	
 	
 	@SuppressWarnings("unchecked")
	@POST
 	@Path("/calculate_employe_daily_status")
 	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 	@Produces(MediaType.APPLICATION_JSON)
 	public static Response calculateIndividualDailyAccount(@FormParam("id") String id,
 			@FormParam("date") Date date,@FormParam("type") String type){
 		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		Connection connectionAcc=null;
		double total_credit_amount=0;
		double total_debit_amount=0;
		double totalBalance=0;
		
		System.out.println(jsObject);@SuppressWarnings("unused")
		String subQuery=new String();
		if(jsObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", jsObject.get("status"));
//			return Response.ok()
//					.entity(jsonObject)
//					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			jsonObject.clear();
			System.out.println(role_id);
		}
			try {
				String email=(String) jsObject.get("email");
				connectionAcc=DBConnection.SqlConnection();
				PreparedStatement preparedStatement=connectionAcc.prepareStatement(Config.get_daily_general);
				preparedStatement.setDate(1, date);
				preparedStatement.setString(2, email);
				ResultSet resultSet=preparedStatement.executeQuery();
				while(resultSet.next()){
				Object array=	 resultSet.getObject(1);
					try {
						org.json.JSONArray total_array=new org.json.JSONArray(array.toString());
						for(int i=0;i<total_array.length();i++){
							org.json.JSONObject individualObject =total_array.getJSONObject(i);
							String credit_amount=individualObject.getString("credit_amount");
							String debit_amount = individualObject.getString("debit_amount");
							String string=debit_amount.replace("$", "").replace(",", "");
							double debit_amount_d=Double.parseDouble(string);
							total_debit_amount=debit_amount_d+total_debit_amount;
							double credit_amount_d=Double.parseDouble(credit_amount.replace("$", "").replace(",", ""));
							total_credit_amount=credit_amount_d+total_credit_amount;
							individualObject.put("total_credit_amount", total_credit_amount);
							individualObject.put("total_debit_amount", total_debit_amount);
							total_array.put(i,individualObject);
						}
						if(total_credit_amount>total_debit_amount)
						totalBalance=total_credit_amount-total_debit_amount;
						else
							totalBalance=total_debit_amount-total_credit_amount;
						
						org.json.JSONObject object=new org.json.JSONObject();
						object.put("totalBalance", totalBalance);
						object.put("data", total_array);
					PreparedStatement statement= connectionAcc.prepareStatement(Config.daily_general_insert);
					statement.setString(1, email);
					statement.setObject(2,Helper.convertToPGObject(object));
					statement.setString(3, type);
					statement.setDate(4, date);
					statement.setDouble(5, totalBalance);
					int status=	statement.executeUpdate();
						if(status==1){
							jsonObject.clear();
							jsonObject.put("status", "Succes");
							jsonObject.put("message", "Transaaction Added Successfully");
						}
						else{
							jsonObject.clear();
							jsonObject.put("status", "Failed");
							jsonObject.put("message", "Failed To Add Transaaction");
							
						}
						//System.out.println(total_array);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					statement.close();
					connectionAcc.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		
 		
 		return Response.ok()
 				.entity(jsonObject)
 				.header("Access-Control-Allow-Methods", "POST").build();
 	}
	
	
}
