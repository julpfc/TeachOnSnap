package com.julvez.pfc.teachonsnap.manager.log;

import com.julvez.pfc.teachonsnap.manager.log.log4j2.LogManagerLog4j2;

public class LogManagerFactory {

	private static LogManager manager;
	
	public static LogManager getManager(){
		if(manager==null){
			manager = new LogManagerLog4j2();
		}
		return manager;
	}
}

