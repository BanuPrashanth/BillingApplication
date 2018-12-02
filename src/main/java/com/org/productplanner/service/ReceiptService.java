package com.org.productplanner.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Receipt;
import com.org.productplanner.rowmappers.ReceiptRowMapper;

import static com.org.productplanner.queries.Query.*;

@Service
public class ReceiptService extends CommonService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Map<String,String> generateReceiptId(Date receiptDate,boolean simple)
    {
		System.out.println("Invoice Date: "+receiptDate+" Simple: "+simple);
    	if(simple)
    	{
    		return generateID("S_REC_", "TABLE_X_RECEIPT", "receiptID", receiptDate);
    	}
    	else
    	{
    		return generateID("REC_", "TABLE_RECEIPT", "receiptID", receiptDate);
    	}
		
    }
	@Transactional
	public boolean saveReceipt(Receipt receipt,boolean simple)
	{
		System.out.println("Receipt : "+receipt.toString());
		String updateInvoice=simple?UPDATE_X_INVOICE:UPDATE_INVOICE;
		String saveReceipt=simple?SAVE_X_RECEIPT:SAVE_RECEIPT;
		String saveMTM=simple?SAVE_X_MTM:SAVE_MTM;
		receipt.setObjid(getNEXTObjId(simple?"TABLE_X_RECEIPT":"TABLE_RECEIPT"));
		int output= jdbcTemplate.update(saveReceipt, 
				receipt.getObjid(),
				receipt.getReceiptID(),
				receipt.getCustomerID(),
				receipt.getOpeningBalance(),
				receipt.getPreviousOpeningBalance(),
				receipt.getPreviousAdvanceBalance(),
				receipt.getTotalAmtPaid(),
				receipt.getClosingBalance(),
				receipt.getTransactionType(),
				receipt.getComment(),
				receipt.getReceiptDate(),
				receipt.getClosingAdvanceAmount()
				);
		
		for (Invoice invoice : receipt.getInvoices()) {
			String paymentStatus="N";
			if(invoice.getCurrentBalance()==0)
			{
				paymentStatus="Y";
			}
			jdbcTemplate.update(saveMTM, invoice.getInvoiceID(),receipt.getReceiptID());
			
			jdbcTemplate.update(updateInvoice,invoice.getCurrentBalance(),paymentStatus,invoice.getInvoiceID());
		}
		return output==1?true:false;
	}
	
	public List<Receipt> getReceipts(Receipt receipt,boolean simple)
	{
		String query=simple?GET_X_RECEIPT:GET_RECEIPT;
		return jdbcTemplate.query(query, 
				new Object[]{receipt.getFromDate(),receipt.getToDate()},
				new ReceiptRowMapper());
	}

	public void downloadReceipt(Receipt receipt,HttpServletResponse response,boolean simple) 
	{
		System.out.println("Downloading receipt for : "+receipt.toString());
		try {
			generatePDF(receipt, response, simple);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
