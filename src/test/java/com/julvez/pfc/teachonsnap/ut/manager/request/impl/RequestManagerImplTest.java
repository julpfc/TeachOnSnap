package com.julvez.pfc.teachonsnap.ut.manager.request.impl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.impl.RequestManagerImpl;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.manager.request.RequestManagerTest;

public class RequestManagerImplTest extends RequestManagerTest {
	@Mock
	private StringManager stringManager;
	
	@Mock
	private LogManager logger;

	@Override
	protected RequestManager getManager() {
		return new RequestManagerImpl(stringManager, logger);
	}
		
	@Test
	public void testGetRequestLanguage() {
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testGetRequestLanguage();
	}
		
		
	@Test
	public void testGetIP() {
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testGetIP();
	}

	@Test
	public void testGetParameter() {
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testGetParameter();
	}

	@Test
	public void testSplitParamsFromControllerURI() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testSplitParamsFromControllerURI();
	}

	@Test
	public void testGetBooleanParameter() {
		Mockito.when(stringManager.isTrue("true")).thenReturn(true);
		Mockito.when(stringManager.isTrue(BLANK_STRING)).thenReturn(false);
		Mockito.when(stringManager.isTrue(EMPTY_STRING)).thenReturn(false);
		Mockito.when(stringManager.isTrue(NULL_STRING)).thenReturn(false);
		super.testGetBooleanParameter();
	}
	
	@Test
	public void testGetNumericParameter() {
		Mockito.when(stringManager.isNumeric("101")).thenReturn(true);
		Mockito.when(stringManager.isNumeric(BLANK_STRING)).thenReturn(false);
		Mockito.when(stringManager.isNumeric(EMPTY_STRING)).thenReturn(false);
		Mockito.when(stringManager.isNumeric(NULL_STRING)).thenReturn(false);
		super.testGetNumericParameter();
	}
	
	@Test
	public void testDownloadFile() {
		super.testDownloadFile();
		Mockito.verify(logger, Mockito.atLeastOnce())
			.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}

}
