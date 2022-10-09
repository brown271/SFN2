package ca.sheridancollege.fourothreeindustries.rest;

import java.util.InputMismatchException;
import java.util.List;

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
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;
import ca.sheridancollege.fourothreeindustries.services.AccountService;
import ca.sheridancollege.fourothreeindustries.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccountService accountService;
	
	@CrossOrigin()
	@GetMapping("/page/{page}")
	public String findAdminsByPage(@PathVariable Long page) {
		List<Admin> admins = adminService.getAdminsByPage(page);
		String json ="[";
		for (Admin eg: admins) {
			json+= eg.simpleJSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		return json;
		
	}
	
	@CrossOrigin()
	@PostMapping("/")
	public String addAdmin(@RequestBody Admin admin ) {
		try {
			Role role = roleRepository.findByRoleName(admin.getAccount().getRole().getRoleName());
			admin.getAccount().setRole(role);
			adminService.isAdminValid(admin);
			adminService.addAdmin(admin);
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\",\"status\":500}";
		}
		String name = admin.getAccount().getPersonalInfo().getFirstName() + " " + admin.getAccount().getPersonalInfo().getLastName();
		return "{\"message\":\"Successfully Created " + name + "!\",\"status\":200}";
	}
	
	@CrossOrigin()
	@GetMapping("/id/{id}")
	public String findAdminById(@PathVariable Long id) {
		Admin admin = adminService.findAdminByAccount(accountService.findAccountById(id));
		if(admin == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			return "{\"user\":" + admin.JSONify() + ",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteAdminById(@PathVariable Long id) {
		Admin admin = adminService.findAdminByAccount(accountService.findAccountById(id));
		if(admin == null) {
			return "{\"message\":\"Can't find user\",\"status\":500}";
		}
		else {
			String name = admin.getAccount().getPersonalInfo().getFirstName() + " " + admin.getAccount().getPersonalInfo().getLastName();
			adminService.deleteAdminById(admin.getId());
			return "{\"message\":\"Deletion of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	
	
	@CrossOrigin()
	@PutMapping("/")
	public String putAdmin(@RequestBody Admin admin ) {
		try {
			System.out.println(admin);
			Admin currentAdmin = adminService.findAdminByAccount(admin.getAccount());
			Role role = roleRepository.findByRoleName(admin.getAccount().getRole().getRoleName());
			admin.getAccount().setRole(role);
			admin.getAccount().setPassword(currentAdmin.getAccount().getPassword());
			admin.getAccount().setUsername(currentAdmin.getAccount().getUsername());
			admin.setId(currentAdmin.getId());
			System.out.println(admin);
			adminService.isAdminValid(admin);
			adminService.updateAdmin(admin);
		}catch(InputMismatchException e) {
			return "{\"message\":\"" + e.getMessage() + "\",\"status\":500}";
		}
		String name = admin.getAccount().getPersonalInfo().getFirstName() + " " + admin.getAccount().getPersonalInfo().getLastName();
		return "{\"message\":\"Successfully Created " + name + "!\",\"status\":200}";
	}
}
