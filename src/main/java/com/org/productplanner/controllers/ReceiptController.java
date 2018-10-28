package com.org.productplanner.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.Receipt;
import com.org.productplanner.service.ReceiptService;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

	@Autowired
	private ReceiptService receiptService;
	
	@RequestMapping(value="/generate/Id/{receiptDate}/{simple}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> generateID(@PathVariable(value="receiptDate") Date receiptDate,@PathVariable(value="simple") boolean simple)
	{
		return receiptService.generateReceiptId(receiptDate, simple);
	}
	
	@RequestMapping(value="/save/{simple}",method = RequestMethod.POST)
	public @ResponseBody boolean save(@RequestBody Receipt receipt,@PathVariable(value="simple") boolean simple)
	{		
		return receiptService.saveReceipt(receipt,simple);
	}
	
	@RequestMapping(value="/{simple}",method = RequestMethod.POST)
	public @ResponseBody List<Receipt> getReceipts(@RequestBody Receipt receipt,@PathVariable(value="simple") boolean simple)
	{
		return receiptService.getReceipts(receipt,simple);
	}
	
	@RequestMapping(value="/download/{simple}",method = RequestMethod.POST)
	public @ResponseBody void download(@RequestBody Receipt receipt,HttpServletResponse response,@PathVariable(value="simple") boolean simple)
	{
		receiptService.downloadReceipt(receipt,response,simple);
	}
}
