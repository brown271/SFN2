package ca.sheridancollege.fourothreeindustries.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface EmailGroupRepository extends JpaRepository <EmailGroup, Long>{
	public List<EmailGroup> findByRoles(Role role);
	public List<EmailGroup> findByName(String name);
	
	public List<EmailGroup> findAllByRoles(Role role, Pageable page);
	public List<EmailGroup> findAllByName(String Name, Pageable page);
}
