package com.julvez.pfc.teachonsnap.ut.controller.common.admin;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.admin.FollowTagController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.ut.controller.common.AdminControllerTest;

public class FollowTagControllerTest extends AdminControllerTest {

	@Mock
	private UserGroupService groupService;
	
	@Mock
	private TagService tagService;
	
	private short idGroup = 1;
	private int idTag = 1;
	private int invalidIdTag = -1;
	
	
	@Override
	protected CommonController getController() {
		return new FollowTagController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, stringManager, 
				pageService, groupService, tagService);
	}
	
	@Test	
	public void testProcessAdminController() {		
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{""+idGroup});
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		UserGroup group = mock(UserGroup.class);
		when(groupService.getGroup(idGroup)).thenReturn(group);
		testProcessController();	
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_TAG_FOLLOWED), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_TAG)).thenReturn(idTag);
		testProcessController();
		try {
			verify(response, times(2)).sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		Tag tag = mock(Tag.class);
		when(tagService.getTag(idTag)).thenReturn(tag);
		
		testProcessController();
		verify(groupService).followTag(group, tag);
		verify(requestManager, times(6)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_TAG)).thenReturn(invalidIdTag);
		when(requestManager.getNumericParameter(request, Parameter.UNFOLLOW_TAG)).thenReturn(idTag);
		
		testProcessController();
		verify(groupService).unfollowTag(group, tag);
		verify(requestManager, times(8)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response, times(2)).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
