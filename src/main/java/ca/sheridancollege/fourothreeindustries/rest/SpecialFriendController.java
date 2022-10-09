package ca.sheridancollege.fourothreeindustries.rest;

import java.util.InputMismatchException;

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

import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.domain.Volunteer;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;
import ca.sheridancollege.fourothreeindustries.services.SpecialFriendService;
import ca.sheridancollege.fourothreeindustries.services.VolunteerService;

@RestController
@RequestMapping("/api/sf")
public class SpecialFriendController {

	@Autowired
	private SpecialFriendRepository sfr;
	private int pageSize =  5;
	@Autowired
	private SpecialFriendService specialFriendService;
	
	@CrossOrigin()
	@GetMapping("/page/{page}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getSpecialFriends(@PathVariable Long page) {
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<SpecialFriend> yup = sfr.findAll(pageRequest);
		String json ="[";
		for (SpecialFriend eg: yup.toList()) {
			json+= eg.simpleJSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		return json;
	}
	

	
	@CrossOrigin()
	@PostMapping("/")
	public String createSpecialFriend(@RequestBody SpecialFriend specialFriend) {
		try {
			specialFriendService.isSpecialFriendValid(specialFriend);
			specialFriendService.addSpecialFriend(specialFriend);
			String name = specialFriend.getPersonalInfo().getFirstName() + " " + specialFriend.getPersonalInfo().getLastName();
			return "{\"status\":200,\"message\":\"" + name + " was created successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
	@CrossOrigin()
	@PutMapping("/")
	public String updateSpecialFriend(@RequestBody SpecialFriend specialFriend) {
		try {
			specialFriendService.isSpecialFriendValid(specialFriend);
			specialFriendService.updateSpecialFriend(specialFriend);
			String name =specialFriend.getPersonalInfo().getFirstName() + " " + specialFriend.getPersonalInfo().getLastName();
			return "{\"status\":200,\"message\":\"" + name + " was edited successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteSpecialFriendById(@PathVariable Long id) {
		SpecialFriend specialFriend = specialFriendService.getSpecialFriendById(id);
		if(specialFriend == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			String name =specialFriend.getPersonalInfo().getFirstName() + " " + specialFriend.getPersonalInfo().getLastName();
			specialFriendService.deleteSpecialFriendById(id);
			return "{\"message\":\"Deletion of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/id/{id}")
	public String getVolunteerById(@PathVariable Long id) {
		SpecialFriend specialFriend = specialFriendService.getSpecialFriendById(id);
		if(specialFriend == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			return "{\"user\":" + specialFriend.JSONify() + ",\"status\":200}";
		}
	}
}
