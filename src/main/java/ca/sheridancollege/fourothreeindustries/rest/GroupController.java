package ca.sheridancollege.fourothreeindustries.rest;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;
import ca.sheridancollege.fourothreeindustries.services.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {

	@Autowired
	private EmailGroupRepository egr;
	@Autowired
	private AccountRepository acr;
	@Autowired
	private SpecialFriendRepository sfr;
	@Autowired
	private RoleRepository rr;
	@Autowired
	private GroupService gs;
	private int pageSize =  5;
	
	
	@GetMapping("/id/{id}")
	public String getGroupById(@PathVariable Long id) {
		EmailGroup eg = egr.findById(id).get();
		if (eg != null) {
			return "{\"emailGroup\":\"" + eg.JSONify() + "\"}";
		}
		return "{\"emailGroup\":\"null\"}";
	}
	
	@GetMapping("/deleteById/{id}")
	public String deleteGroupById(@PathVariable Long id) {
		egr.deleteById(id);
		return "{\"message\":\"Group Deleted!\"}";
	}
	
	@GetMapping("/page/{page}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getEmailGroups(@PathVariable Long page) {
		
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<EmailGroup> yup = egr.findAll(pageRequest);
		String json ="[";
		for (EmailGroup eg: yup.toList()) {
			json+= eg.JSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		
		return json;
	}
	
	@PutMapping("/update")
	public String updateEmailGroup(@RequestBody String eg) {
		
		String cur = eg;
		List<Account> accounts = new ArrayList<Account>();
		List<SpecialFriend> sFriends = new ArrayList<SpecialFriend>();
		List<Role> roles = new ArrayList<Role>();
		List<Long> idList = new ArrayList<Long>();
		System.out.println(eg);
		cur = eg.substring(eg.indexOf("\"id\"") + 6);
		System.out.println(cur);
		Long id = Long.parseLong(cur.substring(0,cur.indexOf("\"")));
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
		try {
			gs.validateEmailGroup(emailGroup);
			gs.updateEmailGroup(emailGroup);
			return "{\"message\":\"Update Success\"}";
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\"}";
		}
	}
	
	
}
