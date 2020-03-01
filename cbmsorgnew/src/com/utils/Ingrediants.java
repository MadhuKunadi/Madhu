package com.utils;

import java.sql.Date;

public class Ingrediants {

	private String projectId;
	
	private String warehouse_id;
	private String warehouse_name;
	private String shipping_address;
	private String store_name;
	private Date raised_date;
	private int remaining_stock;
	private String store_id;


	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public int getRemaining_stock() {
		return remaining_stock;
	}

	public void setRemaining_stock(int remaining_stock) {
		this.remaining_stock = remaining_stock;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Date getRaised_date() {
		return raised_date;
	}

	public void setRaised_date(Date raised_date) {
		this.raised_date = raised_date;
	}

	public String getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	private String productId;
	
	private String product_name;
	
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	private String stage;

	private String quantity;

	private int indent_id;
	private String status;
	private int is_issued;
	private String product_description;
	private int ingrediant_id;
	private String id;
	
	private String indentId;
	
	private String a;
	

	private String stock_availability;
	private String total_quantity;
	private String remaining_quantity;
	private String issued_quantity;

	public String getStock_availability() {
		return stock_availability;
	}

	public void setStock_availability(String stock_availability) {
		this.stock_availability = stock_availability;
	}

	public String getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(String total_quantity) {
		this.total_quantity = total_quantity;
	}

	public String getRemaining_quantity() {
		return remaining_quantity;
	}

	public void setRemaining_quantity(String remaining_quantity) {
		this.remaining_quantity = remaining_quantity;
	}

	public String getIssued_quantity() {
		return issued_quantity;
	}

	public void setIssued_quantity(String issued_quantity) {
		this.issued_quantity = issued_quantity;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
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

	public int getIngrediant_id() {
		return ingrediant_id;
	}

	public int getIndent_id() {
		return indent_id;
	}

	public void setIngrediant_id(int ingrediant_id) {
		this.ingrediant_id = ingrediant_id;
	}

	public void setIndent_id(int indent_id) {
		this.indent_id = indent_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIs_issued() {
		return is_issued;
	}

	public void setIs_issued(int is_issued) {
		this.is_issued = is_issued;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
