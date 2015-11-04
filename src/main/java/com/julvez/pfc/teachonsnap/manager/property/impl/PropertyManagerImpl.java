package com.julvez.pfc.teachonsnap.manager.property.impl;

import java.io.InputStream;
import java.util.Properties;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;

public class PropertyManagerImpl implements PropertyManager {
	
	private Properties properties;
	private Integer lock = new Integer(0);

	@Override
	public String getProperty(Enum<?> propertyName) {
		String property = null;
		
		if(propertyName!=null){
			if(properties == null){
				loadDefaultProperties();
			}
			if(properties!=null){
				property = properties.getProperty(propertyName.toString());
			}
		}
		return property;
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

					} catch (Exception e) {
						e.printStackTrace();
						properties = null;
					}
				}
			}
		}		
	}

	@Override
	public int getNumericProperty(Enum<?> propertyName) {
		int ret = -1;
		String prop = getProperty(propertyName);
		
		try{
			ret = Integer.parseInt(prop);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		return ret; 
	}
	
}
