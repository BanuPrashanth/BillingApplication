package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Invoice;

public class SimpleInvoiceRowMapper implements RowMapper<Invoice>{

	private boolean isReport;

	public SimpleInvoiceRowMapper(boolean isReport)
	{
		super();
		this.isReport=isReport;
	}
	
	@Override
	public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Invoice invoice=new Invoice();
		if(this.isReport)
		{
			invoice.setCustomerID(rs.getString(1));
			invoice.setCustomerGSTIN(rs.getString(2));
			invoice.setInvoiceID(rs.getString(4));
			invoice.setGrossAmount(rs.getFloat(6));
			invoice.setInvoiceDate(rs.getDate(9));
		}
		else
		{
			invoice.setInvoiceID(rs.getString("X_INVOICE_ID"));
			invoice.setInvoiceDate(rs.getDate("X_INVOICE_DATE"));
			invoice.setNetAmount(rs.getFloat("X_GROSS_AMOUNT"));
			invoice.setPreviousBalance(rs.getFloat("X_BALANCE"));
		}
		
		return invoice;
	}

}
