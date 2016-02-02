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
 * UserProfileController extends {@link AdminController}.
 * <p>
 * Lists user details, and allows to edit them.
 * <p>
 * Manages GET requests to send password reminder and POST request to edit
 * user's details from userProfile.jsp view.
 * <p>
 * Loads information about a user and redirects to the userProfile.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_USER_PROFILE}
 */
public class UserProfileController extends AdminController {

	private static final long serialVersionUID = 8664025334090502956L;
	
	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;

	/**
     * Default constructor
     */
    public UserProfileController() {
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
	public UserProfileController(UserService userService,
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
			//get idUser from params
			int idUser = Integer.parseInt(params[0]);
			
			//get group from id
			User profile = userService.getUser(idUser);
			
			//if user profile found...
			if(profile != null){				
				boolean error = false;

				//get send email reminder parameter				
				boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
				
				if(sendEmail){
					//send email reminder
					boolean sent = userService.sendPasswordRemind(user);
					
					if(sent){
						//success
						setErrorSession(request,ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_REMIND_SENT);
					}
					else{
						//error
						setErrorSession(request,ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR);
					}
					//reload page
					error = true;
					response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_PROFILE.toString() + "/" + profile.getId() + "/"));
				}
				
				else if(request.getMethod().equals("POST")){
					//get user details from view form
					String firstname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.FIRST_NAME));
					String lastname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LAST_NAME));					
					String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
					boolean author = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_AUTHOR);
					boolean admin = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_ADMIN);
					boolean blockUser = requestManager.getBooleanParameter(request, Parameter.USER_BLOCK);
					boolean unblockUser = requestManager.getBooleanParameter(request, Parameter.USER_UNBLOCK);
					String extraInfo = stringManager.unescapeHTML(requestManager.getBlankParameter(request,Parameter.USER_EXTRA_INFO));
					
					//check if user wants to edit names..
					if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
						//check if data changed
						if(firstname.equals(profile.getFirstName()) && lastname.equals(profile.getLastName())){
							//Nothing change
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{							
							if(!firstname.equals(profile.getFirstName())){
								//save first name
								profile = userService.saveFirstName(profile, firstname);					
							}
							if(!lastname.equals(profile.getLastName())){
								//save last name
								profile = userService.saveLastName(profile, lastname);
							}							
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERNAME_SAVED));			
						}
					}
					else if(extraInfo != null){ //check if user wants to edit additional information..	
						
						if(extraInfo.equals(profile.getExtraInfo())){
							//Nothing change
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//save additional information
							User modUser = userService.saveExtraInfo(profile, extraInfo);
							
							if(modUser != null){
								//success
								profile = modUser;
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
							}
							else{	
								//error
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
							}
						}
						
					}
					else if(!stringManager.isEmpty(newPassword)){ //check if user wants to change password..
						if(userService.validatePassword(profile, newPassword)){
							//Nothing change
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//save password
							userService.savePassword(profile, newPassword);
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));			
						}					
					}
					else if(blockUser && !profile.isBanned()){ //check if user wants to ban user..
						String reason = requestManager.getParameter(request, Parameter.USER_BLOCK_REASON);
						
						if(!stringManager.isEmpty(reason)){
							//block user
							User modUser = userService.blockUser(profile, reason, user);
							
							if(modUser != null){
								//success
								profile = modUser;
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
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
					else if(unblockUser && profile.isBanned()){	//check if user wants to unblock user..	
						//unblock user
						User modUser = userService.unblockUser(profile, user);
						
						if(modUser != null){
							//success
							profile = modUser;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
						}
						else{		
							//error
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
						}						
					}					
					else if(author == profile.isAuthor() && admin == profile.isAdmin()){ //check if user wants to edit roles..
						//Nothing change
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));
					}
					else if(author != profile.isAuthor() || admin != profile.isAdmin()){	
						//user wants to edit roles..
						User modUser = null;
						
						if(author && !profile.isAuthor()){
							//save author role
							modUser = userService.saveAuthor(profile);
						}
						if(admin && !profile.isAdmin()){
							//save admin role
							modUser = userService.saveAdmin(modUser!=null?modUser:profile);
						}
						if(!author && profile.isAuthor()){
							//remove author role
							modUser = userService.removeAuthor(modUser!=null?modUser:profile);
						}
						if(!admin && profile.isAdmin()){
							//remove admin role
							modUser = userService.removeAdmin(modUser!=null?modUser:profile);
						}
						
						if(modUser != null){
							//success
							profile = modUser;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
						}
						else{
							//error
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
						}						
					}
					else{
						//Bad params
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}								
				}
				
				//if no error and no previous parameters used...
				if(!error){
					List<UserGroup> groups = groupService.getGroupsFromUser(profile);
					requestManager.setAttribute(request, Attribute.LIST_GROUP, groups);
					
					List<Page> pageStack = pageService.getAdminUserProfilePageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_MANAGER.toString()));
					
					requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
					
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp").forward(request, response);
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
}
