package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
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
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Servlet implementation class TagController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserService userService = UserServiceFactory.getService();
	private StatsService visitService = StatsServiceFactory.getService();
	private URLService requestService = URLServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();
	
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		
		if(visit!=null) user = visit.getUser();
		
		boolean loginError = true;
		boolean emailRemind = false;
		
		String lastPage = requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE);
		
		// Si no tiene sesión iniciada en la app
		if(user == null){
			String email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL);
			
			if(email!=null){
				user = userService.getUserFromEmail(email);
				String password = requestManager.getParameter(request,Parameter.LOGIN_PASSWORD);
			
				if(password!=null){
					if(userService.validatePassword(user, password)){
						if(!user.isBanned()){
							//Login OK
							if(visit == null){
								visit = visitService.createVisit(requestManager.getIP(request));							
							}
							
							Visit visitu = visitService.saveUser(visit,user);
							
							if(visitu != null){							
								visit = visitu;
							}
							requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
							loginError = false;
						}
						else{
							emailRemind = true;
							loginError = false;
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_BANNED, ErrorMessageKey.USER_BANNED));
						}
					}					
				}				
				
			}
			else{
				email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REMIND);

				// Si olvidó su contraseña ...
				if(email != null){
					emailRemind = true;
					loginError = false;
					
					user = userService.getUserFromEmail(email);
					
					if(user != null){
						boolean sent = userService.sendPasswordRemind(user);
						
						if(sent){
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_REMIND_SENT));
						}
						else{
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR));
						}							
					}
					else {
						requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.PASSWORD_REMIND_EMAIL_ERROR));
					}
				}
				
				else{
					email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REGISTER);

					// Si se quiere registrar ...
					if(email != null){
						emailRemind = true;
						loginError = false;
						
						user = userService.getUserFromEmail(email);
												
						if(user != null){
							//Ya estaba registrado
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_EMAIL_DUPLICATE));
						}
						else {
							String firstname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.FIRST_NAME));
							String lastname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LAST_NAME));
							String acceptLang = requestManager.getRequestLanguage(request);
							
							Language userLang = langService.getUserSessionLanguage(acceptLang, null, null);
							
							if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
								
								boolean verifiedEmail = userService.verifyEmailDomain(email);
								
								if(verifiedEmail){
								
									boolean sent = userService.sendRegister(email, firstname, lastname, userLang);
									
									if(sent){
										requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.REGISTER_SENT));
									}
									else{
										requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR));
									}
								}
								else{
									requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_UNVERIFIED_MAIL));
								}
							}
							else{
								// No llegan parámetros obligatorios -> Login
								requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_LOGIN, ErrorMessageKey.NONE));
							}
						}
					}
				}
			}
		}
		else{
			// Ya estaba logueado
			loginError = false;
			
			boolean logOut = requestManager.getBooleanParameter(request,Parameter.LOGOUT);
			if(logOut){
				visit.setUser(null);
				requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);	
				lastPage = requestService.getHomeURL();
			}
		}
			
		if(loginError){
			requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_LOGIN, ErrorMessageKey.NONE));			
		}
		else if(!emailRemind){
			requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.NONE));
		}	
		
		// GOTO LastPage
		
		if(lastPage==null) lastPage = requestService.getHomeURL();
		response.sendRedirect(lastPage);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
