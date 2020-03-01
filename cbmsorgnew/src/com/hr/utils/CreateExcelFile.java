/**
 * 
 */
package com.hr.utils;

/**
 * @author Admin
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;
/*
 * Here we will learn how to create Excel file and header for the same.
 */
public class CreateExcelFile {
	int rownum = 0;
	HSSFSheet firstSheet;
	Collection<File> files;
	HSSFWorkbook workbook;
	File exactFile;
	{
		workbook = new HSSFWorkbook();
		firstSheet = workbook.createSheet("FIRST SHEET");
		Row headerRow = firstSheet.createRow(rownum);
		headerRow.setHeightInPoints(40);
	}
	
	public static String filename="";
	public static void main(String args[]) throws Exception {
		List<String> headerRow = new ArrayList<String>();
		headerRow.add("Employee No");
		headerRow.add("Employee Name");
		headerRow.add("Employee Address");
		List<String> firstRow = new ArrayList<String>();
		firstRow.add("1111");
		firstRow.add("Gautam");
		firstRow.add("India");
		List<List> recordToAdd = new ArrayList<List>();
		recordToAdd.add(headerRow);
		for(int i=0;i<10;i++){
			recordToAdd.add(firstRow);	
		}
		
		CreateExcelFile cls = new CreateExcelFile(recordToAdd);
		cls.createExcelFile();
	}
	
	public static void storeExcel(String  String_array,String Project) throws Exception{
		List<String> empty_nextRow = new ArrayList<String>();
		List<List> recordToAdd = new ArrayList<List>();
		org.json.JSONObject projectObject=new JSONObject(Project);
		List<String> project_firstRow = new ArrayList<String>();
		project_firstRow.add("ProjectId");
		project_firstRow.add("Project name");
		project_firstRow.add("Project code");
		project_firstRow.add("Start date");
		project_firstRow.add("Project type");
		project_firstRow.add("Priority");
		recordToAdd.add(project_firstRow);
		List<String> project_nextRow = new ArrayList<String>();
		project_nextRow.add(projectObject.getString("project_id"));
		project_nextRow.add(projectObject.getString("project_name"));
		project_nextRow.add(projectObject.getString("project_code"));
		project_nextRow.add(projectObject.getString("start_date"));
		project_nextRow.add(projectObject.getString("project_type"));
		project_nextRow.add(projectObject.getString("priority"));
		filename=projectObject.getString("project_code")+"-"+projectObject.getString("project_id")+".xls";
		recordToAdd.add(project_nextRow);
		recordToAdd.add(empty_nextRow);
		String s_materials=projectObject.getJSONArray("materials").toString();
		org.json.JSONArray materials=new JSONArray(s_materials);
		List<String> material_firstRow = new ArrayList<String>();
		material_firstRow.add("Product Id");
		material_firstRow.add("Product name");
		material_firstRow.add("Product code");
		material_firstRow.add("Product unit");
		material_firstRow.add("CAS number");
		material_firstRow.add("Product quantity");
		material_firstRow.add("Used quantity");
		recordToAdd.add(material_firstRow);
		for(int m=0;m<materials.length();m++){
			org.json.JSONObject materialobject=materials.getJSONObject(m);
			
			List<String> material = new ArrayList<String>();
			material.add(materialobject.getString("product_id"));
			material.add(materialobject.getString("product_name"));
			material.add(materialobject.getString("product_code"));
			material.add(materialobject.getString("product_unit"));
			material.add(materialobject.getString("cas_number"));
			material.add(String.valueOf(materialobject.getDouble("quantity")));
			material.add(String.valueOf(materialobject.getDouble("used_qty")));
			recordToAdd.add(material);
		}
		recordToAdd.add(empty_nextRow);
		
		org.json.JSONArray array=new JSONArray(String_array);
		for(int s=0;s<array.length();s++){
			org.json.JSONObject stages_object=array.getJSONObject(s);
			
			List<String> headerRow = new ArrayList<String>();
			headerRow.add(stages_object.getString("stage"));
			String s_batches=stages_object.getJSONArray("batches").toString();
			
			recordToAdd.add(headerRow);
			org.json.JSONArray batches_array=new JSONArray(s_batches);
			List<String> batch_firstRow = new ArrayList<String>();
			batch_firstRow.add("Batch date");
			batch_firstRow.add("Batch no");
			batch_firstRow.add("Batch size");
			batch_firstRow.add("Batch status");
			batch_firstRow.add("Start date");
			batch_firstRow.add("End date");
			batch_firstRow.add("Batch Output");
			batch_firstRow.add("Output used");
			recordToAdd.add(batch_firstRow);
			for(int b=0;b<batches_array.length();b++){
				org.json.JSONObject batch_object=batches_array.getJSONObject(b);
				List<String> batch_nextRow = new ArrayList<String>();
				batch_nextRow.add(batch_object.getString("batch_date"));
				batch_nextRow.add(batch_object.getString("batch_id"));
				batch_nextRow.add(String.valueOf(batch_object.getDouble("batch_quantity")));
				batch_nextRow.add(batch_object.getString("batch_status"));
				batch_nextRow.add(batch_object.getString("started"));
				batch_nextRow.add(batch_object.getString("ended"));
				batch_nextRow.add(String.valueOf(batch_object.getDouble("output_yield")));
				batch_nextRow.add(String.valueOf(batch_object.getDouble("output_qty_used")));
				recordToAdd.add(batch_nextRow);
			}
			recordToAdd.add(empty_nextRow);
		}
		CreateExcelFile cls = new CreateExcelFile(recordToAdd);
		cls.createExcelFile();
	}
	void createExcelFile(){
		FileOutputStream fos = null;
		try {
			
			String cat_home=System.getProperty("catalina.home")+"/webapps/ROOT/";
			fos=new FileOutputStream(new File(cat_home+filename));
			HSSFCellStyle hsfstyle=workbook.createCellStyle();
			hsfstyle.setBorderBottom((short) 1);
			hsfstyle.setFillBackgroundColor((short)245);
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 CreateExcelFile(List<List> l1) throws Exception {
		try {
			for (int j = 0; j < l1.size(); j++) {
				Row row = firstSheet.createRow(rownum);
				List<String> l2= l1.get(j);
				for(int k=0; k<l2.size(); k++)
				{
					Cell cell = row.createCell(k);
					cell.setCellValue(l2.get(k));
				}
				rownum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
