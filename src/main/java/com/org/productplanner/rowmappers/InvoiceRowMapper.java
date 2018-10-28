package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Invoice;

public class InvoiceRowMapper implements RowMapper<Invoice>{

	private boolean isReport;
	
	public InvoiceRowMapper(boolean isReport)
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
			invoice.setCgstAmount(rs.getFloat(7));
			invoice.setSgstAmount(rs.getFloat(8));
			invoice.setGstAmount(rs.getFloat(9));
			invoice.setNetAmount(rs.getFloat(10));
			invoice.setInvoiceDate(rs.getDate(13));
		}
		else
		{
			invoice.setInvoiceID(rs.getString("INVOICE_ID"));
			invoice.setInvoiceDate(rs.getDate("INVOICE_DATE"));
			invoice.setNetAmount(rs.getFloat("INVOICE_AMOUNT"));
			invoice.setPreviousBalance(rs.getFloat("BALANCE"));
		}
		
		return invoice;
	}

}
