/**
 * 
 */
package com.cs.model;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Admin
 *
 */

public class Model {
	String id;
	String product_id;
	String  stage_name;
	String  project_id;
	String stages;
	String products;
	String type;
	String model_note;
	double batch_size;
	double  antisipated_yield;
	double output_yield;
	String comments;
	int inputstages;
	String batches;
	String departmentid;
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	String indentno;
	String project_type;
	String status;
	String ar_number;
	String in_house_batch_id;
	String ar_doc;
	int isreprification;
	String purpose;
	String product_unit;
	
	
	

	public String getProduct_unit() {
		return product_unit;
	}
	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}
	public int getIsreprification() {
		return isreprification;
	}
	public void setIsreprification(int isreprification) {
		this.isreprification = isreprification;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the ar_doc
	 */
	public String getAr_doc() {
		return ar_doc;
	}
	/**
	 * @param ar_doc the ar_doc to set
	 */
	public void setAr_doc(String ar_doc) {
		this.ar_doc = ar_doc;
	}
	/**
	 * @return the ar_number
	 */
	public String getAr_number() {
		return ar_number;
	}
	/**
	 * @param ar_number the ar_number to set
	 */
	public void setAr_number(String ar_number) {
		this.ar_number = ar_number;
	}
	/**
	 * @return the in_house_batch_id
	 */
	public String getIn_house_batch_id() {
		return in_house_batch_id;
	}
	/**
	 * @param in_house_batch_id the in_house_batch_id to set
	 */
	public void setIn_house_batch_id(String in_house_batch_id) {
		this.in_house_batch_id = in_house_batch_id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the project_type
	 */
	public String getProject_type() {
		return project_type;
	}
	/**
	 * @param project_type the project_type to set
	 */
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	/**
	 * @return the indentno
	 */
	public String getIndentno() {
		return indentno;
	}
	/**
	 * @param indentno the indentno to set
	 */
	public void setIndentno(String indentno) {
		this.indentno = indentno;
	}
	/**
	 * @return the deprtmentid
	 */
//	public int getDepartmentid() {
//		return departmentid;
//	}
//	/**
//	 * @param deprtmentid the deprtmentid to set
//	 */
//	public void setDepartmentid(int departmentid) {
//		this.departmentid = departmentid;
//	}
	/**
	 * @return the batches
	 */
	public String getBatches() {
		return batches;
	}
	/**
	 * @param batches the batches to set
	 */
	public void setBatches(String batches) {
		this.batches = batches;
	}

	String start_date ;
	String start_time;
	String end_date ;
	String  end_time ;
	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return the start_time
	 */
	public String getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	/**
	 * @return the end_date
	 */
	public String getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return the end_time
	 */
	public String getEnd_time() {
		return end_time;
	}
	/**
	 * @param end_time the end_time to set
	 */
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	/**
	 * @return the inputstages
	 */
	public int getInputstages() {
		return inputstages;
	}
	/**
	 * @param inputstages the inputstages to set
	 */
	public void setInputstages(int inputstages) {
		this.inputstages = inputstages;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the output_yield
	 */
	public double getOutput_yield() {
		return output_yield;
	}
	/**
	 * @param output_yield the output_yield to set
	 */
	public void setOutput_yield(double output_yield) {
		this.output_yield = output_yield;
	}

	String projectbatchid;

	/**
	 * @return the projectbatchid
	 */
	public String getProjectbatchid() {
		return projectbatchid;
	}

	/**
	 * @param projectbatchid the projectbatchid to set
	 */
	public void setProjectbatchid(String projectbatchid) {
		this.projectbatchid = projectbatchid;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the model_note
	 */
	public String getModel_note() {
		return model_note;
	}
	/**
	 * @param model_note the model_note to set
	 */
	public void setModel_note(String model_note) {
		this.model_note = model_note;
	}

	/**
	 * @return the batch_size
	 */
	public double getBatch_size() {
		return batch_size;
	}

	/**
	 * @param batch_size the batch_size to set
	 */
	public void setBatch_size(double batch_size) {
		this.batch_size = batch_size;
	}

	/**
	 * @return the antisipated_yield
	 */
	public double getAntisipated_yield() {
		return antisipated_yield;
	}

	/**
	 * @param antisipated_yield the antisipated_yield to set
	 */
	public void setAntisipated_yield(double antisipated_yield) {
		this.antisipated_yield = antisipated_yield;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the stages
	 */
	public String getStages() {
		return stages;
	}

	/**
	 * @param stages the stages to set
	 */
	public void setStages(String stages) {
		this.stages = stages;
	}

	/**
	 * @return the products
	 */
	
	public String getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(String products) {
		this.products = products;
	}

	/**
	 * @return the project_id
	 */
	public String getProject_id() {
		return project_id;
	}

	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the stage_name
	 */
	public String getStage_name() {
		return stage_name;
	}

	/**
	 * @param stage_name the stage_name to set
	 */
	public void setStage_name(String stage_name) {
		this.stage_name = stage_name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
