package com.julvez.pfc.teachonsnap.manager.property;

import java.util.List;

public interface PropertyManager {

	public static final String DEFAULT_PROPERTIES_FILE = "/teachonsnap.properties";
	
	public String getProperty(Enum<?> propertyName);

	public String getProperty(Enum<?> propertyName, String propertyNameExtension);
	
	public long getNumericProperty(Enum<?> propertyName);
	
	public long getNumericProperty(Enum<?> propertyName, String propertyNameExtension);

	public boolean getBooleanProperty(Enum<?> propertyName);

	public boolean getBooleanProperty(Enum<?> propertyName, String propertyNameExtension);

	public List<String> getListProperty(Enum<?> propertyName);

	public List<String> getListProperty(Enum<?> propertyName, String propertyNameExtension);
	
}
