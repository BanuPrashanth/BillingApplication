package com.org.productplanner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.PartyWise;
import com.org.productplanner.beans.Receipt;
import com.org.productplanner.beans.Report;
import com.org.productplanner.beans.Stock;
import com.org.productplanner.beans.Variant;
import com.org.productplanner.repository.ReportRepository;
import static com.org.productplanner.queries.Query.*;


@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ReceiptService receiptService;
	
	public List<Stock> getStocks(Report report,boolean simple)
	{
		reportRepository.setQuery(simple?GET_X_STOCKS:GET_STOCKS);
		return reportRepository.getStocks(report);
	}
	
	public List<PartyWise> getParties(Report report, boolean simple)
	{
		List<PartyWise> allPartyWiseReport=new ArrayList<PartyWise>();
		if("SALES".equalsIgnoreCase(report.getTransactionType()))
		{
			reportRepository.setQuery(simple?GET_X_SALES:GET_SALES);
		}
		else if("RECEIPT".equalsIgnoreCase(report.getTransactionType()))
		{
			reportRepository.setQuery(simple?GET_X_REC:GET_REC);
		}
		else
		{
			reportRepository.setQuery(simple?GET_X_SALES:GET_SALES);
			allPartyWiseReport.addAll(reportRepository.getParties(report));
			reportRepository.setQuery(simple?GET_X_REC:GET_REC);
			allPartyWiseReport.addAll(reportRepository.getParties(report));
			return allPartyWiseReport;
		}
		return reportRepository.getParties(report);
	}
	public Map<String, ?> exportReceiptReport(Report report,HttpServletResponse response)
	{
		System.out.println("Generating monthly reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyName", receiptService.getCompany().getName());
		model.put("fromDate", report.getFromDate());
		model.put("toDate", report.getToDate());
        //Sheet Name
        model.put("sheetname", "Receipt Report");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("RECEIPT DATE");
        headers.add("RECEIPT NO");
        headers.add("PARTY NAME");
        headers.add("AMOUNT RECIVED");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        int sno=0;
        float totalAmtReceived=0;
        List<Object> row=null;
        List<List<Object>> results = new ArrayList<List<Object>>();
        List<Receipt> listofReceipt=report.getReceipts();
        for (Receipt receipt : listofReceipt) {
        	row = new ArrayList<Object>();
        	row.add(++sno);
        	System.out.println("Receipt date: "+receipt.getReceiptDate());
        	row.add(receipt.getReceiptDate());
        	row.add(receipt.getReceiptID());
        	row.add(receipt.getCustomerID());
        	totalAmtReceived +=receipt.getTotalAmtPaid();
        	row.add(receipt.getTotalAmtPaid());
        	results.add(row);
		}
        model.put("results",results);
        model.put("totalAmtReceived", totalAmtReceived);
        String currentDate=CommonService.getFormattedDate(new Date(), "dd-MM-yyy_hh-mm-ss");
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename="+"ReceiptReport_"+currentDate+".xls" );
        return model;
	}
	public Map<String, ?> exportSalesReport(Report report,HttpServletResponse response)
	{
		System.out.println("Generating monthly sales reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyName", invoiceService.getCompany().getName());
		model.put("fromDate", report.getFromDate());
		model.put("toDate", report.getToDate());
        //Sheet Name
        model.put("sheetname", "Sales Report");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("BILL DATE");
        headers.add("INVOICE NO");
        headers.add("CUSTOMER NAME");
        headers.add("CUSTOMER GSTIN");
        headers.add("GROSS AMOUNT");
        headers.add("DISCOUNT AMOUNT");
        headers.add("TAXABLE AMOUNT");
        headers.add("CGST AMOUNT");
        headers.add("SGST ANOUNT");
        headers.add("IGST AMOUNT");
        headers.add("NET AMOUNT");
        
        
        //Header end
        
        //Rows
        List<Object> row=null;
        List<List<Object>> results = new ArrayList<List<Object>>();
        List<Invoice> invoices=report.getInvoices();
        List<String> listOfInvoiceIds=new ArrayList<String>();
        System.out.println("Invoice In Report "+invoices!=null?invoices.toString():"Invoice is null");
        for (Invoice invoice : invoices) {
        	listOfInvoiceIds.add(invoice.getInvoiceID());
        	row = new ArrayList<Object>();
        	row.add(invoice.getSno());
        	System.out.println("Invoice Date: "+invoice.getInvoiceDate());
        	row.add(invoice.getInvoiceDate());
        	row.add(invoice.getInvoiceID());
        	row.add(invoice.getCustomerID());
        	row.add(invoice.getCustomerGSTIN());
        	row.add(invoice.getGrossAmount());
        	row.add(invoice.getDiscountAmount());
        	row.add(invoice.getTaxableAmount());
        	row.add(invoice.getCgstAmount());
        	row.add(invoice.getSgstAmount());
        	row.add(invoice.getIgstAmount());
        	row.add(invoice.getNetAmount());
        	for(float variant: invoice.getVariants())
        	{
        		row.add(variant);
        	}
        	results.add(row);
        	System.out.println("Rows ==> "+row.toString());
		}
        model.put("results", results);
        List<String> variantHeader=invoiceService.getVariants(listOfInvoiceIds);
        for (String variant : variantHeader) {
			headers.add("V - "+variant);
		}
        model.put("headers", headers);
        //End Of The Report
        //Header
      //Headers List
        List<String> headerSum = new ArrayList<String>();
        headerSum.add("VARIANT");
        headerSum.add("CGST");
        headerSum.add("CGST AMOUNT");
        headerSum.add("SGST");
        headerSum.add("SGST AMOUNT");
        headerSum.add("IGST");
        headerSum.add("IGST AMOUNT");
        model.put("headerSum", headerSum);
        //Content
        List<Object> rowSum=null;
        List<List<Object>> resultsSum = new ArrayList<List<Object>>();
        List<Variant> listofVariantInstancesSum=report.getListofVariantInstancesSum();
        for (Variant variantSum : listofVariantInstancesSum) {
        	rowSum=new ArrayList<Object>();
        	rowSum.add("IF GST IS "+variantSum.getName()+"% IN PRODUCT");
        	rowSum.add(variantSum.getCgst());
        	rowSum.add(variantSum.getCgstAmount());
        	rowSum.add(variantSum.getSgst());
        	rowSum.add(variantSum.getSgstAmount());
        	rowSum.add(variantSum.getIgst());
        	rowSum.add(variantSum.getIgstAmount());
        	resultsSum.add(rowSum);
		}
        model.put("resultsSum", resultsSum);
        String currentDate=CommonService.getFormattedDate(new Date(), "dd-MM-yyy_hh-mm-ss");
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename="+"SalesReport_"+currentDate+".xls");
        return model;
	}

	public Map<String, ?> exportStockReport(Report report, HttpServletResponse response) {

		System.out.println("Generating monthly reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyName", receiptService.getCompany().getName());
		model.put("fromDate", report.getFromDate());
		model.put("toDate", report.getToDate());
        //Sheet Name
        model.put("sheetname", "Stock Wise Report");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("DATE");
        headers.add("CUSTOMER NAME");
        headers.add("QUANTITY");
        headers.add("AMOUNT");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        int sno=0;
        List<Object> row=null;
        List<List<Object>> results = new ArrayList<List<Object>>();
        List<Stock> listofStocks=report.getStocks();
        for (Stock stock : listofStocks) {
        	row = new ArrayList<Object>();
        	row.add(++sno);
        	row.add(stock.getDate());
        	row.add(stock.getCustomerName());
        	row.add(stock.getQuantity());
        	row.add(stock.getAmount());
        	results.add(row);
		}
        model.put("results",results);
        model.put("totalQuantity", report.getTotalQuantity());
        model.put("totalAmount", report.getTotalAmount());
        String currentDate=CommonService.getFormattedDate(new Date(), "dd-MM-yyy_hh-mm-ss");
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename="+"StockWiseReport_"+currentDate+".xls" );
        return model;
	}

	public Map<String, ?> exportPartyWiseReport(Report report, HttpServletResponse response) {

		System.out.println("Generating monthly reports ...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyName", receiptService.getCompany().getName());
		model.put("fromDate", report.getFromDate());
		model.put("toDate", report.getToDate());
        //Sheet Name
        model.put("sheetname", "Party Wise Report");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("S.NO");
        headers.add("TRANSACTION TYPE");
        headers.add("TRANSACTION DATE");
        headers.add("REFERENCE NUMBER");
        headers.add("TRANSACTION AMOUNT");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        int sno=0;
        List<Object> row=null;
        List<List<Object>> results = new ArrayList<List<Object>>();
        List<PartyWise> listofPartyWise=report.getParties();
        for (PartyWise partyWise : listofPartyWise) {
        	row = new ArrayList<Object>();
        	row.add(++sno);
        	row.add(partyWise.getTransactionType());
        	row.add(partyWise.getTransactionDate());
        	row.add(partyWise.getReferenceNumber());
        	row.add(partyWise.getTransactionAmount());
        	results.add(row);
		}
        model.put("results",results);
        model.put("closingBalance", report.getClosingBalance());
        String currentDate=CommonService.getFormattedDate(new Date(), "dd-MM-yyy_hh-mm-ss");
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename="+"PartyWiseReport_"+currentDate+".xls" );
        return model;
	}
}
