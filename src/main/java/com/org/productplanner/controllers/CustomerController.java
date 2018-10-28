package com.org.productplanner.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.Customer;
import com.org.productplanner.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/{generateId}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> generateCustomerId()
	{
		return customerService.generateID("CUST_", "CUSTOMER_TBL", "customerID");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Customer> get()
	{
		return customerService.getCustomers();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void save(@RequestBody Customer customer)
	{
		customerService.save(customer);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody void update(@RequestBody Customer customer)
	{
		customerService.update(customer);
	}
	
	@RequestMapping(value="/{customerID}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable(value="customerID") String customerID)
	{
		customerService.delete(customerID);
	}
}
