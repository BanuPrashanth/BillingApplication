package com.org.productplanner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.Product;
import com.org.productplanner.repository.ProductRepository;

@Service
public class ProductService extends CommonService{
	
	@Autowired
	private ProductRepository productRepository;
	
    public void addProduct(Product product)
    {
    	product.setObjid(getNEXTObjId("PRODUCT_TBL"));
    	product.setStatus(replaceForDB(product.getStatus()));
    	productRepository.addProduct(product);
    }
    
    public List<Product> getProducts()
    {
    	return productRepository.getProducts();
    }
    
	public void update(Product product)
    {
		product.setStatus(replaceForDB(product.getStatus()));
    	productRepository.updateProducts(product);
    	
    }
    public void deleteProduct(String productID)
    {
    	productRepository.deleteProducts(productID);
    }
    
}
