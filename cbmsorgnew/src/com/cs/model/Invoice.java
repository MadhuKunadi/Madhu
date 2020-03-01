package com.cs.model;

import org.json.simple.JSONArray;

public class Invoice {
	String id;
	String purchaseorder_id;
	String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPurchaseorder_id() {
		return purchaseorder_id;
	}
	public void setPurchaseorder_id(String purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}
	public String getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getProduct_details() {
		return product_details;
	}
	public void setProduct_details(String product_details) {
		this.product_details = product_details;
	}
	String invoice_id;
	String product_details;
	

}
