package com.org.productplanner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.Customer;
import com.org.productplanner.repository.CustomerRepository;

@Service
public class CustomerService extends CommonService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
    public void save(Customer customer)
    {
    	customer.setObjid(getNEXTObjId("CUSTOMER_TBL"));
    	customerRepository.save(customer);
    }

    public List<Customer> getCustomers()
    {
    	return customerRepository.getCustomers();
    }
    public void update(Customer customer)
    {
    	customerRepository.update(customer);
    }
    
    public void delete(String customerID)
    {
    	customerRepository.delete(customerID);
    }
    public void updateOpeningBalance(String customerID)
    {
        customerRepository.updateOpeningBalance(customerID);
    }
}
