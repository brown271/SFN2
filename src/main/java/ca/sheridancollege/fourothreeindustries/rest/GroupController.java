package ca.sheridancollege.fourothreeindustries.rest;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private GroupService gs;
	private int pageSize =  5;
	
	@CrossOrigin()
	@GetMapping("/id/{id}")
	public String getGroupById(@PathVariable Long id) {
		EmailGroup eg = egr.findById(id).get();
		if (eg != null) {
			return "{\"emailGroup\":\"" + eg.JSONify() + "\"}";
		}
		return "{\"emailGroup\":\"null\"}";
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteGroupById(@PathVariable Long id) {
		String name = "";
		if(egr.findById(id).get() != null) {
			name = "~" + egr.findById(id).get().getName() + "~ ";
		}
		egr.deleteById(id);
		return "{\"message\":\"Group "+ name + "Deleted!\"}";
	}
	
	@CrossOrigin()
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
	
	@CrossOrigin()
	@PostMapping("/add")
	public String addEmailGroup(@RequestBody String eg) {
		EmailGroup emailGroup = gs.parseJSONStringIntoEmailGroup(eg);
		try {
			gs.validateEmailGroup(emailGroup);
			gs.addGroup(emailGroup);
			return "{\"message\":\"Create Success\"}";
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
	@CrossOrigin()
	@PutMapping("/update")
	public String updateEmailGroup(@RequestBody String eg) {
		//Java cannot parse the object so we have to do it manually
		EmailGroup emailGroup = gs.parseJSONStringIntoEmailGroup(eg);
		
		//update the email group after
		try {
			gs.validateEmailGroup(emailGroup);
			gs.updateEmailGroup(emailGroup);
			return "{\"message\":\"Update Success\",\"status\":200}";
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\",\"status\":500}";
		}
	}
	
	
}
