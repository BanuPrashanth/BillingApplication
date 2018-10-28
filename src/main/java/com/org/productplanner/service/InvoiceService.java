package com.org.productplanner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.StringTokenizer;
import com.org.productplanner.beans.DeliveryNote;
import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Notes;
import com.org.productplanner.beans.Variant;

import static com.org.productplanner.queries.Query.*;
import com.org.productplanner.rowmappers.InvoiceRowMapper;
import com.org.productplanner.rowmappers.NotesRowMapper;
import com.org.productplanner.rowmappers.SimpleInvoiceRowMapper;
import com.org.productplanner.rowmappers.SimpleNotesRowMapper;

@Service
public class InvoiceService extends CommonService{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Map<String,String> generateInvoiceId(Date invoiceDate,boolean simple)
    {
    	if(simple)
    	{
    		return generateID(getFinancialYear(invoiceDate), "INVOICE_X_TBL", "invoiceID", null);
    	}
    	else
    	{
    		return generateID(getFinancialYear(invoiceDate), "INVOICE_TBL", "invoiceID", null);
    	}
		
    }

	private String getFinancialYear(Date invoiceDate)
	{
		String financialYear="";
		String currentYear=getFormattedDate(new Date(), "yy");
		
		String inputDate=getFormattedDate(invoiceDate, "MM-yy");
		StringTokenizer stInput=new StringTokenizer(inputDate, "-");
		String inputMonth=stInput.nextToken();
		String inputYear=stInput.nextToken();
		int currYear=Integer.parseInt(currentYear);
		if(Integer.parseInt(inputMonth)>4 && currentYear.equalsIgnoreCase(inputYear))
		{
			financialYear="20"+currYear+"-"+ ++currYear+"_";
		}
		else
		{
			int pre=currYear-1;
			financialYear="20"+ pre+"-"+ currYear+"_";
		}
		return financialYear;
	}
	@Transactional
	public boolean saveInvoice(Invoice invoice,boolean simple)
    {
    	int output=0;
    	
    	if(simple)
    	{
    		invoice.setObjid(getNEXTObjId("INVOICE_X_TBL"));
    		output=jdbcTemplate.update(SAVE_X_INVOICE, 
    				invoice.getObjid(),
        			invoice.getInvoiceID(),
        			invoice.getCustomerID(),
        			invoice.getGrossAmount(),
        			invoice.getGrossAmount(),
        			invoice.getInvoiceDate()
    				);
        	if(output==1)
        	{
        		System.out.println("Invoice Saved");
        		
        		for(Notes note:invoice.getNotes())
            	{
            		jdbcTemplate.update(UPDATE_X_DELIVERY_NOTE, invoice.getInvoiceID(),note.getDeliveryNoteID());
            	}
        	}
    	}
    	else
    	{
    		invoice.setObjid(getNEXTObjId("INVOICE_TBL"));
    		output=jdbcTemplate.update(SAVE_INVOICE,
    				invoice.getObjid(),
        			invoice.getInvoiceID(),
        			invoice.getCustomerID(),
        			invoice.getGrossAmount(),
        			invoice.getCgstAmount(),
        			invoice.getSgstAmount(),
        			invoice.getGstAmount(),
        			invoice.getNetAmount(),
        			invoice.getNetAmount(),
        			invoice.getInvoiceDate()
    				);
        	if(output==1)
        	{
        		System.out.println("Invoice Saved");
        		
        		for(Notes note:invoice.getNotes())
            	{
            		jdbcTemplate.update(UPDATE_DELIVERY_NOTE, invoice.getInvoiceID(),note.getDeliveryNoteID());
            	}
        	}
    	}
    	
    	
	
	return output==1?true:false;
    }
    
