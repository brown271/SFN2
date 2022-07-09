package ca.sheridancollege.fourothreeindustries.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;

@Service
public class AccountService implements UserDetailsService{
	@Autowired 
	private AccountRepository ar;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (ar.findByUsernameLike(username).size() > 0) {
			return ar.findByUsernameLike(username).get(0);
		}
		else {
			throw new UsernameNotFoundException("Could not find the username: " + username);
			
		}
	}

}
