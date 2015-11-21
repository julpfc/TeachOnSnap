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
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

public class UserProfileController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	private PageService pageService = PageServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			int idUser = Integer.parseInt(params[0]);
			
			User profile = userService.getUser(idUser);
			
			if(profile != null){
				
				boolean error = false;
				
				boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
				
				if(sendEmail){
					boolean sent = userService.sendPasswordRemind(user);
					
					if(sent){
						setErrorSession(request,ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_REMIND_SENT);
					}
					else{
						setErrorSession(request,ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.MAIL_SEND_ERROR);
					}
					
					error = true;
					response.sendRedirect(ControllerURI.ADMIN_USER_PROFILE.toString() + "/" + profile.getId() + "/");
				}
				
				else if(request.getMethod().equals("POST")){
					
					String firstname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.FIRST_NAME));
					String lastname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LAST_NAME));					
					String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
					boolean author = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_AUTHOR);
					boolean admin = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_ADMIN);
					boolean blockUser = requestManager.getBooleanParameter(request, Parameter.USER_BLOCK);
					boolean unblockUser = requestManager.getBooleanParameter(request, Parameter.USER_UNBLOCK);
					
					if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
						
						if(firstname.equals(profile.getFirstName()) && lastname.equals(profile.getLastName())){
							//No ha cambiado nada
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//Ha cambiado
							if(!firstname.equals(profile.getFirstName())){
								profile = userService.saveFirstName(profile, firstname);					
							}
							if(!lastname.equals(profile.getLastName())){
								profile = userService.saveLastName(profile, lastname);
							}							
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERNAME_SAVED));			
						}
					}
					else if(!stringManager.isEmpty(newPassword)){
						if(userService.validatePassword(profile, newPassword)){
							//No ha cambiado nada
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
						}
						else{
							//Ha cambiado
							userService.savePassword(profile, newPassword);
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));			
						}					
					}
					else if(blockUser && !profile.isBanned()){
						String reason = requestManager.getParameter(request, Parameter.USER_BLOCK_REASON);
						
						if(!stringManager.isEmpty(reason)){
							User modUser = userService.blockUser(profile, reason, user);
							
							if(modUser != null){
								profile = modUser;
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
							}
							else{								
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
							}						
						}
						else{
							//No recibimos los parámetros correctamente
							error = true;
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}						
					}
					else if(unblockUser && profile.isBanned()){					
						User modUser = userService.unblockUser(profile, user);
						
						if(modUser != null){
							profile = modUser;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
						}
						else{								
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
						}						
					}					
					else if(author == profile.isAuthor() && admin == profile.isAdmin()){
						//No ha cambiado nada
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));
					}
					else if(author != profile.isAuthor() || admin != profile.isAdmin()){						
						User modUser = null;
						
						if(author && !profile.isAuthor()){
							modUser = userService.saveAuthor(profile);
						}
						if(admin && !profile.isAdmin()){
							modUser = userService.saveAdmin(modUser!=null?modUser:profile);
						}
						if(!author && profile.isAuthor()){							
							modUser = userService.removeAuthor(modUser!=null?modUser:profile);
						}
						if(!admin && profile.isAdmin()){
							modUser = userService.removeAdmin(modUser!=null?modUser:profile);
						}
						
						if(modUser != null){
							profile = modUser;
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
						}
						else{								
							setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
						}						
					}
					else{
						//No recibimos los parámetros correctamente
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}								
				}
				
				if(!error){
					List<Page> pageStack = pageService.getAdminUserProfilePageStack(profile);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, ControllerURI.ADMIN_USER_MANAGER.toString());
					
					requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
					request.getRequestDispatcher("/WEB-INF/views/preferences.jsp").forward(request, response);
				}
			}
			else{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}
