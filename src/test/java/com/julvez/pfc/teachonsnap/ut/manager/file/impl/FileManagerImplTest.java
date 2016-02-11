package com.julvez.pfc.teachonsnap.ut.manager.file.impl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.file.impl.FileManagerImpl;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.ut.manager.file.FileManagerTest;

public class FileManagerImplTest extends FileManagerTest {

	@Mock
	private LogManager logger;
	
	@Override
	protected FileManager getManager() {		
		return new FileManagerImpl(logger);
	}
	
	@Test
	public void testCopyStream() {
		super.testCopyStream();
		Mockito.verify(logger, Mockito.atLeastOnce())
		.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}
	
	@Test
	public void testDelete() {
		super.testDelete();
		Mockito.verify(logger, Mockito.atLeastOnce())
		.error((Throwable)Mockito.anyObject(), Mockito.anyString());
	}

}
