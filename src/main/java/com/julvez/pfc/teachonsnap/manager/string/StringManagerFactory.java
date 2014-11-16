package com.julvez.pfc.teachonsnap.manager.string;

import com.julvez.pfc.teachonsnap.manager.string.impl.StringManagerImpl;

public class StringManagerFactory {

	private static StringManager manager;
	
	public static StringManager getManager(){
		if(manager==null){
			manager = new StringManagerImpl();
		}
		return manager;
	}
}
