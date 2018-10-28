package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Notes;

public class SimpleNotesRowMapper implements RowMapper<Notes>{

	
	@Override
	public Notes mapRow(ResultSet rs, int rowNum) throws SQLException {
		Notes note=new Notes(
				rs.getInt("OBJID"),
				rs.getString("X_PRODUCT_ID"),
				rs.getString("PROD_DESC"),
				rs.getString("X_NOTATION"),
				rs.getInt("X_QUANTITY"),
				rs.getInt("X_RATE"),
				rs.getFloat("X_TOTAL"),
				rs.getInt("X_DISCOUNT"),
				rs.getFloat("X_NET_TOTAL"),
				rs.getString("OTHER_PRODUCT_CODE"),
				rs.getString("X_DELIVERY_NOTE_ID"));
		return note;
	}

}