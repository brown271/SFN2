package ca.sheridancollege.fourothreeindustries.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.sheridancollege.fourothreeindustries.domain.*;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;

class GroupServiceTest {
	
	public GroupService gs = new GroupService();
	
	@Test
	void testValidateEmailGroupException() {
		EmailGroup test = new EmailGroup(1l,"TEST GROUP","", null, null, null);
		assertThrows(InputMismatchException.class, () -> gs.validateEmailGroup(test));
	}
	
	@Test
	void testValidateEmailGroupGood() {
		SpecialFriend s1 = new SpecialFriend();
		Account acc = new Account();
		Role role = new Role();
		List<SpecialFriend> friends = new ArrayList<SpecialFriend>();
		List<Account> accounts = new ArrayList<Account>();
		List<Role> roles = new ArrayList<Role>();
		friends.add(s1);
		accounts.add(acc);
		roles.add(role);
		EmailGroup test = new EmailGroup(1l,"TEST GROUP","TEST DESCRIPTION", friends, accounts, roles);
		assertDoesNotThrow( () -> gs.validateEmailGroup(test));
	}
	
	@Test
	void testValidateEmailGroupBoundary() {
		SpecialFriend s1 = new SpecialFriend();
		Account acc = new Account();
		Role role = new Role();
		List<SpecialFriend> friends = new ArrayList<SpecialFriend>();
		List<Account> accounts = new ArrayList<Account>();
		List<Role> roles = new ArrayList<Role>();
		friends.add(s1);
		accounts.add(acc);
		roles.add(role);
		EmailGroup test = new EmailGroup(1l,"      ","                         ", friends, accounts, roles);
		assertThrows(InputMismatchException.class, () -> gs.validateEmailGroup(test));
	}

	@Test
	void testIsGroupNameValidGood() {
		assertTrue(gs.isGroupNameValid("403 Industries"));
	}
	
	@Test
	void testIsGroupNameValidBoundary() {
		assertFalse(gs.isGroupNameValid("               "));
	}
	
	@Test
	void testIsGroupNameValidException() {
		assertFalse(gs.isGroupNameValid(""));
	}

	@Test
	void testIsGroupDescriptionValidGood() {
		assertTrue(gs.isGroupDescriptionValid("SOME BASIC DESCRIPTION"));
	}
	
	@Test
	void testIsGroupDescriptionValidBoundary() {
		assertFalse(gs.isGroupDescriptionValid("               "));
	}
	
	@Test
	void testIsGroupDescriptionValidException() {
		assertFalse(gs.isGroupDescriptionValid(""));
	}

	@Test
	void testIsGroupAccountsAndSpecialFriendsValidGood() {
		List<SpecialFriend> friends = new ArrayList<SpecialFriend>();
		List<Account> accounts = new ArrayList<Account>();
		SpecialFriend s1 = new SpecialFriend();
		Account acc = new Account();
		friends.add(s1);
		accounts.add(acc);
		assertTrue(gs.isGroupAccountsAndSpecialFriendsValid(accounts,friends));
	}
	
	@Test
	void testIsGroupAccountsAndSpecialFriendsValidBoundary() {
		List<SpecialFriend> friends = new ArrayList<SpecialFriend>();
		List<Account> accounts = null;
		SpecialFriend s1 = new SpecialFriend();
		Account acc = new Account();
		friends.add(s1);
		assertFalse(gs.isGroupAccountsAndSpecialFriendsValid(accounts,friends));
	}
	
	@Test
	void testIsGroupAccountsAndSpecialFriendsValidException() {
		List<SpecialFriend> friends = new ArrayList<SpecialFriend>();
		List<Account> accounts =  new ArrayList<Account>();
		SpecialFriend s1 = new SpecialFriend();
		Account acc = new Account();
		friends.add(s1);
		assertFalse(gs.isGroupAccountsAndSpecialFriendsValid(accounts,friends));
	}
/*
	@Test
	void testIsGroupRolesValid() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupId() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupDescription() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupName() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupRoles() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupAccounts() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONIntoEmailGroupSpecialFriends() {
		fail("Not yet implemented");
	}

	@Test
	void testParseJSONStringIntoEmailGroup() {
		fail("Not yet implemented");
	}
	*/

}
