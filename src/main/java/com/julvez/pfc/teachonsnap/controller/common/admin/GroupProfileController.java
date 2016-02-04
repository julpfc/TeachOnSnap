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
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * GroupProfileController extends {@link AdminController}.
 * <p>
 * Lists group members, and allows to add/remove them to/from group.
 * Allows to edit group name and to delete the group. It also allows to 
 * add a list of emails in batch mode (if emails are already registered).  
 * <p>
 * Manages GET requests to add/remove members and POST request to edit
 * group's name or add a batch of emails from adminGroup.jsp view.
 * <p>
 * Loads information about a group, name and members and redirects to the 
 * adminGroup.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_GROUP_PROFILE}
 */
public class GroupProfileController extends AdminController {

	private static final long serialVersionUID = 3560463278587542099L;

	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;

	/**
     * Default constructor
     */
    public GroupProfileController() {
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
	public GroupProfileController(UserService userService,
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
				UserGroup newGroup = null;
				boolean error = false;
				
				//get remove group parameter
				boolean removeGroup = requestManager.getBooleanParameter(request, Parameter.USER_GROUP_REMOVE);
				
				//get idUser member to remove from parameter
				int idRemovedUser = requestManager.getNumericParameter(request, Parameter.USER_GROUP_REMOVE_USER);
				//get user from id
				User removeUser = userService.getUser(idRemovedUser);
				
				//if user wants to delete the group...
				if(removeGroup){					
					//remove group
					if(groupService.removeGroup(profile)){
						//success
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_DELETED);						
					}
					else{
						//error
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					//redirect to previous page
					error = true;
					response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString()));
					
				}
				else if(removeUser != null){
					//remove user
					newGroup = groupService.removeUser(profile, removeUser);
					
					if(newGroup != null){
						//success
						profile = newGroup;
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);						
					}
					else{
						//error
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					//redirect to previous page
					error = true;
					response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId()));
					
				}
				else if(request.getMethod().equals("POST")){
					//get change name parameter or batch of emails
					String groupName = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.USER_GROUP_NAME));
					List<String> emails = stringManager.split(requestManager.getParameter(request, Parameter.REGISTER_MULTIPLE_EMAILS),",");
					
					//if edit name..
					if(!stringManager.isEmpty(groupName)){						
						//check if it's a new name
						if(groupName.equalsIgnoreCase(profile.getGroupName())){
							//Nothing changed
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//Save new name
							newGroup = groupService.saveGroupName(profile, groupName);
							
							if(newGroup != null){
								//success
								profile = newGroup;
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED));			
							}
							else{
								//error
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_GROUP));
							}							
						}
					}
					else if(emails != null){
						//add email's batch
						newGroup = groupService.addUserByMailList(profile, emails);
						
						if(newGroup != null){
							//success
							profile = newGroup;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.GROUP_USERS_ADDED));							
						}
						else{
							//error
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));							
						}	
					}					
					else{
						//No recibimos los parámetros correctamente
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}													
				}
				
				//if no error and no previous parameters used...
				if(!error){
					//get page common information and store for the view
					List<Page> pageStack = pageService.getAdminGroupProfilePageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString()));
					
					requestManager.setAttribute(request, Attribute.USERGROUP, profile);
					
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/adminGroup.jsp").forward(request, response);
				}
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
