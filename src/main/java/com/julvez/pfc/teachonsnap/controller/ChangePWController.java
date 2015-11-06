package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.request.RequestService;
import com.julvez.pfc.teachonsnap.service.request.RequestServiceFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class ChangePWController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	private UserService userService = UserServiceFactory.getService();
	private RequestService requestService = RequestServiceFactory.getService();
		
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePWController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String[] params = requestManager.getControllerParams(request);
		
		if(params!=null && params.length>0){
			String token = params[0];
			
			User user = userService.getUserFromPasswordTemporaryToken(token);
			
			if(user != null){
				
				String host = requestService.getHost();
		
				request.setAttribute(Attribute.LANGUAGE_USERLANGUAGE.toString(), user.getLanguage());
				request.setAttribute(Attribute.USER.toString(), user);
				request.setAttribute(Attribute.STRING_HOST.toString(), host);
				
				//TODO Loguear la página en la que estamos	  
				System.out.println("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
		
				if(request.getMethod().equals("POST")){
					String newPassword = requestManager.getParamNewPassword(request);
					
					if(!stringManager.isEmpty(newPassword)){
						userService.savePassword(user, newPassword);
						
						userService.deletePasswordTemporaryToken(user);
						
						requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));			
						
						response.sendRedirect(requestService.getHomeURL());
					}
					else{
						//No recibimos los parámetros correctamente
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}				
				}
				else{
					request.getRequestDispatcher("/WEB-INF/views/changepw.jsp").forward(request, response);	 
				}
			}
			else{
				//No recupera usuario
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//Sin token -> Mandar a error 404
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
