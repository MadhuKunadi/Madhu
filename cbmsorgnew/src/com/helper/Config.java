package com.helper;

public class Config {
	public final static String  calculate_invoice="SELECT public.calculate_invoice_function(?,?,?,?)";
	public final static String gst_product="SELECT product_cgst, product_sgst, product_igst, product_taxable "
			+ "FROM public.tbl_products where product_id=?";
	public final static String add_Invoice="select fn_generate_invoice(?,?,?,?,?)";
	public final static String add_Accounts="insert into tbl_general_ledger(general_ledger_id,created_date,created_time,party_name,"
			+ "account_title,expense_category,debit_amount,credit_amount,payment_method,invoice_number,total_amount,invoice_date,"
			+ "receipt_upload,remarks,created_by) values(?,?,?,?,?,?,?,?,?,?)";
	public final static String get_daily_general ="SELECT  array_to_json(array_agg(row_to_json(ledger))) from (select *"
			+ " from tbl_general_ledger where created_date=? and created_by=?) ledger";
	public static String daily_general_insert ="INSERT INTO public.total_gl_transactions"
			+ "( created_by, total_transactions, type, transaction_date,balance_amount)VALUES (?, ?, ?, ?, ?)";
	public static final String calculate_salesorder="select calculate_salesorder_function(?,?,?,?)";
	public static final String add_salesorder= "select fn_generate_salesorder(?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String salesList="select salesorder_id,barcode_number,customer_id,customer_name,product_name,product_id,"
			+ "quantity,unit,product_price::money::numeric::float8,cgst,sgst,igst,discount,cgst_amount::money::numeric::float8,"
			+ "sgst_amount::money::numeric::float8,igst_amount::money::numeric::float8,discount_amount::money::numeric::float8,"
			+ "product_total::money::numeric::float8 from tbl_sales";
	public static final String store_products_list ="select product_name as product_description from "
			+ "inventory_received_order_from_warehouse where store_id=?";
	public static final String module_count="select count(*), "
			+ "(select count(*) from tbl_vendor where mark_for_deletion=0) as vendor_count, "
			+ "(select count(*) from tbl_vendor where status='Active') as vendor_ActiveCount, "
			+ "(select count(*) from tbl_vendor where status='InActive') as Vendor_InActiveCount,"
			+ "(select count(*) from tbl_warehouse where mark_for_deletion=0) as warehouse_count,  "
			+ "(select count(*) from tbl_project) as project_count "
			+ "from tbl_department where mark_for_deletion=0";
	public static final String get_products="select tbl_products.product_id,"
			    + "tbl_products.product_name||'-'||tbl_brand.brand_name||'-'||product_unit.product_unit as product_description from tbl_products "
				+ "inner join product_unit on product_unit.id=tbl_products.product_unit "
				+ "inner join tbl_brand on tbl_products.product_brand_id=tbl_brand.brand_id "
				+ "where tbl_products.mark_for_deletion=0 and tbl_products.item_prohibited=0 and tbl_products.item_hide=0";
	public final static String add_Transaction="select dayend_transaction_function(?,?,?,?)";
	public final static String customer_ledger_list="select * from individual_customer_ldeger";
	public final static String vendor_ledger_list="select * from individual_vendor_ldeger where vendor_id=?";
}
