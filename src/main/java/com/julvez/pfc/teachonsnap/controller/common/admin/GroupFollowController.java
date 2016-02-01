package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

public class GroupFollowController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();
	
		
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			short idUserGroup = Short.parseShort(params[0]);
			
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			if(profile != null){
				
				
					List<Page> pageStack = pageService.getAdminGroupFollowPageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					
					requestManager.setAttribute(request, Attribute.USERGROUP, profile);
					
					List<User> authorFollowings = groupService.getAuthorFollowings(profile);					
					requestManager.setAttribute(request, Attribute.LIST_USER, authorFollowings);
					
					List<Tag> tagFollowings = groupService.getTagFollowings(profile);					
					requestManager.setAttribute(request, Attribute.LIST_TAG, tagFollowings);
					
					String backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId());					
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					
					request.getRequestDispatcher("/WEB-INF/views/adminGroupFollow.jsp").forward(request, response);
				
			}
			else{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}
