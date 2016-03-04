package com.julvez.pfc.teachonsnap.ut.url.impl;

import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.impl.URLServiceImpl;
import com.julvez.pfc.teachonsnap.url.model.URLPropertyName;
import com.julvez.pfc.teachonsnap.ut.url.URLServiceTest;

public class URLServiceImplTest extends URLServiceTest {

	@Mock
	private PropertyManager properties;
	
	@Override
	protected URLService getService() {
		return new URLServiceImpl(properties);
	}
	
	@Override
	public void setUp() {
		super.setUp();
		when(properties.getProperty(URLPropertyName.TEACHONSNAP_PROTOCOL)).thenReturn(protocol);
		when(properties.getProperty(URLPropertyName.TEACHONSNAP_HOST)).thenReturn(host);		
	}
}
