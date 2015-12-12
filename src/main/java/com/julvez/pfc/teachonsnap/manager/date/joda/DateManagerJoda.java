package com.julvez.pfc.teachonsnap.manager.date.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

public class DateManagerJoda implements DateManager {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private LogManager logger = LogManagerFactory.getManager();

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
			logger.error(t, "Error formateando fecha actual: " + format);			
		}
		
		if(dtf != null){
			datetime = dtf.print(new DateTime());
		}
		
		return datetime;
	}

}
