package com.org.productplanner.beans;

import java.util.Date;
import java.util.List;


public class DeliveryNote {
	
	private int objid;
	
	private String deliveryNoteID;
		
	private String customerID;
	
	private String customerName;
	
	private List<Notes> notes;
	
	private Date deliveryDate;
	
	private float grandTotal;
	
	private boolean invoiceGenerated;
	
	private String status;
	
	private int sno;
	
	private java.sql.Date fromDate;
	
	private java.sql.Date toDate;
	
	public DeliveryNote()
	{
		
	}

	public DeliveryNote(String deliveryNoteID, Date deliveryDate) {
		this.deliveryNoteID = deliveryNoteID;
		this.deliveryDate = deliveryDate;
	}

	public int getObjid() {
		return objid;
	}


	public void setObjid(int objid) {
		this.objid = objid;
	}


	public String getDeliveryNoteID() {
		return deliveryNoteID;
	}


	public void setDeliveryNoteID(String deliveryNoteID) {
		this.deliveryNoteID = deliveryNoteID;
	}


	public String getCustomerID() {
		return customerID;
	}


	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public List<Notes> getNotes() {
		return notes;
	}


	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public float getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}


	public boolean getInvoiceGenerated() {
		return invoiceGenerated;
	}


	public void setInvoiceGenerated(boolean invoiceGenerated) {
		this.invoiceGenerated = invoiceGenerated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public java.sql.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.sql.Date fromDate) {
		this.fromDate = fromDate;
	}

	public java.sql.Date getToDate() {
		return toDate;
	}

	public void setToDate(java.sql.Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "DeliveryNote [objid=" + objid + ", deliveryNoteID=" + deliveryNoteID + ", customerID=" + customerID
				+ ", customerName=" + customerName + ", deliveryDate=" + deliveryDate + ", grandTotal=" + grandTotal
				+ ", invoiceGenerated=" + invoiceGenerated + ", status=" + status + ", sno=" + sno + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

}
