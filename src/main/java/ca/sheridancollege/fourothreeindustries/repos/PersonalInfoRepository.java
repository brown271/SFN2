package ca.sheridancollege.fourothreeindustries.repos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface PersonalInfoRepository extends JpaRepository <PersonalInfo,Long>{

	public List<PersonalInfo> findByEmailContainsIgnoreCase(String email);
}
