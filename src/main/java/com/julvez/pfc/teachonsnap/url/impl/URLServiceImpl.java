package com.julvez.pfc.teachonsnap.url.impl;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.URLPropertyName;

public class URLServiceImpl implements URLService {

	private static final String PROTOCOL = "https://";
	
	protected PropertyManager properties = PropertyManagerFactory.getManager();
	protected RequestManager requestManager = RequestManagerFactory.getManager();

	@Override
	public String getHost() {
		return properties.getProperty(URLPropertyName.TEACHONSNAP_HOST);		
	}

	@Override
	public String getAbsoluteURL(String relativeURL) {
		return PROTOCOL +  properties.getProperty(URLPropertyName.TEACHONSNAP_HOST) + relativeURL;
	}

	@Override
	public String getHomeURL() {
		// TODO Añadirle lo del contexto
		String context = "/";

		return PROTOCOL +  properties.getProperty(URLPropertyName.TEACHONSNAP_HOST) + context;
	}
	

}
