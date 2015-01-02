package com.julvez.pfc.teachonsnap.manager.property;

import com.julvez.pfc.teachonsnap.manager.property.impl.PropertyManagerImpl;

public class PropertyManagerFactory {

private static PropertyManager manager;
	
	public static PropertyManager getManager(){
		if(manager==null){
			manager = new PropertyManagerImpl();
		}
		return manager;
	}
}
