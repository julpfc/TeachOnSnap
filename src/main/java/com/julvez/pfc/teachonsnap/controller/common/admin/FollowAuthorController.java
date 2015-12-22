package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.url.model.SearchType;
import com.julvez.pfc.teachonsnap.user.group.UserGroupService;
import com.julvez.pfc.teachonsnap.user.group.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

public class FollowAuthorController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	private final int MAX_RESULTS_PAGE = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);

	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length >= 1 && stringManager.isNumeric(params[0]) && params.length <= 2){
			if(params.length == 2 && stringManager.isNumeric(params[1])){	
				pageResult = Integer.parseInt(params[1]);
			}
			
			short idUserGroup = Short.parseShort(params[0]);
			
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			if(profile != null){
			
				boolean error = false;
	
				int idFollowAuthor = requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR);
				
				if(idFollowAuthor > 0){
					error = true;
				
					User followAuthor = userService.getUser(idFollowAuthor);
					
					if(followAuthor != null){
						UserGroup modGroup = groupService.followAuthor(profile, followAuthor);
						
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				int idUnFollowAuthor = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR);
				
				if(idUnFollowAuthor > 0){
					error = true;
				
					User unfollowAuthor = userService.getUser(idUnFollowAuthor);
					
					if(unfollowAuthor != null){
						UserGroup modGroup = groupService.unfollowAuthor(profile, unfollowAuthor);
						
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				if(!error){
					String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);
					String searchType = requestManager.getParameter(request, Parameter.SEARCH_TYPE);
					
					if(!stringManager.isEmpty(searchQuery) && !stringManager.isEmpty(searchType)){
						if(!SearchType.EMAIL.equals(searchType) && !SearchType.NAME.equals(searchType)){
							searchQuery = null;
							searchType = null;
						}					
					}	
					else{
						searchQuery = null;
						searchType = null;
					}				
		
						
					boolean hasNextPage = false;
										
					List<AuthorFollowed> authors = null;
					List<User> users = null;
					List<User> authorFollowings = groupService.getAuthorFollowings(profile);
					
					if(searchType != null && SearchType.EMAIL.equals(searchType)){
						users = userService.searchAuthorsByEmail(searchQuery, pageResult);
					}
					else if(searchType != null && SearchType.NAME.equals(searchType)){
						users = userService.searchAuthorsByName(searchQuery, pageResult);
					}
					else{
						users = userService.getAuthors(pageResult);	
					}
					
					authors = userService.getAuthorsFollowed(users, authorFollowings);
					
					if(authors!= null && authors.size()>MAX_RESULTS_PAGE){
						hasNextPage = true;
						authors.remove(MAX_RESULTS_PAGE);
					}
					
					String nextPage = null;
					if(hasNextPage){
						nextPage = request.getServletPath()+"/"+profile.getId()+"/"+(pageResult+MAX_RESULTS_PAGE);
						if(searchType != null){
							nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					
					String prevPage = null;
					if(pageResult>0){				
						prevPage = request.getServletPath()+"/"+profile.getId()+"/";
						
						if(pageResult>MAX_RESULTS_PAGE){
							prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
						}
						
						if(searchType != null){
							prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
					
					String backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId());
					if(searchQuery != null){
						backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_AUTHOR.toString() + profile.getId());
					}
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					
					requestManager.setAttribute(request, Attribute.LIST_AUTHOR_FOLLOWED, authors);
					
					List<Page> pageStack = pageService.getAdminGroupFollowAuthorSearchPageStack(profile, searchQuery, searchType);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
		
					
					request.getRequestDispatcher("/WEB-INF/views/followAuthor.jsp").forward(request, response);
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
