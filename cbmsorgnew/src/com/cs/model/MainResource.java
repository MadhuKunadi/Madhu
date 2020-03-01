package com.cs.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainResource {
	public static Statement STATEMENT;
	public static Connection CONNECTION;
	public static ResultSet RESULTSET;
	public static CallableStatement CALLABLESTATMENT;
	
	public static Log log=LogFactory.getLog(MainResource.class);
	
	public static JSONObject jsonObject=new JSONObject();
	public static JSONArray jsonArray=new JSONArray();
}
