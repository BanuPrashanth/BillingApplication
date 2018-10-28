package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Receipt;

public class ReceiptRowMapper implements RowMapper<Receipt>{

	@Override
	public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
		Receipt receipt=new Receipt();
		receipt.setCustomerID(rs.getString(1));
		receipt.setReceiptID(rs.getString(2));
		receipt.setTotalAmtPaid(rs.getFloat(3));
		receipt.setReceiptDate(rs.getDate(4));
		return receipt;
	}

}
