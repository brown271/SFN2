package ca.sheridancollege.fourothreeindustries.services;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;

@Service
public class AccountService implements UserDetailsService{
	@Autowired 
	private AccountRepository accountRepo;
	@Autowired 
	private PersonalInfoService personalInfoService;
	@Autowired
	private RoleRepository roleRepo;
	
	private int minUsernameLength = 5;
	private int maxUsernameLength = 30;
	private int minPasswordLength = 8;
	private String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (accountRepo.findByUsernameLike(username).size() > 0) {
			return accountRepo.findByUsernameLike(username).get(0);
		}
		else {
			throw new UsernameNotFoundException("Could not find the username: " + username);
			
		}
	}
	
	public boolean isCurrentAccountValid(Account account) {
		String out = "";
		if(!isUsernameValidSize(account.getUsername())) {
			out+="Error: username must be between " + minUsernameLength + " and " + maxUsernameLength + " characters;";
		}
		if (!isPasswordValid(account.getPassword())) {
			out+="Error: password must be atelast "  + minPasswordLength + " and contain;";
		}
		try {
			personalInfoService.isPersonalInfoValid(account.getPersonalInfo());
		}catch(InputMismatchException e){
			out+=e.getMessage();
		}
		if(!isAccountRoleValid(account.getRole())) {
			out+="Error: user must have a role;";
			
		}
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public boolean isNewAccountValid(Account account) {
		String out = "";
		if(!isUsernameValidSize(account.getUsername())) {
			out+="Error: username must be between " + minUsernameLength + " and " + maxUsernameLength + " characters;";
		}
		if(!isUsernameUnique(account.getUsername())) {
			out+="Error: username must be unique, " + account.getUsername() + " is already taken;";
		}
		if (!isPasswordValid(account.getPassword())) {
			out+="Error: password must be atelast "  + minPasswordLength + " and contain 1 uppercase, 1 lowercase, 1 number and 1 special character;";
		}
		try {
			personalInfoService.isPersonalInfoValid(account.getPersonalInfo());
		}catch(InputMismatchException e){
			out+=e.getMessage();
		}
		if(!isAccountRoleValid(account.getRole())) {
			out+="Error: user must have a role;";
			
		}
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
		
	}
	
	public boolean isUsernameValidSize(String username) {
		if(username == null) {
			return false;
		}
		username = username.replace(" ", "");
		if(username.length() > maxUsernameLength || username.length() < minUsernameLength) {
			return false;
		}
		return true;
	}
	
	public boolean isUsernameUnique(String username) {
		if (username == null) {
			return false;
		}
		username = username.replace(" ", "");
		if(accountRepo.findByUsernameLike(username).size()>0) {
			return false;
		}
		return true;
	}
	
	public boolean isPasswordValid(String password) {
		if(password == null) {
			return false;
		}
		password = password.replace(" ", "");
		if( password.length() < minPasswordLength) {
			return false;
		}
		if(!password.matches(passwordRegex)) {
			return false;
		}
		return true;
		
	}
	
	public boolean isAccountRoleValid(Role role) {
		if (role == null) {
			return false;
		}

		return true;
	}
	
	public Account findAccountById(Long id) {
		return accountRepo.findById(id).get();
	}
	
	//public static 

}
