package ca.sheridancollege.fourothreeindustries.repos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.Role;

public interface RoleRepository extends JpaRepository <Role,Long>{
	
	public Role findByRoleName(String roleName);
	
	public List<Role> RoleNameIn(List<String> names);
}
