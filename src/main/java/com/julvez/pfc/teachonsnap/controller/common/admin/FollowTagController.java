package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.group.UserGroupService;
import com.julvez.pfc.teachonsnap.user.group.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.User;

public class FollowTagController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private PageService pageService = PageServiceFactory.getService();
	private UserGroupService groupService = UserGroupServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	private final int MAX_RESULTS_PAGE = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);

	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length >= 1 && stringManager.isNumeric(params[0]) && params.length <= 2){
			if(params.length == 2 && stringManager.isNumeric(params[1])){	
				pageResult = Integer.parseInt(params[1]);
			}
			
			short idUserGroup = Short.parseShort(params[0]);
			
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			if(profile != null){
			
				boolean error = false;
	
				int idFollowTag = requestManager.getNumericParameter(request, Parameter.FOLLOW_TAG);
				
				if(idFollowTag > 0){
					error = true;
				
					Tag followTag = tagService.getTag(idFollowTag);
					
					if(followTag != null){
						UserGroup modGroup = groupService.followTag(profile, followTag);
						
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				int idUnFollowTag = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_TAG);
				
				if(idUnFollowTag > 0){
					error = true;
				
					Tag unfollowTag = tagService.getTag(idUnFollowTag);
					
					if(unfollowTag != null){
						UserGroup modGroup = groupService.unfollowTag(profile, unfollowTag);
						
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				if(!error){
					String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);					
					
					if(stringManager.isEmpty(searchQuery)){						
						searchQuery = null;						
					}						
						
					boolean hasNextPage = false;
										
					List<TagFollowed> followTags = null;
					List<Tag> tags = null;
					List<Tag> tagFollowings = groupService.getTagFollowings(profile);
					
					if(searchQuery != null){
						tags = tagService.searchTag(searchQuery, pageResult);
					}					
					else{
						tags = tagService.getTags(pageResult);	
					}
					
					followTags = tagService.getTagsFollowed(tags, tagFollowings);
					
					if(followTags!= null && followTags.size()>MAX_RESULTS_PAGE){
						hasNextPage = true;
						followTags.remove(MAX_RESULTS_PAGE);
					}
					
					String nextPage = null;
					if(hasNextPage){
						nextPage = request.getServletPath()+"/"+profile.getId()+"/"+(pageResult+MAX_RESULTS_PAGE);
						if(searchQuery != null){
							nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
						}
					}
					
					String prevPage = null;
					if(pageResult>0){				
						prevPage = request.getServletPath()+"/"+profile.getId()+"/";
						
						if(pageResult>MAX_RESULTS_PAGE){
							prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
						}
						
						if(searchQuery != null){
							prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
						}
					}
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
					
					String backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId());
					if(searchQuery != null){
						backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId());
					}
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					
					requestManager.setAttribute(request, Attribute.LIST_TAG_FOLLOWED, followTags);
					
					List<Page> pageStack = pageService.getAdminGroupFollowTagSearchPageStack(profile, searchQuery);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
		
					
					request.getRequestDispatcher("/WEB-INF/views/followTag.jsp").forward(request, response);
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
