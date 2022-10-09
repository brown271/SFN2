package ca.sheridancollege.fourothreeindustries.repos;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface AccountRepository extends JpaRepository <Account,Long>{
	public List<Account> findByUsernameLike(String username);
	public List<Account> findAllByUsername(String username, Pageable page);
	public List<Account> findByRole(Role role);
	public List<Account> findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(String firstName, String lastName);
	public List<Account> findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(String firstName, String lastName);
}
