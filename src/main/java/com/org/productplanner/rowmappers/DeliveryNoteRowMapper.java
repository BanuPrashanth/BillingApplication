package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.DeliveryNote;

public class DeliveryNoteRowMapper implements RowMapper<DeliveryNote>{

	@Override
	public DeliveryNote mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
			DeliveryNote deliveryNote=new DeliveryNote();
			deliveryNote.setObjid(rs.getInt(1));
			deliveryNote.setDeliveryNoteID(rs.getString(2));
			deliveryNote.setGrandTotal(rs.getFloat(3));
			deliveryNote.setInvoiceGenerated("Y".equalsIgnoreCase(rs.getString(4)));
			deliveryNote.setDeliveryDate(rs.getDate(5));
			deliveryNote.setStatus(rs.getString(6));
		return deliveryNote;
	}

}
