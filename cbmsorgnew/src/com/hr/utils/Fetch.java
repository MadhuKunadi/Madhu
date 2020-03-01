/**
 * 
 */
package com.hr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



/**
 * @author Akhil yelakanti 
 * @date 12/15/2018 04:45PM
 * Contains the sample code to retrive the data using offset and fetch from postgresql
 * All it need three parameters offset,row_count, count. offset is row number to retrive from, it will come in the output you run the API,
 * count is initially 0 in the response of first time when you run then the resultset lenth will be the count,
 * row count should defines the no of rows to be retrive.
 *
 */
@Path("Fetch")
public class Fetch {
	
	int offset;
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	int row_count;
	/**
	 * @return the row_count
	 */
	public int getRow_count() {
		return row_count;
	}

	/**
	 * @param row_count the row_count to set
	 */
	public void setRow_count(int row_count) {
		this.row_count = row_count;
	}
	int count;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	@SuppressWarnings("unchecked")
	@POST
    @Path("/fetchNextRows")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response fetchNextRows(Fetch object) {
		
		int fetch_count=object.getRow_count();
		
		int offset_count=object.getOffset();
		int previous_count=object.getCount();
		
		if(fetch_count==0) {
			fetch_count=2;
		}
		System.out.println("fetch_count: "+fetch_count);
		System.out.println(" before offset_count: "+offset_count+" previous_count: "+previous_count);

		
		System.out.println(" after offset_count: "+offset_count);
		
		JSONObject responseObject=new JSONObject();
		
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {
			connection=sqlConnection();
			preparedStmt=connection.prepareStatement("SELECT itmid, iname FROM item ORDER BY  itmid  desc OFFSET '"+offset_count+"' ROWS FETCH NEXT "+fetch_count+" ROW ONLY");

			System.out.println("fetchNextRows: "+preparedStmt);
			resultSet = preparedStmt.executeQuery();
			JSONArray fetched_records = new JSONArray();
			/**
			 *  fetching the data to json array from the resultset
			 *  
			 **/
			 while (resultSet.next()) {
	             HashMap<String, Object> obj = new HashMap<String, Object>();
		            int total_rows = resultSet.getMetaData().getColumnCount();
		            for (int i = 0; i < total_rows; i++) {
		            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
		            	Object  columnvalue=resultSet.getObject(i + 1);
		            	obj.put(columnname, columnvalue);
		            }
		            fetched_records.add(obj);
			 }
			 responseObject.clear();
			 responseObject.put("offset", offset_count);
			 responseObject.put("count", fetched_records.size());
			 responseObject.put("row_count", fetch_count);
			 responseObject.put("fetched_records", fetched_records);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Response.ok() //200
				.entity(responseObject).build();
		
	}

	private Connection sqlConnection() throws ClassNotFoundException, SQLException {
		Connection	CONNECTION=null;
		Class.forName("org.postgresql.Driver");
		CONNECTION = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","chinna");
	return CONNECTION;
	}
}
