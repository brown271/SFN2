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
	
	public  boolean validateEmailGroup(EmailGroup eg) throws InputMismatchException{
		boolean isValid = true;
		String out =  "";
		if (eg.getName() == null || eg.getName().length() < 5 || eg.getName().length()>30 ) {
			out+="Name must be between 5 and 30 characters.";
			isValid = false;
		}
		if (eg.getDescription() == null || eg.getDescription().length() < 10 || eg.getDescription().length()>100 ) {
			out+="Description must be between 10 and 100 characters.";
			isValid = false;
		}
		if ((eg.getSpecialFriends() == null || eg.getSFNAccounts() == null )||(eg.getSpecialFriends().size() + eg.getSFNAccounts().size() )< 2) {
			out+="There must be atleast 2 Special Freinds or SFN Accounts chosen.";
			isValid = false;
		}
		if (eg.getRoles() == null|| eg.getRoles().size() <1) {
			out+="There must be atleast 1 permission selected.";
			isValid = false;
		}
		if (isValid) {
			return true;
			}
		else {
			throw new InputMismatchException(out);
		}
	}
	
	public EmailGroup parseJSONStringIntoEmailGroup(String eg) throws InputMismatchException{
		List<Account> accounts = new ArrayList<Account>();
		List<SpecialFriend> sFriends = new ArrayList<SpecialFriend>();
		List<Role> roles = new ArrayList<Role>();
		List<Long> idList = new ArrayList<Long>();
		String cur = eg;
		System.out.println(eg);
		cur = eg.substring(eg.indexOf("\"id\"") + 6);
		Long id = null;
		if(cur.charAt(0) != 'u') {
			id = Long.parseLong(cur.substring(0,cur.indexOf("\"")));
		}
	
		System.out.println(id);
		cur = eg.substring(eg.indexOf("\"description\"")+15);
		String desc =  cur.substring(0,cur.indexOf("\""));
		System.out.println(desc);
		cur = eg.substring(eg.indexOf("\"name\"")+8);
		System.out.println("Cur at Name " + cur);
		String name = cur.substring(0,cur.indexOf("\""));
		System.out.println("Name " + name);
		eg = eg.substring(Math.max(eg.indexOf(name) + name.length() + 2, eg.indexOf(desc) + desc.length() + 2));
		System.out.println(cur);
		
		cur = eg.substring(eg.indexOf("roles"));
		
		if (cur.indexOf("]") - cur.indexOf("[") > 2) {
			while(cur.indexOf("id") < cur.indexOf("]") && cur.indexOf("id") != -1 ) {
				cur = cur.substring(cur.indexOf("\"id\"")+6);
				System.out.println(cur);
				idList.add(Long.parseLong(cur.substring(0,cur.indexOf("\""))));
			}
			roles = rr.findAllById(idList);
			idList.clear();
			
		}
		cur = eg.substring(eg.indexOf("SFNAccounts"));
		System.out.println("SFN Accounts");
		System.out.println(cur);
		cur = cur.replace("\"", "");
		System.out.println(cur);
		
		if (cur.indexOf("]") - cur.indexOf("[") > 2) {
			while(cur.indexOf("id") < cur.indexOf("]") && cur.indexOf("id") != -1 ) {
				cur = cur.substring(cur.indexOf("id")+3);
				System.out.println(cur);
				idList.add(Long.parseLong(cur.substring(0,cur.indexOf("}"))));
			}
			accounts = acr.findAllById(idList);
			idList.clear();
			
		}

		cur = eg.substring(eg.indexOf("specialFriends"));
		if (cur.indexOf("]") - cur.indexOf("[") > 2) {
			while(cur.indexOf("id") < cur.indexOf("]") && cur.indexOf("id") != -1) {
				cur = cur.substring(cur.indexOf("\"id\"")+6);
				System.out.println(cur);
				idList.add(Long.parseLong(cur.substring(0,cur.indexOf("\""))));
			}
			sFriends = sfr.findAllById(idList);
			idList.clear();
			
		}
	
		
		
		EmailGroup emailGroup = new EmailGroup(id,name,desc,sFriends,accounts,roles);
		for(Role r : roles) {
			System.out.println(r);
		}
		return emailGroup;
	}
}
