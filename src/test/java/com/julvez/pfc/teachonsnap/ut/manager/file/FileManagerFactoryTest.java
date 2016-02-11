package com.julvez.pfc.teachonsnap.ut.manager.file;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class FileManagerFactoryTest extends ManagerFactoryTest<FileManager> {

	@Override
	protected FileManager getTestManager() {		
		return FileManagerFactory.getManager();
	}
}
