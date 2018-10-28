package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Company;

public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company=new Company();
		company.setId(rs.getString(1));
		company.setName(rs.getString(2));
		company.setAddress(rs.getString(3));
		company.setGstin(rs.getString(4));
		company.setMobile(rs.getString(5));
		company.setPrimaryPhone(rs.getString(6));
		company.setSecondaryPhone(rs.getString(7));
		company.setEmail(rs.getString(8));
		company.setState(rs.getString(9));;
		return company;
	}

}
