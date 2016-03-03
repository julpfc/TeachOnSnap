package com.julvez.pfc.teachonsnap.ut.manager.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class DBManagerTest extends ManagerTest<DBManager> {

	@Test
	public void testGetQueryResultList() {
		assertNull(test.getQueryResultList(NULL_STRING, null));
		assertNull(test.getQueryResultList(EMPTY_STRING, null));
		assertNull(test.getQueryResultList(BLANK_STRING, null));
		
		List<Integer> list = test.getQueryResultList("SQL_1234", Integer.class);
		assertNotNull(list);
		
		int i = 1;
		for(int element:list){
			assertEquals(i++, element);
		}		
	}

	@Test
	public void testGetQueryResultUnique() {
		assertNull(test.getQueryResultUnique(NULL_STRING, null));
		assertNull(test.getQueryResultUnique(EMPTY_STRING, null));
		assertNull(test.getQueryResultUnique(BLANK_STRING, null));
		
		assertEquals(1, (int)test.getQueryResultUnique("SQL_1", Integer.class));
	}

	@Test
	public void testUpdateQueries() {
		assertEquals(0,test.updateQuery("SQL_DROP_TABLE"));
		assertEquals(0,test.updateQuery("SQL_CREATE_TABLE"));
		
		assertNull(test.getQueryResultUnique("SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.insertQueryAndGetLastInserID("SQL_INSERT_TABLE", 0));
		
		assertEquals(0, (int)test.getQueryResultUnique("SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.updateQuery("SQL_UPDATE_TABLE", 2,1));
		
		assertEquals(2, (int)test.getQueryResultUnique("SQL_SELECT_TABLE", Integer.class, 1));
	}

	@Test
	public void testGetQueryResultList_NoCommit() {
		assertNull(test.getQueryResultList_NoCommit(null, NULL_STRING, null));
		assertNull(test.getQueryResultList_NoCommit(null, EMPTY_STRING, null));
		assertNull(test.getQueryResultList_NoCommit(null, BLANK_STRING, null));
		
		Object session = test.beginTransaction();
		assertNotNull(session);
		
		List<Integer> list = test.getQueryResultList_NoCommit(session, "SQL_1234", Integer.class);
		assertNotNull(list);
		
		test.endTransaction(true, session);
		
		int i = 1;
		for(int element:list){
			assertEquals(i++, element);
		}		
	}

	@Test
	public void testGetQueryResultUnique_NoCommit() {
		assertNull(test.getQueryResultUnique_NoCommit(null, NULL_STRING, null));
		assertNull(test.getQueryResultUnique_NoCommit(null, EMPTY_STRING, null));
		assertNull(test.getQueryResultUnique_NoCommit(null, BLANK_STRING, null));
		
		Object session = test.beginTransaction();
		assertNotNull(session);

		assertEquals(1, (int)test.getQueryResultUnique_NoCommit(session, "SQL_1", Integer.class));
		
		test.endTransaction(true, session);
	}

	@Test
	public void testUpdateQueries_NoCommit() {
		assertEquals(0,test.updateQuery("SQL_DROP_TABLE"));
		Object session = test.beginTransaction();
		assertNotNull(session);
		
		assertEquals(0,test.updateQuery_NoCommit(session, "SQL_CREATE_TABLE"));
		
		assertNull(test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.insertQueryAndGetLastInserID_NoCommit(session, "SQL_INSERT_TABLE", 0));
		
		assertEquals(0, (int)test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.updateQuery_NoCommit(session, "SQL_UPDATE_TABLE", 2,1));
		
		assertEquals(2, (int)test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		test.endTransaction(false, session);
		
		assertNull(test.getQueryResultUnique("SQL_SELECT_TABLE", Integer.class, 1));

		assertEquals(0,test.updateQuery("SQL_DROP_TABLE"));
		session = test.beginTransaction();
		assertNotNull(session);
		
		assertEquals(0,test.updateQuery_NoCommit(session, "SQL_CREATE_TABLE"));
		
		assertNull(test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.insertQueryAndGetLastInserID_NoCommit(session, "SQL_INSERT_TABLE", 0));
		
		assertEquals(0, (int)test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		assertEquals(1, test.updateQuery_NoCommit(session, "SQL_UPDATE_TABLE", 2,1));
		
		assertEquals(2, (int)test.getQueryResultUnique_NoCommit(session, "SQL_SELECT_TABLE", Integer.class, 1));
		
		test.endTransaction(true, session);
		
		assertEquals(2, (int)test.getQueryResultUnique("SQL_SELECT_TABLE", Integer.class, 1));
	}
	
	@Test
	public void testClose(){
		test.close();
		assertNull(test.getQueryResultUnique("SQL_1", Integer.class));
	}
}
