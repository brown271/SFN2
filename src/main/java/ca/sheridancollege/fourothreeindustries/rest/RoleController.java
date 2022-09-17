package ca.sheridancollege.fourothreeindustries.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.services.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	private RoleService rs;

	@GetMapping("/")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getAllRoles() {
		String out ="[";
		for(Role r: rs.getAllRoles()) {
			out+="{\"id\":\"" + r.getId() + "\",\"roleName\":\"" + r.getRoleName() + "\"},";
		}
		out = (out.substring(0,out.length()-1) + "]");
		return out;
	}
}
