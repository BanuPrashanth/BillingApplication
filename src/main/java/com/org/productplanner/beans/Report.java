package com.org.productplanner.beans;

import java.sql.Date;
import java.util.List;

public class Report {

	/**
	 * common for all reports
	 */
	private Date fromDate;
	/**
	 * common for all reports
	 */
	private Date toDate;
	/**
	 * this member variable will be used
	 * for receipt report
	 */
	private List<Receipt> receipts;
	/**
	 * below member variable will be used
	 * for sales report
	 */
	private List<Invoice> invoices;

	private List<Variant> listofVariantInstancesSum;
	
	private List<Float> variants;

	/**
	 * below member variables will
	 * used only for Stock Wise Reports
	 */
	private String productID;
	
	private String productDescription;
	
	private int totalQuantity;
	
	private float totalAmount;
	
	private List<Stock> stocks;
	
	/**
	 * below member variables will
	 * used only for Party Wise Reports
	 */
	private String customerID;
	
	private String customerName;
	
	private String transactionType;
	
	private float closingBalance;
	
	private List<PartyWise> parties;
	
	public Report()
	{
		
	}
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	public List<Variant> getListofVariantInstancesSum() {
		return listofVariantInstancesSum;
	}

	public void setListofVariantInstancesSum(List<Variant> listofVariantInstancesSum) {
		this.listofVariantInstancesSum = listofVariantInstancesSum;
	}

	public List<Float> getVariants() {
		return variants;
	}

	public void setVariants(List<Float> variants) {
		this.variants = variants;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
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

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public float getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(float closingBalance) {
		this.closingBalance = closingBalance;
	}

	public List<PartyWise> getParties() {
		return parties;
	}

	public void setParties(List<PartyWise> parties) {
		this.parties = parties;
	}
	
	
}
