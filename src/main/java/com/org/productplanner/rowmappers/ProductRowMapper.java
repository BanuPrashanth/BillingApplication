package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.Product;
import com.org.productplanner.service.CommonService;

public class ProductRowMapper extends CommonService implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rownum) throws SQLException {
		Product product=new Product();
		product.setObjid(rs.getInt(1));
		product.setProductID(rs.getString(2));
		product.setProductDescription(rs.getString(3));
		product.setRate(rs.getFloat(4));
		product.setStatus(replaceForGUI(rs.getString(5)));
		product.setGst(rs.getFloat(6));
		product.setProductType(rs.getString(7));
		product.setOtherProductCode(rs.getString(8));
		product.setEffectiveDate(rs.getDate(10));
		return product;
	}

}
