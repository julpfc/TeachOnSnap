package com.julvez.pfc.teachonsnap.manager.property.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class PropertyManagerImpl implements PropertyManager {
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	

	private Properties properties;
	private Integer lock = new Integer(0);
	
	@Override
	public String getProperty(Enum<?> propertyName) {
		return getProperty(propertyName, null);
	}

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
						logger.error(t, "Error recuperando fichero de properties: " + DEFAULT_PROPERTIES_FILE);
						properties = null;
					}
				}
			}
		}		
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
			if(properties == null){
				loadDefaultProperties();
			}
			if(properties!=null){
				String propertyKey = propertyName.toString();
				
				if(!stringManager.isEmpty(propertyNameExtension)){
					propertyKey = propertyKey + propertyNameExtension;
				}
				property = properties.getProperty(propertyKey);
			}
		}
		return property;
	}

	@Override
	public long getNumericProperty(Enum<?> propertyName, String propertyNameExtension) {
		long ret = -1;
		String prop = getProperty(propertyName, propertyNameExtension);
		
		if(stringManager.isNumeric(prop)){
			ret = Long.parseLong(prop);
		}
		return ret; 
	}

	@Override
	public boolean getBooleanProperty(Enum<?> propertyName,
			String propertyNameExtension) {
		boolean ret = false;
		
		String prop = getProperty(propertyName, propertyNameExtension);
		
		ret = stringManager.isTrue(prop);
		
		return ret;
	}

	@Override
	public List<String> getListProperty(Enum<?> propertyName,
			String propertyNameExtension) {
		List<String> list = null;
		
		String prop = getProperty(propertyName, propertyNameExtension);
		
		list = stringManager.split(prop, ",");		
		
		return list;
	}
	
}
