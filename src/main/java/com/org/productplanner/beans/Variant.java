package com.org.productplanner.beans;

public class Variant {

	private String name;
	
	private float cgst;
	
	private float cgstAmount;
	
	private float sgst;
	
	private float sgstAmount;
	
	private float igst;
	
	private float igstAmount;
	
	public Variant()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
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

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}

	@Override
	public String toString() {
		return "Variant [name=" + name + ", cgst=" + cgst + ", cgstAmount=" + cgstAmount + ", sgst=" + sgst
				+ ", sgstAmount=" + sgstAmount + ", igst=" + igst + ", igstAmount=" + igstAmount + "]";
	}
	
}
