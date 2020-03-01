package com.utils;

import java.util.List;

public class APIResponse {
	
	List<Object> listOfData;
	String status;
	String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Object> getListOfData() {
		return listOfData;
	}
	public void setListOfData(List<Object> listOfData) {
		this.listOfData = listOfData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
