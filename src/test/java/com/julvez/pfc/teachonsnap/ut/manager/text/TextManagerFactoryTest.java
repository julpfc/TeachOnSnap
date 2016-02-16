package com.julvez.pfc.teachonsnap.ut.manager.text;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.manager.text.TextManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class TextManagerFactoryTest extends ManagerFactoryTest<TextManager> {

	@Override
	protected TextManager getTestManager() {	
		return TextManagerFactory.getManager();
	}	

}
