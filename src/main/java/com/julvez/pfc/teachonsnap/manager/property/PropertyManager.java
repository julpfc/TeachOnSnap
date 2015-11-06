package com.julvez.pfc.teachonsnap.manager.property;

public interface PropertyManager {

	public static final String DEFAULT_PROPERTIES_FILE = "/teachonsnap.properties";
	
	public String getProperty(Enum<?> propertyName);
	
	public int getNumericProperty(Enum<?> propertyName);

	public boolean getBooleanProperty(Enum<?> propertyName);
	
}
