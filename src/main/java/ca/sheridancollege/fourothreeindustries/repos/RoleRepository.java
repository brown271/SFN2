package ca.sheridancollege.fourothreeindustries.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.Role;

public interface RoleRepository extends JpaRepository <Role,Long>{
	public Role findByRoleName(String roleName);
}
