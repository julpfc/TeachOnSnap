package com.julvez.pfc.teachonsnap.manager.json;

import com.julvez.pfc.teachonsnap.manager.json.jackson.JSONManagerJackson;

public class JSONManagerFactory {

	private static JSONManager manager;

	public static JSONManager getManager() {
		if(manager==null){
			manager = new JSONManagerJackson();
		}
		return manager;
	}
	
	
	
}
