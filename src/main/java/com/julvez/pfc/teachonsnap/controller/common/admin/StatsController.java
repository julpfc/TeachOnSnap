package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
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
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * StatsController extends {@link AdminController}.
 * <p>
 * Loads last month or year information stats and redirects to the
 * adminStats.jsp view.
 * <p>
 * Manages GET requests from adminStats.jsp view to download CSV files
 * from stats data.  
 * <p> 
 * Mapped in {@link ControllerURI#ADMIN_STATS_MONTH} and
 * {@link ControllerURI#ADMIN_STATS_YEAR}
 */
public class StatsController extends AdminController {

	private static final long serialVersionUID = 8243763181318212982L;
	
	/**
     * Default constructor
     */
    public StatsController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService());        
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
	 */
	public StatsController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				pageService);
	}
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		//Get controller's mapping
		ControllerURI uri = ControllerURI.getURIFromPath(request.getServletPath());
			
		if(uri != null){		
			List<StatsData> stats = null;
			List<StatsData> statsExtra = null;
			List<StatsData> statsExtra2 = null;
			List<Page> pageStack = null;
			StatsType statsType = null;			
			boolean error = false;
			
			//get stat user is asking to export in CSV format
			int exportIndex = requestManager.getNumericParameter(request, Parameter.EXPORT);
			
			//depending on the controller's mapping
			switch (uri) {
				case ADMIN_STATS_MONTH:
					switch(exportIndex){
						case 0:
							//export last month global visits
							stats = statsService.getVisitsLastMonth();
							error = true;
							exportCSV(stats, response);
							break;
						case 1:
							//export last month lessons visits
							statsExtra = statsService.getLessonsVisitsLastMonth();
							error = true;
							exportCSV(statsExtra, response);
							break;
						case 2:
							//export last month author visits
							statsExtra2 = statsService.getAuthorsVisitsLastMonth();
							error = true;
							exportCSV(statsExtra2, response);
							break;
						default:
							//list last month stats
							stats = statsService.getVisitsLastMonth();
							statsExtra = statsService.getLessonsVisitsLastMonth();
							statsExtra2 = statsService.getAuthorsVisitsLastMonth();
							break;
					}					
					statsType = StatsType.MONTH;
					
					break;
				case ADMIN_STATS_YEAR:
					switch(exportIndex){
						case 0:
							//export last year global visits
							stats = statsService.getVisitsLastYear();
							error = true;
							exportCSV(stats, response);
							break;
						case 1:
							//export last year lessons visits
							statsExtra = statsService.getLessonsVisitsLastYear();
							error = true;
							exportCSV(statsExtra, response);
							break;
						case 2:	
							//export last year authors visits
							statsExtra2 = statsService.getAuthorsVisitsLastYear();
							error = true;
							exportCSV(statsExtra2, response);
							break;
						default:
							//list last year stats
							stats = statsService.getVisitsLastYear();
							statsExtra = statsService.getLessonsVisitsLastYear();
							statsExtra2 = statsService.getAuthorsVisitsLastYear();
							break;
					}						
					statsType = StatsType.YEAR;
			
					break;
				default:
					//bad mapping
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					break;
			}

			//if no error and no export -> redirect to the view	
			if(!error){
				//get page common information and store for the view
				pageStack = pageService.getAdminStatsPageStack(statsType);
				requestManager.setAttribute(request, Attribute.STRING_STATS_TYPE, statsType.toString());				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
				requestManager.setAttribute(request, Attribute.LIST_STATS_DATA, stats);
				requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA, statsExtra);
				requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA_2, statsExtra2);
			
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/adminStats.jsp").forward(request, response);
			}				
		}
		else{
			//bad mapping
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}				
	}


	/**
	 * Converts stats information to Comma-separated values (CSV) file
	 * and downloads it.
	 * @param stats to generate CSV from
	 * @param response Response
	 */
	private void exportCSV(List<StatsData> stats, HttpServletResponse response) {
		//get CSV data
		String statsCSV = statsService.getCSVFromStats(stats);
		
		//get input stream to CSV
		InputStream input = new ByteArrayInputStream(statsCSV.getBytes());
		
		//download stream to response
		requestManager.downloadFile(response, "text/csv", "data.csv", input);				
	}	
}
