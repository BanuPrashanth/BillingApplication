package com.org.productplanner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.org.productplanner.beans.Customer;
import com.org.productplanner.rowmappers.CustomerRowMapper;

import static com.org.productplanner.queries.Query.*;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void save(Customer customer)
    {
    	jdbcTemplate.update(ADD_CUSTOMER, new Object[]
        			{
        					customer.getObjid(),
        					customer.getCustomerID(),
        					customer.getCustomerName(),
        					customer.getAddress(),
        					customer.getPhone(),
        					customer.getGst(),
        					customer.getPanNumber(),
							customer.getCustomerSPOC(),
							customer.getOpeningBalance(),
        					customer.getState(),
        					customer.getStartDate()
            			});
    }
    
    public void delete(String customerID)
	{
    	jdbcTemplate.update(DELETE_CUSTOMERS,customerID);
	}
	
	public void update(Customer customer)
	{
    		jdbcTemplate.update(UPDATE_CUSTOMER, 
    				customer.getCustomerName(),
    				customer.getAddress(),
    				customer.getPhone(),
    				customer.getGst(),
    				customer.getPanNumber(),
					customer.getCustomerSPOC(),
					customer.getOpeningBalance(),
    				customer.getState(),
    				new Date(),
    				customer.getCustomerID());
	}

	public List<Customer> getCustomers()
	{
		return jdbcTemplate.query(GET_CUSTOMER, new CustomerRowMapper());
	}
	
}
