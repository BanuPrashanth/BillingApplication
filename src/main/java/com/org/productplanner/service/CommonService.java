package com.org.productplanner.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.org.productplanner.beans.Company;
import com.org.productplanner.beans.DeliveryNote;
import com.org.productplanner.beans.Invoice;
import com.org.productplanner.beans.Receipt;

import static com.org.productplanner.queries.Query.*;
import com.org.productplanner.rowmappers.CompanyRowMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class CommonService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @param loginDetails
	 * @return
	 */

    public List<String> getStates()
    {
    	return jdbcTemplate.queryForList(GET_STATES, String.class);
    }
    
    public Map<String,String> getStateGSTType(String customerID)
    {
    	Map<String,String> stateGSTType=new HashMap<String,String>();
    	String customerState=jdbcTemplate.queryForObject(GET_CUSTOMER_STATE, String.class,new Object[]{customerID});
    	if(isSGST(customerState))
    	{
    		stateGSTType.put("stateGSTType", "SGST");
    	}
    	else
    	{
    		stateGSTType.put("stateGSTType", "IGST");
    	}
    	System.out.println("GST Type: "+stateGSTType.get("stateGSTType"));
    	return stateGSTType;
    	
    }
    
    public Map<String,String> generateID(Object...params)
    {
    	String id="";
    	String prefix=(String)params[0];
    	String tableName=(String)params[1];
    	String key=(String)params[2];
    	Date date=params.length>3?date=(Date)params[3]:null;
    	
    	Map<String,String> mapID=new HashMap<String,String>();
    	if(date!=null)
    	{
    		id=prefix+getNEXTObjId(tableName)+"_"+getFormattedDate(date);
    	}
    	else
    	{
    		id=prefix+getNEXTObjId(tableName);
    	}
    	mapID.put(key, id);
    	return mapID;
    }
    
    /**
     * This method generates unique object ID for the corresponding table passed as parameter.
     * @select
     *   query= SELECT IFNULL(MAX(OBJID),0)+1 FROM Table_Name 
     * @param tableName
     * @return next unique object ID
     */
    public int getNEXTObjId(String tableName)
    {
    	return jdbcTemplate.queryForObject(GET_NEXT_OBJID+tableName, Integer.class);
    }
    
    public String getFormattedDate(Date date)
    {
    	SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyy");
		return formatDate.format(date);
    }
    public static String getFormattedDate(Date date,String pattern)
    {
    	return  new SimpleDateFormat(pattern).format(date);
    }
    public void generatePDF(Object object,HttpServletResponse response,boolean simple) throws JRException, IOException, SQLException
    {
    	InputStream jasperStream=null;
    	Map<String,Object> prepareMap=new HashMap<String,Object>();
    	if(object instanceof Invoice)
    	{
    		Invoice invoice=(Invoice)object;
    		prepareMap.put("INVOICE_ID", invoice.getInvoiceID());
    		String netAmountInWords=NumberToWord.convert((int) invoice.getNetAmount());
    		prepareMap.put("NET_AMOUNT_IN_WORDS", "Rs. "+netAmountInWords);
    		if(simple)
    		{
    			//BOOT-INF/classes/
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/SimpleInvoice.jrxml");
    		}
    		else
    		{
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/Invoice.jrxml");
    		}
    		
    	}
    	else if(object instanceof DeliveryNote)
    	{
    		DeliveryNote deliveryNote=(DeliveryNote)object;
    		prepareMap.put("DELIVERY_NOTE_ID", deliveryNote.getDeliveryNoteID());
    		String netAmountInWords=NumberToWord.convert((int) deliveryNote.getGrandTotal());
    		prepareMap.put("NET_AMOUNT_IN_WORDS", "Rs. "+netAmountInWords);
    		if(simple)
    		{
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/SimpleOrderNote.jrxml");
    		}
    		else
    		{
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/OrderNote.jrxml");
    		}
    		
    	}
    	else if(object instanceof Receipt)
    	{
    		Receipt receipt=(Receipt) object;
    		String dueAmount=receipt.getTotalAmtPaid()+"/-  ";
    		dueAmount+=NumberToWord.convert((int) receipt.getTotalAmtPaid());
    		System.out.println("Receipt ID for simple receipt "+receipt.getReceiptID()+" Simple "+simple);
    		prepareMap.put("RECEIPT_ID", receipt.getReceiptID());
    		prepareMap.put("DUE_AMOUNT", dueAmount+" Only");
    		if(simple)
    		{
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/SimpleReceipt.jrxml");
    		}
    		else
    		{
    			jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/Receipt.jrxml");
    		}
    	}
    		
    	//InputStream jasperStream =ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/templates/Invoice.jrxml");
		System.out.println("Loaded xml");		
		JasperDesign jasperDesign=JRXmlLoader.load(jasperStream);
		System.out.println("Loaded JRxml");
		JasperReport jasperReport=JasperCompileManager.compileReport(jasperDesign);
		System.out.println("compiled ...");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, prepareMap, jdbcTemplate.getDataSource().getConnection());
		System.out.println("Filled report");
		response.setContentType("application/x-pdf");
		response.setHeader("Content-Disposition", "inline; filename=demo.pdf");
		System.out.println(jasperPrint.getName());
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		System.out.println("sending file");
    }
    
    public boolean isIGST(String invoiceId)
    {
    	String customerID=jdbcTemplate.queryForObject(GET_CUSTOMER_ID_FROM_INVOICE_ID, String.class,new Object[]{invoiceId});
    	String customerState=jdbcTemplate.queryForObject(GET_CUSTOMER_STATE, String.class,new Object[]{customerID});
    	String companyState=getCompany().getState();
    	if(customerState!=null && customerState.equalsIgnoreCase(companyState))
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public boolean isSGST(String customerState)
    {
    	return getCompany().getState().equalsIgnoreCase(customerState)?true:false;
    }
    public Company getCompany()
    {
    	return jdbcTemplate.queryForObject(GET_COMPANY, new CompanyRowMapper());
    }
    public String formatString(List<String> listOfString)
    {
    	StringBuilder formatedString=new StringBuilder();
    	if(listOfString!=null && !listOfString.isEmpty())
    	{
    		for (int i=0;i<listOfString.size();i++) {
				if(i==listOfString.size()-1)
				{
					formatedString.append("'"+listOfString.get(i)+"'");
				}
				else
				{
					formatedString.append("'"+listOfString.get(i)+"',");
				}
			}
    	}
    	return formatedString.toString();
    }
    
    public static CellStyle getDateStyle(HSSFWorkbook workbook)
	{
		CellStyle cellStyle = workbook.createCellStyle();
	    CreationHelper createHelper = workbook.getCreationHelper();
	    short dateFormat = createHelper.createDataFormat().getFormat("dd-MM-yyyy");
	    cellStyle.setDataFormat(dateFormat);
	    return cellStyle;
	}
    /**
     * This method converts the status from GUI to DB compatible
     * @param status compatible to GUI
     * @return status compatible to DB
     */
    public String replaceForDB(String status)
    {
		return "ACTIVE".equalsIgnoreCase(status)?"A":"I";
    }
    
    public String replaceForGUI(String status)
	{
		return "A".equalsIgnoreCase(status)?"Active":"Inactive";
	}
}
