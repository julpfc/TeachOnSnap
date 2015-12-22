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
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.group.UserGroupService;
import com.julvez.pfc.teachonsnap.user.group.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.User;

public class GroupProfileController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			short idUserGroup = Short.parseShort(params[0]);
			
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			if(profile != null){
				
				UserGroup newGroup = null;
				boolean error = false;
				
				boolean removeGroup = requestManager.getBooleanParameter(request, Parameter.USER_GROUP_REMOVE);
				
				int idRemovedUser = requestManager.getNumericParameter(request, Parameter.USER_GROUP_REMOVE_USER);
				User removeUser = userService.getUser(idRemovedUser);
				
				if(removeGroup){
					
					
					if(groupService.removeGroup(profile)){						
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_DELETED);						
					}
					else{
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					error = true;
					response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString()));
					
				}
				else if(removeUser != null){
					newGroup = groupService.removeUser(profile, removeUser);
					
					if(newGroup != null){
						profile = newGroup;
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);						
					}
					else{
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					error = true;
					response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId()));
					
				}
				else if(request.getMethod().equals("POST")){
					String groupName = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.USER_GROUP_NAME));
					List<String> emails = stringManager.split(requestManager.getParameter(request, Parameter.REGISTER_MULTIPLE_EMAILS),",");
					
					if(!stringManager.isEmpty(groupName)){
						
						if(groupName.equalsIgnoreCase(profile.getGroupName())){
							//No ha cambiado nada
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//Ha cambiado
							newGroup = groupService.saveGroupName(profile, groupName);
							
							if(newGroup != null){
								profile = newGroup;
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED));			
							}
							else{
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_GROUP));
							}							
						}
					}
					else if(emails != null){
						
						newGroup = groupService.addUserByMailList(profile, emails);
						
						if(newGroup != null){
							profile = newGroup;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_USERS_ADDED));							
						}
						else{
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));							
						}	
					}					
					else{
						//No recibimos los parámetros correctamente
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}													
				}
				
				if(!error){
					List<Page> pageStack = pageService.getAdminGroupProfilePageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString()));
					
					requestManager.setAttribute(request, Attribute.USERGROUP, profile);
					request.getRequestDispatcher("/WEB-INF/views/adminGroup.jsp").forward(request, response);
				}
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
