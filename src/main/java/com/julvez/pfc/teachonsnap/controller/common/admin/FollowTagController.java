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
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
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
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * FollowTagController extends {@link AdminController}.
 * <p>
 * Lists followed tags by the group to be unfollowed, and lists all tags to be followed.
 * It also allows to search tags, and paginate results in all cases.  
 * <p>
 * Manages GET requests to un/follow tags and searches from followTag.jsp view.
 * <p>
 * Loads inforamtion about a group, followers and tags and redirects to the followTag.jsp
 * view to select tag to be un/followed.
 * <p>
 * Mapped in {@link ControllerURI#ADMIN_GROUP_FOLLOW_TAG}
 */
public class FollowTagController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	/** Pagination. Limit of tags per cloud. */
	private final int MAX_TAGS_PER_CLOUD;

	/** Provides the functionality to work with application's group of users. */
	private UserGroupService groupService;
	
	/** Provides the functionality to work with tags */
	private TagService tagService;

	/**
     * Default constructor
     */
    public FollowTagController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService(),
        	UserGroupServiceFactory.getService(),
        	TagServiceFactory.getService());        
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
	 * @param groupService Provides the functionality to work with application's group of users.
	 * @param tagService Provides the functionality to work with tags
	 */
	public FollowTagController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService,
			UserGroupService groupService, TagService tagService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				pageService);
		
		if(groupService == null || tagService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.groupService = groupService;		
		this.tagService = tagService;		
		MAX_TAGS_PER_CLOUD = (int)properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
				
		int pageResult = 0;
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length >= 1 && stringManager.isNumeric(params[0]) && params.length <= 2){
			
			//get page number
			if(params.length == 2 && stringManager.isNumeric(params[1])){	
				pageResult = Integer.parseInt(params[1]);
			}
			
			//get idGroup from params
			short idUserGroup = Short.parseShort(params[0]);
			
			//get group from id
			UserGroup profile = groupService.getGroup(idUserGroup);
			
			//if group found...
			if(profile != null){			
				boolean error = false;
	
				//get tag from id
				int idFollowTag = requestManager.getNumericParameter(request, Parameter.FOLLOW_TAG);
				
				//if tag found...
				if(idFollowTag > 0){
					error = true;
				
					//get tag from id
					Tag followTag = tagService.getTag(idFollowTag);
									
					if(followTag != null){
						//follow tag
						UserGroup modGroup = groupService.followTag(profile, followTag);
						
						//check result and redirect to previous page
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						//tag not found
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				int idUnFollowTag = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_TAG);
				
				//if tag found...
				if(idUnFollowTag > 0){
					error = true;
				
					//get tag from id
					Tag unfollowTag = tagService.getTag(idUnFollowTag);
					
					if(unfollowTag != null){
						//unfollow tag
						UserGroup modGroup = groupService.unfollowTag(profile, unfollowTag);
						
						//check result and redirect to previous page
						if(modGroup != null){
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.GROUP_SAVED);
						}
						else{
							setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
						}		
						response.sendRedirect(urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId()));
					}
					else{
						//tag not found
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				
				//if no error and no previous parameters processed
				if(!error){
					//get search parameters
					String searchQuery = requestManager.getParameter(request, Parameter.SEARCH_QUERY);					
					
					//verify search parameters
					if(stringManager.isEmpty(searchQuery)){						
						searchQuery = null;						
					}						
						
					boolean hasNextPage = false;										
					List<TagFollowed> followTags = null;
					List<Tag> tags = null;
					
					//get tags followed by the group
					List<Tag> tagFollowings = groupService.getTagFollowings(profile);
					
					//get search result tags or all tags
					if(searchQuery != null){
						tags = tagService.searchTag(searchQuery, pageResult);
					}					
					else{
						tags = tagService.getTags(pageResult);	
					}
					
					//Mark tags followed on list
					followTags = tagService.getTagsFollowed(tags, tagFollowings);
					
					//check if is there a next page
					if(followTags!= null && followTags.size()>MAX_TAGS_PER_CLOUD){
						hasNextPage = true;
						followTags.remove(MAX_TAGS_PER_CLOUD);
					}
					
					//get next page link
					String nextPage = null;
					if(hasNextPage){
						nextPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/" + (pageResult+MAX_TAGS_PER_CLOUD));
						if(searchQuery != null){
							nextPage = nextPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
						}
					}
					
					//get previous page link
					String prevPage = null;
					if(pageResult>0){				
						prevPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/");
						
						if(pageResult>MAX_TAGS_PER_CLOUD){
							prevPage = prevPage + (pageResult-MAX_TAGS_PER_CLOUD);
						}
						
						if(searchQuery != null){
							prevPage = prevPage + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery;
						}
					}
					//store next/prev page links to request for view
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
					//get page common information and store for the view
					String backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId());
					if(searchQuery != null){
						backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId());
					}
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					requestManager.setAttribute(request, Attribute.LIST_TAG_FOLLOWED, followTags);
					List<Page> pageStack = pageService.getAdminGroupFollowTagSearchPageStack(profile, searchQuery);				
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
		
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/followTag.jsp").forward(request, response);
				}
			}
			else{
				//group not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}		
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
