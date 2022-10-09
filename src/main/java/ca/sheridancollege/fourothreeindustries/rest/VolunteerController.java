package ca.sheridancollege.fourothreeindustries.rest;

import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Admin;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.domain.TeamMember;
import ca.sheridancollege.fourothreeindustries.domain.Volunteer;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;
import ca.sheridancollege.fourothreeindustries.services.AccountService;
import ca.sheridancollege.fourothreeindustries.services.VolunteerService;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {
	
	private int pageSize = 5;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccountService accountService;
	
	
	@CrossOrigin()
	@PostMapping("/")
	public String createVolunteer(@RequestBody Volunteer volunteer) {
		try {
			Role role = roleRepository.findByRoleName(volunteer.getAccount().getRole().getRoleName());
			volunteer.getAccount().setRole(role);
			volunteerService.isVolunteerValid(volunteer);
			volunteerService.addVolunteer(volunteer);
			String volunteerName = volunteer.getAccount().getPersonalInfo().getFirstName() + " " + volunteer.getAccount().getPersonalInfo().getLastName();
			return "{\"status\":200,\"message\":\"" + volunteerName + " was created successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
	@CrossOrigin()
	@PutMapping("/")
	public String updateVolunteer(@RequestBody Volunteer volunteer) {
		try {
			Volunteer currentVolunteer = volunteerService.findVolunteerByAccount(volunteer.getAccount());
			Role role = roleRepository.findByRoleName(volunteer.getAccount().getRole().getRoleName());
			volunteer.getAccount().setRole(role);
			volunteer.getAccount().setPassword(currentVolunteer.getAccount().getPassword());
			volunteer.getAccount().setUsername(currentVolunteer.getAccount().getUsername());
			volunteer.setId(currentVolunteer.getId());
			volunteerService.isVolunteerValid(volunteer);
			volunteerService.updateVolunteer(volunteer);
			String volunteerName = volunteer.getAccount().getPersonalInfo().getFirstName() + " " + volunteer.getAccount().getPersonalInfo().getLastName();
			return "{\"status\":200,\"message\":\"" + volunteerName + " was edited successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteVolunteerById(@PathVariable Long id) {
		Volunteer volunteer = volunteerService.findVolunteerByAccount(accountService.findAccountById(id));
		if(volunteer == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			String name = volunteer.getAccount().getPersonalInfo().getFirstName() + " " + volunteer.getAccount().getPersonalInfo().getLastName();
			volunteerService.deleteVolunteerById(volunteer.getId());
			return "{\"message\":\"Deletion of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/id/{id}")
	public String getVolunteerById(@PathVariable Long id) {
		Volunteer volunteer = volunteerService.findVolunteerByAccount(accountService.findAccountById(id));
		if(volunteer == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			return "{\"user\":" + volunteer.JSONify() + ",\"status\":200}";
		}
	}

}
