package com.org.productplanner.beans;

import java.sql.Date;

public class Stock {

	private int objid;
	
	private Date date;
	
	private String customerName;
	
	private int quantity;
	
	private float amount;

	public Stock()
	{
		
	}
	
	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Stock [objid=" + objid + ", date=" + date + ", customerID=" + customerName + ", quantity=" + quantity
				+ ", amount=" + amount + "]";
	}
	
	
}
