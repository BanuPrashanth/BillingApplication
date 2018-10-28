package com.org.productplanner.beans;

import java.sql.Date;

public class User {
	
	private int objid;
	
	private String userID;
	
	private String userName;
	
	private String password;

	private String address;
	
	private String phone;
	
	private String status;
	
	private String userType;
	
	private Date createDate;

	public User()
	{
		
	}

	public User(int objid, String userID, String userName, String password, String address, String phone, String status,
			String userType, Date createDate) {
		super();
		this.objid = objid;
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.status = status;
		this.userType = userType;
		this.createDate = createDate;
	}

	public int getObjid() {
		return objid;
	}

	public void setObjid(int objid) {
		this.objid = objid;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType=userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
}
