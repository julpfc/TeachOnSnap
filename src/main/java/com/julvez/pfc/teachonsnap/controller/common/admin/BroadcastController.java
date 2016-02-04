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
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
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
 * BroadcastController extends {@link AdminController}.
 * <p>
 * Broadcasts to a group of users or to all users in the applciation
 * <p>
 * Manages POST request (send broadcast) from adminBroadcast.jsp view.
 * <p>
 * Loads inforamtion about a group and redirects to the view to compose the broadcast subject and message.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_BROADCAST}
 */
public class BroadcastController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	/** Pagination. Limit of users per page. */
	private final int MAX_USERS_PER_PAGE;

	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;
	
	/** Provides the functionality to work with notifications */
	private NotifyService notifyService;
		
	
	/**
     * Default constructor
     */
    public BroadcastController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService(),
        	UserGroupServiceFactory.getService(),
        	NotifyServiceFactory.getService());        
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
	 * @param notifyService Provides the functionality to work with notifications
	 */
	public BroadcastController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService,
			UserGroupService groupService, NotifyService notifyService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				pageService);
		
		if(groupService == null || notifyService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.groupService = groupService;
		this.notifyService = notifyService;
		MAX_USERS_PER_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params == null || params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			UserGroup profile = null;
			boolean error = false;
			
			if(params != null){
				//get idGroup from params
				short idUserGroup = Short.parseShort(params[0]);
			
				//get group from id
				profile = groupService.getGroup(idUserGroup);
				
				if(profile == null){
					//group not found -> error
					error = true;
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}			
			
			//if no error & broadcast POST
			if(!error && request.getMethod().equals("POST")){
				
				//get broadcast data from POST params
				String broadcastMessage = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.BROADCAST_MESSAGE));
				String broadcastSubject = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.BROADCAST_SUBJECT));
				
				//check valid params
				if(broadcastMessage != null && broadcastSubject !=  null){					
					boolean success = false;
					
					//if group is specified
					if(profile != null){
						//send broadcast to group
						success = notifyService.broadcast(profile.getUsers(), broadcastSubject, broadcastMessage);						
					}
					else{
						//no group -> broadcast to all app's users
						List<User> users = null;
						int pageResult = 0;
						boolean hasNextPage = true;
						success = true;
							
						//get users page and broadcast users
						while(hasNextPage && success){
							hasNextPage = false;
							users = userService.getUsers(pageResult);
							
							if(users!= null && users.size()>MAX_USERS_PER_PAGE){
								hasNextPage = true;
								users.remove(MAX_USERS_PER_PAGE);
								pageResult = pageResult + MAX_USERS_PER_PAGE;
							}
							success = notifyService.broadcast(users, broadcastSubject, broadcastMessage);
						}
					}
				
					//generate message flags for the view
					if(success){
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.BROADCAST_SENT));
					}
					else{
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SEND, ErrorMessageKey.BROADCAST_ERROR));
					}					
				}
				else{
					//bad params
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			
			//if no error -> redirect to the view
			if(!error){
				//store group specifics information to request for the view
				if(profile != null){
					List<Page> pageStack = pageService.getAdminBroadcastGroupPageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId()));
				}				
				requestManager.setAttribute(request, Attribute.USERGROUP, profile);
				
				//dispatch to the view
				request.getRequestDispatcher("/WEB-INF/views/adminBroadcast.jsp").forward(request, response);
			}
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}
