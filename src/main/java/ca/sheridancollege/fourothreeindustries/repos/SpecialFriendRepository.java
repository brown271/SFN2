package ca.sheridancollege.fourothreeindustries.repos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.*;

public interface SpecialFriendRepository extends JpaRepository <SpecialFriend,Long>{
	
	public List<SpecialFriend> findByPersonalInfo_FirstNameContainsIgnoreCaseAndPersonalInfo_LastNameContainsIgnoreCase(String firstName, String lastName);
	public List<SpecialFriend> findByPersonalInfo_FirstNameContainsIgnoreCaseOrPersonalInfo_LastNameContainsIgnoreCase(String firstName, String lastName);
}
