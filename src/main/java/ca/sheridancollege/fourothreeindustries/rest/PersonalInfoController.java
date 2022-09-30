package ca.sheridancollege.fourothreeindustries.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;
import ca.sheridancollege.fourothreeindustries.services.GroupService;
import ca.sheridancollege.fourothreeindustries.services.PersonalInfoService;
import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;

@RestController
@RequestMapping("/api/pi")
public class PersonalInfoController {
	@Autowired
	private SpecialFriendRepository sfr;
	@Autowired
	private AccountRepository acr;
	@Autowired
	private PersonalInfoService pIs;
	
	@CrossOrigin()
	@GetMapping("/searchForPersonalInfo/{searchKey}")
	public String searchForPI(@PathVariable String searchKey) {
		searchKey = searchKey.trim();
		 //second search key to allow for more advanced searching
		//takes everything after a space to allow searching of first or last name
		String searchKey2 = "";
		//search result holders
		List<Account> accounts;
		List<SpecialFriend> sFriends;
		//if there is a space 
		if (searchKey.contains(" ")) {
			//populate the second search key with everything after the space
			searchKey2 = searchKey.substring(searchKey.lastIndexOf(" ")+1);
			searchKey = searchKey.substring(0,searchKey.lastIndexOf(" "));
			
			 accounts = acr.findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
			 sFriends = sfr.findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
		}
		else {
			//searchkey two is search key one
			searchKey2 = searchKey;
			 accounts = acr.findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
			 sFriends = sfr.findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(searchKey, searchKey2);
		}
		
		
		//System.out.println(message);
		return pIs.compileListOfFriendsAndAccountsIntoJSONString(accounts, sFriends);
	}
	
	
}
