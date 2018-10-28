package com.org.productplanner.beans;

public class Company {

	private String id;
	
	private String name;
	
	private String address;
	
	private String gstin;
	
	private String mobile;
	
	private String primaryPhone;
	
	private String secondaryPhone;
	
	private String email;
	
	private String state;
	
	public Company()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", gstin=" + gstin + ", mobile="
				+ mobile + ", primaryPhone=" + primaryPhone + ", secondaryPhone=" + secondaryPhone + ", email=" + email
				+ ", state=" + state + "]";
	}
	
}