    public boolean downloadInoice(Invoice invoice,HttpServletResponse response,boolean simple)
    {
    	try {
    		System.out.println("Invoice: "+invoice.toString());
    		generatePDF(invoice, response,simple);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    public List<Notes> generateInvoice(Invoice invoice,boolean simple)
    {
    	List<Notes> allNotes=new ArrayList<Notes>();
    	List<DeliveryNote> deliveryNotes=null;
    	
    	if(simple)
    	{
    		deliveryNotes=jdbcTemplate.query(
    				GET_X_DELIVERY_NOTE,
    				new Object[]{
    						invoice.getCustomerID(),
    						invoice.getFromDate(),
    						invoice.getToDate()
    				},
    				(rs, rowNum) -> new DeliveryNote(
    						rs.getString("X_DELIVERY_NOTE_ID"),
    						rs.getDate("X_DELIVERY_DATE"))
    				);    		
    	}
    	else
    	{
    		deliveryNotes=jdbcTemplate.query(
    				GET_DELIVERY_NOTE,
    				new Object[]{
    						invoice.getCustomerID(),
    						invoice.getFromDate(),
    						invoice.getToDate()
    				},
    				(rs, rowNum) -> new DeliveryNote(
    						rs.getString("DELIVERY_NOTE_ID"),
    						rs.getDate("DELIVERY_DATE"))
    				);
    	}
    	for(DeliveryNote deliveryNote: deliveryNotes)
		{
			allNotes.addAll(getNotes(deliveryNote.getDeliveryNoteID(),simple));
		}
    	System.out.println("allNotes: "+allNotes.toString());
    	return allNotes;
    }
    public List<Notes> getNotesForGeneratedInvoice(String invoiceId,boolean simple)
    {
    	System.out.println("*******InvoiceId: "+invoiceId);
    	List<Notes> allNotes=new ArrayList<Notes>();
    	List<DeliveryNote> deliveryNotes=getDeliveryNoteForGeneratedInvoice(invoiceId, simple);
    	for(DeliveryNote deliveryNote: deliveryNotes)
		{
			
			allNotes.addAll(getNotes(deliveryNote.getDeliveryNoteID(),simple));
		}
    	return allNotes;
    }
    public List<DeliveryNote> getDeliveryNoteForGeneratedInvoice(String invoiceId,boolean simple)
    {
    	List<DeliveryNote> deliveryNoteId=null;
    	if(simple)
    	{
    		deliveryNoteId=jdbcTemplate.query(GET_X_DN_FOR_INV_GEN, new Object[]{invoiceId}, (rs, rowNum) -> new DeliveryNote(
					rs.getString("X_DELIVERY_NOTE_ID"),
					rs.getDate("X_DELIVERY_DATE")));
    	}
    	else
    	{
    		deliveryNoteId=jdbcTemplate.query(GET_DN_FOR_INV_GEN, new Object[]{invoiceId}, (rs, rowNum) -> new DeliveryNote(
					rs.getString("DELIVERY_NOTE_ID"),
					rs.getDate("DELIVERY_DATE")));
    	}
    	System.out.println("**************Delivery Note Id: "+deliveryNoteId);
    	return deliveryNoteId;
    }
    
    public List<Notes> getNotes(String deliveryNoteId,boolean simple)
    {
    	List<Notes> notes= null;
    	if(simple)
    	{
    		notes=jdbcTemplate.query(GET_X_NOTES_FROM_ID, 
    				new Object[]{deliveryNoteId},
    				new SimpleNotesRowMapper());
    	}
    	else
    	{
    		notes=jdbcTemplate.query(
					GET_NOTES_FROM_ID, 
					new Object[]{deliveryNoteId},
					new NotesRowMapper()
					);
    	}
    	System.out.println(notes.toString());
    	return notes;
    }
    
    public List<Invoice> getInvoices(String customerID,boolean simple)
	{
		List<Invoice> invoices=null;
		RowMapper<Invoice> rowMapper=simple?new SimpleInvoiceRowMapper(false):new InvoiceRowMapper(false);
		if(simple)
		{
			System.out.println("fetching invoice for "+customerID+" without tax.");
			invoices=jdbcTemplate.query(
    				GET_X_INVOICE,
    				new Object[]{customerID},
    				rowMapper
    				);
		}
		else
		{
			System.out.println("fetching invoice for "+customerID+" with tax.");
			invoices=jdbcTemplate.query(
    				GET_INVOICE,
    				new Object[]{customerID},
    				rowMapper
    				);
		}
		
		return invoices;
	}
    public float getAdvance(String customerID,boolean simple)
    {
    	float advance=0;
    	try{
    		String query=simple?GET_LATEST_X_ADVANCE:GET_LATEST_ADVANCE;
        	Object obj= jdbcTemplate.queryForObject(query, Object.class, customerID,customerID);
        	advance=Float.parseFloat(obj+"");
    	}catch(EmptyResultDataAccessException e)
    	{
    		System.out.println("Customer "+customerID+" not found!");
    	}
    	return advance;
    }
    public Map<String,Object> getInvoiceReport(Invoice invoice,boolean simple)
    {
    	List<Invoice> invoices=null;
    	List<String> listOfInvoiceId=new ArrayList<String>();
    	Map<String,Object> response=new HashMap<String,Object>();
    	RowMapper<Invoice> rowMapper=simple?new SimpleInvoiceRowMapper(true):new InvoiceRowMapper(true);
		if(simple)
		{
			invoices=jdbcTemplate.query(
    				GET_X_INVOICE_REPORT,
    				new Object[]{invoice.getFromDate(),invoice.getToDate()},
    				rowMapper
    				);
		}
		else
		{
			invoices=jdbcTemplate.query(
    				GET_INVOICE_REPORT,
    				new Object[]{invoice.getFromDate(),invoice.getToDate()},
    				rowMapper
    				);
			
		}
		for (Invoice inv : invoices) {
			
			if(isIGST(inv.getInvoiceID()))
			{
				inv.setIgstAmount(inv.getCgstAmount()+inv.getSgstAmount());
				inv.setCgstAmount(0);
				inv.setSgstAmount(0);
			}
			listOfInvoiceId.add(inv.getInvoiceID());
			inv.setDiscountAmount(getDiscount(inv.getInvoiceID(),simple));
			inv.setTaxableAmount(inv.getGrossAmount()-inv.getDiscountAmount());
			if(!simple)
			{
				List<Float> listOfVariantInstances=getVariantInstances(inv.getInvoiceID());
				inv.setVariants(listOfVariantInstances);
			}
			System.out.println("Invoice : "+inv.toString());
		}
		response.put("invoices", invoices);
		if(!simple)
		{
			response.put("variants", getVariants(listOfInvoiceId));
		}
		response.put("listofVariantInstancesSum", getVariantSummation(listOfInvoiceId));
		return response;
    }
    
    public float getDiscount(String invoiceId,boolean simple)
    {
    	String query=simple?GET_X_INVOICE_DISCOUNT:GET_INVOICE_DISCOUNT;
    	String discount=jdbcTemplate.queryForObject(query, new Object[]{invoiceId}, String.class);
    	System.out.println("Discount"+discount);
    	float _discount=discount!=null?Float.parseFloat(discount):0;
    	System.out.println("_discount after parse");
    	return _discount;
    }
    
    public List<String> getVariants(List<String> listOfInvoiceId)
    {
    	return jdbcTemplate.queryForList(GET_VARIANTS.replace("#", formatString(listOfInvoiceId)), String.class);
    }
    
    public List<Float> getVariantInstances(String invoiceId)
    {
    	List<Float> listofVariantInstances=jdbcTemplate.queryForList(GET_VARIANT_INSTANCES, new Object[]{invoiceId}, Float.class);
    	System.out.println("listofVariantInstances ==> "+listofVariantInstances.toString());
    	return listofVariantInstances;
    }
    
    public List<Variant> getVariantSummation(List<String> listOfInvoiceId)
    {

    	Variant variant=null;
    	List<Variant> listOfVariants=new ArrayList<Variant>();
    	List<Map<String,Object>> result=jdbcTemplate.queryForList(GET_SUMMATION_OF_VARIANTS.replace("#", formatString(listOfInvoiceId)));
    	for (Map<String, Object> map : result) {
			variant=new Variant();
			float gst=Float.parseFloat(""+map.get("GST"));
			variant.setName(""+map.get("GST"));
			variant.setCgst(gst/(float) 2);
			variant.setCgstAmount(Float.parseFloat(""+map.get("CGST")));
			variant.setSgst(gst/(float) 2);
			variant.setSgstAmount(Float.parseFloat(""+map.get("SGST")));
			variant.setIgst(gst);
			variant.setIgstAmount(Float.parseFloat(""+map.get("IGST")));
			listOfVariants.add(variant);
		}
    	System.out.println("listOfVariants ==>==> "+listOfVariants.toString());
    	
    	return listOfVariants;
    }
    
    
}
