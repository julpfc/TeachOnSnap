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

public class GroupsController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();

		
	private final int MAX_RESULTS_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);

	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		//Lista los grupos, recibe /num_pagina
		
		int pageResult = 0;
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params == null || (params!=null && params.length == 1 && stringManager.isNumeric(params[0]))){
			if(params != null){	
				pageResult = Integer.parseInt(params[0]);
			}
			
			boolean error = false;

			if(request.getMethod() == "POST"){
				String groupName = requestManager.getParameter(request,Parameter.USER_GROUP_NAME);

				// Si se quiere crear un nuevo grupo ...
				if(!stringManager.isEmpty(groupName)){
					
					UserGroup group = groupService.createGroup(groupName);
											
					if(group != null){
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED));
					}
					else {
						//Ya existía ese nombre
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_GROUP));
					}
				}
				else if(groupName != null){
					// No llegan parámetros obligatorios
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);									
				}
			}
			
			if(!error){
				String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);
				
				if(stringManager.isEmpty(searchQuery)){					
					searchQuery = null;					
				}				
	
				boolean hasNextPage = false;
									
				List<UserGroup> groups = null;
			
				if(searchQuery != null){
					groups = groupService.searchGroupsByName(searchQuery, pageResult);
				}				
				else{
					groups = groupService.getGroups(pageResult);	
				}				
				
				
				if(groups!= null && groups.size()>MAX_RESULTS_PAGE){
					hasNextPage = true;
					groups.remove(MAX_RESULTS_PAGE);
				}
				
				String nextPage = null;
				if(hasNextPage){
					nextPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString() + (pageResult+MAX_RESULTS_PAGE));
					if(searchQuery != null){
						nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
					}
				}
				
				String prevPage = null;
				if(pageResult>0){				
					prevPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString());
					
					if(pageResult>MAX_RESULTS_PAGE){
						prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
					}
					
					if(searchQuery != null){
						prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
					}
				}
				requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
				requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
				
				requestManager.setAttribute(request, Attribute.LIST_GROUP, groups);
				
				
				List<Page> pageStack = pageService.getAdminGroupsSearchPageStack(searchQuery);				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
	
				
				request.getRequestDispatcher("/WEB-INF/views/adminGroups.jsp").forward(request, response);
			}
		
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}		

	}
}
