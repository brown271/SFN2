package ca.sheridancollege.fourothreeindustries.services;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;

@Service
public class GroupService {
	
	@Autowired
	private EmailGroupRepository egr;
	@Autowired
	private AccountRepository acr;
	@Autowired
	private SpecialFriendRepository sfr;
	@Autowired
	private RoleRepository rr;
	
	public EmailGroup addGroup(EmailGroup eg){
		try {
			validateEmailGroup(eg);
			eg = egr.save(eg);
			return eg;
		}catch(InputMismatchException e) {
			return new EmailGroup(null,null,e.getMessage(),null,null,null);
		}
	}
	
	public  void deleteEmailGroupById(Long id) {
		if (egr.findById(id).get() != null) {
			 egr.deleteById(id);
			 
		}
		
	}
	
	public EmailGroup updateEmailGroup(EmailGroup eg) {
		try {
			validateEmailGroup(eg);
			if (eg.getId() == null) {
				return new EmailGroup(null,null,"Email Group Missing ID. Can't update.",null,null,null);
			}
			eg = egr.save(eg);
			return eg;
		}catch(InputMismatchException e) {
			return new EmailGroup(null,null,e.getMessage(),null,null,null);
		}
	}
	
	//this class compiles all the errors and returns them at once instead of displaying them one at a time
	//it does so by adding to the out variable when an improper parameter is found
	public  boolean validateEmailGroup(EmailGroup eg) throws InputMismatchException{
		boolean isValid = true;
		String out =  "";
		//group name is null or not correct length
		if (!isGroupNameValid(eg.getName())) {
			out+="Name must be between 5 and 30 characters.";
			isValid = false;
		}
		//group description is null or not correct length
		if (!isGroupDescriptionValid(eg.getDescription()) ) {
			out+="Description must be between 10 and 100 characters.";
			isValid = false;
		}
		//special friends or sfaccounts are null or their group has less than 2 members
		if (!isGroupAccountsAndSpecialFriendsValid(eg.getSFNAccounts(), eg.getSpecialFriends())) {
			out+="There must be atleast 2 Special Freinds or SFN Accounts chosen.";
			isValid = false;
		}
		//missing roles 
		if (!isGroupRolesValid(eg.getRoles())) {
			out+="There must be atleast 1 permission selected.";
			isValid = false;
		}
		//if valid, return true if not cast a inputmismatch with the message of all errors
		if (isValid) {
			return true;
			}
		else {
			throw new InputMismatchException(out);
		}
	}
	
	public boolean isGroupNameValid(String name) {
		int minNameLength = 5; 
		int maxNameLength = 30;
		if(name == null) {
			return false;
		}
		name = name.trim();
		if(name.length() < minNameLength || name.length() > maxNameLength) {
			return false;
		}
		return true;
	}
	
	public boolean isGroupDescriptionValid(String description) {
		int minNameLength = 10;
		int maxNameLength = 100;
		if(description == null) {
			return false;
		}
		description = description.trim();
		if(description.length() < minNameLength || description.length() > maxNameLength) {
			return false;
		}
		return true;
	}
	
	public boolean isGroupAccountsAndSpecialFriendsValid(List<Account> accounts, List<SpecialFriend> specialFriends) {
		int minMembers = 2;
		//if either are null, invalid group
		if( specialFriends == null || accounts == null ) {
			return false;
		}
		//need to be atleast minmembers between the two
		if	((specialFriends.size() + accounts.size() )< minMembers) {
			return false;
		}
		return true;
	}
	
	public boolean isGroupRolesValid(List<Role> roles) {
		int minRoleSize = 1;
		if (roles == null) {
			return false;
		}
		if (roles.size() < minRoleSize) {
			return false;
		}
		return true;
	}
	
	public Long parseJSONIntoEmailGroupId(String json) {
		Long id = null;
		//6 is the size of the offset from word "id":" 
		json = json.substring(json.indexOf("\"id\"") + 6);
		//if our id isnt undefined
		if(json.charAt(0) != 'u') {
			id = Long.parseLong(json.substring(0,json.indexOf("\"")));
		}
		return id;
	}
	
