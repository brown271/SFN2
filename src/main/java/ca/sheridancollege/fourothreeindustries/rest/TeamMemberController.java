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
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;
import ca.sheridancollege.fourothreeindustries.services.AccountService;
import ca.sheridancollege.fourothreeindustries.services.TeamMemberService;

@RestController
@RequestMapping("/api/tm")
public class TeamMemberController {
	
	@Autowired
	private TeamMemberService teamMemberService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccountService accountService;

	@CrossOrigin()
	@PostMapping("/")
	public String addTeamMember(@RequestBody TeamMember teamMember ) {
		try {
			Role role = roleRepository.findByRoleName(teamMember.getAccount().getRole().getRoleName());
			teamMember.getAccount().setRole(role);
			teamMemberService.isTeamMemberValid(teamMember);
			teamMemberService.addTeamMember(teamMember);
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\",\"status\":500}";
		}
		String name = teamMember.getAccount().getPersonalInfo().getFirstName() + " " + teamMember.getAccount().getPersonalInfo().getLastName();
		return "{\"message\":\"Successfully Created " + name + "!\",\"status\":200}";
	}
	
	@CrossOrigin()
	@GetMapping("/id/{id}")
	public String findTeamMemberById(@PathVariable Long id) {
		TeamMember teamMember = teamMemberService.findByAccount(accountService.findAccountById(id));
		if(teamMember == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			return "{\"user\":" + teamMember.JSONify() + ",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteTeamMemberById(@PathVariable Long id) {
		TeamMember teamMember = teamMemberService.findByAccount(accountService.findAccountById(id));
		if(teamMember == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			String name = teamMember.getAccount().getPersonalInfo().getFirstName() + " " + teamMember.getAccount().getPersonalInfo().getLastName();
			teamMemberService.deleteTeamMemberById(teamMember.getId());
			return "{\"message\":\"Deletion of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	
	
	@CrossOrigin()
	@PutMapping("/")
	public String putTeamMember(@RequestBody TeamMember teamMember ) {
		try {
			TeamMember currentTeamMember = teamMemberService.findByAccount(teamMember.getAccount());
			Role role = roleRepository.findByRoleName(teamMember.getAccount().getRole().getRoleName());
			teamMember.getAccount().setRole(role);
			teamMember.getAccount().setPassword(currentTeamMember.getAccount().getPassword());
			teamMember.getAccount().setUsername(currentTeamMember.getAccount().getUsername());
			teamMember.setId(currentTeamMember.getId());
			teamMemberService.isTeamMemberValid(teamMember);
			teamMemberService.updateTeamMember(teamMember);
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\",\"status\":500}";
		}
		String name = teamMember.getAccount().getPersonalInfo().getFirstName() + " " + teamMember.getAccount().getPersonalInfo().getLastName();
		return "{\"message\":\"Successfully Edited " + name + "!\",\"status\":200}";
	}
}
