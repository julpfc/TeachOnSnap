package com.julvez.pfc.teachonsnap.ut.page.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.impl.PageServiceImpl;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.ut.page.PageServiceTest;

public class PageServiceImplTest extends PageServiceTest {

	@Mock
	private URLService urlService;
	
	@Override
	protected PageService getService() {
		return new PageServiceImpl(urlService);
	}

	@Override
	public void setUp() {		
		super.setUp();
		when(urlService.getAbsoluteURL(anyString())).thenReturn(URL);
	}	
}
