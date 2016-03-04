package com.julvez.pfc.teachonsnap.ut.notify;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class NotifyServiceTest extends ServiceTest<NotifyService> {

	protected String email = "name@teachonsnap.com";
	protected String message = "Notification message";
	protected String subject = "Notification subject";
	
	@Test
	public void testInfoUserString() {
		User user = mock(User.class);
		when(user.getEmail()).thenReturn(email);
		
		assertFalse(test.info(null, EMPTY_STRING));
		assertTrue(test.info(user, message));
	}

	@Test
	public void testInfoUserStringString() {
		User user = mock(User.class);
		when(user.getEmail()).thenReturn(email);
		
		assertFalse(test.info(null, null, EMPTY_STRING));
		assertTrue(test.info(user, subject, message));
	}

	@Test
	public void testBroadcast() {
		User user = mock(User.class);
		when(user.getEmail()).thenReturn(email);
		
		List<User> users = new ArrayList<User>();
		
		assertFalse(test.broadcast(null, null, EMPTY_STRING));
		assertFalse(test.broadcast(users, subject, message));
		
		users.add(user);
		assertTrue(test.broadcast(users, subject, message));
	}
}
