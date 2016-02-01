package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

public class BroadcastController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	
	private final int MAX_RESULTS_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params == null || params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			UserGroup profile = null;
			boolean error = false;
			
			if(params != null){
				short idUserGroup = Short.parseShort(params[0]);
			
				profile = groupService.getGroup(idUserGroup);
				
				if(profile == null){
					error = true;
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			
			
			if(!error && request.getMethod().equals("POST")){
				
				String broadcastMessage = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.BROADCAST_MESSAGE));
				String broadcastSubject = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.BROADCAST_SUBJECT));
				
				if(broadcastMessage != null && broadcastSubject !=  null){					
					boolean success = false;
					
					if(profile != null){
						success = notifyService.broadcast(profile.getUsers(), broadcastSubject, broadcastMessage);						
					}
					else{
						List<User> users = null;
						int pageResult = 0;
						boolean hasNextPage = true;
						success = true;
							
						while(hasNextPage && success){
							hasNextPage = false;
							users = userService.getUsers(pageResult);
							
							if(users!= null && users.size()>MAX_RESULTS_PAGE){
								hasNextPage = true;
								users.remove(MAX_RESULTS_PAGE);
								pageResult = pageResult + MAX_RESULTS_PAGE;
							}
							success = notifyService.broadcast(users, broadcastSubject, broadcastMessage);
						}
					}
				
					if(success){
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.BROADCAST_SENT));
					}
					else{
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SEND, ErrorMessageKey.BROADCAST_ERROR));
					}					
				}
				else{
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			
			if(!error){
				
				
				if(profile != null){
					List<Page> pageStack = pageService.getAdminBroadcastGroupPageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);

					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId()));
				}
				
				requestManager.setAttribute(request, Attribute.USERGROUP, profile);
				
				request.getRequestDispatcher("/WEB-INF/views/adminBroadcast.jsp").forward(request, response);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}
