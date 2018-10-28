package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.PartyWise;

public class PartyWiseRowMapper implements RowMapper<PartyWise>{

	@Override
	public PartyWise mapRow(ResultSet rs, int rowNum) throws SQLException {
		PartyWise party=new PartyWise();
		party.setReferenceNumber(rs.getString(1));
		party.setTransactionDate(rs.getDate(2));
		party.setTransactionAmount(rs.getFloat(3));
		party.setTransactionBalance(rs.getFloat(4));
		party.setTransactionType(rs.getString(5));
		party.setInvoiceAmount(rs.getFloat(6));
		return party;
	}

}
