package com.julvez.pfc.teachonsnap.manager.date;

import com.julvez.pfc.teachonsnap.manager.date.joda.DateManagerJoda;


public class DateManagerFactory {
	private static DateManager manager;

	public static DateManager getManager() {
		if(manager==null){
			manager = new DateManagerJoda();
		}
		return manager;
	}
}
