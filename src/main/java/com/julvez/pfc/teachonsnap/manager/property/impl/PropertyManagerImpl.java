package com.julvez.pfc.teachonsnap.manager.property.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Implementation of the PropertyManager, uses internal {@link LogManager} 
 * to log the errors and {@link StringManager} to manipulate strings.
 */
public class PropertyManagerImpl implements PropertyManager {
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/** Properties file handler */
	private Properties properties;
	
	/** Passwords file handler */
	private Properties passwords;
	
	/** Lock for concurrent access to the properties file */
	private Integer lock;
	
		
	/**
	 * Constructor requires all parameters not to be null
	 * @param stringManager String manager providing string manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 */
	public PropertyManagerImpl(StringManager stringManager, LogManager logger) {
		if(stringManager == null || logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.stringManager = stringManager;
		this.logger = logger;
		lock = new Integer(0);
		loadDefaultProperties();
		loadDefaultPasswords();
	}

	@Override
	public String getProperty(Enum<?> propertyName) {
		return getProperty(propertyName, null);
	}

	
	@Override
	public long getNumericProperty(Enum<?> propertyName) {
		return getNumericProperty(propertyName, null);
	}

	@Override
	public boolean getBooleanProperty(Enum<?> propertyName) {
		return getBooleanProperty(propertyName, null);
	}

	@Override
	public List<String> getListProperty(Enum<?> propertyName) {
		return getListProperty(propertyName, null);
	}

	@Override
	public String getProperty(Enum<?> propertyName, String propertyNameExtension) {
		String property = null;
		
		if(propertyName!=null){
			//check if the properties file is loaded
			if(properties == null){
				loadDefaultProperties();
			}
			if(properties!=null){
				//get key
				String propertyKey = propertyName.toString();
				
				//complete key if extension present
				if(!stringManager.isEmpty(propertyNameExtension)){
					propertyKey = propertyKey + propertyNameExtension;
				}
				//get property with key
				property = properties.getProperty(propertyKey);
			}
		}
		return property;
	}

	@Override
	public long getNumericProperty(Enum<?> propertyName, String propertyNameExtension) {
		long ret = -1;
		
		//get property from key
		String prop = getProperty(propertyName, propertyNameExtension);
		
		//try getting the numeric value
		if(stringManager.isNumeric(prop)){
			ret = Long.parseLong(prop);
		}
		return ret; 
	}

	@Override
	public boolean getBooleanProperty(Enum<?> propertyName,
			String propertyNameExtension) {
		boolean ret = false;
		//get property from key
		String prop = getProperty(propertyName, propertyNameExtension);

		//try getting the boolean value
		ret = stringManager.isTrue(prop);
		
		return ret;
	}

	@Override
	public List<String> getListProperty(Enum<?> propertyName,
			String propertyNameExtension) {
		List<String> list = null;
		//get property from key
		String prop = getProperty(propertyName, propertyNameExtension);
		
		//get the list of comma-separated values
		list = stringManager.split(prop, ",");		
		
		return list;
	}

	@Override
	public String getPasswordProperty(Enum<?> propertyName) {
		String password = null;
		
		if(propertyName!=null){
			//check if the passwords file is loaded
			if(passwords == null){
				loadDefaultPasswords();
			}
			if(passwords!=null){
				//get key
				String propertyKey = propertyName.toString();
				
				//get password with key
				password = passwords.getProperty(propertyKey);
			}
		}
		return password;
	}
	
	
	/**
	 * Loads the default property file
	 */
	private void loadDefaultProperties() {
		if(properties == null){		
			synchronized (lock) {
				if(properties == null){
					try{
						InputStream is = PropertyManager.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);
						properties = new Properties();
						properties.load(is);
						is.close();

					} catch (Throwable t) {
						logger.error(t, "Error accessing properties file: " + DEFAULT_PROPERTIES_FILE);
						properties = null;
					}
				}
			}
		}		
	}
	
	/**
	 * Loads the default password property file
	 */
	private void loadDefaultPasswords() {
		if(passwords == null){		
			synchronized (lock) {
				if(passwords == null){
					try{
						InputStream is = PropertyManager.class.getResourceAsStream(DEFAULT_PASSWORDS_FILE);
						passwords = new Properties();
						passwords.load(is);
						is.close();

					} catch (Throwable t) {
						logger.error(t, "Error accessing passwords properties file: " + DEFAULT_PASSWORDS_FILE);
						passwords = null;
					}
				}
			}
		}		
	}

}
