package ca.sheridancollege.fourothreeindustries.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface VolunteerRepository extends JpaRepository <Volunteer,Long>{

	public Volunteer findByAccount(Account account);
}
