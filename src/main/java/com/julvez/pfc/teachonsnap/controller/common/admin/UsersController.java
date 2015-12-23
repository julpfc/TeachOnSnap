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
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.SearchType;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

public class UsersController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private PageService pageService = PageServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	private final int MAX_RESULTS_PAGE = (int)properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		//Lista los usuarios, recibe /num_pagina
		
		int pageResult = 0;
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params == null || (params!=null && params.length == 1 && stringManager.isNumeric(params[0]))){
			if(params != null){	
				pageResult = Integer.parseInt(params[0]);
			}
			
			boolean error = false;

			if(request.getMethod() == "POST"){
				String email = requestManager.getParameter(request,Parameter.LOGIN_EMAIL_REGISTER);

				// Si se quiere registrar ...
				if(email != null){
					
					User newUser = userService.getUserFromEmail(email);
											
					if(newUser != null){
						//Ya estaba registrado
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.REGISTER_EMAIL_DUPLICATE));
					}
					else {
						String firstname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.FIRST_NAME));
						String lastname = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LAST_NAME));
						short idLang = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
						
						Language userLang = langService.getLanguage(idLang);
						
						if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname) && userLang != null){
							
							boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
							
							if(sendEmail){							
								userService.sendRegister(email, firstname, lastname, userLang);
								newUser = userService.getUserFromEmail(email);									
							}
							else{
								newUser = userService.createUser(email, firstname, lastname, userLang);								
							}
							
							if(newUser != null){
								boolean author = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_AUTHOR);
								boolean admin = requestManager.getBooleanParameter(request, Parameter.USER_ROLE_ADMIN);
								
								if(author){
									userService.saveAuthor(newUser);
								}
								if(admin){
									userService.saveAdmin(newUser);
								}

								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED));
								
							}
							else{
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
							}
						}
						else{
							// No llegan parámetros obligatorios
							error = true;
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}
					}
				}
				else{
					
					List<String> emails = stringManager.split(requestManager.getParameter(request, Parameter.REGISTER_MULTIPLE_EMAILS),",");
					
					if(emails != null){
						User newUser = null;
						short idLang = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);						
						Language userLang = langService.getLanguage(idLang);
						
						if(userLang != null){
							
							boolean sendEmail = requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL);
							
							for(String mail:emails){
								
								if(sendEmail){							
									userService.sendRegister(mail, "", "", userLang);
								}
								else{
									userService.createUser(mail, "", "", userLang);									
								}
								
								newUser = userService.getUserFromEmail(mail);									
								
								if(newUser == null){
									error = true;
									setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
									break;
								}
							}
							if(!error){
								setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERS_CREATED));
							}
							else error = false;
						}
						else{
							// No llegan parámetros obligatorios
							error = true;
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}						
					}					
				}
			}
			
			if(!error){
				String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);
				String searchType = requestManager.getParameter(request, Parameter.SEARCH_TYPE);
				
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
				
				if(searchType != null && SearchType.EMAIL.equals(searchType)){
					users = userService.searchUsersByEmail(searchQuery, pageResult);
				}
				else if(searchType != null && SearchType.NAME.equals(searchType)){
					users = userService.searchUsersByName(searchQuery, pageResult);
				}
				else{
					users = userService.getUsers(pageResult);	
				}
				
				
				if(users!= null && users.size()>MAX_RESULTS_PAGE){
					hasNextPage = true;
					users.remove(MAX_RESULTS_PAGE);
				}
				
				String nextPage = null;
				if(hasNextPage){
					nextPage = request.getServletPath()+"/"+(pageResult+MAX_RESULTS_PAGE);
					if(searchType != null){
						nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
					}
				}
				
				String prevPage = null;
				if(pageResult>0){				
					prevPage = request.getServletPath()+"/";
					
					if(pageResult>MAX_RESULTS_PAGE){
						prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
					}
					
					if(searchType != null){
						prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType;
					}
				}
				requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
				requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
				
				requestManager.setAttribute(request, Attribute.LIST_USER, users);
				
				List<Page> pageStack = pageService.getAdminUsersSearchPageStack(searchQuery, searchType);				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
	
				
				request.getRequestDispatcher("/WEB-INF/views/adminUsers.jsp").forward(request, response);
			}
		
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}		

	}
}
