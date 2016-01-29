package com.julvez.pfc.teachonsnap.manager.date.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;

/**
 * Implementation of the DateManager, uses internal {@link LogManager} 
 * to log the errors.
 */
public class DateManagerJoda implements DateManager {

	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 */
	public DateManagerJoda(LogManager logger) {
		if(logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.logger = logger;
	}
	
	@Override
	public String getCurrentDate() {
		return getCurrentDate(DateManager.DEFAULT_DATE_FORMAT);
	}

	@Override
	public String getCurrentDate(String format) {
		String datetime = null;
		
		DateTimeFormatter dtf = null;
		
		try{
			//get date formatter from format
			dtf = DateTimeFormat.forPattern(format);
		}
		catch(Throwable t){
			logger.error(t, "Error parsing date pattern: " + format);			
		}
		
		if(dtf != null){
			//format current date
			datetime = dtf.print(new DateTime());
		}
		
		return datetime;
	}

}
