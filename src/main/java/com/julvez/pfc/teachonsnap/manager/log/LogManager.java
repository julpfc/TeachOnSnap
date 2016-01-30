package com.julvez.pfc.teachonsnap.manager.log;

/** Log manager providing logging capabilities */
public interface LogManager {

	/**
	 * Logs message in DEBUG level.
	 * @param debug Message
	 */
	public void debug(String debug);
	
	/**
	 * Logs message in ERROR level.
	 * @param error Message 
	 */
	public void error(String error);
	
	/**
	 * Logs exception and message in ERROR level.
	 * @param t exception
	 * @param error Message
	 */
	public void error(Throwable t, String error);
		
	/**
	 * Logs message in INFO level.
	 * @param info Message
	 */
	public void info(String info);
	
	/**
	 * Adds a prefix to all log's traces within this thread
	 * from now on. 
	 * @param prefix Prefix
	 * @see #removePrefix()
	 */
	public void addPrefix(String prefix);	
	
	/**
	 * Removes a previous prefix from all log's traves
	 * within this thread from now on.
	 * @see #addPrefix(String)
	 */
	public void removePrefix();

	/**
	 * Starts timer from 0. To be used with
	 * {@link #infoTime(String)}
	 */
	public void startTimer();

	/**
	 * Logs message in INFO level including the timer time.
	 * Stops timer and logs the time. See {@link #startTimer()}.
	 * <p>i.e.<p>
	 *    logger.startTimer();
	 *    
	 *    try{
	 *    //do some heavy stuff with a external resource 
	 *    //(database, file system, network,...)
	 *    
	 *    logger.infoTime("Done!"); // >> [00:01:234] Done!
	 *    }
	 *    catch(Trhowable t){
	 *       //Exception handling
	 *              
	 *       logger.clearTimer(); //Stops timer in case exception
	 *    }
	 * @param info Message
	 */
	public void infoTime(String info);

	/**
	 * Resets and stops the timer only in case no 
	 * {@link #infoTime(String)} is called.
	 */
	public void clearTimer();
}
