package com.julvez.pfc.teachonsnap.manager.date;

/** Date manager providing date manipulation utilities */
public interface DateManager {

	/** Default date format */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Returns current date in the default format.
	 * @return current date
	 */
	public String getCurrentDate();
	
	/**
	 * Returns current date in the specified format
	 * @param format Date string format
	 * @return current date
	 */
	public String getCurrentDate(String format);
}
