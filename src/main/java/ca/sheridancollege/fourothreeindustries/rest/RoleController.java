package ca.sheridancollege.fourothreeindustries.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.Sfn2Application.GenSecurity;
import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.services.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@CrossOrigin()
	@GetMapping("/")
	@GenSecurity
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getAllRoles() {
		String out ="[";
		for(Role r: roleService.getAllRoles()) {
			out+="{\"id\":\"" + r.getId() + "\",\"roleName\":\"" + r.getRoleName() + "\"},";
		}
		if(out.length()> 1) {
			out = (out.substring(0,out.length()-1) + "]");
		}
		else {
			out = "[]";
		}
		
		return out;
	}
	
	@CrossOrigin()
	@GetMapping("/editableRolesInSession")
	public String getEditableRolesInMySession() {
		List<Role> currentRoles = roleService.findCurrentRoles();
		List<Role> editableRoles = new ArrayList<Role>();
		String out ="[";
		for(Role currentRole: currentRoles) {
			for(Role editableRole: roleService.findRolesMyRoleCanEdit(currentRole)) {
				if(!editableRoles.contains(editableRole)) {
					editableRoles.add(editableRole);
					out+="{\"id\":\"" + editableRole.getId() + "\",\"roleName\":\"" + editableRole.getRoleName() + "\"},";
				}
			}
		}
		if(out.length()> 1) {
			out = (out.substring(0,out.length()-1) + "]");
		}
		else {
			out = "[]";
		}
		return out;
		
	}
}
