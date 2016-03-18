package com.julvez.pfc.teachonsnap.it.page;

import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.ut.page.PageServiceTest;

public class PageServiceIT extends PageServiceTest {

	@Override
	protected PageService getService() {		
		return PageServiceFactory.getService();
	}


	@Override
	public void testGetAdminUsersPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/users/";
		super.testGetAdminUsersPageStack();
		URL = urlTemp;
	}

	@Override
	public void testGetAdminUsersSearchPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/users/";
		URL2 = "https://localhost/admin/users/?searchQuery=search&searchType=email";
		URL3 = "https://localhost/admin/users/?searchQuery=search&searchType=name";
		super.testGetAdminUsersSearchPageStack();
		URL = URL2 = URL3 = urlTemp;
	}

	@Override
	public void testGetAdminUserProfilePageStack() {
		String urlTemp = URL;
		URL = URL2 = "https://localhost/admin/users/";
		super.testGetAdminUserProfilePageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetAdminGroupsSearchPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/groups/?searchQuery=search";
		super.testGetAdminGroupsSearchPageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetAdminGroupsPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		super.testGetAdminGroupsPageStack();
		URL = urlTemp;
	}

	@Override
	public void testGetAdminGroupProfilePageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		super.testGetAdminGroupProfilePageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetAdminGroupFollowPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/group/follow/0/";
		super.testGetAdminGroupFollowPageStack();
		URL = URL2 = URL3 = urlTemp;
	}

	@Override
	public void testGetAdminGroupFollowAuthorPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/group/follow/0/";
		URL4 = "https://localhost/admin/group/follow/author/0/";
		super.testGetAdminGroupFollowAuthorPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetAdminGroupFollowAuthorSearchPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/group/follow/0/";
		URL4 = "https://localhost/admin/group/follow/author/0/";
		URL5 = "https://localhost/admin/group/follow/author/0/?searchQuery=search&searchType=email";
		URL6 = "https://localhost/admin/group/follow/author/0/?searchQuery=search&searchType=name";
		super.testGetAdminGroupFollowAuthorSearchPageStack();
		URL = URL2 = URL3 = URL4 = URL5 = URL6 = urlTemp;
	}

	@Override
	public void testGetAdminGroupFollowTagPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/group/follow/0/";
		URL4 = "https://localhost/admin/group/follow/tag/0/";
		super.testGetAdminGroupFollowTagPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp; 
	}

	@Override
	public void testGetAdminGroupFollowTagSearchPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/group/follow/0/";
		URL4 = "https://localhost/admin/group/follow/tag/0/";
		URL5 = "https://localhost/admin/group/follow/tag/0/?searchQuery=search";
		super.testGetAdminGroupFollowTagSearchPageStack();
		URL = URL2 = URL3 = URL4 = URL5 = urlTemp;
	}

	@Override
	public void testGetUserFollowPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/follow/0/";
		super.testGetUserFollowPageStack();
		URL = urlTemp;
	}

	@Override
	public void testGetUserFollowAuthorPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/follow/0/";
		URL2 = "https://localhost/follow/author/0/";
		super.testGetUserFollowAuthorPageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetUserFollowAuthorSearchPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/follow/0/";
		URL2 = "https://localhost/follow/author/0/";
		URL3 = "https://localhost/follow/author/0/?searchQuery=search&searchType=email";
		URL4 = "https://localhost/follow/author/0/?searchQuery=search&searchType=name";
		super.testGetUserFollowAuthorSearchPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetStatsTestPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/stats/test/0";
		super.testGetStatsTestPageStack();
		URL = urlTemp;
	}

	@Override
	public void testGetStatsLessonPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/stats/lesson/month/0";
		URL2 = "https://localhost/stats/lesson/year/0";
		super.testGetStatsLessonPageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetStatsAuthorPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/stats/author/month/0";
		URL2 = "https://localhost/stats/author/year/0";
		super.testGetStatsAuthorPageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetStatsAuthorLessonPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/stats/author/month/0";
		URL2 = "https://localhost/stats/author/lesson/month/0";
		URL3 = "https://localhost/stats/author/year/0";
		URL4 = "https://localhost/stats/author/lesson/year/0";
		super.testGetStatsAuthorLessonPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetStatsAuthorLessonTestPageStack() {
		String urlTemp = URL;		
		URL = "https://localhost/stats/author/month/0";
		URL2 = "https://localhost/stats/author/lesson/month/0";
		URL3 = "https://localhost/stats/author/lesson/test/0";		
		super.testGetStatsAuthorLessonTestPageStack();
		URL = URL2 = URL3 = urlTemp;
	}

	@Override
	public void testGetStatsLessonTestPageStack() {
		String urlTemp = URL;		
		URL3 = "https://localhost/stats/lesson/month/0";
		URL4 = "https://localhost/stats/lesson/test/0";
		super.testGetStatsLessonTestPageStack();
		URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetAdminStatsPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/admin/stats/year/";
		super.testGetAdminStatsPageStack();
		URL = URL2 = urlTemp;
	}

	@Override
	public void testGetStatsAdminAuthorLessonTestPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/stats/admin/author/month/0";
		URL3 = "https://localhost/stats/admin/author/lesson/month/0";
		URL4 = "https://localhost/stats/admin/author/lesson/test/0";
		super.testGetStatsAdminAuthorLessonTestPageStack();
		URL = URL2 = URL3 = URL4 =urlTemp;
	}

	@Override
	public void testGetStatsAdminLessonTestPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/stats/admin/lesson/month/0";
		URL3 = "https://localhost/stats/admin/lesson/test/0";
		super.testGetStatsAdminLessonTestPageStack();
		URL = URL2 = URL3 = urlTemp;
	}

	@Override
	public void testGetStatsAdminAuthorPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/stats/admin/author/month/0";
		URL3 = "https://localhost/admin/stats/year/";
		URL4 = "https://localhost/stats/admin/author/year/0";
		super.testGetStatsAdminAuthorPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetStatsAdminAuthorLessonPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/stats/admin/author/month/0";
		URL3 = "https://localhost/stats/admin/author/lesson/month/0";
		URL4 = "https://localhost/admin/stats/year/";
		URL5 = "https://localhost/stats/admin/author/year/0";
		URL6 = "https://localhost/stats/admin/author/lesson/year/0";
		super.testGetStatsAdminAuthorLessonPageStack();
		URL = URL2 = URL3 = URL4 = URL5 = URL6 = urlTemp;
	}

	@Override
	public void testGetStatsAdminLessonPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/stats/month/";
		URL2 = "https://localhost/stats/admin/lesson/month/0";
		URL3 = "https://localhost/admin/stats/year/";
		URL4 = "https://localhost/stats/admin/lesson/year/0";
		super.testGetStatsAdminLessonPageStack();
		URL = URL2 = URL3 = URL4 = urlTemp;
	}

	@Override
	public void testGetAdminBroadcastGroupPageStack() {
		String urlTemp = URL;
		URL = "https://localhost/admin/groups/";
		URL2 = "https://localhost/admin/group/0/";
		URL3 = "https://localhost/admin/broadcast/0/";
		super.testGetAdminBroadcastGroupPageStack();
		URL = URL2 = URL3 = urlTemp;
	}
}
