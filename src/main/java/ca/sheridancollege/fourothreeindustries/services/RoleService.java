package ca.sheridancollege.fourothreeindustries.services;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private  RoleRepository roleRepo;
	
	public  List<Role> getAllRoles(){
		return roleRepo.findAll();
	}
	
	public List<Role> findCurrentRoles(){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<String> stringRoles =  (auth.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList()));
		return roleRepo.RoleNameIn(stringRoles);
	}
	
	public List<Role> findRolesMyRoleCanEdit(Role role){
		System.out.println("ROLE");
		System.out.println(role);
		if (role == null || role.getRoleName() == "ANONYMOUS") {
			return null;
		}
		List<Role> roles = new ArrayList<Role>();
		if(role.getRoleName().equals("VOLUNTEER") || role.getRoleName().equals("TEAM_MEMBER")  || role.getRoleName().equals("ADMIN") ) {
			roles.add(roleRepo.findByRoleName("SPECIAL_FRIEND"));
		}
		if (role.getRoleName().equals("TEAM_MEMBER")  || role.getRoleName().equals("ADMIN")) {
			roles.add(roleRepo.findByRoleName("VOLUNTEER"));

		}
		if(role.getRoleName().equals("ADMIN")) {
			roles.add(roleRepo.findByRoleName("ADMIN"));
			roles.add(roleRepo.findByRoleName("TEAM_MEMBER"));
		}
		return roles;
	}
	
	
	
}
