package com.org.productplanner.views;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.org.productplanner.service.CommonService;

@SuppressWarnings("deprecation")
public class SalesReportView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook,
            HttpServletRequest request,
            HttpServletResponse response)
    {
		//VARIABLES REQUIRED IN MODEL
		String companyName=(String)model.get("companyName");
        String sheetName = (String)model.get("sheetname");
        Date fromDate=(Date)model.get("fromDate");
        Date toDate=(Date)model.get("toDate");
        List<String> headers = (List<String>)model.get("headers");
        List<String> headerSum = (List<String>)model.get("headerSum");
        List<List<Object>> results = (List<List<Object>>)model.get("results");
        List<List<Object>> resultSum = (List<List<Object>>)model.get("resultsSum");
        List<String> numericColumns = new ArrayList<String>();
        if (model.containsKey("numericcolumns"))
            numericColumns = (List<String>)model.get("numericcolumns");
        //BUILD DOC
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 15);
        int currentRow = 0;
        short currentColumn = 0;
        CellStyle dateStyle=CommonService.getDateStyle(workbook);
        //CREATE STYLE FOR HEADER
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont); 
        //POPULATE HEADER COLUMNS
        /*
         * Setting Compnay Details
         */
        HSSFRow company = sheet.createRow(++currentRow);
        HSSFCell companyNameCell=company.createCell(0);
        companyNameCell.setCellValue("Company Name");
        companyNameCell.setCellStyle(headerStyle);
        
        HSSFCell companyValue=company.createCell(1);
        companyValue.setCellValue(companyName);
        sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 1, 3));
        /*
         * Setting Report Title
         */
        HSSFRow title = sheet.createRow(++currentRow);
        HSSFCell titleName=title.createCell(0);
        titleName.setCellValue("Report Title");
        titleName.setCellStyle(headerStyle);
        
        HSSFCell titleValue=title.createCell(1);
        titleValue.setCellValue(sheetName);
        sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 1, 3));
        /*
         * Setting Report Duration
         */
        HSSFRow duration = sheet.createRow(++currentRow);
        HSSFCell name=duration.createCell(0);
        name.setCellValue("Duration(From-To)");
        name.setCellStyle(headerStyle);
        
        HSSFCell fromCell=duration.createCell(1);
        fromCell.setCellValue(fromDate);
        fromCell.setCellStyle(dateStyle);
        
        HSSFCell toCell=duration.createCell(2);
        toCell.setCellValue(toDate);
        toCell.setCellStyle(dateStyle);
        currentRow=currentRow+4;
        HSSFRow headerRow = sheet.createRow(currentRow);
        for(String header:headers){
            HSSFRichTextString text = new HSSFRichTextString(header);
            HSSFCell cell = headerRow.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);            
            currentColumn++;
        }
        
        //POPULATE VALUE ROWS/COLUMNS
        currentRow++;//exclude header
        for(List<Object> result: results){
            currentColumn = 0;
            HSSFRow row = sheet.createRow(currentRow);
            for(Object value : result){//used to count number of columns
                HSSFCell cell = row.createCell(currentColumn);
                if (numericColumns.contains(headers.get(currentColumn))){
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(0);
                } else {
                	if(value instanceof Float || value instanceof Integer)
                	{
                		cell.setCellValue(Double.parseDouble(""+value));
                	}
                	else if(value instanceof Date)
                	{
                         cell.setCellValue(((Date)value));
                         cell.setCellStyle(dateStyle);
                	}
                	else if(value instanceof String)
                	{
                		 HSSFRichTextString text = new HSSFRichTextString(""+value);                
                         cell.setCellValue(text);         
                	}
                }
                currentColumn++;
            }
            currentRow++;
        }
        
        currentRow=currentRow+6;//exclude header
      //POPULATE HEADER COLUMNS
        HSSFRow headerRowSum = sheet.createRow(currentRow);
        currentColumn=0;
        for(String sumHeader:headerSum){
            HSSFRichTextString text = new HSSFRichTextString(sumHeader);
            HSSFCell cell = headerRowSum.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);            
            currentColumn++;
        }
        currentRow++;
        for(List<Object> result: resultSum){
            currentColumn = 0;
            HSSFRow row = sheet.createRow(currentRow);
            for(Object value : result){//used to count number of columns
                HSSFCell cell = row.createCell(currentColumn);
                if (numericColumns.contains(headers.get(currentColumn))){
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(0);
                } else {
                	if(value instanceof Float || value instanceof Integer)
                	{
                		cell.setCellValue(Double.parseDouble(""+value));
                	}
                	else if(value instanceof Date)
                	{
                         cell.setCellValue(((Date)value));
                         cell.setCellStyle(dateStyle);
                	}
                	else if(value instanceof String)
                	{
                		 HSSFRichTextString text = new HSSFRichTextString(""+value);                
                         cell.setCellValue(text);         
                	}
                }
                currentColumn++;
            }
            currentRow++;
        }
    }

	
}
