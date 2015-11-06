package com.julvez.pfc.teachonsnap.service.request.impl;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyName;
import com.julvez.pfc.teachonsnap.service.request.RequestService;

public class RequestServiceImpl implements RequestService {

	private static final String PROTOCOL = "https://";
	
	protected PropertyManager properties = PropertyManagerFactory.getManager();

	@Override
	public String getHost() {
		return properties.getProperty(PropertyName.TEACHONSNAP_HOST);		
	}

	@Override
	public String getAbsoluteURL(String relativeURL) {
		return PROTOCOL +  properties.getProperty(PropertyName.TEACHONSNAP_HOST) + relativeURL;
	}

	@Override
	public String getHomeURL() {
		// TODO Añadirle lo del contexto
		String context = "/";

		return PROTOCOL +  properties.getProperty(PropertyName.TEACHONSNAP_HOST) + context;
	}
	


}
