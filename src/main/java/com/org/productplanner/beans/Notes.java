package com.org.productplanner.beans;

public class Notes {

	
	private int objid;
	private String productID;
	private String productDescription;
	private String notation;
	private int quantity;
	private float rate;
	private float total;
	private int discount;
	private float netTotal;
	private float cgst;
	private float cgstAmount;
	private float sgst;
	private float sgstAmount;
	private float netPrice;
	private String hsnNumber;
	private String deliveryNoteID;
	
	public Notes()
	{
		
	}
	
	//Note with tax
	public Notes(int objid, String productID, String productDescription, String notation, int quantity, int rate,
			float total, int discount, float netTotal, int cgst, float cgstAmount, int sgst, float sgstAmount,
			float netPrice, String hsnNumber, String deliveryNoteID) {
		super();
		this.objid = objid;
		this.productID = productID;
		this.productDescription = productDescription;
		this.notation = notation;
		this.quantity = quantity;
		this.rate = rate;
		this.total = total;
		this.discount = discount;
		this.netTotal = netTotal;
		this.cgst = cgst;
		this.cgstAmount = cgstAmount;
		this.sgst = sgst;
		this.sgstAmount = sgstAmount;
		this.netPrice = netPrice;
		this.hsnNumber = hsnNumber;
		this.deliveryNoteID = deliveryNoteID;
	}

	//Note without tax
	public Notes(int objid, String productID, String productDescription, String notation, int quantity, int rate,
			float total, int discount, float netTotal, String hsnNumber, String deliveryNoteID) {
		super();
		this.objid = objid;
		this.productID = productID;
		this.productDescription = productDescription;
		this.notation = notation;
		this.quantity = quantity;
		this.rate = rate;
		this.total = total;
		this.discount = discount;
		this.netTotal = netTotal;
		this.hsnNumber = hsnNumber;
		this.deliveryNoteID = deliveryNoteID;
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

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public float getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(float netTotal) {
		this.netTotal = netTotal;
	}	

	public float getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}
	
	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public float getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(float netPrice) {
		this.netPrice = netPrice;
	}

	public String getHsnNumber() {
		return hsnNumber;
	}

	public void setHsnNumber(String hsnNumber) {
		this.hsnNumber = hsnNumber;
	}

	public String getDeliveryNoteID() {
		return deliveryNoteID;
	}

	public void setDeliveryNoteID(String deliveryNoteID) {
		this.deliveryNoteID = deliveryNoteID;
	}	

	@Override
	public String toString() {
		return "Notes [objid=" + objid + ", productID=" + productID + ", productDescription=" + productDescription
				+ ", notation=" + notation + ", quantity=" + quantity + ", rate=" + rate + ", total=" + total
				+ ", discount=" + discount + ", netTotal=" + netTotal + ", cgst=" + cgst + ", cgstAmount=" + cgstAmount
				+ ", sgst=" + sgst + ", sgstAmount=" + sgstAmount + ", netPrice=" + netPrice + ", hsnNumber="
				+ hsnNumber + ", deliveryNoteID=" + deliveryNoteID + "]";
	}

	
	
}
