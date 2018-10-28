package com.org.productplanner.beans;

import java.util.Date;
import java.util.List;

public class Receipt {

	private int objid;
	
	private String receiptID;
	
	private String customerID;
	
	private float openingBalance;
	
	private String transactionType;
	
	private String comment;

	private float closingBalance;
	
	private float closingAdvanceAmount;
	
	private float totalAmtPaid;
	
	private java.sql.Date fromDate;
	
	private java.sql.Date toDate;
	
	private java.sql.Date receiptDate;
	
	private List<Invoice> invoices;
	
	public Receipt()
	{
		
	}

	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
	}

	public String getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public float getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(float openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(float closingBalance) {
		this.closingBalance = closingBalance;
	}
	
	public float getClosingAdvanceAmount() {
		return closingAdvanceAmount;
	}

	public void setClosingAdvanceAmount(float closingAdvanceAmount) {
		this.closingAdvanceAmount = closingAdvanceAmount;
	}
	public float getTotalAmtPaid() {
		return totalAmtPaid;
	}

	public void setTotalAmtPaid(float totalAmtPaid) {
		this.totalAmtPaid = totalAmtPaid;
	}

	public java.sql.Date getReceiptDate() {
		return receiptDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.sql.Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(java.sql.Date toDate) {
		this.toDate = toDate;
	}

	public void setReceiptDate(java.sql.Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public String toString() {
		return "Receipt [objid=" + objid + ", receiptID=" + receiptID + ", customerID=" + customerID
				+ ", openingBalance=" + openingBalance + ", transactionType=" + transactionType + ", comment=" + comment
				+ ", closingBalance=" + closingBalance + ", totalAmtPaid="
				+ totalAmtPaid + ", fromDate=" + fromDate + ", toDate=" + toDate + ", receiptDate=" + receiptDate + "]";
	}
	
	
}
