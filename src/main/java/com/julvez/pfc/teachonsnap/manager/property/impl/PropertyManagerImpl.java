package com.julvez.pfc.teachonsnap.manager.property.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class PropertyManagerImpl implements PropertyManager {
	
	private StringManager stringManager = StringManagerFactory.getManager();

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
		
		if(stringManager.isNumeric(prop)){
			ret = Integer.parseInt(prop);
		}
		return ret; 
	}

	@Override
	public boolean getBooleanProperty(Enum<?> propertyName) {
		boolean ret = false;
		
		String prop = getProperty(propertyName);
		
		ret = stringManager.isTrue(prop);
		
		return ret;
	}

	@Override
	public List<String> getListProperty(Enum<?> propertyName) {
		List<String> list = null;
		
		String prop = getProperty(propertyName);
		
		if(!stringManager.isEmpty(prop)){
			String[] params = null;
			if(prop.contains(",")){
				params = prop.split(",");
			}
			else params = new String[]{prop};
			
			if(params != null){
				list = new ArrayList<String>();
				for(String param:params){
					list.add(param.toLowerCase().trim());
				}
			}			
		}		
		
		return list;
	}
	
}
