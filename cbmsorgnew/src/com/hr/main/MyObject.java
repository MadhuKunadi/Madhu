/**
 * 
 */
package com.hr.main;

/**
 * @author Admin
 *
 */
public class MyObject {
	String data;
	String sessionid;
	String newpassword;
	String confirmnewpassword;
	String doc_id;
	String process_id;
	String comments;
	
	/**
	 * @return the process_id
	 */
	public String getProcess_id() {
		return process_id;
	}
	/**
	 * @param process_id the process_id to set
	 */
	public void setProcess_id(String process_id) {
		this.process_id = process_id;
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
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}
	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}
	String document;
	
	/**
	 * @return the doc_id
	 */
	public String getDoc_id() {
		return doc_id;
	}
	/**
	 * @param doc_id the doc_id to set
	 */
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	/**
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}
	/**
	 * @param newpassword the newpassword to set
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * @return the confirmnewpassword
	 */
	public String getConfirmnewpassword() {
		return confirmnewpassword;
	}
	/**
	 * @param confirmnewpassword the confirmnewpassword to set
	 */
	public void setConfirmnewpassword(String confirmnewpassword) {
		this.confirmnewpassword = confirmnewpassword;
	}
	/**
	 * @return the sessionid
	 */
	public String getSessionid() {
		return sessionid;
	}
	/**
	 * @param sessionid the sessionid to set
	 */
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the apiID
	 */
	public String getApiID() {
		return apiID;
	}
	/**
	 * @param apiID the apiID to set
	 */
	public void setApiID(String apiID) {
		this.apiID = apiID;
	}
	String  apiID;
	
	int employeeid;
	String emp_firstname;
	String emp_lastname;
	String emp_fathername;
	String emp_dob;
	String emp_gender;
	String emp_phone;
	String emp_emailid;
	String emp_comm_adress;
	String emp_perm_adress;
	String emp_nationality;
	String emp_maritual_status;
	String emp_skills;
	int org_id;
	int departmentid;
	int positionid;
	int roleid ;
	String reporting;
	int userid;
	String name;
	int reportingid;
	String department;
	String position;
	
	
	int organizationid ;
	String organizationname ;
	int orgunitid ;
	String organizationunitname ;
	int departmentunitid; 
	String departmentunitName;
	
	/**
	 * @return the organizationid
	 */
	public int getOrganizationid() {
		return organizationid;
	}
	/**
	 * @param organizationid the organizationid to set
	 */
	public void setOrganizationid(int organizationid) {
		this.organizationid = organizationid;
	}
	/**
	 * @return the organizationname
	 */
	public String getOrganizationname() {
		return organizationname;
	}
	/**
	 * @param organizationname the organizationname to set
	 */
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	/**
	 * @return the orgunitid
	 */
	public int getOrgunitid() {
		return orgunitid;
	}
	/**
	 * @param orgunitid the orgunitid to set
	 */
	public void setOrgunitid(int orgunitid) {
		this.orgunitid = orgunitid;
	}
	/**
	 * @return the organizationunitname
	 */
	public String getOrganizationunitname() {
		return organizationunitname;
	}
	/**
	 * @param organizationunitname the organizationunitname to set
	 */
	public void setOrganizationunitname(String organizationunitname) {
		this.organizationunitname = organizationunitname;
	}
	/**
	 * @return the departmentunitid
	 */
	public int getDepartmentunitid() {
		return departmentunitid;
	}
	/**
	 * @param departmentunitid the departmentunitid to set
	 */
	public void setDepartmentunitid(int departmentunitid) {
		this.departmentunitid = departmentunitid;
	}
	/**
	 * @return the departmentunitName
	 */
	public String getDepartmentunitName() {
		return departmentunitName;
	}
	/**
	 * @param departmentunitName the departmentunitName to set
	 */
	public void setDepartmentunitName(String departmentunitName) {
		this.departmentunitName = departmentunitName;
	}
	/**
	 * @return the department
	 */
	public String  getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @return the reportingid
	 */
	public int getReportingid() {
		return reportingid;
	}
	/**
	 * @param reportingid the reportingid to set
	 */
	public void setReportingid(int reportingid) {
		this.reportingid = reportingid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	String description;
	/**
	 * @return the reporting
	 */
	public String getReporting() {
		return reporting;
	}
	/**
	 * @param reporting the reporting to set
	 */
	public void setReporting(String reporting) {
		this.reporting = reporting;
	}
	/**
	 * @return the roleid
	 */
	public int getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	String emp_joining_date;
	String emp_status;
	String emp_joining_sal;
	String bnk_acnt_holdername;
	String bnk_acnt_number;
	String bnk_name;
	String bnk_ifsc;
	String bnk_branch;
	int createdby;
	String username;
	String password;
	String emp_job_history;
	String varempid, bloodgroup, emp_alt_phone,emp_family_details;
	/**
	 * @return the varempid
	 */
	public String getVarempid() {
		return varempid;
	}
	/**
	 * @param varempid the varempid to set
	 */
	public void setVarempid(String varempid) {
		this.varempid = varempid;
	}
	/**
	 * @return the bloodgroup
	 */
	public String getBloodgroup() {
		return bloodgroup;
	}
	/**
	 * @param bloodgroup the bloodgroup to set
	 */
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	/**
	 * @return the emp_alt_phone
	 */
	public String getEmp_alt_phone() {
		return emp_alt_phone;
	}
	/**
	 * @param emp_alt_phone the emp_alt_phone to set
	 */
	public void setEmp_alt_phone(String emp_alt_phone) {
		this.emp_alt_phone = emp_alt_phone;
	}
	/**
	 * @return the emp_family_details
	 */
	public String getEmp_family_details() {
		return emp_family_details;
	}
	/**
	 * @param emp_family_details the emp_family_details to set
	 */
	public void setEmp_family_details(String emp_family_details) {
		this.emp_family_details = emp_family_details;
	}
	/**
	 * @return the employeeid
	 */
	public int getEmployeeid() {
		return employeeid;
	}
	/**
	 * @param employeeid the employeeid to set
	 */
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	/**
	 * @return the emp_firstname
	 */
	public String getEmp_firstname() {
		return emp_firstname;
	}
	/**
	 * @param emp_firstname the emp_firstname to set
	 */
	public void setEmp_firstname(String emp_firstname) {
		this.emp_firstname = emp_firstname;
	}
	/**
	 * @return the emp_lastname
	 */
	public String getEmp_lastname() {
		return emp_lastname;
	}
	/**
	 * @param emp_lastname the emp_lastname to set
	 */
	public void setEmp_lastname(String emp_lastname) {
		this.emp_lastname = emp_lastname;
	}
	/**
	 * @return the emp_fathername
	 */
	public String getEmp_fathername() {
		return emp_fathername;
	}
	/**
	 * @param emp_fathername the emp_fathername to set
	 */
	public void setEmp_fathername(String emp_fathername) {
		this.emp_fathername = emp_fathername;
	}
	/**
	 * @return the emp_dob
	 */
	public String getEmp_dob() {
		return emp_dob;
	}
	/**
	 * @param emp_dob the emp_dob to set
	 */
	public void setEmp_dob(String emp_dob) {
		this.emp_dob = emp_dob;
	}
	/**
	 * @return the emp_gender
	 */
	public String getEmp_gender() {
		return emp_gender;
	}
	/**
	 * @param emp_gender the emp_gender to set
	 */
	public void setEmp_gender(String emp_gender) {
		this.emp_gender = emp_gender;
	}
	/**
	 * @return the emp_phone
	 */
	public String getEmp_phone() {
		return emp_phone;
	}
	/**
	 * @param emp_phone the emp_phone to set
	 */
	public void setEmp_phone(String emp_phone) {
		this.emp_phone = emp_phone;
	}
	/**
	 * @return the emp_emailid
	 */
	public String getEmp_emailid() {
		return emp_emailid;
	}
	/**
	 * @param emp_emailid the emp_emailid to set
	 */
	public void setEmp_emailid(String emp_emailid) {
		this.emp_emailid = emp_emailid;
	}
	/**
	 * @return the emp_comm_adress
	 */
	public String getEmp_comm_adress() {
		return emp_comm_adress;
	}
	/**
	 * @param emp_comm_adress the emp_comm_adress to set
	 */
	public void setEmp_comm_adress(String emp_comm_adress) {
		this.emp_comm_adress = emp_comm_adress;
	}
	/**
	 * @return the emp_perm_adress
	 */
	public String getEmp_perm_adress() {
		return emp_perm_adress;
	}
	/**
	 * @param emp_perm_adress the emp_perm_adress to set
	 */
	public void setEmp_perm_adress(String emp_perm_adress) {
		this.emp_perm_adress = emp_perm_adress;
	}
	/**
	 * @return the emp_nationality
	 */
	public String getEmp_nationality() {
		return emp_nationality;
	}
	/**
	 * @param emp_nationality the emp_nationality to set
	 */
	public void setEmp_nationality(String emp_nationality) {
		this.emp_nationality = emp_nationality;
	}
	/**
	 * @return the emp_maritual_status
	 */
	public String getEmp_maritual_status() {
		return emp_maritual_status;
	}
	/**
	 * @param emp_maritual_status the emp_maritual_status to set
	 */
	public void setEmp_maritual_status(String emp_maritual_status) {
		this.emp_maritual_status = emp_maritual_status;
	}
	/**
	 * @return the emp_skills
	 */
	public String getEmp_skills() {
		return emp_skills;
	}
	/**
	 * @param emp_skills the emp_skills to set
	 */
	public void setEmp_skills(String emp_skills) {
		this.emp_skills = emp_skills;
	}

	/**
	 * @return the org_id
	 */
	public int getOrg_id() {
		return org_id;
	}
	/**
	 * @param org_id the org_id to set
	 */
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	/**
	 * @return the departmentid
	 */
	public int getDepartmentid() {
		return departmentid;
	}
	/**
	 * @param departmentid the departmentid to set
	 */
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}
	/**
	 * @return the positionid
	 */
	public int getPositionid() {
		return positionid;
	}
	/**
	 * @param positionid the positionid to set
	 */
	public void setPositionid(int positionid) {
		this.positionid = positionid;
	}
	/**
	 * @return the emp_joining_date
	 */
	public String getEmp_joining_date() {
		return emp_joining_date;
	}
	/**
	 * @param emp_joining_date the emp_joining_date to set
	 */
	public void setEmp_joining_date(String emp_joining_date) {
		this.emp_joining_date = emp_joining_date;
	}
	/**
	 * @return the emp_status
	 */
	public String getEmp_status() {
		return emp_status;
	}
	/**
	 * @param emp_status the emp_status to set
	 */
	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
	}
	/**
	 * @return the emp_joining_sal
	 */
	public String getEmp_joining_sal() {
		return emp_joining_sal;
	}
	/**
	 * @param emp_joining_sal the emp_joining_sal to set
	 */
	public void setEmp_joining_sal(String emp_joining_sal) {
		this.emp_joining_sal = emp_joining_sal;
	}
	/**
	 * @return the bnk_acnt_holdername
	 */
	public String getBnk_acnt_holdername() {
		return bnk_acnt_holdername;
	}
	/**
	 * @param bnk_acnt_holdername the bnk_acnt_holdername to set
	 */
	public void setBnk_acnt_holdername(String bnk_acnt_holdername) {
		this.bnk_acnt_holdername = bnk_acnt_holdername;
	}
	/**
	 * @return the bnk_acnt_number
	 */
	public String getBnk_acnt_number() {
		return bnk_acnt_number;
	}
	/**
	 * @param bnk_acnt_number the bnk_acnt_number to set
	 */
	public void setBnk_acnt_number(String bnk_acnt_number) {
		this.bnk_acnt_number = bnk_acnt_number;
	}
	/**
	 * @return the bnk_name
	 */
	public String getBnk_name() {
		return bnk_name;
	}
	/**
	 * @param bnk_name the bnk_name to set
	 */
	public void setBnk_name(String bnk_name) {
		this.bnk_name = bnk_name;
	}
	/**
	 * @return the bnk_ifsc
	 */
	public String getBnk_ifsc() {
		return bnk_ifsc;
	}
	/**
	 * @param bnk_ifsc the bnk_ifsc to set
	 */
	public void setBnk_ifsc(String bnk_ifsc) {
		this.bnk_ifsc = bnk_ifsc;
	}
	/**
	 * @return the bnk_branch
	 */
	public String getBnk_branch() {
		return bnk_branch;
	}
	/**
	 * @param bnk_branch the bnk_branch to set
	 */
	public void setBnk_branch(String bnk_branch) {
		this.bnk_branch = bnk_branch;
	}
	/**
	 * @return the createdby
	 */
	public int getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the emp_job_history
	 */
	public String getEmp_job_history() {
		return emp_job_history;
	}
	/**
	 * @param emp_job_history the emp_job_history to set
	 */
	public void setEmp_job_history(String emp_job_history) {
		this.emp_job_history = emp_job_history;
	}
	
	String purchaseorder_id;
	String store_id;
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getPurchaseorder_id() {
		return purchaseorder_id;
	}
	public void setPurchaseorder_id(String purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	public String getWareouse_name() {
		return wareouse_name;
	}
	public void setWareouse_name(String wareouse_name) {
		this.wareouse_name = wareouse_name;
	}
	public String getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getPurchaseorder_status() {
		return purchaseorder_status;
	}
	public void setPurchaseorder_status(String purchaseorder_status) {
		this.purchaseorder_status = purchaseorder_status;
	}
	public String getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	String warehouse_id;
	String vendor_id;
	String wareouse_name;
	String invoice_id;
	String purchaseorder_status;
	String shipping_address;
	String product_description;
	String status;
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	String vendor_name;
	String  created_date;
	String to_date;
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	int expiry_date;
	public int getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(int expiry_date) {
		this.expiry_date = expiry_date;
	}
	public int getManufacture_date() {
		return manufacture_date;
	}
	public void setManufacture_date(int manufacture_date) {
		this.manufacture_date = manufacture_date;
	}
	public double getProduct_total() {
		return product_total;
	}
	public void setProduct_total(double product_total) {
		this.product_total = product_total;
	}
	public int getReturn_qty() {
		return return_qty;
	}
	public void setReturn_qty(int return_qty) {
		this.return_qty = return_qty;
	}
	int manufacture_date;
	double product_total;
	int return_qty;
	String purchaseorder_referencenumber;
    String exchange_type;
    String purchaseorder_returns_id;
    String customer_name;
    public String getPurchaseorder_returns_date() {
		return purchaseorder_returns_date;
	}
	public void setPurchaseorder_returns_date(String purchaseorder_returns_date) {
		this.purchaseorder_returns_date = purchaseorder_returns_date;
	}
	public String getPurchase_return_comments() {
		return purchase_return_comments;
	}
	public void setPurchase_return_comments(String purchase_return_comments) {
		this.purchase_return_comments = purchase_return_comments;
	}
	String purchaseorder_returns_date;
    String purchase_return_comments;
    
    public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	String role;
	public String getPurchaseorder_returns_id() {
		return purchaseorder_returns_id;
	}
	public void setPurchaseorder_returns_id(String purchaseorder_returns_id) {
		this.purchaseorder_returns_id = purchaseorder_returns_id;
	}
	public String getExchange_type() {
	return exchange_type;
}
public void setExchange_type(String exchange_type) {
	this.exchange_type = exchange_type;
}
	public String getPurchaseorder_referencenumber() {
		return purchaseorder_referencenumber;
	}
	public void setPurchaseorder_referencenumber(
			String purchaseorder_referencenumber) {
		this.purchaseorder_referencenumber = purchaseorder_referencenumber;
	}
	String from_date;

	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	String purchaseorder_raised_date;

	public String getPurchaseorder_raised_date() {
		return purchaseorder_raised_date;
	}
	public void setPurchaseorder_raised_date(String purchaseorder_raised_date) {
		this.purchaseorder_raised_date = purchaseorder_raised_date;
	}
	String id;
	String product_details;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct_details() {
		return product_details;
	}
	public void setProduct_details(String product_details) {
		this.product_details = product_details;
	}
	String salesorder_id;

	public String getSalesorder_id() {
		return salesorder_id;
	}
	public void setSalesorder_id(String salesorder_id) {
		this.salesorder_id = salesorder_id;
	}
}
