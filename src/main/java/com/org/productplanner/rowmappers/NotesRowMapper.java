package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.org.productplanner.beans.Notes;

public class NotesRowMapper implements RowMapper<Notes>{

	
	@Override
	public Notes mapRow(ResultSet rs, int rowNum) throws SQLException {
		Notes note=new Notes(
				rs.getInt("OBJID"),
				rs.getString("PRODUCT_ID"),
				rs.getString("PROD_DESC"),
				rs.getString("NOTATION"),
				rs.getInt("QUANTITY"),
				rs.getInt("RATE"),
				rs.getFloat("TOTAL"),
				rs.getInt("DISCOUNT"),
				rs.getFloat("NET_TOTAL"),
				rs.getInt("TAX_1"),
				rs.getFloat("TAX_1_AMT"),
				rs.getInt("TAX_2"),
				rs.getFloat("TAX_2_AMT"),
				rs.getFloat("NET_AMOUNT"),
				rs.getString("OTHER_PRODUCT_CODE"),
				rs.getString("DELIVERY_NOTE_ID"));
		return note;
	}

}

