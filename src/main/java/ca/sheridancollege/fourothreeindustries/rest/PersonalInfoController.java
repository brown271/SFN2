package ca.sheridancollege.fourothreeindustries.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;
import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;

@RestController
@RequestMapping("/api/pi")
public class PersonalInfoController {
	@Autowired
	private SpecialFriendRepository sfr;
	@Autowired
	private AccountRepository acr;
	
	@GetMapping("/searchForPersonalInfo/{searchKey}")
	public String searchForPI(@PathVariable String searchKey) {
		searchKey = searchKey.trim();
		String searchKey2 = "";
		List<Account> accounts;
		List<SpecialFriend> sFriends;
		
		if (searchKey.contains(" ")) {
			searchKey2 = searchKey.substring(searchKey.lastIndexOf(" ")+1);
			searchKey = searchKey.substring(0,searchKey.lastIndexOf(" "));
			
			 accounts = acr.findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
			 sFriends = sfr.findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
		}
		else {
			searchKey2 = searchKey;
			 accounts = acr.findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
			 sFriends = sfr.findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
		}
		
		String message = "{";
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
		
		if (sFriends.size()>0) {
			if( message.length() > 4) {
				message+=",";
			}
			
			message+="\"specialFriends\":[";
			for(int i = 0; i < sFriends.size(); i++) {
				message+=sFriends.get(i).JSONify();
				if (i < sFriends.size()-1 && sFriends.size() > 1) {
					message+=",";
				}
			}
			message+="]";
		}
		
		
		message+="}";
		//System.out.println(message);
		return message;
	}
}
