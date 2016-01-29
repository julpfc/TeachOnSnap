package com.julvez.pfc.teachonsnap.manager.json;

/** JSON manager providing JSON manipulation utilities */
public interface JSONManager {

	/**
	 * Describes a simplified view of the JSON entity. You can specify
	 * which fields will be in the simple view using this class to
	 * annotate them.
	 */
	public static class SimpleView {}

	/**
	 * Converts the JSON string into the entity class.
	 * @param json JSON string
	 * @param clazz Entity class
	 * @return the entity object from the JSON string, null if error
	 */
	public <T> T JSON2Object(String json, Class<T> clazz);
	
	/**
	 * Converts the JSON string into the entity class using only the
	 * simple view (only annotated fields).
	 * @param json JSON string
	 * @param clazz Entity class
	 * @return the simple view of the entity object from the JSON 
	 * string, null if error
	 */	
	public <T> T JSON2SimpleObject(String json, Class<T> clazz);

	/**
	 * Converts the object to a JSON string.
	 * @param o Object
	 * @return the JSON string, null if error
	 */
	public String object2JSON(Object o);
	
	/**
	 * Converts the object to a JSON string using only the
	 * simple view (only annotated fields).
	 * @param o Object
	 * @return the JSON string, null if error
	 */
	public String object2SimpleJSON(Object o);

}
