package com.org.productplanner.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.productplanner.beans.PartyWise;
import com.org.productplanner.beans.Report;
import com.org.productplanner.beans.Stock;
import com.org.productplanner.service.ReportService;
import com.org.productplanner.views.PartyWiseReportView;
import com.org.productplanner.views.ReceiptReportView;
import com.org.productplanner.views.SalesReportView;
import com.org.productplanner.views.StockWiseReportView;

@Controller
@RequestMapping("/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/receipt",method = RequestMethod.POST)
	public ModelAndView exportReceiptReport(@RequestBody Report report,HttpServletResponse response)
	{
		return new ModelAndView(new ReceiptReportView(), reportService.exportReceiptReport(report, response));
	}
	
	@RequestMapping(value="/sales",method = RequestMethod.POST)
	public ModelAndView exportSales(@RequestBody Report report,HttpServletResponse response)
	{
		return new ModelAndView(new SalesReportView(), reportService.exportSalesReport(report, response));
	}
	
	@RequestMapping(value="/stocks/{simple}",method = RequestMethod.POST)
	public @ResponseBody List<Stock> getStockWiseReport(@RequestBody Report report,@PathVariable(value="simple") boolean simple)
	{
		return reportService.getStocks(report,simple);
	}
	
	@RequestMapping(value="/stockWise",method = RequestMethod.POST)
	public ModelAndView exportStockReport(@RequestBody Report report,HttpServletResponse response)
	{
		return new ModelAndView(new StockWiseReportView(), reportService.exportStockReport(report, response));
	}
	
	@RequestMapping(value="/partyWise/{simple}",method = RequestMethod.POST)
	public @ResponseBody List<PartyWise> getPartyWiseReport(@RequestBody Report report,@PathVariable(value="simple") boolean simple)
	{
		return reportService.getParties(report, simple);
	}
	
	@RequestMapping(value="/partyWise",method = RequestMethod.POST)
	public ModelAndView exportPartyWiseReport(@RequestBody Report report,HttpServletResponse response)
	{
		return new ModelAndView(new PartyWiseReportView(), reportService.exportPartyWiseReport(report, response));
	}
}