	public String parseJSONIntoEmailGroupDescription(String json) {
		String desc = "";
		//15 is the size of the offset from word "description":" 
		json = json.substring(json.indexOf("\"description\"")+15); 
		desc =  json.substring(0,json.indexOf("\""));
		return desc;
	}
	
	public String parseJSONIntoEmailGroupName(String json) {
		String name = "";
		//8 is the size of the offset from word "name":" 
		 json =  json.substring( json.indexOf("\"name\"")+8);
		 name = json.substring(0,json.indexOf("\""));
		 return name;
	}
	
	public boolean isJSONValidSize(String json) {
		if (json != null && json.length() > 0) {
			return true;
		}
		return false;
	}
	
	public List<Role> parseJSONIntoEmailGroupRoles(String json){
		List<Role> roles = new ArrayList<Role>();
		json = json.substring(json.indexOf("\"roles\""));
		//while id is present in current block 
		while(json.indexOf("id") < json.indexOf("]") && json.indexOf("id") != -1 ) {
			//6 is the size of the offset from word "id":" 
			json = json.substring(json.indexOf("\"id\"")+6);
			Long id = Long.parseLong(json.substring(0,json.indexOf("\"")));
			roles.add(rr.findById(id).get());
			}
		return roles;
	}
	
	public List<Account> parseJSONIntoEmailGroupAccounts(String json){
		List<Account> accounts = new ArrayList<Account>();
		json = json.substring(json.indexOf("SFNAccounts"));
		//due to a Java error, this json structure is sometimes ints and sometime strings
		//we remove the possiblity and just delete all " to make it int based
		json = json.replace("\"", "");
		//while id is present in current block 
		while(json.indexOf("id") < json.indexOf("]") && json.indexOf("id") != -1 ) {
			//6 is the size of the offset from word id: 
			json = json.substring(json.indexOf("id")+3);
			//since its int, we have to use the end of the current object not the quote as a stop
			Long id = Long.parseLong(json.substring(0,json.indexOf("}")));
			accounts.add(acr.findById(id).get());
		}
		return accounts;
	}
	
	public List<SpecialFriend> parseJSONIntoEmailGroupSpecialFriends(String json){
		List<SpecialFriend> specialFriends = new ArrayList<SpecialFriend>();
		json = json.substring(json.indexOf("specialFriends"));
		//while id is present in current block 
		while(json.indexOf("id") < json.indexOf("]") && json.indexOf("id") != -1) {
			//6 is the size of the offset from word "id":" 
			json = json.substring(json.indexOf("\"id\"")+6);
			Long id = Long.parseLong(json.substring(0,json.indexOf("\"")));
			specialFriends.add(sfr.findById(id).get());
		}
		return specialFriends;
	}
	
	public EmailGroup parseJSONStringIntoEmailGroup(String json) throws InputMismatchException{
		//return if our string is too short
		if (!isJSONValidSize(json)) {
			return null;
		}
		Long id = parseJSONIntoEmailGroupId(json);
		String desc = parseJSONIntoEmailGroupDescription(json);
		String name = parseJSONIntoEmailGroupName(json);
		//move the JSON String ahead to the maximum,
		//we get the max index between name and desc to find where the beginning Email Group structure ended
		json = json.substring(Math.max(json.indexOf(name) + name.length() + 2, json.indexOf(desc) + desc.length() + 2));
		List<Role> roles = parseJSONIntoEmailGroupRoles(json);
		List<Account> accounts = parseJSONIntoEmailGroupAccounts(json);
		List<SpecialFriend> specialFriends = parseJSONIntoEmailGroupSpecialFriends(json);
		EmailGroup emailGroup = new EmailGroup(id,name,desc,specialFriends,accounts,roles);
		
		return emailGroup;
	}
}
