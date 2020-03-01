package com.cs.addtransaction;

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

import com.cs.New;
import com.cs.TokenCheck;
import com.cs.member.Member;
import com.helper.Config;
import com.helper.DBConnection;
import com.helper.Helper;

@Path("Transaction")
public class AddTransaction {
	static Statement  statement;
	static ResultSet  resultset;
	static Connection connection;
	static JSONArray  jsonArray   =new JSONArray();
	static JSONObject jsonObject  =new JSONObject();
	static JSONArray  subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String     sessionId;
	@SuppressWarnings("unused")
	private static Log log=LogFactory.getLog(Member.class);
	/*
	 * Add Transaction
	 */
	@SuppressWarnings({"unchecked"})
	@POST
	@Path("/AddTransaction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response AddExpenseCategory(@FormParam("transaction_date") Date transaction_date,
			@FormParam("transaction_id") String transaction_id,@FormParam("cheque_num") String cheque_num,
			@FormParam("type") String type,@FormParam("balance") Double balance,
			@FormParam("member_type_name") String member_type_name,@FormParam("member_name") String member_name,
			@FormParam("expense_category") String expense_category,@FormParam("invoice_date") Date invoice_date
			,@FormParam("invoice_num") String invoice_num,@FormParam("debit") Double debit,
			@FormParam("credit") Double credit,@FormParam("member_id") int member_id,@FormParam("member_type_id") int member_type_id,
			@FormParam("payment_mode") String payment_mode,@FormParam("remarks") String remarks
			,@FormParam("category_id") String category_name,@FormParam("category_sub_id") String category_sub_name){
		//			if(member_type==null || member_type.isEmpty()||member_name==null || member_name.isEmpty()||expense_category==null || expense_category.isEmpty()
		//					||invoice_date==null || invoice_date.isEmpty()){
		//					jsonObject.clear();
		//					jsonObject.put("status", "Failed");
		//					jsonObject.put("message",  "Fields are empty");
		//			return Response.ok()
		//				.entity(jsonObject)
		//				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		//			}
		JSONObject jsonObject=new JSONObject();
		String Query = null;
		if(jsonObject.containsKey("status")){
			jsonObject.clear();
			jsonObject.put("status", "Failed");
			jsonObject.put("message", 
					jsonObject.get("status"));
			return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{

		}


		if(cheque_num!=null&&!cheque_num.isEmpty()) {
			payment_mode="cheque";
		}else if(transaction_id!=null&&!transaction_id.isEmpty()) {
			payment_mode="net banking";
		}else {
			payment_mode="cash";
		}
		try {

			SqlConnection();
			String balance_check=getBalance();

			System.out.println(balance_check);
			if(balance_check==null||balance_check.isEmpty()||balance_check.contains("0.0")||balance_check.contains(" ")) {
				balance_check="0";
			}
			try {
				if(invoice_date==null){
					invoice_date=transaction_date;

				}else{
					invoice_date=invoice_date;
				}

				Double ob=0.0,bal=0.0;
				Double remaning_amount=0.0;
				type=type.trim();
				System.out.println("type"+type+"type");
				System.out.println("debit : "+debit);
				System.out.println("credit : "+credit);

				String getData="select opening_balance,balance from gendral_ledger order by ledger_id desc limit 1";

				ResultSet resultSet1 = connection.createStatement().executeQuery(getData);
				while (resultSet1.next()) {
					ob=resultSet1.getDouble(1);
					bal=resultSet1.getDouble(2);
				}
				if(bal==0 || bal==null){
					ob=credit;
					bal=credit;
				}else{
					ob=ob+0;
					if(credit==null || credit==0){
						bal=bal-debit;
					}
					else{
						bal=bal+credit;
					}
				}
				if(debit!=null) {
					if(debit>0) {
					}else {
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Debit amount is low");
						return Response.ok()
								.entity(jsonObject)
								.header("Access-Control-Allow-Methods", "POST").build();
					}
					if(Double.parseDouble(balance_check)<debit) {
						System.err.println(balance_check);
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "Insufficient balance");
						return Response.ok()
								.entity(jsonObject)
								.header("Access-Control-Allow-Methods", "POST").build();
					}else {
						remaning_amount=Double.parseDouble(balance_check)-debit;
					}

					Query ="insert into gendral_ledger(member_id,category_id,category_name,"
							+ "category_sub_id,category_sub_name,transaction_id,cheque_num,"
							+ "transaction_date,member_type,member_name,expense_category,"
							+ "invoice_date,credit,debit,balance,invoice_id,payment_mode,"
							+ "comments,opening_balance,member_type_id)"
							+ "values('"+member_id+"','"+category_name+"',"
							+ "(select category_name from category_master where category_id='"+category_name+"'),"
							+ "'"+category_sub_name+"',(select category_sub_name from category_sub_table where category_sub_id='"+category_sub_name+"'),"
							+ "'"+transaction_id+"','"+cheque_num+"','"+transaction_date+"',"
							+ "'"+member_type_name+"','"+member_name+"','"+expense_category+"',"
							+ "'"+invoice_date+"','0','"+debit+"','"+bal+"','"+invoice_num+"',"
							+ "'"+payment_mode+"','"+remarks+"','"+ob+"','"+member_type_id+"')";
					System.err.println(Query);
				}else if (credit!=null) {
					if(credit>0) {
					}else {
						jsonObject.clear();
						jsonObject.put("Status", "Failed");
						jsonObject.put("Message", "credit amount is low");
						return Response.ok()
								.entity(jsonObject)
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
					}
					System.out.println(credit);
					remaning_amount=credit+Double.parseDouble(balance_check);
					Query ="insert into gendral_ledger(member_id,category_id,category_name,category_sub_id,category_sub_name,transaction_id,cheque_num,transaction_date,member_type,member_name,expense_category,invoice_date,credit,debit,balance,invoice_id,payment_mode,comments,opening_balance,member_type_id)"
							+ "values('"+member_id+"','"+category_name+"',(select category_name from category_master where category_id='"+category_name+"'),'"+category_sub_name+"',(select category_sub_name from category_sub_table where category_sub_id='"+category_sub_name+"'),"
							+ "'"+transaction_id+"','"+cheque_num+"','"+transaction_date+"','"+member_type_name+"','"+member_name+"','"+expense_category+"','"+invoice_date+"','"+credit+"','0','"+bal+"','"+invoice_num+"','"+payment_mode+"','"+remarks+"','"+ob+"','"+member_type_id+"')";
					System.err.println(Query);

				}else{
					System.out.println(credit +" "+debit);
				}
				int i=statement.executeUpdate(Query);
				if(i>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Transaction Added Successfully");
				}else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to add the Transaction");
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
	 * get Transaction list
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getTransactionList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getExpenseCategory(@FormParam("id") String id){
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
		/*String Query ="select invoice_id as invoice_num,invoice_date::date,expense_category,debit,credit,balance,payment_date,created_date::date,created_by, modified_date, \r\n" + 
				"	modified_by, member_type, member_name,\r\n" + 
				"	(select COALESCE(balance::varchar ,'0') as opening_balance from gendral_ledger where created_date ::date=current_date- interval '1 day' order by ledger_id desc limit 1)  from gendral_ledger\r\n" + 
				"	where created_date::date=current_date";*/
		String Query="select gendral_ledger.member_id,gendral_ledger.invoice_id as invoice_num,gendral_ledger.invoice_date,gendral_ledger.expense_category, " + 
				" gendral_ledger.debit,gendral_ledger.credit,gendral_ledger.balance,gendral_ledger.payment_mode,gendral_ledger.transaction_id,gendral_ledger.cheque_num,gendral_ledger.created_date::date as created_date," + 
				" gendral_ledger.member_type,gendral_ledger.ledger_id,gendral_ledger.transaction_date,case when gendral_ledger.comments ='null' then 'No Remarks' else gendral_ledger.comments end as remarks,gendral_ledger.opening_balance, " + 
				" gendral_ledger.category_name,gendral_ledger.category_id,gendral_ledger.category_sub_id,gendral_ledger.category_sub_name," + 
				" member_type_master.member_type as type_name,member_table.member_name,gendral_ledger.with_hold_amount "
				+ "from gendral_ledger inner join member_type_master on member_type_master.member_type_id=gendral_ledger.member_type_id " + 
				" inner join member_table on member_table.member_id=gendral_ledger.member_id ";
		//+ "where gendral_ledger.created_date::date=current_date";
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
					jsonObject.put("Content", arrayList);
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
				.header("Access-Control-Allow-Methods", "POST").build();
	}

	/*
	 * get Member type based on names list
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getMembertypeBasedNamesList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getMembertypeBasedNamesList(@FormParam("id") String id,@FormParam("member_id") String member_id){
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
		String Query="select gendral_ledger.member_id,gendral_ledger.invoice_id as invoice_num,gendral_ledger.invoice_date,gendral_ledger.expense_category, " + 
				" gendral_ledger.debit,gendral_ledger.credit,gendral_ledger.balance,gendral_ledger.payment_mode,gendral_ledger.transaction_id,gendral_ledger.cheque_num,gendral_ledger.created_date::date as created_date," + 
				" gendral_ledger.member_type,gendral_ledger.ledger_id,gendral_ledger.transaction_date,case when gendral_ledger.comments ='null' then 'No Remarks' else gendral_ledger.comments end as remarks," + 
				" gendral_ledger.category_name,gendral_ledger.category_id,gendral_ledger.category_sub_id,gendral_ledger.category_sub_name," + 
				" member_type_master.member_type as type_name,member_table.member_name,(select COALESCE(balance::varchar ,'0') as opening_balance from gendral_ledger where created_date ::date=current_date- interval '1 day' order by ledger_id desc limit 1)" + 
				"  from gendral_ledger inner join member_type_master on member_type_master.member_type_id::varchar=gendral_ledger.member_type " + 
				" inner join member_table on member_table.member_id=gendral_ledger.member_id where gendral_ledger.member_id='"+member_id+"'";
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
					jsonObject.put("Content", arrayList);
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


	/*
	 * Trail Balance
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/getTrailBalanceList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getTrailBalanceList(@FormParam("id") String id){
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
		String Query="select category_sub_name,sum(credit) as credit,sum(debit) as debit from gendral_ledger group by category_sub_name  order by credit";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				Query="select 'Total' as category_sub_name,(select sum(credit)  from gendral_ledger where credit>0)credit,(select sum(debit) from gendral_ledger where debit>0)debit  order by credit";

				resultset=statement.executeQuery(Query);
				ArrayList total=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){

					JSONObject object= new JSONObject();
					object.put("elements", arrayList);
					object.put("total", total);
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Content", object);
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
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}


	//	/*
	//	 * get Trail Balance List
	//	 */
	//	@SuppressWarnings("unchecked")
	//	@POST
	//	@Path("/getTrailBalanceList")
	//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public static Response getTransactionsumList(@FormParam("id") String id){
	//		JSONObject jsonObject=new JSONObject();
	//		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
	//		String credit_query ="select category_sub_name, sum(credit) as amount,(select sum(credit) from gendral_ledger where credit>0)  from gendral_ledger where credit>0	group by category_sub_name";
	//		String debit_query ="select category_sub_name, sum(debit) as amount,(select sum(debit)  from gendral_ledger where debit>0)  from gendral_ledger where debit>0" + 
	//				"group by category_sub_name";
	//		System.out.println(jsObject);
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
	//		}
	//				try {
	//			SqlConnection();
	//			try {
	////				System.out.println(Query);
	//				resultset=statement.executeQuery(credit_query);
	//				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
	//				ResultSet resultset_deb=statement.executeQuery(debit_query);
	//				ArrayList arrayList_debit=convertResultSetIntoArrayList(resultset_deb);
	//				jsonObject.clear();
	//				jsonObject.put("Status", "Success");
	//				if(arrayList!=null&&!arrayList.isEmpty()){
	//					jsonObject.put("credit", arrayList);
	//				}else{
	//					jsonObject.put("creditMessage", "List is empty");
	//				}if(arrayList_debit!=null&&!arrayList_debit.isEmpty()){
	//					jsonObject.put("debit", arrayList);
	//				}else{
	//					jsonObject.put("debitMessage", "List is empty");
	//				}
	//			} catch (SQLException e) {
	//				 jsonObject.clear();
	//				 jsonObject.put("Status", "Failed");
	//				 jsonObject.put("Message", "Something went wrong");
	//				 jsonObject.put("error", e.getMessage());
	//			} catch (Exception e) {
	//				 jsonObject.clear();
	//				 jsonObject.put("Status", "Failed");
	//				 jsonObject.put("Message", "Please try again");
	//				 jsonObject.put("error", e.getMessage());
	//			}
	//		} catch (IOException e) {
	//			 jsonObject.clear();
	//			 jsonObject.put("Status", "Failed");
	//			 jsonObject.put("Message", "Something went wrong");
	//			 jsonObject.put("error", e.getMessage());
	//		}
	//		 return Response.ok()
	//					.entity(jsonObject)
	//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/ProfitLosePaybleReceivable")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response ProfitLosePaybleReceivable(@FormParam("id") String id,@FormParam("days") String days){
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
		String Query ="select sum(debit) as debit,sum(credit) as credit from gendral_ledger where created_date::date between current_date - INTERVAL '"+days+" days' and current_date";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
				Double debit = 0.0;
				Double credit = 0.0;
				while (resultset.next()) {
					debit=resultset.getDouble("debit");
					credit=resultset.getDouble("credit");
				}
				if (debit>credit) {
					debit=debit-credit;
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message",debit + " loss from last "+days+" days");
				}else {
					debit=credit-debit;
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message",debit + " profit from last "+days+" days");
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
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	//	/*
	//	 * Add Online Transaction
	//	 */
	//	@SuppressWarnings({"unchecked"})
	//	@POST
	//	@Path("/AddOnlineTransaction")
	//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public static Response AddOnlineTransaction(@FormParam("type") String type,@FormParam("transaction_id") String transaction_id,@FormParam("cheque_num") String cheque_num,@FormParam("balance") Double balance,@FormParam("created_date") String created_date,
	//			@FormParam("member_type") String member_type,@FormParam("member_name") String member_name,@FormParam("expense_category") String expense_category,@FormParam("invoice_date") String invoice_date
	//			,@FormParam("invoice_num") String invoice_num,@FormParam("debit") Double debit,@FormParam("credit") Double credit){
	//			if(created_date==null || created_date.isEmpty()||member_type==null || member_type.isEmpty()||member_name==null || member_name.isEmpty()||expense_category==null || expense_category.isEmpty()
	//					||invoice_date==null || invoice_date.isEmpty()||invoice_num==null || invoice_num.isEmpty()||transaction_id==null || transaction_id.isEmpty()||cheque_num==null || cheque_num.isEmpty()){
	//					jsonObject.clear();
	//					jsonObject.put("status", "Failed");
	//					jsonObject.put("message",  "Fields are empty");
	//			return Response.ok()
	//				.entity(jsonObject)
	//				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//			}
	//			JSONObject jsonObject=new JSONObject();
	//			String Query = null;
	//			if(jsonObject.containsKey("status")){
	//						jsonObject.clear();
	//						jsonObject.put("status", "Failed");
	//						jsonObject.put("message", jsonObject.get("status"));
	//				return Response.ok()
	//						.entity(jsonObject)
	//						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//			}else{
	//			
	//			}  
	//			try {
	//				SqlConnection();
	////				String member_id=getFixedMemberId("00");
	////				System.out.println(member_id);
	//				String balance_check=getBalance();
	//				System.out.println(balance_check);
	//				if(balance_check==null||balance_check.isEmpty()||balance_check.contains("0.0")) {
	//					balance_check="0";
	//				}
	//				try {Double remaning_amount=0.0;
	//				type=type.trim();
	//				System.out.println("type"+type+"type");
	//					if(Integer.parseInt(type)==0) {
	//						if(debit>0) {
	//						}else {
	//							jsonObject.clear();
	//							jsonObject.put("Status", "Failed");
	//							jsonObject.put("Message", "Debit amount is low");
	//							return Response.ok()
	//									.entity(jsonObject)
	//									.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//						}
	//						if(debit>Double.parseDouble(balance_check)) {
	//							jsonObject.clear();
	//							jsonObject.put("Status", "Failed");
	//							jsonObject.put("Message", "Debit amount is too high");
	//							return Response.ok()
	//									.entity(jsonObject)
	//									.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//						}else {
	//							remaning_amount=Double.parseDouble(balance_check)-debit;
	//						}
	//						Query ="insert into gendral_ledger(transaction_id,cheque_num,member_type,member_name,expense_category,invoice_date,credit,debit,balance,invoice_id)"
	//								+ "values('"+transaction_id+"','"+cheque_num+"','"+member_type+"','"+member_name+"','"+expense_category+"','"+invoice_date+"','0','"+debit+"','"+remaning_amount+"','"+invoice_num+"')";
	//					}else if (Integer.parseInt(type)==1) {
	//						if(credit>0) {
	//						}else {
	//							jsonObject.clear();
	//							jsonObject.put("Status", "Failed");
	//							jsonObject.put("Message", "credit amount is low");
	//							return Response.ok()
	//									.entity(jsonObject)
	//									.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//						}System.out.println(credit);
	//						remaning_amount=credit+Double.parseDouble(balance_check);
	//						Query ="insert into gendral_ledger(transaction_id,cheque_num,member_type,member_name,expense_category,invoice_date,credit,debit,balance,invoice_id)"
	//								+ "values('"+transaction_id+"','"+cheque_num+"','"+member_type+"','"+member_name+"','"+expense_category+"','"+invoice_date+"','"+credit+"','0.0','"+remaning_amount+"','"+invoice_num+"')";
	//					}else {
	//						
	//					}System.out.println(Query);
	//					int i=statement.executeUpdate(Query);
	//					if(i>0){
	//						jsonObject.clear();
	//						jsonObject.put("Status", "Success");
	//						jsonObject.put("Message", "Transaction Added Successfully");
	//					}else{
	//						jsonObject.clear();
	//						jsonObject.put("Status", "Failed");
	//						jsonObject.put("Message", "Failed to insert");
	//					}
	//				} catch (SQLException e) {
	//					e.printStackTrace();
	//						jsonObject.clear();
	//						jsonObject.put("Status", "Failed");
	//						jsonObject.put("Message", "Something went wrong");
	//						jsonObject.put("error", e.getMessage());
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//						jsonObject.clear();
	//						jsonObject.put("Status", "Failed");
	//						jsonObject.put("Message", "Please try again");
	//						jsonObject.put("error", e.getMessage());
	//				}
	//			} catch (IOException e) {
	//						jsonObject.clear();
	//						jsonObject.put("Status", "Failed");
	//						jsonObject.put("Message", "Something went wrong");
	//						jsonObject.put("error", e.getMessage());
	//			}
	//			return Response.ok()
	//					.entity(jsonObject)
	//					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	//		
	//		}
	/*
	 * Account payable list 
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@POST
	@Path("/DVAccountPaybaleList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getAccountPaybaleList(@FormParam("id") String id,@FormParam("member_type_id") int member_type_id,
		@FormParam("member_id") int member_id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()){
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
					.header("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT").build();
		}else{
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}

		String Query="";
		//if(member_type_id==1){
		// Query="SELECT member_id,member_name,invoice_id,invoice_date,expense_category,debit,credit,balance  from gendral_ledger WHERE member_type_id='"+member_type_id+"' and member_id='"+member_id+"' and invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";

		System.out.println("from_date: "+from_date+",  todate"+to_date);
		if(from_date==null||from_date.isEmpty()||from_date.contains("undefined")||to_date==null||to_date.isEmpty()||to_date.contains("undefined")){
			
			Query="SELECT gendral_ledger.member_id,member_table.member_name,gendral_ledger.invoice_id, "
					+ "gendral_ledger.invoice_date,gendral_ledger.expense_category,gendral_ledger.debit,"
					+ "gendral_ledger.credit,gendral_ledger.balance from gendral_ledger "
					+ "inner join member_table on gendral_ledger.member_id = member_table.member_id "
					+ "WHERE gendral_ledger.member_type_id='"+member_type_id+"' and gendral_ledger.member_id='"+member_id+"' " ;

		}else{
			Query="SELECT gendral_ledger.member_id,member_table.member_name,gendral_ledger.invoice_id, "
					+ "gendral_ledger.invoice_date,gendral_ledger.expense_category,gendral_ledger.debit,"
					+ "gendral_ledger.credit,gendral_ledger.balance from gendral_ledger "
					+ "inner join member_table on gendral_ledger.member_id = member_table.member_id "
					+ "WHERE gendral_ledger.member_type_id='"+member_type_id+"' and gendral_ledger.member_id='"+member_id+"' "
					+ "and gendral_ledger.invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";

		}
		//}
		//		    else {
		//			 Query="SELECT member_id,member_name,invoice_id,invoice_date,expense_category,debit,credit,balance from gendral_ledger WHERE invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
		//		}
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(Query);
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
			jsonObject.put("error",e.getMessage());
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
	/*
	 * Account receivable list 
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@POST
	@Path("/DVAccountReceivableList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getAccountReceivableList(@FormParam("id") String id,@FormParam("member_type_id") int member_type_id,@FormParam("member_id") int member_id,@FormParam("from_date") String from_date,@FormParam("to_date") String to_date){
		JSONObject jsonObject=new JSONObject();
		if(id==null || id.isEmpty()){
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

		String Query="";
		//if(member_type_id==2){
		Query="SELECT gendral_ledger.member_id,member_table.member_name,gendral_ledger.invoice_id, "
				+ "gendral_ledger.invoice_date,gendral_ledger.expense_category,gendral_ledger.debit,"
				+ "gendral_ledger.credit,gendral_ledger.balance from gendral_ledger "
				+ "inner join member_table on gendral_ledger.member_id = member_table.member_id "
				+ "WHERE gendral_ledger.member_type_id='"+member_type_id+"' and gendral_ledger.member_id='"+member_id+"' "
				+ "and gendral_ledger.invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"';";
		//Query="SELECT member_id,member_name,invoice_id,invoice_date,expense_category,debit,credit,balance  from gendral_ledger WHERE member_id='"+member_id+"' and invoice_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
		//}
		//else {
		//			 Query="SELECT member_id,member_name,invoice_id,invoice_date,expense_category,debit,credit,balance from gendral_ledger WHERE created_date BETWEEN '"+from_date+"' AND '"+to_date+"'";
		//		}
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				System.out.println(Query);
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
			jsonObject.put("error",e.getMessage());
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
	/*
	 * get Balance sheet
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/getBalanceSheetList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getBalanceSheetList(@FormParam("id") String id){
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
		String Query="select category_name,category_sub_name,debit,credit from gendral_ledger where category_id in (3,4)";
		try {
			SqlConnection();
			try {
				System.out.println(Query);
				resultset=statement.executeQuery(Query);
				ArrayList arrayList=convertResultSetIntoArrayList(resultset);
				Query="select 'Total' as category_name, '' as category_sub_name, (select sum(debit)  from gendral_ledger where category_id in (3,4) )debit,(select sum(credit)from gendral_ledger where category_id in (3,4) )credit  order by credit";
				resultset=statement.executeQuery(Query);
				ArrayList total=convertResultSetIntoArrayList(resultset);
				if(arrayList!=null&&!arrayList.isEmpty()){

					JSONObject object= new JSONObject();
					object.put("elements", arrayList);
					object.put("total", total);
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Content", object);
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
	/*
	 * public static String getFixedMemberId(String id){
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
	 * } }
	 */

	public static String getBalance(){
		String balanace_Query="select COALESCE(balance::varchar ,'0') as balance from GeneralLedger order by ledger_id desc limit 1";
		String Rid="";
		try{
			System.out.println(balanace_Query);
			ResultSet resultset=statement.executeQuery(balanace_Query);
			boolean results=resultset.next();
			if(results) {
				Rid=resultset.getString("balance");
			}else {
				Rid="0";
			}
			/*while(resultset.next()){

			}*/
			return Rid;
		}catch(Exception exc){
			System.out.println(exc.getMessage());
			return "0.0";
		}
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

	@SuppressWarnings({ "unchecked", "unused" })
	@POST
	@Path("customerLedger")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response customerLedger(@FormParam("id") String id,
			@FormParam("customer_id") String customer_id){
		JSONObject jsonObject= new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
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
		}
		Connection connectionCustCount=null;
		try{

			String email=(String) jsObject.get("email");
			connectionCustCount = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionCustCount.prepareStatement(Config.customer_ledger_list);
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
				connectionCustCount.close();
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
	@Path("vendorLedger")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response vendorLedger(@FormParam("id") String id,
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
		Connection connectionVendorLedger=null;
		try{
			String email=(String) jsObject.get("email");
			connectionVendorLedger = DBConnection.SqlConnection();
			PreparedStatement preparedStatement= connectionVendorLedger.prepareStatement(Config.vendor_ledger_list);
			preparedStatement.setString(1,vendor_id);
			@SuppressWarnings("unused")
			ResultSet resultSet=preparedStatement.executeQuery();
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
				connectionVendorLedger.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Response.ok()
				.entity(jsonObject)
				.header("Access-Control-Allow-Methods", "POST").build();
	}

}
