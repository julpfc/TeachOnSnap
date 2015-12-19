package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

public class StatsController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private StatsService statsService = StatsServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		ControllerURI uri = ControllerURI.getURIFromPath(request.getServletPath());
			
		if(uri != null){
		
			List<StatsData> stats = null;
			List<StatsData> statsExtra = null;
			List<StatsData> statsExtra2 = null;
			List<Page> pageStack = null;
			StatsType statsType = null;			
			boolean error = false;
				
			switch (uri) {
				case ADMIN_STATS_MONTH:
					stats = statsService.getVisitsLastMonth();
					statsExtra = statsService.getLessonsVisitsLastMonth();
					statsExtra2 = statsService.getAuthorsVisitsLastMonth();
					statsType = StatsType.MONTH;
					
					break;
				case ADMIN_STATS_YEAR:
					stats = statsService.getVisitsLastYear();
					statsExtra = statsService.getLessonsVisitsLastYear();
					statsExtra2 = statsService.getAuthorsVisitsLastYear();
					statsType = StatsType.YEAR;
			
					break;
				default:
					error = true;
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					break;
			}

				
			if(!error){
				pageStack = pageService.getAdminStatsPageStack(statsType);

				String statsCSV = statsService.getCSVFromStats(stats);
				String statsExtraCSV = statsService.getCSVFromStats(statsExtra);
				String statsExtra2CSV = statsService.getCSVFromStats(statsExtra2);

				requestManager.setAttribute(request, Attribute.STRING_STATS_TYPE, statsType.toString());				
				requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
				requestManager.setAttribute(request, Attribute.LIST_STATS_DATA, stats);
				requestManager.setAttribute(request, Attribute.STRING_STATS_CSV, statsCSV);
				requestManager.setAttribute(request, Attribute.STRING_STATS_EXTRA_CSV, statsExtraCSV);
				requestManager.setAttribute(request, Attribute.STRING_STATS_EXTRA_2_CSV, statsExtra2CSV);
				requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA, statsExtra);
				requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA_2, statsExtra2);
			
				request.getRequestDispatcher("/WEB-INF/views/adminStats.jsp").forward(request, response);
			}
				
			}
			else{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
				
	}
}
