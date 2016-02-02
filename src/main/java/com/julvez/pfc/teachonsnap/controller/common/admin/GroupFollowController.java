package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
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
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * GroupFollowController extends {@link AdminController}.
 * <p>
 * Lists followed authors and tags by the group.   
 * <p> 
 * Loads information about a group, followers and authors and redirects 
 * to the adminGroupFollow.jsp * view to select author to be un/followed.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_GROUP_FOLLOWS}
 */
public class GroupFollowController extends AdminController {

	private static final long serialVersionUID = -6322083349154906796L;
	
	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;	
	
	/**
     * Default constructor
     */
    public GroupFollowController() {
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
	public GroupFollowController(UserService userService,
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
	}
	
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			//get idGroup from params
			short idUserGroup = Short.parseShort(params[0]);
			
			//get group from id
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			//if group found...
			if(profile != null){				
				//get page common information and store for the view
				List<Page> pageStack = pageService.getAdminGroupFollowPageStack(profile);				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);				
				
				requestManager.setAttribute(request, Attribute.USERGROUP, profile);				
				
				List<User> authorFollowings = groupService.getAuthorFollowings(profile);					
				requestManager.setAttribute(request, Attribute.LIST_USER, authorFollowings);
				
				List<Tag> tagFollowings = groupService.getTagFollowings(profile);					
				requestManager.setAttribute(request, Attribute.LIST_TAG, tagFollowings);
				
				String backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId());					
				requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
				
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/adminGroupFollow.jsp").forward(request, response);				
			}
			else{
				//group not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
