package com.cs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import com.onbarcode.barcode.Code39;


@Path("barcode")
public class GenerateBarcode {

	
	static Statement statement;
	static Connection connection;
	static ResultSet resultset;
	static PreparedStatement preparestatement;
	static JSONArray jsonArray=new JSONArray();
	static JSONObject jsonObject=new JSONObject();
	static JSONArray subjsonarray=new JSONArray();
	static JSONObject subjsonobject=new JSONObject();
	static String sessionId;
	private static Log log=LogFactory.getLog(GenerateBarcode.class);
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/generateBarcode")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response acceptorreject(@FormParam("token") String id,@FormParam("id") String roll_id,@FormParam("product_name") String product_name,@FormParam("product_unit") String product_unit,
			@FormParam("expiry_date") String expiry_date,@FormParam("manufacturing_date") String manufacturing_date,@FormParam("price") String price,
			@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("cgst") String cgst,@FormParam("discount") String discount,
			@FormParam("product_price") String product_price,@FormParam("productid") String productid,
			@FormParam("vendor_id") String vendor_id,@FormParam("batch_id") String batch_id,@FormParam("purchase_requisition_id") String purchase_requisition_id,@FormParam("store_id") String store_id) throws FileNotFoundException, DocumentException, com.itextpdf.text.DocumentException{
		int role_id=0;
		String email=null;
		JSONObject jsonObject=new JSONObject();
//		if(roll_id==null || roll_id.isEmpty()){
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
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
		}else{
			email=(String) jsObject.get("email");
			role_id=(int) jsObject.get("role_id");
			if(role_id==3) {
				store_id=(String) jsObject.get("auth_id");
			}
			System.out.println(role_id);
		}
		try {
			SqlConnection();	
		
			try {
//				String pcoId=new String();
//				org.json.JSONArray jsArray=new org.json.JSONArray(roll_id);
//				for(int i=0;i<jsArray.length();i++){
//					org.json.JSONObject jsObj=jsArray.getJSONObject(i);
//					if(pcoId!=null&&!pcoId.isEmpty()){
//						pcoId=pcoId+","+jsObj.getInt("id");
//					}else{
//						int pcId=jsObj.getInt("id");
//						pcoId=Integer.toString(pcId);
//					}
//				}
				
				
				int min = 100000000;
				int max = 999999999;

				Random r = new Random();
				int randomnumber = r.nextInt(max - min + 1) + min;

				String barcodecods = String.valueOf(randomnumber);
				
				Code39 barcode = new Code39();
		           barcode.setData(barcodecods);
		           barcode.setX(2);
		           barcode.setY(60);
		           barcode.setBarcodeWidth(150);
		           barcode.setBarcodeHeight(80);

		           barcode.setLeftMargin(0);
		           barcode.setRightMargin(0);
		           barcode.drawBarcode("D:\\barcode\\java-code39.png");  //change path of image according to you
		           
		           File f = new File("D:\\barcode\\java-code39.png");		//change path of image according to you
		   		FileInputStream fis = new FileInputStream(f);
		   		byte byteArray[] = new byte[(int)f.length()];
		   		fis.read(byteArray);
		   		String imageString = Base64.encodeBase64String(byteArray);
		   		
		   		System.out.println(imageString);
		   		//decode Base64 String to image
//		   		FileOutputStream fos = new FileOutputStream("E:\\barcode\\destinationImage.png"); //change path of image according to you
//		   		byteArray = Base64.decodeBase64(imageString);
//		   		fos.write(byteArray);
//		   		System.out.println(byteArray);
		   		
		   		System.out.println("Image converted into base 64");
		    
		   		fis.close();
		   		//fos.close();
		   		
//		   		 jsonObject.clear();
//				 jsonObject.put("Status", "Success");
//				 jsonObject.put("BarcodeImage", imageString);
				 
				 JSONArray imageArray = new JSONArray();
				 JSONObject imageObject=new JSONObject();
				 imageObject.put("base64", imageString);
				 imageObject.put("filetype", "image/jpg");
				 imageArray.add(imageObject);
				 System.out.println(imageArray);
				 
//			     document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
//			    document.close();
			 
			    System.out.println("Barcode Generated Successfully");
			    
			    
			    /*
			     * Author siba sankar
			     * i commented this block
			     */
//			    String Query = "select 1 from barcode where product_id='"+productid+"'";
//			    
//			    resultset=statement.executeQuery(Query);
//			   int b=0;
//			    while(resultset.next()){
//			    	b=resultset.getInt(1);
//			    }
//				if(b==1){
//					jsonObject.clear();
//					jsonArray.clear();
//					jsonObject.put("Status", "Failed");
//					jsonObject.put("Messaage", "Barcode is already exists");
//					jsonArray.add(jsonObject);
//				}
//				else{
			    if(role_id==4) {
					System.out.println("purchase_requisition_id "+purchase_requisition_id);
					String fetchStoreIDQuery="select distinct(tbl_store_indent.store_id) from tbl_store_indent inner join tbl_purchase_requisition\r\n" + 
							"  on tbl_store_indent.purchase_requisition_id = tbl_purchase_requisition.purchase_requisition_id \r\n" + 
							"  where tbl_store_indent.purchase_requisition_id='"+purchase_requisition_id+"'";
					resultset=statement.executeQuery(fetchStoreIDQuery);
					while(resultset.next()) {
						store_id=resultset.getString(1);
					}
					System.out.println("fetchStoreIDQuery "+ fetchStoreIDQuery);
			    }
					
//				String Query="insert into barcode(barcode_id,barcode_number,barcode_image,product_name,product_unit,manufactur_date,expiry_date,price,cgst,sgst,igst,discount,total)"
//						+ "values('"+roll_id+"','"+barcodecods+"','"+imageArray+"','"+product_name+"','"+product_unit+"','"+manufacturing_date+"','"+expiry_date+"','"+price+"',"
//								+ "'"+cgst+"','"+sgst+"','"+igst+"','"+discount+"','"+totalprice+"')";
//				String funQuery="select * from barcode_function('"+roll_id+"','"+barcodecods+"','"+imageArray+"','"+productid+"','"+product_name+"','"+price+"',"
//						+ "'"+product_unit+"','"+manufacturing_date+"','"+expiry_date+"','"+cgst+"','"+igst+"','"+sgst+"','"+discount+"','"+totalprice+"')";
					String funQuery="select barcode_function3('"+roll_id+"','"+barcodecods+"','"+imageArray+"','"+productid+"',"
							+ "'"+product_name+"','"+price+"','"+manufacturing_date+"',"
									+ "'"+expiry_date+"','"+discount+"','"+vendor_id+"','"+store_id+"')";
				
				System.out.println(funQuery);
				CallableStatement caStatement=connection.prepareCall(funQuery);
				caStatement.execute();
				ResultSet resultSet=caStatement.getResultSet();
				
//				int i=statement.executeUpdate(Query);
//				if(i>0){
//					jsonObject.clear();
//					jsonObject.put("Status", "Success");
//					jsonObject.put("Message", "Inserted Successfully");
//				}
	
				String result=new String();
				while(resultSet.next()){
					result=resultSet.getString(1);
				}
				
				if(result.contains("Success")){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Inserted Successfully");
				}
				else{
					jsonObject.clear();
					jsonObject.put("Status", "Failed");
					jsonObject.put("Message", "Failed to update");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/barcodegeneratedProductsList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response barcodegeneratedProductsList(@FormParam("id") String id,@FormParam("store_id") String store_id) throws SQLException, IOException{
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		System.out.println("store_id"+store_id);
		int role_id=0;
		String auth_id=null;
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
			role_id=(int) jsObject.get("role_id");
			auth_id=(String) jsObject.get("auth_id");
			System.out.println("roleid"+role_id);

			System.out.println("auther id "+auth_id);
			if(role_id==1) {
				System.out.println("RoleId "+role_id);
			}else {
				store_id=auth_id;
			}
		}
		System.out.println("store_id"+store_id);
		//String Query ="select * from purchase_order";
		//String Query ="select distinct tbl_generate_invoice.id,tbl_generate_invoice.purchase_id,tbl_generate_invoice.invoice_id from tbl_generate_invoice ";
		String Query1=null;
		
		 
		try {
			connection=SqlConnection1();
			try {
					Query1="select distinct(barcode.barcode_number),barcode.store_id,barcode.barcode_id,barcode.barcode_image,barcode.product_name,barcode.product_unit,barcode.manufactur_date,\r\n" + 
							"barcode.expiry_date,barcode.product_id, barcode.discount, barcode.price,barcode.vendor_id  from barcode " + 
							" where barcode.store_id='"+store_id+"'";
					preparestatement=connection.prepareStatement(Query1);
//					preparestatement.setString(1, store_id);
					System.out.println("Query1= "+preparestatement);
					resultset = preparestatement.executeQuery();

//				}else {
//				 Query2 ="select barcode.barcode_id,barcode.barcode_number,barcode.barcode_image,barcode.product_name,"
//						+ "barcode.product_unit,barcode.manufactur_date, "
//						+ "barcode.expiry_date,barcode.product_id,barcode.discount,barcode.price from barcode";
////						+ "tbl_vendor1.vendor_name from barcode "
////						+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id =barcode.vendor_id";
//					preparestatement=connection.prepareStatement(Query2);
//					System.out.println("Query2= "+preparestatement);
//					resultset = preparestatement.executeQuery();
//				}
//				preparestatement=connection.prepareStatement(Query1);
//				resultset = preparestatement.executeQuery();
			
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/editBarcodeGeneratedList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response editBarcodeGeneratedList(@FormParam("barcode_id") String barcode_id,@FormParam("id") String id){
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
		String Query ="select barcode.barcode_id,barcode.barcode_number,barcode.barcode_image,"
				+ "barcode.product_name,barcode.manufactur_date,barcode.expiry_date,"
				+ "tbl_vendor1.vendor_name,barcode.vendor_id,"
				+ "barcode.product_id,barcode.discount,barcode.price from barcode "
				+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id =barcode.vendor_id  where barcode_id='"+barcode_id+"'";
		try {
			SqlConnection();
			try {
				resultset=statement.executeQuery(Query);
				@SuppressWarnings("rawtypes")
				ArrayList arrayList=convertResultSetIntoArrayList1(resultset);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateBarcodeGeneratedList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response updateBarcodeGeneratedList(@FormParam("token") String id,@FormParam("product_name") String product_name,@FormParam("product_unit") String product_unit,
			@FormParam("expiry_date") String expiry_date,@FormParam("manufacturing_date") String manufacturing_date,@FormParam("price") String price,
			@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("cgst") String cgst,@FormParam("discount") String discount,
			@FormParam("totalprice") String totalprice,@FormParam("product_id") String product_id,
			@FormParam("barcode_number") String barcode_number,@FormParam("vendor_id") String vendor_id){
		JSONObject jsonObject=new JSONObject();
		
//		if(warehouse_name==null || warehouse_name.isEmpty()|| vendor_name==null || vendor_name.isEmpty()|| product_description==null || product_description.isEmpty()
//				|| product_unit==null || product_unit.isEmpty()|| product_quantity==null || product_quantity.isEmpty()|| note_or_reason==null || note_or_reason.isEmpty()
//				|| customer_name==null || customer_name.isEmpty()) {
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
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
//				String Query="UPDATE barcode SET product_name='"+product_name+"',product_unit='"+ product_unit+"',"
//						+ "manufactur_date='"+manufacturing_date+"',expiry_date='"+expiry_date+"',price='"+price+"',cgst='"+cgst+"',"
//								+ "sgst='"+sgst+"',igst='"+igst+"',discount='"+discount+"',total='"+total+"' where barcode_id='"+barcode_id+"'";
//				
//				int st=statement.executeUpdate(Query);
//				if(st>0){
				
				String funQuery="select barcode_function(0,'"+barcode_number+"','','"+product_id+"',"
						+ "'"+product_name+"','"+price+"','"+manufacturing_date+"',"
								+ "'"+expiry_date+"','"+discount+"','"+vendor_id+"')";
				System.out.println(funQuery);
				CallableStatement caStatement=connection.prepareCall(funQuery);
				caStatement.execute();
				ResultSet resultSet=caStatement.getResultSet();
				String result=new String();
				while(resultSet.next()){
					result=resultSet.getString(1);
				}
				
				if(result.contains("Success")){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}
			else{
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
	
	
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
	
	
	public static void SqlConnection() throws IOException {
		// TODO Auto-generated method stub
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
	
	@SuppressWarnings("unchecked")
	public static ArrayList convertResultSetIntoArrayList1(ResultSet resultSet) throws Exception {
		ArrayList resultsetArray=new ArrayList(); 
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			Map map=new HashMap();
			for (int i = 0; i <total_rows; i++) {
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				
				if(columnName.contains("price")){
					String columnValue = resultSet.getString(i + 1);
					if(columnValue.contains("$")){
						columnValue=columnValue.replace("$","");
					}
					map.put(columnName,columnValue);
				}
				else if(columnName.contains("product_cgst")||columnName.contains("product_sgst")||columnName.contains("igst")||columnName.contains("discount")){
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
	public static Connection SqlConnection1() throws IOException {
		// TODO Auto-generated method stub
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
		return connection;
	}	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/barcodegeneratedProductsList1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public static Response barcodegeneratedProductsList1(@FormParam("id") String id,@FormParam("store_id") String store_id) throws SQLException, IOException{
		JSONObject jsonObject=new JSONObject();
		JSONObject jsObject=TokenCheck.checkTokenStatus(id);
		System.out.println(jsObject);
		System.out.println("store_id"+store_id);
		int role_id=0;
		String auth_id=null;
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
			role_id=(int) jsObject.get("role_id");
			auth_id=(String) jsObject.get("auth_id");
			System.out.println("roleid"+role_id);

			System.out.println("auther id "+auth_id);
			if(role_id==1) {
				System.out.println("RoleId "+role_id);
			}else {
				store_id=auth_id;
			}
		}
		System.out.println("store_id"+store_id);
		//String Query ="select * from purchase_order";
		//String Query ="select distinct tbl_generate_invoice.id,tbl_generate_invoice.purchase_id,tbl_generate_invoice.invoice_id from tbl_generate_invoice ";
		String Query1=null;
		
		 
		try {
			connection=SqlConnection1();
			try {
					Query1="select distinct(barcode.barcode_id),barcode.barcode_number,barcode.barcode_image,barcode.product_name,\r\n" + 
							"	barcode.product_unit,barcode.manufactur_date,barcode.expiry_date,barcode.product_id,barcode.discount,\r\n" + 
							"    barcode.price,tbl_store_indent_products.store_id from barcode\r\n" + 
							"    inner join tbl_store_indent_products on tbl_store_indent_products.product_id=barcode.product_id ";
//							+ "wheretbl_store_indent_products.store_id=?";
					preparestatement=connection.prepareStatement(Query1);
//					preparestatement.setString(1, store_id);
					System.out.println("Query1= "+preparestatement);
					resultset = preparestatement.executeQuery();

//				}else {
//				 Query2 ="select barcode.barcode_id,barcode.barcode_number,barcode.barcode_image,barcode.product_name,"
//						+ "barcode.product_unit,barcode.manufactur_date, "
//						+ "barcode.expiry_date,barcode.product_id,barcode.discount,barcode.price from barcode";
////						+ "tbl_vendor1.vendor_name from barcode "
////						+ "inner join tbl_vendor1 on tbl_vendor1.vendor_id =barcode.vendor_id";
//					preparestatement=connection.prepareStatement(Query2);
//					System.out.println("Query2= "+preparestatement);
//					resultset = preparestatement.executeQuery();
//				}
//				preparestatement=connection.prepareStatement(Query1);
//				resultset = preparestatement.executeQuery();
			
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateBarcodeGeneratedList1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public static Response updateBarcodeGeneratedList1(@FormParam("token") String id,@FormParam("product_name") String product_name,@FormParam("product_unit") String product_unit,
			@FormParam("expiry_date")  Date expiry_date,@FormParam("manufacturing_date")  Date manufacturing_date,@FormParam("price") String price,
			@FormParam("sgst") String sgst,@FormParam("igst") String igst,@FormParam("cgst") String cgst,@FormParam("discount") String discount,
			@FormParam("totalprice") String totalprice,@FormParam("product_id") String product_id,
			@FormParam("barcode_number") String barcode_number,@FormParam("vendor_id") String vendor_id,@FormParam("store_id") String store_id) throws SQLException{
		JSONObject jsonObject=new JSONObject();
//		if(warehouse_name==null || warehouse_name.isEmpty()|| vendor_name==null || vendor_name.isEmpty()|| product_description==null || product_description.isEmpty()
//				|| product_unit==null || product_unit.isEmpty()|| product_quantity==null || product_quantity.isEmpty()|| note_or_reason==null || note_or_reason.isEmpty()
//				|| customer_name==null || customer_name.isEmpty()) {
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
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST").build();
		}else{
			@SuppressWarnings("unused")
			String email=(String) jsObject.get("email");
			int role_id=(int) jsObject.get("role_id");
			System.out.println(role_id);
		}
		try {
			SqlConnection();		
		
			try {
				connection.setAutoCommit(false);
//				String Query="UPDATE barcode SET product_name='"+product_name+"',product_unit='"+ product_unit+"',"
//						+ "manufactur_date='"+manufacturing_date+"',expiry_date='"+expiry_date+"',price='"+price+"',cgst='"+cgst+"',"
//								+ "sgst='"+sgst+"',igst='"+igst+"',discount='"+discount+"',total='"+total+"' where barcode_id='"+barcode_id+"'";
//				
//				int st=statement.executeUpdate(Query);
//				if(st>0){
				
				//i commented, on 27-01-2020 5.00 PM 
//				String funQuery="select barcode_function(0,'"+barcode_number+"','','"+product_id+"',"
//						+ "'"+product_name+"','"+price+"','"+manufacturing_date+"',"
//								+ "'"+expiry_date+"','"+discount+"','"+vendor_id+"')";
//				String Query="select 1 from pricing_table where product_id='"+product_id+"' and store_id='"+store_id+"'";
//				resultset=statement.executeQuery(Query);
//				int result=0;
//				while(resultset.next()) {
//					result=resultset.getInt(1);
//					
//				}
				int seqid=0;
				//if(result==0) {
	                String Query1="select (max(pricinghistory_table.count),0)+1 from pricinghistory_table where product_id='"+product_id+"'";
					resultset=statement.executeQuery(Query1);
					while(resultset.next()) {
						seqid=resultset.getInt(1);
						
					}
					String Query2="update pricing_table set price=?,"+
							"manufactur_date=?,expiry_date=?"+ 
							"where barcode_number=? and product_id=? and store_id=?";
					preparestatement.executeUpdate();
					preparestatement.setString(1, price);	
					preparestatement.setDate(2, (java.sql.Date) manufacturing_date);	
					preparestatement.setDate(3, (java.sql.Date) expiry_date);	
					preparestatement.setString(4, barcode_number);	
					preparestatement.setString(5, product_id);	
					preparestatement.setString(6, store_id);
					preparestatement.executeUpdate();
					
					String Query3 =	"update pricinghistory_table set to_date=current_date where product_id=? and count=?";
					preparestatement.executeUpdate();
					preparestatement.setString(1, product_id);
					preparestatement.setInt(2, seqid-1);
					preparestatement.executeUpdate();

					String Query4 = "insert into pricinghistory_table(barcode_number,product_id,product_name,current_price,count,from_date) "+
							  "values(?,?,?,?,?,current_date)";
					
					preparestatement.setString(1, barcode_number);
					preparestatement.setString(2, product_id);
					preparestatement.setString(3, product_name);
					preparestatement.setString(4, price);
					preparestatement.setInt(5, seqid);
					
					int pst=preparestatement.executeUpdate();
						
			//	}
				
				if(pst>0){
					jsonObject.clear();
					jsonObject.put("Status", "Success");
					jsonObject.put("Message", "Updated Successfully");
				}
			else{
				connection.rollback();
				jsonObject.clear();
				jsonObject.put("Status", "Failed");
				jsonObject.put("Message", "Failed to update");
				}
				
			} catch (SQLException e) {
				connection.commit();
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Something went wrong");
				 jsonObject.put("error", e.getMessage());
			} catch (Exception e) {
				connection.commit();
				e.printStackTrace();
				jsonObject.clear();
				 jsonObject.put("Status", "Failed");
				 jsonObject.put("Message", "Please try again");
				 jsonObject.put("error", e.getMessage());
			}
		} catch (IOException e) {
			connection.commit();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 return Response.ok()
					.entity(jsonObject)
					.header("Access-Control-Allow-Methods", "POST").build();
	
	}
}
