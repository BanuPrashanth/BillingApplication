package com.example.productplanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) 
	{
		//Date date=new Date("Wed Oct 04 05:30:00 IST 2017");
		//long date=Date.parse("04-10-2017");
		Date date=null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse("04-10-2017");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		System.out.println(date);
	}
}
