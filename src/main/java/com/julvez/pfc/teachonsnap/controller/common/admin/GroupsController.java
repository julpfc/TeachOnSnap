package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
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
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * GroupsController extends {@link AdminController}.
 * <p>
 * Lists groups, paginate them and allows to search by name. 
 * It also allows to create a new group.  
 * <p>
 * Manages GET requests to list groups and searches and POST requests
 * to create new groups from adminGroups.jsp view.
 * <p>
 * Loads groups information and redirects to the adminGroups.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_GROUP_MANAGER}
 */
public class GroupsController extends AdminController {

	private static final long serialVersionUID = 5178009737978123436L;

	/** Pagination. Limit of users per page. */
	private final int MAX_USERS_PER_PAGE;

	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;

	/**
     * Default constructor
     */
    public GroupsController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService(),
        	UserGroupServiceFactory.getService());        
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
	 * @param groupService Provides the functionality to work with application's group of users.
	 */
	public GroupsController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService,
			UserGroupService groupService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				pageService);
		
		if(groupService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.groupService = groupService;		
		MAX_USERS_PER_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params == null || (params!=null && params.length == 1 && stringManager.isNumeric(params[0]))){
			boolean error = false;

			//get page number
			if(params != null){	
				pageResult = Integer.parseInt(params[0]);
			}
			
			if(request.getMethod() == "POST"){
				
				//get new group name parameter
				String groupName = requestManager.getParameter(request,Parameter.USER_GROUP_NAME);

				//Check if we need to create a new group ...
				if(!stringManager.isEmpty(groupName)){
					
					//create new group
					UserGroup group = groupService.createGroup(groupName);
											
					if(group != null){
						//success
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED));
					}
					else {
						//Error, duplicate name
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_GROUP));
					}
				}
				else if(groupName != null){
					//Name parameter is present but empty
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);									
				}
			}
			
			//if no error -> redirect to the view
			if(!error){
				//get search parameters
				String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);
				
				//verify search parameters
				if(stringManager.isEmpty(searchQuery)){					
					searchQuery = null;					
				}				
	
				boolean hasNextPage = false;									
				List<UserGroup> groups = null;
				
				//get search result groups or all groups
				if(searchQuery != null){
					groups = groupService.searchGroupsByName(searchQuery, pageResult);
				}				
				else{
					groups = groupService.getGroups(pageResult);	
				}				
				
				//check if is there a next page
				if(groups!= null && groups.size()>MAX_USERS_PER_PAGE){
					hasNextPage = true;
					groups.remove(MAX_USERS_PER_PAGE);
				}
				
				//get next page link
				String nextPage = null;
				if(hasNextPage){
					nextPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString() + (pageResult+MAX_USERS_PER_PAGE));
					if(searchQuery != null){
						nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
					}
				}
				
				//get previous page link
				String prevPage = null;
				if(pageResult>0){				
					prevPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString());
					
					if(pageResult>MAX_USERS_PER_PAGE){
						prevPage = prevPage + (pageResult-MAX_USERS_PER_PAGE);
					}					
					if(searchQuery != null){
						prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
					}
				}
				//store next/prev page links to request for view
				requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
				requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
				
				//get page common information and store for the view
				requestManager.setAttribute(request, Attribute.LIST_GROUP, groups);
								
				List<Page> pageStack = pageService.getAdminGroupsSearchPageStack(searchQuery);				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
	
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/adminGroups.jsp").forward(request, response);
			}		
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}		
	}
}
