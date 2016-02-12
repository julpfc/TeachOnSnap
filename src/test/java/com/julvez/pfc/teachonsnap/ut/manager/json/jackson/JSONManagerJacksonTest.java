package com.julvez.pfc.teachonsnap.ut.manager.json.jackson;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.jackson.JSONManagerJackson;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.ut.manager.json.JSONManagerTest;

public class JSONManagerJacksonTest extends JSONManagerTest {

	@Mock
	private LogManager logger;
	
	@Override
	protected JSONManager getManager() {		
		return new JSONManagerJackson(logger);
	}

	@Test
	public void testJSON2Object() {
		super.testJSON2Object();
		Mockito.verify(logger, Mockito.atLeastOnce())
			.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}

	@Test
	public void testJSON2SimpleObject() {
		super.testJSON2SimpleObject();
		Mockito.verify(logger, Mockito.atLeastOnce())
			.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}
	
}
