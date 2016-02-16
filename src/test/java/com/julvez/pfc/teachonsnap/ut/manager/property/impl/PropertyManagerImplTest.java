package com.julvez.pfc.teachonsnap.ut.manager.property.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.impl.PropertyManagerImpl;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.manager.property.PropertyManagerTest;

public class PropertyManagerImplTest extends PropertyManagerTest {

	@Mock
	private StringManager stringManager;
	
	@Mock
	private LogManager logger;
	
	@Override
	protected PropertyManager getManager() {	
		return new PropertyManagerImpl(stringManager, logger);
	}

	@Test
	public void testGetPropertyEnumOfQ() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testGetPropertyEnumOfQ();
	}
	
	@Test
	public void testGetPropertyEnumOfQString() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		super.testGetPropertyEnumOfQString();
	}
	
	@Test
	public void testGetNumericPropertyEnumOfQ() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		Mockito.when(stringManager.isNumeric(Mockito.anyString())).thenReturn(true);
		Mockito.when(stringManager.isNumeric(NULL_STRING)).thenReturn(false);
		super.testGetNumericPropertyEnumOfQ();
	}
	
	@Test
	public void testGetNumericPropertyEnumOfQString() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		Mockito.when(stringManager.isNumeric(Mockito.anyString())).thenReturn(true);
		Mockito.when(stringManager.isNumeric(NULL_STRING)).thenReturn(false);
		super.testGetNumericPropertyEnumOfQString();
	}
	
	@Test
	public void testGetBooleanPropertyEnumOfQ() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		Mockito.when(stringManager.isTrue(Mockito.anyString())).thenReturn(true);
		Mockito.when(stringManager.isTrue(NULL_STRING)).thenReturn(false);
		super.testGetBooleanPropertyEnumOfQ();
	}
	
	@Test
	public void testGetBooleanPropertyEnumOfQString() {
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		Mockito.when(stringManager.isTrue(Mockito.anyString())).thenReturn(true);
		Mockito.when(stringManager.isTrue(NULL_STRING)).thenReturn(false);
		super.testGetBooleanPropertyEnumOfQString();
	}
	
	@Test
	public void testGetListPropertyEnumOfQ() {
		List<String> mails = new ArrayList<String>();
		mails.add("uc3m.es");
		mails.add("it.uc3m.es");
		mails.add("gmail.com");
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		Mockito.when(stringManager.split(Mockito.anyString(), Mockito.anyString())).thenReturn(mails);
		Mockito.when(stringManager.split(Mockito.eq(NULL_STRING), Mockito.anyString())).thenReturn(null);
		super.testGetListPropertyEnumOfQ();
	}
	
	@Test
	public void testGetListPropertyEnumOfQString() {
		List<String> list = new ArrayList<String>();
		list.add("101");		
		Mockito.when(stringManager.split(Mockito.anyString(), Mockito.anyString())).thenReturn(list);
		Mockito.when(stringManager.split(Mockito.eq(NULL_STRING), Mockito.anyString())).thenReturn(null);
		super.testGetListPropertyEnumOfQString();
	}
}
