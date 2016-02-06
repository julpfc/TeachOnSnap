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
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.url.model.SearchType;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

/**
 * FollowAuthorController extends {@link CommonController}.
 * <p>
 * Lists followed author by the user to be unfollowed, and lists all authors to be followed.
 * It also allows to search author by email or name, and paginate results in all cases.  
 * <p>
 * Manages GET requests to un/follow authors and searches from followAuthor.jsp view.
 * <p>
 * Loads information about a user, followers and authors and redirects to the followAuthor.jsp
 * view to select author to be un/followed.
 * <p>
 * Mapped in {@link ControllerURI#USER_FOLLOW_AUTHOR}
 */
public class FollowAuthorController extends CommonController {

	private static final long serialVersionUID = -7198834439817832153L;

	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;
		
	/** Pagination. Limit of users per page. */
	private final int MAX_USERS_PER_PAGE;

	/**
     * Default constructor
     */
    public FollowAuthorController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService());        
    }
    
    /**
	 * Constructor requires all parameters not to be null
	 * @param userService Provides the functionality to work with application's users.
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param statsService Provides the functionality to work with application's stats
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 * @param properties Property manager providing access to properties files
	 * @param stringManager String manager providing string manipulation utilities
	 * @param pageService Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) 
	 */
	public FollowAuthorController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(pageService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.pageService = pageService;
				
		MAX_USERS_PER_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length >= 1 && stringManager.isNumeric(params[0]) && params.length <= 2){
			
			//get page number
			if(params.length == 2 && stringManager.isNumeric(params[1])){	
				pageResult = Integer.parseInt(params[1]);
			}
			//get idUser from params
			short idUser = Short.parseShort(params[0]);
			
			//get user from id
			User profile = userService.getUser(idUser);
			
			//if user found...
			if(profile != null){
				boolean error = false;

				//get idAuthor to follow from parameter
				int idFollowAuthor = requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR);
				
				//check if the parameter is present
				if(idFollowAuthor > 0){
					error = true;
				
					//get author from id
					User followAuthor = userService.getUser(idFollowAuthor);
					
					//if author found...
					if(followAuthor != null){
						//follow author
						User modUser = userService.followAuthor(profile, followAuthor);
						
						//check result and redirect to previous page
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
						//author not found
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				//get idAuthor to unfollow from parameter
				int idUnFollowAuthor = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR);
				
				//check if the parameter is present
				if(idUnFollowAuthor > 0){
					error = true;
				
					//get author from id
					User unfollowAuthor = userService.getUser(idUnFollowAuthor);
					
					//if author found...
					if(unfollowAuthor != null){
						//unfollow author
						User modUser = userService.unfollowAuthor(profile, unfollowAuthor);
						
						//check result and redirect to previous page
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
						//author not found
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				//if no error and no previous parameters processed
				if(!error){
					//get search parameters
					String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);
					String searchType = requestManager.getParameter(request, Parameter.SEARCH_TYPE);
					
					//verify search parameters
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
					
					//get authors followed by the user
					List<User> authorFollowings = userService.getUsersFromIDs(profile.getAuthorFollowed());
					
					//get search result authors or all authors
					if(searchType != null && SearchType.EMAIL.equals(searchType)){
						users = userService.searchAuthorsByEmail(searchQuery, pageResult);
					}
					else if(searchType != null && SearchType.NAME.equals(searchType)){
						users = userService.searchAuthorsByName(searchQuery, pageResult);
					}
					else{
						users = userService.getAuthors(pageResult);	
					}
					
					//Mark authors followed on list
					authors = userService.getAuthorsFollowed(users, authorFollowings);
					
					//check if is there a next page
					if(authors!= null && authors.size()>MAX_USERS_PER_PAGE){
						hasNextPage = true;
						authors.remove(MAX_USERS_PER_PAGE);
					}
					
					//get next page link
					String nextPage = null;
					if(hasNextPage){
						nextPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() +"/" + (pageResult+MAX_USERS_PER_PAGE));
						if(searchType != null){
							nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					
					//get previous page link
					String prevPage = null;
					if(pageResult>0){				
						prevPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/");
						
						if(pageResult>MAX_USERS_PER_PAGE){
							prevPage = prevPage + (pageResult-MAX_USERS_PER_PAGE);
						}
						
						if(searchType != null){
							prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
						}
					}
					//store next/prev page links to request for view
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
					//get page common information and store for the view
					String backPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOWS.toString() + profile.getId());
					if(searchQuery != null){
						backPage = urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId());
					}
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);					
					requestManager.setAttribute(request, Attribute.LIST_AUTHOR_FOLLOWED, authors);					
					List<Page> pageStack = pageService.getUserFollowAuthorSearchPageStack(profile, searchQuery, searchType);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
		
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/followAuthor.jsp").forward(request, response);
				}
			}
			else{
				//user not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}		
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}


	@Override
	protected boolean isPrivateZone() {		
		return true;
	}
}
