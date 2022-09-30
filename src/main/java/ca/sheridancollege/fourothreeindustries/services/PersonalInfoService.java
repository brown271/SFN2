package ca.sheridancollege.fourothreeindustries.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;

@Service
public class PersonalInfoService {

	@Autowired
	private AccountRepository acr;
	@Autowired
	private SpecialFriendRepository sfr;
	
	public String compileListOfFriendsAndAccountsIntoJSONString(List<Account> accounts, List<SpecialFriend> specialFriends) {
		String message = "{";
		if (accounts == null && specialFriends == null) {
			return "{\"Message\":\"Special Friends and Accounts were null, cannot JSONify\"}";
		}
		if (accounts.size() + specialFriends.size() == 0) {
			return "{\"Message\":\"There were no SpecialFriends or Accounts, cannot JSONify\"}";
		}
		if (accounts.size()> 0 ) {
			message+="\"SFNAccounts\":[";
			for(int i = 0; i < accounts.size(); i++) {
				message+=accounts.get(i).JSONify();
				if (i < accounts.size()-1 && accounts.size() > 1) {
					message+=",";
				}
			}
			message+="]";
		}
		if (specialFriends.size()>0) {
			//if accounts were above, add a comma to split the JSON data
			if( accounts.size() > 0) {
				message+=",";
			}
			message+="\"specialFriends\":[";
			for(int i = 0; i < specialFriends.size(); i++) {
				message+=specialFriends.get(i).JSONify();
				if (i < specialFriends.size()-1 && specialFriends.size() > 1) {
					message+=",";
				}
			}
			message+="]";
		}
		message+="}";
		return message;
	}
}
