package ca.sheridancollege.fourothreeindustries.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.Role;
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
		assertDoesNotThrow( () -> gs.validateEmailGroup(test));
	}

}
