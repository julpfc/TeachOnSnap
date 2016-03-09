package com.julvez.pfc.teachonsnap.ut.controller.common.pager;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.pager.TagController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.ut.controller.common.PagerControllerTest;

public class TagControllerTest extends PagerControllerTest {

	@Override
	protected CommonController getController() {		
		return new TagController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, tagService);
	}
	
	@Test
	public void testProcessController() {
		Tag tag = mock(Tag.class);
		when(tagService.getTag(anyString())).thenReturn(tag);
		when(tagService.getLessonsFromTag(eq(tag), anyInt())).thenReturn((List<Lesson>)new ArrayList<Lesson>());
		super.testProcessController();
		verify(statsService).saveTag(visit, tag);
	}
}
