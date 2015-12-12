package com.julvez.pfc.teachonsnap.manager.log.log4j2;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;


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
		ThreadContext.push(prefix);
	}

	@Override
	public void removePrefix() {
		ThreadContext.pop();
	}

	private String getCallerClassName(){
		return (new Throwable()).getStackTrace()[2].getClassName();
	}
	
	@Override
	public void startTimer() {
		ThreadContext.push(""+System.currentTimeMillis());		
	}

	@Override
	public void infoTime(String info) {
		String time = getTime();
		LogManager.getLogger(getCallerClassName()).info("[" + time + "] " + info);		
	}

	private String getTime() {
		String time = "NOTIME";
		
		String timeSaved = ThreadContext.pop();
		
		try{
			long timeInMillis = Long.parseLong(timeSaved);
			time = formatTime(System.currentTimeMillis() - timeInMillis);
			
		}
		catch(Throwable t){
			LogManager.getLogger(getCallerClassName()).error("Error recuperando tiempo de ejecución: ", t);			
		}
		return time;
	}
	
	private String formatTime(long timeInMillis){
		double timeInSecondsWithDecimals = timeInMillis / 1000.0;
		double timeInSecondsIntPart = Math.floor(timeInSecondsWithDecimals);
		
		int millis = (int)((timeInSecondsWithDecimals - timeInSecondsIntPart) * 1000);
		
		double timeInMinsWithDecimals = timeInSecondsIntPart / 60.0;
		double timeInMinsIntPart = Math.floor(timeInMinsWithDecimals);
		
		int secs = (int)((timeInMinsWithDecimals - timeInMinsIntPart) * 60);
		
		int mins = (int) timeInMinsIntPart;
		
		DecimalFormat df = new DecimalFormat("00");
		DecimalFormat dfMillis = new DecimalFormat("000");
		
		return new StringBuilder(df.format(mins)).append(":").append(df.format(secs)).append(":").append(dfMillis.format(millis)).toString();
	}

	@Override
	public void clearTimer() {
		ThreadContext.pop();		
	}
}
