package com.org.productplanner.controllers;

import java.util.Date;
import java.util.HashMap;
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

import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Notes;
import com.org.productplanner.service.InvoiceService;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
	@RequestMapping(value="/generate/Id/{invoiceDate}/{simple}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> invoice(@PathVariable(value="invoiceDate") Date invoiceDate,@PathVariable(value="simple") boolean simple)
	{
		return invoiceService.generateInvoiceId(invoiceDate,simple);
	}
	
	@RequestMapping(value="/{customerId}/{simple}",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getInvoice(@PathVariable(value="customerId") String customerId,@PathVariable(value="simple") boolean simple)
	{
		Map<String,Object> receipt=new HashMap<String,Object>();
		receipt.put("invoices", invoiceService.getInvoices(customerId, simple));
		receipt.put("openingAdvanceAmount", invoiceService.getAdvance(customerId, simple));
		return receipt;
	}
	@RequestMapping(value="/report/{simple}",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getInvoiceReport(@RequestBody Invoice invoice,@PathVariable(value="simple") boolean simple)
	{		
		return invoiceService.getInvoiceReport(invoice, simple);
	}
	
	@RequestMapping(value="/generate/{simple}",method = RequestMethod.POST)
	public @ResponseBody List<Notes> invoice(@RequestBody Invoice invoice,@PathVariable(value="simple") boolean simple)
	{
		return invoiceService.generateInvoice(invoice,simple);
	}
	
	@RequestMapping(value="/generate/{invoiceId}/{simple}",method = RequestMethod.GET)
	public @ResponseBody List<Notes> getNotesForGeneratedInvoice(@PathVariable(value="invoiceId") String invoiceId,@PathVariable(value="simple") boolean simple)
	{
		return invoiceService.getNotesForGeneratedInvoice(invoiceId, simple);
	}
	
	@RequestMapping(value="/{simple}",method = RequestMethod.POST)
	public @ResponseBody boolean invoiceSave(@RequestBody Invoice invoice,@PathVariable(value="simple") boolean simple)
	{
		return invoiceService.saveInvoice(invoice,simple);
	}
	
	@RequestMapping(value="/download/{simple}",method = RequestMethod.POST)
	public @ResponseBody boolean invoiceDownload(@RequestBody Invoice invoice,HttpServletResponse response,@PathVariable(value="simple") boolean simple)
	{
		return invoiceService.downloadInoice(invoice, response,simple);
	}
}
