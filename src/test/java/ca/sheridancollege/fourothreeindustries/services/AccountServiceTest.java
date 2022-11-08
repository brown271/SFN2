package ca.sheridancollege.fourothreeindustries.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountServiceTest {
	
	private AccountService accountService = new AccountService();

	@Test
	void testIsUsernameValidSizeGood() {
		assertTrue(accountService.isUsernameValidSize("testnametestname"));
	}
	@Test
	void testIsUsernameValidSizeBad() {
		assertFalse(accountService.isUsernameValidSize("te"));
	}
	@Test
	void testIsUsernameValidSizeBoundary() {
		assertFalse(accountService.isUsernameValidSize(" ur     ne "));
	}
	
	@Test
	void testIsPasswordValidGood() {
		assertTrue(accountService.isPasswordValid("eHJ12?2ssawaL"));
	}
	@Test
	void testIsPasswordValidBad() {
		assertFalse(accountService.isPasswordValid("e"));
	}
	@Test
	void testIsPasswordValidBoundary() {
		assertFalse(accountService.isPasswordValid("pas      word"));
	}


}
