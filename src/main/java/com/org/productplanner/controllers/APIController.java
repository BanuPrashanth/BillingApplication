package com.org.productplanner.controllers;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.service.CommonService;


@Controller
@RequestMapping("/api")
public class APIController {

	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String homepage()
	{
		System.out.println("Redirecting to User Home Page ...");
		return "index.html";
	}

	@RequestMapping(value="/get/stateGSTType/{customerID}",method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getStateGSTType(@PathVariable(value="customerID") String customerID)
	{
		return commonService.getStateGSTType(customerID);
	}	
	
	@RequestMapping(value="/states",method = RequestMethod.GET)
	public @ResponseBody List<String> getStates()
	{
		return commonService.getStates();
		
	}
}
