package com.intl.aoc.cro.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.intl.aoc.dbconnection.dbConnection;
import com.intl.aoc.rules.controller.SetGet;
import com.intl.aoc.so.controller.SOMethodController;
@Path("DashBoardController")
@SuppressWarnings({"unchecked","rawtypes"})
public class DashBoardController {
	@Path("/getData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData()
	{
		JSONObject json = new JSONObject();
		Connection conn =null;
		int automatedCount=0;
		int manualCount=0;
		int unfitCount=0;
		int totalPostingsCount=0;
		String automated= " SELECT count(*) from aoc_transcations where posting_type_id=1 and istransactioncompleted=0";
		String manual= " SELECT count(*) from aoc_transcations where posting_type_id=2 and istransactioncompleted=0";
		String unfit= " SELECT count(*) from aoc_unfit_for_posting";
		String totalPostings= " SELECT count(*) from aoc_final_postings";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result1 = conn.createStatement().executeQuery(automated);
				automatedCount = getCount(result1);
				ResultSet result2 = conn.createStatement().executeQuery(manual);
				manualCount = getCount(result2);
				ResultSet result3 = conn.createStatement().executeQuery(unfit);
				unfitCount = getCount(result3);
				ResultSet result4 = conn.createStatement().executeQuery(totalPostings);
				totalPostingsCount = getCount(result4);

				json.clear();
				json.put("Status", "Success");
				json.put("AutomatedTransaction", automatedCount);
				json.put("ManualTransaction", manualCount);
				json.put("Unfit", unfitCount);
				json.put("TotalPostings", totalPostingsCount);

			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	@Path("/getProfile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(SetGet data)
	{
		JSONObject json = new JSONObject();
		int roleId = data.getRoleId();
		Connection conn =null;
		String query= "SELECT * from aoc_logins where role_id="+roleId+" ";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result = conn.createStatement().executeQuery(query);
				JSONObject jsObject =	SOMethodController.convertResultSetToJSON(result);
				if(jsObject!=null && !jsObject.isEmpty())
				{
					json.clear();
					json.put("Status", "Success");
					json.put("Data", jsObject);
				}
				else
				{
					json.clear();
					json.put("Status", "Failed");
					json.put("Message", "No Data Found");
				}
			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	@Path("/getPeacePostingReports")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPeacePostingReports(SetGet data) throws ParseException
	{
		JSONObject json = new JSONObject();
		String fromdate = data.getFromDate();
		String toDate = data.getToDate();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = formate.parse(toDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(todate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		toDate =formate.format(cal.getTime());
		List dataList = new ArrayList();
		Connection conn =null;

		/*
		select * from aoc_final_postings inner join aoc_transcations on aoc_transcations.transcation_id=
        aoc_final_postings.transcation_id where aoc_transcations.create_date between '2019-10-10' and '2019-10-12'
        and upper(peace_or_field)='PEACE'
		 */
		String query= "select distinct transcation_id from aoc_transcations where posting_type_id=1 and create_date between '"+fromdate+"' and  '"+toDate+"'";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result = conn.createStatement().executeQuery(query);
				ArrayList transactionsList = SOMethodController.convertResultSetToList(result);
				if(transactionsList!=null && !transactionsList.isEmpty())
				{
					for(int i=0;i<transactionsList.size();i++)
					{
						JSONObject jsObject = (JSONObject) transactionsList.get(i);
						String transaction = (String) jsObject.get("transcation_id");
						String getPeacePostQuery ="select * from aoc_final_postings where  upper(peace_or_field)='PEACE' and transcation_id='"+transaction+"' ";
						ResultSet getResult = conn.createStatement().executeQuery(getPeacePostQuery);
						ArrayList getData = SOMethodController.convertResultSetToList(getResult);
						if(getData!=null && !getData.isEmpty())
						{
							dataList.addAll(getData);
						}
					}
				}
				System.out.println(dataList.size());
				if(dataList!=null && !dataList.isEmpty())
				{
					json.clear();
					json.put("Status", "Success");
					json.put("Data", dataList);
				}
				else
				{
					json.clear();
					json.put("Status", "Failed");
					json.put("Message", "No Data Found");
				}

			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	@Path("/getFieldPostingReports")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFieldPostingReports(SetGet data) throws ParseException
	{
		JSONObject json = new JSONObject();
		String fromdate = data.getFromDate();
		String toDate = data.getToDate();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = formate.parse(toDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(todate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		toDate =formate.format(cal.getTime());
		System.out.println("toDate..."+toDate);
		List dataList = new ArrayList();
		Connection conn =null;
		String query= "select distinct transcation_id from aoc_transcations where posting_type_id=1 and create_date between '"+fromdate+"' and  '"+toDate+"'";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result = conn.createStatement().executeQuery(query);
				ArrayList transactionsList = SOMethodController.convertResultSetToList(result);
				if(transactionsList!=null && !transactionsList.isEmpty())
				{
					for(int i=0;i<transactionsList.size();i++)
					{
						JSONObject jsObject = (JSONObject) transactionsList.get(i);
						String transaction = (String) jsObject.get("transcation_id");
						String getFieldPostQuery ="select * from aoc_final_postings where  upper(peace_or_field)='FIELD' and transcation_id='"+transaction+"' ";
						ResultSet getResult = conn.createStatement().executeQuery(getFieldPostQuery);
						ArrayList getData = SOMethodController.convertResultSetToList(getResult);
						if(getData!=null && !getData.isEmpty())
						{
							dataList.addAll(getData);
						}
					}
				}
				System.out.println(dataList.size());
				if(dataList!=null && !dataList.isEmpty())
				{
					json.clear();
					json.put("Status", "Success");
					json.put("Data", dataList);
				}
				else
				{
					json.clear();
					json.put("Status", "Failed");
					json.put("Message", "No Data Found");
				}

			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	@Path("/getErePostingReports")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getErePostingReports(SetGet data) throws ParseException
	{
		JSONObject json = new JSONObject();
		String fromdate = data.getFromDate();
		String toDate = data.getToDate();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = formate.parse(toDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(todate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		toDate =formate.format(cal.getTime());
		List dataList = new ArrayList();
		Connection conn =null;
		String query= "select distinct transcation_id from aoc_transcations where posting_type_id=1 and create_date between '"+fromdate+"' and  '"+toDate+"'";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result = conn.createStatement().executeQuery(query);
				ArrayList transactionsList = SOMethodController.convertResultSetToList(result);
				if(transactionsList!=null && !transactionsList.isEmpty())
				{
					for(int i=0;i<transactionsList.size();i++)
					{
						JSONObject jsObject = (JSONObject) transactionsList.get(i);
						String transaction = (String) jsObject.get("transcation_id");
						String getPeacePostQuery ="select * from aoc_final_postings inner join  aoc_units on upper(aoc_units.unit) = upper(aoc_final_postings.posting_unit) " + 
								"    where transcation_id='"+transaction+"' and aoc_units.type_of_unit_id=1 ";
						ResultSet getResult = conn.createStatement().executeQuery(getPeacePostQuery);
						ArrayList getData = SOMethodController.convertResultSetToList(getResult);
						if(getData!=null && !getData.isEmpty())
						{
							dataList.addAll(getData);
						}
					}
				}
				System.out.println(dataList.size());
				if(dataList!=null && !dataList.isEmpty())
				{
					json.clear();
					json.put("Status", "Success");
					json.put("Data", dataList);
				}
				else
				{
					json.clear();
					json.put("Status", "Failed");
					json.put("Message", "No Data Found");
				}

			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	@Path("/getManualPostingReport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManualPostingReport(SetGet data) throws ParseException
	{
		JSONObject json = new JSONObject();
		String fromdate = data.getFromDate();
		String toDate = data.getToDate();
		String reason = data.getReason();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = formate.parse(toDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(todate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		toDate =formate.format(cal.getTime());
		Connection conn =null;
		String query= " select * from aoc_manual_postings inner join aoc_transcations on aoc_transcations.transcation_id= " + 
				"    aoc_manual_postings.transcation_id where aoc_transcations.create_date between '"+fromdate+"' and '"+toDate+"' " + 
				"    and upper(reason)='"+reason.toUpperCase()+"'";
		try {
			conn = dbConnection.sqlConnectionDB();
			try
			{
				ResultSet result = conn.createStatement().executeQuery(query);
				ArrayList transactionsList = SOMethodController.convertResultSetToList(result);

				System.out.println(transactionsList.size());
				if(transactionsList!=null && !transactionsList.isEmpty())
				{
					json.clear();
					json.put("Status", "Success");
					json.put("Data", transactionsList);
				}
				else
				{
					json.clear();
					json.put("Status", "Failed");
					json.put("Message", "No Data Found");
				}

			} catch (Exception e) {
				json.clear();
				json.put("Status", "Failed");
				json.put("Message", "Something went wrong");
				json.put("error", e.getMessage());
			}
		} catch (Exception e) {
			json.clear();
			json.put("Status", "Failed");
			json.put("Message", "Connection Failed");
		}
		finally {
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok().entity(json).build();
	}
	public static int getCount(ResultSet result) throws SQLException
	{
		int count=0;
		while(result.next())
		{
			count = result.getInt(1);
		}
		return count;
	}
}
