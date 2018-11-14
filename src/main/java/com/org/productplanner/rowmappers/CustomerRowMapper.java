package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Customer;

public class CustomerRowMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer=new Customer();
		customer.setCustomerID(rs.getString(2));
		customer.setCustomerName(rs.getString(3));
		customer.setAddress(rs.getString(4));
		customer.setPhone(rs.getString(5));
		customer.setGst(rs.getString(6));
		customer.setPanNumber(rs.getString(7));
		customer.setCustomerSPOC(rs.getString(8));
		customer.setOpeningBalance(rs.getString(9));
		customer.setState(rs.getString(10));
		customer.setStartDate(rs.getDate(11));
		return customer;
	}

	

}
