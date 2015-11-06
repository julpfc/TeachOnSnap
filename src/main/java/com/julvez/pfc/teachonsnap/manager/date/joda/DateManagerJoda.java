package com.julvez.pfc.teachonsnap.manager.date.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;

public class DateManagerJoda implements DateManager {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	public String getCurrentDate() {
		return getCurrentDate(DEFAULT_DATE_FORMAT);
	}

	@Override
	public String getCurrentDate(String format) {
		String datetime = null;
		
		DateTimeFormatter dtf = null;
		
		try{
			dtf = DateTimeFormat.forPattern(format);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		if(dtf != null){
			datetime = dtf.print(new DateTime());
		}
		
		return datetime;
	}

}
