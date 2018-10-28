package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Stock;

public class StockRowMapper implements RowMapper<Stock>{

	@Override
	public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
		Stock stock=new Stock();
		stock.setDate(rs.getDate(1));
		stock.setCustomerName(rs.getString(2));
		stock.setQuantity(rs.getInt(3));
		stock.setAmount(rs.getFloat(4));
		return stock;
	}

}
