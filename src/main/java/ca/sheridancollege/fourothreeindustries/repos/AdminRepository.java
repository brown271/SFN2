package ca.sheridancollege.fourothreeindustries.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface AdminRepository extends JpaRepository <Admin ,Long>{

	public Admin findByAccount(Account account);
}
