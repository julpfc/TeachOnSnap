package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.url.model.SearchType;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

public class FollowAuthorController extends CommonController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private PageService pageService = PageServiceFactory.getService();

	private final int MAX_RESULTS_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);

	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length >= 1 && stringManager.isNumeric(params[0]) && params.length <= 2){
			if(params.length == 2 && stringManager.isNumeric(params[1])){	
				pageResult = Integer.parseInt(params[1]);
			}
			
			short idUser = Short.parseShort(params[0]);
			
			User profile = userService.getUser(idUser);
			
			if(profile != null){
			
				boolean error = false;
	
				int idFollowAuthor = requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR);
				
				if(idFollowAuthor > 0){
					error = true;
				
					User followAuthor = userService.getUser(idFollowAuthor);
					
					if(followAuthor != null){
						User modUser = userService.followAuthor(profile, followAuthor);
						
						if(modUser != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
							if(profile.getId() == user.getId()){
								user = modUser;
							}
							profile = modUser;							
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.USER_FOLLOWS.toString() + profile.getId()));
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
						User modUser = userService.unfollowAuthor(profile, unfollowAuthor);
						
						if(modUser != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
							if(profile.getId() == user.getId()){
								user = modUser;
							}
							profile = modUser;
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.USER_FOLLOWS.toString() + profile.getId()));
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
					List<User> authorFollowings = userService.getUsersFromIDs(profile.getAuthorFollowed());
					
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
						nextPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() +"/" + (pageResult+MAX_RESULTS_PAGE));
						if(searchType != null){
							nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					
					String prevPage = null;
					if(pageResult>0){				
						prevPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/");
						
						if(pageResult>MAX_RESULTS_PAGE){
							prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
						}
						
						if(searchType != null){
							prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
					
					String backPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOWS.toString() + profile.getId());
					if(searchQuery != null){
						backPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId());
					}
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					
					requestManager.setAttribute(request, Attribute.LIST_AUTHOR_FOLLOWED, authors);
					
					List<Page> pageStack = pageService.getUserFollowAuthorSearchPageStack(profile, searchQuery, searchType);				
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


	@Override
	protected boolean isPrivateZone() {		
		return true;
	}
}
