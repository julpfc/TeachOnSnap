package com.julvez.pfc.teachonsnap.manager.json;

public interface JSONManager {

	public <T> T JSON2Object(String json, Class<T> clazz);
	
	public <T> T JSON2SimpleObject(String json, Class<T> clazz);

	public String object2JSON(Object o);
	
	public String object2SimpleJSON(Object o);

}
