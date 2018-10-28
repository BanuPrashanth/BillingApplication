package com.org.productplanner.beans;

import java.util.Date;

public class Product {
	
	private int objid;
	
	private String productID;
	
	private String productDescription;
	
	private float rate;

	private String status;
	
	private float gst;
	
	private String productType;
	
	private String otherProductCode;
	
	private String otherProductDetails;
	
	private Date effectiveDate;
	
	//Default constructor
	public Product()
	{
		
	}
	
	public Product(String productID, String productDescription,int rate,int gst)
	{
		this.productID = productID;
		this.productDescription = productDescription;
		this.rate=rate;
		this.gst=gst;
	}
	//Parametrized constructor
	public Product(int objid, String productID, String productDescription,int rate, String status, int gst,
			String productType, String otherProductCode, Date effectiveDate) {
		
		this.objid = objid;
		this.productID = productID;
		this.productDescription = productDescription;
		this.rate=rate;
		if("A".equalsIgnoreCase(status))
		{
			this.status = "Active";
		}
		else
		{
			this.status = "Inactive";
		}
		
		this.gst = gst;
		this.productType = productType;
		this.otherProductCode = otherProductCode;
		this.effectiveDate = effectiveDate;
	}

	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
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

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		
		this.status = status;
	}

	public float getGst() {
		return gst;
	}

	public void setGst(float gst) {
		this.gst = gst;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOtherProductCode() {
		return otherProductCode;
	}

	public void setOtherProductCode(String otherProductCode) {
		this.otherProductCode = otherProductCode;
	}

	public String getOtherProductDetails() {
		return otherProductDetails;
	}

	public void setOtherProductDetails(String otherProductDetails) {
		this.otherProductDetails = otherProductDetails;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public String toString() {
		return "Product [objid=" + objid + ", productID=" + productID + ", productDescription=" + productDescription
				+ ", rate=" + rate + ", status=" + status + ", gst=" + gst + ", productType=" + productType
				+ ", otherProductCode=" + otherProductCode + ", otherProductDetails=" + otherProductDetails
				+ ", effectiveDate=" + effectiveDate + "]";
	}

	
	
	
}
