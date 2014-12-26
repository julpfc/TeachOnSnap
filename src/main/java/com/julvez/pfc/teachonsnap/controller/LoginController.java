package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private UserService userService = UserServiceFactory.getService();
	
	
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
		
		User user = requestManager.getSessionUser(request);
		
		boolean loginError = true;
		
		// Si no tiene sesión iniciada en la app
		if(user == null){
			String email = requestManager.getParamLoginEmail(request);
			
			if(email!=null){
				user = userService.getUserFromEmail(email);
				String password = requestManager.getParamLoginPassword(request);
			
				if(password!=null){
					if(userService.validatePassword(user, password)){
						//Login OK
						requestManager.setUserSession(request, user);
						loginError = false;
					}					
				}				
				
			}
		}
		else{
			// Ya estaba logueado
			loginError = false;
			
			boolean logOut = requestManager.getParamLogout(request);
			if(logOut){
				requestManager.setUserSession(request, null);				
			}
		}
			
		if(loginError){
			requestManager.setErrorSession(request, ErrorType.ERR_LOGIN);			
		}
		else{
			requestManager.setErrorSession(request, ErrorType.ERR_NONE);
		}	
		
		// GOTO LastPage
		String lastPage = requestManager.getLastPage(request);
		if(lastPage==null) lastPage = "/";
		response.sendRedirect(lastPage);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
