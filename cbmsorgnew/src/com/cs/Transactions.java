package com.cs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.helper.Config;
import com.helper.DBConnection;

public class Transactions {
	
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();

	public static void main(String Args[]) {
		/*try {
			String dates=EmployeeConfig.getDefaultDates();
			System.out.println(dates);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}*/
		addTransactions();
		
	}
	
	@SuppressWarnings("unchecked")
	public static void addTransactions() {
		Connection connection=null;
		//String Query="SELECT json_agg(json_build_object('doc_id', doc_id))  from idb_doc_schedule; ";
		String Query="select opening_balance,balance,with_hold_amount,closing_balance from gendral_ledger order by ledger_id desc limit 1";
		
		try {
			connection=DBConnection.SqlConnection();
			
				double opening_balance = 0,balance = 0,with_hold_amount = 0,closing_balance = 0;
				System.out.println("*---- QUERY TO GET EXISTING BALANCES    :  "+Query +"   -------*");
				ResultSet resultset=connection.createStatement().executeQuery(Query);
				
				while(resultset.next()) {
					closing_balance= resultset.getDouble("balance");
					balance=resultset.getDouble("balance");
					with_hold_amount=resultset.getDouble("with_hold_amount");
					opening_balance=closing_balance;
					
				}
				//String UpdateQuery="dayend_transaction_function('"+opening_balance+"','"+closing_balance+"','"+with_hold_amount+"','"+balance+"')";
				PreparedStatement preparedStatement= connection.prepareStatement(Config.add_Transaction);
				preparedStatement.setDouble(1,opening_balance);
				preparedStatement.setDouble(2,closing_balance);
				preparedStatement.setDouble(3,with_hold_amount);
				preparedStatement.setDouble(4,balance);
				@SuppressWarnings("unused")
				ResultSet resultSet=preparedStatement.executeQuery();
				jsonObject.clear();
				jsonObject.put("status", "Success");
				jsonObject.put("message", "transaction added Successfully");
			}catch(Exception e){
				e.printStackTrace();
				jsonObject.clear();
				jsonObject.put("status", "Failed");
				jsonObject.put("message", "Failed to add Transaction");
			
			}
			 
		
		
				//System.out.println("*-----ERROR WHILE SELECTING DOCTORS LIST-------*  "+e.getMessage());
				//customerService("*-----ERROR WHILE SELECTING DOCTORS LIST-------*",e.getMessage());
//			} catch (JSONException e) {
//				//System.out.println("*-----ERROR WHILE PARSING JSONARRAY OF DOCTORS LIST-------*  "+e.getMessage());
//				//customerService("*-----ERROR WHILE PARSING JSONARRAY OF DOCTORS LIST-------*",e.getMessage());
//			}
//		} catch (IOException e) {
//			System.out.println("*-----ERROR WHILE CONNECTING TO DATABASE-------*  "+e.getMessage());
//			//customerService("*-----ERROR WHILE CONNECTING TO DATABASE-------*",e.getMessage());
//		}
	}
	}
	
	

