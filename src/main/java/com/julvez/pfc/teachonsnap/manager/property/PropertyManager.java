package com.julvez.pfc.teachonsnap.manager.property;

import java.util.List;

/** Property manager providing access to properties files */
public interface PropertyManager {

	/** Default properties file for the application	 */
	public static final String DEFAULT_PROPERTIES_FILE = "/teachonsnap.properties";

	/** Default passwords properties file for the application	 */
	public static final String DEFAULT_PASSWORDS_FILE = "/teachonsnap.password.properties";
	
	/**
	 * Returns the property value associated to the specified key.
	 * @param propertyName key in the properties file
	 * @return the property value associated to the specified key.
	 */
	public String getProperty(Enum<?> propertyName);

	
	/**
	 * Returns the property value associated to the specified key.
	 * It combines the key and the extension to get the final key.
	 * @param propertyName key-prefix at the properties file
	 * @param propertyNameExtension key-suffix at the properties file
	 * @return the property value associated to the specified key.
	 */
	public String getProperty(Enum<?> propertyName, String propertyNameExtension);
	
	/**
	 * Returns the property numeric value associated to the specified key.
	 * @param propertyName key at the properties file
	 * @return the property numeric value associated to the specified key.
	 */
	public long getNumericProperty(Enum<?> propertyName);
	
	/**
	 * Returns the property numeric value associated to the specified key.
	 * It combines the key and the extension to get the final key.
	 * @param propertyName key-prefix at the properties file
	 * @param propertyNameExtension key-suffix at the properties file
	 * @return the property numeric value associated to the specified key.
	 */
	public long getNumericProperty(Enum<?> propertyName, String propertyNameExtension);

	/**
	 * Returns the property boolean value associated to the specified key.
	 * @param propertyName key at the properties file
	 * @return the property boolean value associated to the specified key.
	 */
	public boolean getBooleanProperty(Enum<?> propertyName);

	/**
	 * Returns the property boolean value associated to the specified key.
	 * It combines the key and the extension to get the final key.
	 * @param propertyName key-prefix at the properties file
	 * @param propertyNameExtension key-suffix at the properties file
	 * @return the property boolean value associated to the specified key.
	 */
	public boolean getBooleanProperty(Enum<?> propertyName, String propertyNameExtension);

	/**
	 * Returns the list of comma-separated values associated to the specified key.
	 * @param propertyName key at the properties file
	 * @return the list of comma-separated values associated to the specified key.
	 */
	public List<String> getListProperty(Enum<?> propertyName);

	/**
	 * Returns the list of comma-separated values associated to the specified key.
	 * It combines the key and the extension to get the final key.
	 * @param propertyName key-prefix at the properties file
	 * @param propertyNameExtension key-suffix at the properties file
	 * @return the list of comma-separated values associated to the specified key.
	 */
	public List<String> getListProperty(Enum<?> propertyName, String propertyNameExtension);

	/**
	 * Returns the password property associated to the specified key from the 
	 * application's password properties file.
	 * @param propertyName key in the password properties file
	 * @return the password value associated to the specified key.
	 */
	public String getPasswordProperty(Enum<?> propertyName);
	
}
