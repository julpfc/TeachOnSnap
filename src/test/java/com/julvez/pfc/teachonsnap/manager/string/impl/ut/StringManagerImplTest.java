package com.julvez.pfc.teachonsnap.manager.string.impl.ut;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.impl.StringManagerImpl;
import com.julvez.pfc.teachonsnap.manager.string.ut.StringManagerTest;

@RunWith(MockitoJUnitRunner.class)
public class StringManagerImplTest extends StringManagerTest {

	@Mock
	private LogManager logger;
	
	@Override
	protected StringManager getManager() {		
		return new StringManagerImpl(logger);
	}
	
	@Test
	public void testEncodeURL() {
		super.testEncodeURL();
		Mockito.verify(logger, Mockito.atLeastOnce())
			.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}
	
	@Test
	public void testDecodeURL() {
		super.testDecodeURL();
		Mockito.verify(logger, Mockito.atLeastOnce())
			.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}

}
