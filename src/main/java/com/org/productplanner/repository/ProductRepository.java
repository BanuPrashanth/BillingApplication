package com.org.productplanner.repository;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static com.org.productplanner.queries.Query.*;
import com.org.productplanner.beans.Product;
import com.org.productplanner.rowmappers.ProductRowMapper;

@Repository
@Transactional
public class ProductRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void addProduct(Product product)
    {
    	jdbcTemplate.update(ADD_PRODUCT, new Object[]
        			{
        					product.getObjid(),
            				product.getProductID(),
            				product.getProductDescription(),
            				product.getRate(),
            				product.getStatus(),
            				product.getGst(),
            				product.getProductType(),
            				product.getOtherProductCode(),
            				product.getOtherProductDetails(),
            				product.getEffectiveDate()
            		});
    }
	
	public List<Product> getProducts()
    {
    	return jdbcTemplate.query(GET_PRODUCTS,new ProductRowMapper());
    }
	
	public void updateProducts(Product product)
    {
		jdbcTemplate.update(UPDATE_PRODUCT, 
				product.getProductDescription(),
				product.getRate(),
				product.getStatus(),
				product.getGst(),
				product.getProductType(),
				product.getOtherProductCode(),
				new Date(),
				product.getProductID());
    }
    
    public void deleteProducts(String productID)
    {
    	jdbcTemplate.update(DELETE_PRODUCT,productID);
    }
    
    
    
}
