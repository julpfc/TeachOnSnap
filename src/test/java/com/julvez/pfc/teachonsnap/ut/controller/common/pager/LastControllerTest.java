package com.julvez.pfc.teachonsnap.ut.controller.common.pager;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.pager.LastController;
import com.julvez.pfc.teachonsnap.ut.controller.common.PagerControllerTest;

public class LastControllerTest extends PagerControllerTest {

	@Override
	protected CommonController getController() {
		return new LastController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, tagService);
	}
}
