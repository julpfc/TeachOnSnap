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
import com.julvez.pfc.teachonsnap.lang.model.Language;
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
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

/**
 * UsersController extends {@link AdminController}.
 * <p>
 * Lists users, paginate them and allows to search by name or email. 
 * It also allows to register a new user or a batch list of emails.  
 * <p>
 * Manages GET requests to list users and searches and POST requests
 * to create new users from adminUsers.jsp view.
 * <p>
 * Loads users information and redirects to the adminUsers.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_USER_MANAGER}
 */
public class UsersController extends AdminController {

	private static final long serialVersionUID = 8026700900316342321L;
	
	/** Pagination. Limit of users per page. */
	private final int MAX_USERS_PER_PAGE;


	/**
     * Default constructor
     */
    public UsersController() {
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
	public UsersController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				pageService);
						
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
				//get new user email parameter
				String email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REGISTER);

				//Check if we need to register a new user ...
				if(email != null){
					//get user from mail
					User newUser = userService.getUserFromEmail(email);
											
					if(newUser != null){
						//email is already registered
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_EMAIL_DUPLICATE));
					}
					else {
						//get user details from view form
						String firstname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.FIRST_NAME));
						String lastname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LAST_NAME));
						short idLang = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
						
						//get user language from id
						Language userLang = langService.getLanguage(idLang);
						
						//check user's details
						if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname) && userLang != null){
							//get send email after registration flag
							boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
							
							//if send mail flag...
							if(sendEmail){
								//create user and send email
								userService.sendRegister(email, firstname, lastname, userLang);
								newUser = userService.getUserFromEmail(email);									
							}
							else{
								//just create user
								newUser = userService.createUser(email, firstname, lastname, userLang);								
							}
							
							if(newUser != null){
								//success, save user roles
								boolean author = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_AUTHOR);
								boolean admin = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_ADMIN);
								
								if(author){
									//save author role if present
									userService.saveAuthor(newUser);
								}
								if(admin){
									//save admin role if present
									userService.saveAdmin(newUser);
								}
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));								
							}
							else{
								//error creating user
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
							}
						}
						else{
							//Missing params
							error = true;
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}
					}
				}
				else{
					//get list of emails parameter
					List<String> emails = stringManager.split(requestManager.getParameter(request, Parameter.REGISTER_MULTIPLE_EMAILS),",");
					
					//Check if we need to register a list of emails (batch) ...
					if(emails != null){
						User newUser = null;
						
						//get language for the new users
						short idLang = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);						
						Language userLang = langService.getLanguage(idLang);
						
						if(userLang != null){
							//get send email after registration flag
							boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
							
							//for each mail in batch list
							for(String mail:emails){
								//if send mail flag...
								if(sendEmail){			
									//create user and send email
									userService.sendRegister(mail, "", "", userLang);
								}
								else{
									//just create user
									userService.createUser(mail, "", "", userLang);									
								}
								//check created user
								newUser = userService.getUserFromEmail(mail);
								if(newUser == null){
									//error
									error = true;
									setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
									break;
								}
							}
							if(!error){
								//success
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERS_CREATED));
							}
							else error = false;
						}
						else{
							//Missing params
							error = true;
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}						
					}					
				}
			}
			
			//if no error and no user creation -> redirect to the view
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
				List<User> users = null;
	
				//get search result users or all users
				if(searchType != null && SearchType.EMAIL.equals(searchType)){
					users = userService.searchUsersByEmail(searchQuery, pageResult);
				}
				else if(searchType != null && SearchType.NAME.equals(searchType)){
					users = userService.searchUsersByName(searchQuery, pageResult);
				}
				else{
					users = userService.getUsers(pageResult);	
				}
				
				//check if is there a next page
				if(users!= null && users.size()>MAX_USERS_PER_PAGE){
					hasNextPage = true;
					users.remove(MAX_USERS_PER_PAGE);
				}
				
				//get next page link
				String nextPage = null;
				if(hasNextPage){
					nextPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_MANAGER.toString() + (pageResult+MAX_USERS_PER_PAGE));
					if(searchType != null){
						nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
					}
				}
				
				//get previous page link
				String prevPage = null;
				if(pageResult>0){				
					prevPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_MANAGER.toString());					
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
				requestManager.setAttribute(request, Attribute.LIST_USER, users);				
				List<Page> pageStack = pageService.getAdminUsersSearchPageStack(searchQuery, searchType);				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
	
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/adminUsers.jsp").forward(request, response);
			}		
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
