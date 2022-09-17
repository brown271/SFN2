package ca.sheridancollege.fourothreeindustries.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Role;
import ca.sheridancollege.fourothreeindustries.repos.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private  RoleRepository rr;
	
	public  List<Role> getAllRoles(){
		return rr.findAll();
	}
	
	
}
