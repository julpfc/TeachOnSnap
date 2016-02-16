package com.julvez.pfc.teachonsnap.ut.manager.text.i18n;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.manager.text.i18n.TextManagerI18n;
import com.julvez.pfc.teachonsnap.ut.manager.text.TextManagerTest;

public class TextManagerI18nTest extends TextManagerTest {

	@Override
	protected TextManager getManager() {	
		return new TextManagerI18n();
	}	
}
