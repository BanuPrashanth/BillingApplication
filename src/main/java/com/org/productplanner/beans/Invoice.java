package com.org.productplanner.beans;


import java.sql.Date;
import java.util.List;

public class Invoice {

	private int objid;
	
	private int sno;
	
	private String customerID;
	
	private String customerGSTIN;
		
	private String invoiceID;
	
	private Date invoiceDate;
	
	private Date fromDate;
	
	private float cgstAmount;
	
	private float sgstAmount;
	
	private float igstAmount;
	
	private float gstAmount;
	
	private float discountAmount;
	
	private Date toDate;
	
	private float grossAmount;
	
	private float taxableAmount;
	
	private float netAmount;
	
	private float currentBalance;
	
	private float previousBalance;
	
	private String paymentStatus;
	
	private List<Notes> notes;
	
	private List<Float> variants;
	
	public Invoice(){
		
	}

	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerGSTIN() {
		return customerGSTIN;
	}

	public void setCustomerGSTIN(String customerGSTIN) {
		this.customerGSTIN = customerGSTIN;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public float getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public float getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public float getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}

	public float getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(float gstAmount) {
		this.gstAmount = gstAmount;
	}

	public float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public float getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(float grossAmount) {
		this.grossAmount = grossAmount;
	}

	public float getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(float taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public List<Float> getVariants() {
		return variants;
	}

	public void setVariants(List<Float> variants) {
		this.variants = variants;
	}

	public float getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(float previousBalance) {
		this.previousBalance = previousBalance;
	}

	public float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}

	@Override
	public String toString() {
		return "Invoice [objid=" + objid + ", sno=" + sno + ", customerID=" + customerID + ", customerGSTIN="
				+ customerGSTIN + ", invoiceID=" + invoiceID + ", invoiceDate=" + invoiceDate + ", fromDate=" + fromDate
				+ ", cgstAmount=" + cgstAmount + ", sgstAmount=" + sgstAmount + ", igstAmount=" + igstAmount
				+ ", gstAmount=" + gstAmount + ", discountAmount=" + discountAmount + ", toDate=" + toDate
				+ ", grossAmount=" + grossAmount + ", taxableAmount=" + taxableAmount + ", netAmount=" + netAmount
				+ ", currentBalance=" + currentBalance + ", previousBalance=" + previousBalance + ", paymentStatus="
				+ paymentStatus + "]";
	}

}
