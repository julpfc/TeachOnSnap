package com.julvez.pfc.teachonsnap.manager.log.log4j2;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

/**
 * Implementation of the LogManager, uses internal Log4J2 logger 
 * for logging.
 */
public class LogManagerLog4j2 implements com.julvez.pfc.teachonsnap.manager.log.LogManager {

	@Override
	public void debug(String debug) {
		LogManager.getLogger(getCallerClassName()).debug(debug);
	}

	@Override
	public void error(String error) {
		LogManager.getLogger(getCallerClassName()).error(error);
	}

	@Override
	public void error(Throwable t, String error) {
		LogManager.getLogger(getCallerClassName()).error(error, t);
	}

	@Override
	public void info(String info) {		
		LogManager.getLogger(getCallerClassName()).info(info);		
	}

	@Override
	public void addPrefix(String prefix) {
		//Add prefix to thread context
		ThreadContext.push(prefix);
	}

	@Override
	public void removePrefix() {
		//Remove prefix from thread context
		ThreadContext.pop();
	}

	private String getCallerClassName(){
		//Get method's name from the method which call this method's caller. (2 levels)
		return (new Throwable()).getStackTrace()[2].getClassName();
	}
	
	@Override
	public void startTimer() {
		//Add start time to thread context
		ThreadContext.push(""+System.currentTimeMillis());		
	}

	@Override
	public void infoTime(String info) {
		//Get total time
		String time = getTime();
		LogManager.getLogger(getCallerClassName()).info("[" + time + "] " + info);		
	}

	@Override
	public void clearTimer() {
		//Remove time from thread context
		ThreadContext.pop();		
	}
	
	/**
	 * Recovers start time from thread context and calculates total time from now.
	 * @return Total time ellapsed since {@link #startTimer()} was called, "NOTIME" otherwise.
	 */
	private String getTime() {
		String time = "NOTIME";
		
		//Get start time from thread context
		String timeSaved = ThreadContext.pop();
		
		try{
			//parse time
			long timeInMillis = Long.parseLong(timeSaved);
			
			//format total time
			time = formatTime(System.currentTimeMillis() - timeInMillis);
			
		}
		catch(Throwable t){
			error(t, "Error retrieving executing time: ");						
		}
		return time;
	}
	
	/**
	 * Formats the specified time in milliseconds into [mm:ss:MMM]
	 * @param timeInMillis Time in milliseconds
	 * @return Formatted string [mm:ss:MMM]
	 */
	private String formatTime(long timeInMillis){
		double timeInSecondsWithDecimals = timeInMillis / 1000.0;
		double timeInSecondsIntPart = Math.floor(timeInSecondsWithDecimals);
		
		//Get millis remainder
		int millis = (int)((timeInSecondsWithDecimals - timeInSecondsIntPart) * 1000);
		
		double timeInMinsWithDecimals = timeInSecondsIntPart / 60.0;
		double timeInMinsIntPart = Math.floor(timeInMinsWithDecimals);
		
		//Get secs remainder
		int secs = (int)((timeInMinsWithDecimals - timeInMinsIntPart) * 60);
		
		//Get mins
		int mins = (int) timeInMinsIntPart;
		
		DecimalFormat df = new DecimalFormat("00");
		DecimalFormat dfMillis = new DecimalFormat("000");
		
		//Builds formatted string
		return new StringBuilder(df.format(mins)).append(":").append(df.format(secs)).append(":").append(dfMillis.format(millis)).toString();
	}
}
