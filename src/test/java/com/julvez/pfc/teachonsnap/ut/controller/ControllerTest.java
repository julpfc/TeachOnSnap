package com.julvez.pfc.teachonsnap.ut.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.url.URLService;

@RunWith(MockitoJUnitRunner.class)
public abstract class ControllerTest<C> {
	
	@Mock
	protected LangService langService;
	
	@Mock
	protected URLService urlService;

	@Mock
	protected RequestManager requestManager;
	
	@Mock
	protected LogManager logger;
	
	@Mock
	protected HttpServletRequest request;
	
	@Mock
	protected HttpServletResponse response;
	
	@Mock
	protected RequestDispatcher dispatcher;

	
	protected final String NULL_STRING = null;
	protected final String BLANK_STRING = "     ";
	protected final String EMPTY_STRING = "";
	
	protected C test;
	
	protected abstract C getController();
	
	@Before
	public void setUp() {
		test = getController();
	}
}
