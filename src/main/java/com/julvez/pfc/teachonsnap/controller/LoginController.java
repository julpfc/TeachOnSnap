package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Servlet implementation class LoginController. 
 * <p>
 * Logs user into the application, creates a new visit if necessary.
 * It also manages the user registration request and remind password request. 
 * <p>
 * It's called from login.jsp view.
 * <p>
 * It's a flow controller, redirects to the last controller after success, error controller otherwise.
 * <p>
 * Mapped in {@link ControllerURI#LOGIN}
 */
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 8335167011747283015L;
	
	/** Provides the functionality to work with application's users. */
	private UserService userService;
	
	/** Provides the functionality to work with different languages to the application */
	private LangService langService;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** Provides the functionality to work with application's stats */
	private StatsService statsService;
	
	/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
	private RequestManager requestManager;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
    
    /**
     * Default constructor
     */
    public LoginController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	StringManagerFactory.getManager());        
    }
    
    /**
	 * Constructor requires all parameters not to be null
	 * @param userService Provides the functionality to work with application's users.
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param statsService Provides the functionality to work with application's stats
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public LoginController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			StringManager stringManager) {

		super();
		
		if(userService == null || langService == null || urlService == null
				|| statsService == null || requestManager == null || stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.userService = userService;
		this.langService = langService;
		this.urlService = urlService;
		this.statsService = statsService;
		this.requestManager = requestManager;
		this.stringManager = stringManager;
	}

	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		
		//get current visit from HTTP session
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		
		//get user from visit
		if(visit != null) user = visit.getUser();
		
		boolean loginError = true;
		boolean emailRemind = false;
		
		//get previous URI
		String lastPage = requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE);
		
		//if there is no user then user is not logged in
		if(user == null){
			//get email parameter (user's login)
			String email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL);
			
			//if the email parameter is present, it's because user is trying to log in			
			if(email != null){
				//get user from email
				user = userService.getUserFromEmail(email);
				
				//get password parameter
				String password = requestManager.getParameter(request,Parameter.LOGIN_PASSWORD);
			
				//if user sent the password we try to log in
				if(password != null){
					//if password is correct
					if(userService.validatePassword(user, password)){
						
						//if user is not banned
						if(!user.isBanned()){
							//Login OK
							
							if(visit == null){
								//create visit from IP if necessary
								visit = statsService.createVisit(requestManager.getIP(request));	
							}
							
							//save logged user to visit
							Visit visitu = statsService.saveUser(visit,user);
							
							//update visit
							if(visitu != null){							
								visit = visitu;
							}
							
							//save visit to session
							requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
							loginError = false;
						}
						else{
							//User is banned, save error at session
							emailRemind = true;
							loginError = false;
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_BANNED, ErrorMessageKey.USER_BANNED));
						}
					}					
				}
			}
			else{
				//user is not trying to log in, check if it's asking for a password remind
				email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REMIND);

				// If forgot his password ...
				if(email != null){
					emailRemind = true;
					loginError = false;
					
					//get user from email
					user = userService.getUserFromEmail(email);
					
					//if user exists...
					if(user != null){
						//send password remind
						boolean sent = userService.sendPasswordRemind(user);
						
						//if success...
						if(sent){							
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_REMIND_SENT));
						}
						else{
							//there was a proble sending the reminder
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR));
						}							
					}
					else {
						//email is not registered -> error
						requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.PASSWORD_REMIND_EMAIL_ERROR));
					}
				}				
				else{
					//user did not forget his password, check if it's asking to register
					email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REGISTER);

					//If wants to register...
					if(email != null){
						emailRemind = true;
						loginError = false;
						
						//get user from email
						user = userService.getUserFromEmail(email);
							
						//if user exists
						if(user != null){
							//Error: User's email is already registered
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_EMAIL_DUPLICATE));
						}
						else {
							//it's a new user, get user's details
							String firstname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.FIRST_NAME));
							String lastname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LAST_NAME));
							String acceptLang = requestManager.getRequestLanguage(request);
							
							//get language from accept header, default language if not supported by the application
							Language userLang = langService.getUserSessionLanguage(acceptLang, null, null);
							
							//if mandatory fields are present
							if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
								
								//check email's domain
								boolean verifiedEmail = userService.verifyEmailDomain(email);
								
								//if verified domain
								if(verifiedEmail){
									//create user and send link to finalize registration process
									boolean sent = userService.sendRegister(email, firstname, lastname, userLang);
									
									if(sent){
										//success
										requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.REGISTER_SENT));
									}
									else{
										//error sending email
										requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR));
									}
								}
								else{
									//error, the specified email's domain is not in the white list
									requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_UNVERIFIED_MAIL));
								}
							}
							else{
								// Missing params -> Login
								requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_LOGIN, ErrorMessageKey.NONE));
							}
						}
					}
				}
			}
		}
		else{
			//User is logged in
			loginError = false;
			
			//check if wants to logout
			boolean logOut = requestManager.getBooleanParameter(request,Parameter.LOGOUT);
			
			//if logging out...
			if(logOut){
				//save visit changes to session
				visit.setUser(null);
				requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
				
				//set last page to home 
				lastPage = urlService.getHomeURL();
			}
		}
		
		//if there was a login error
		if(loginError){
			//set login error as last error
			requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_LOGIN, ErrorMessageKey.NONE));			
		}
		else if(!emailRemind){
			//No errors, reset last error
			requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.NONE));
		}	
		
		// GOTO LastPage (home if it's not defined)		
		if(lastPage == null) lastPage = urlService.getHomeURL();
		response.sendRedirect(lastPage);		
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Process HTTP POST requests at doGet()
		doGet(request, response);
	}

}
