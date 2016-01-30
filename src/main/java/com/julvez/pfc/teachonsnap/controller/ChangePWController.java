package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Servlet implementation class TagController
 */
public class ChangePWController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	private UserService userService = UserServiceFactory.getService();
	private URLService requestService = URLServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();
		
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();

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
		
		//Loguear la página en la que estamos
		logger.info("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0){
			String token = params[0];
			
			User user = userService.getUserFromPasswordTemporaryToken(token);
			
			if(user != null){
				
				String host = requestService.getHost();
		
				List<Language> langs = langService.getAllLanguages();
				requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);

				requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, user.getLanguage());
				requestManager.setAttribute(request, Attribute.USER, user);
				requestManager.setAttribute(request, Attribute.STRING_HOST, host);
				
				
		
				if(request.getMethod().equals("POST")){
					String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
					
					if(!stringManager.isEmpty(newPassword)){
						userService.savePassword(user, newPassword);
						
						userService.deletePasswordTemporaryToken(user);
						
						requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));			
						
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
		logger.info("####END#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
