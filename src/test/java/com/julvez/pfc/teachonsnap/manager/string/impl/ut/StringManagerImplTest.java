package com.julvez.pfc.teachonsnap.manager.string.impl.ut;

import org.junit.runner.RunWith;
import org.mockito.Mock;
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

}
