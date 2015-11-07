package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.service.url.Parameter;
import com.julvez.pfc.teachonsnap.service.url.SessionAttribute;
import com.julvez.pfc.teachonsnap.service.url.URLService;
import com.julvez.pfc.teachonsnap.service.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.service.visit.VisitService;
import com.julvez.pfc.teachonsnap.service.visit.VisitServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserService userService = UserServiceFactory.getService();
	private VisitService visitService = VisitServiceFactory.getService();
	private URLService requestService = URLServiceFactory.getService();
	
	private RequestManager requestManager = RequestManagerFactory.getManager();
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
							requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.PASSWORD_REMIND_SEND_ERROR));
						}							
					}
					else {
						requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.PASSWORD_REMIND_EMAIL_ERROR));
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
