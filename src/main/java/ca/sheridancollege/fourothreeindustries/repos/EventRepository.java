package ca.sheridancollege.fourothreeindustries.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.fourothreeindustries.domain.Event;


public interface EventRepository extends JpaRepository< Event, Long>{
	public List<Event> findByDate(LocalDate date);
}
