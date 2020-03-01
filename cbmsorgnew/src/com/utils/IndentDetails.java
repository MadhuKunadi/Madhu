package com.utils;

import java.util.List;

public class IndentDetails {
	
	
	
	private String indentId;
	private String projectId;
	private String createdBy;
	private String updatedBy;
	private String indentType;
	private String deptName;
	private String productId;
	private String warehouse_id;
	
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	private String quantity;
	private List<Ingrediants> listOfIngrediants;

	private String id;
	private int indent_id;
	private String status;
	private int is_issued;
	private String created_on;
	
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public int getIs_issued() {
		return is_issued;
	}
	public void setIs_issued(int is_issued) {
		this.is_issued = is_issued;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIndent_id() {
		return indent_id;
	}
	public void setIndent_id(int indent_id) {
		this.indent_id = indent_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndentId() {
		return indentId;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getIndentType() {
		return indentType;
	}
	public void setIndentType(String indentType) {
		this.indentType = indentType;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public List<Ingrediants> getListOfIngrediants() {
		return listOfIngrediants;
	}
	public void setListOfIngrediants(List<Ingrediants> listOfIngrediants) {
		this.listOfIngrediants = listOfIngrediants;
	}
	
	
}
