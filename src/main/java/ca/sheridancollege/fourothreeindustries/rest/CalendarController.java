package ca.sheridancollege.fourothreeindustries.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.DateEvents;
import ca.sheridancollege.fourothreeindustries.repos.EventRepository;
import ca.sheridancollege.fourothreeindustries.services.CalendarService;

@RestController
@RequestMapping("/api/cal")
public class CalendarController {
	
	@Autowired
	private EventRepository er;
	
	@GetMapping("/")
	public List<DateEvents> getCurrentCalendar(){
		System.out.println("connection");
		LocalDate date = LocalDate.now();
		date = LocalDate.of(date.getYear(),date.getMonth(),1);
		return CalendarService.generateCalendar(date, er);
	}
	
	@GetMapping("/{month}/{year}")
	public List<DateEvents> getSpecificCalendar(@PathVariable Long month, @PathVariable Long year){
		LocalDate date = LocalDate.of(year.intValue(), month.intValue(), 1);
		return CalendarService.generateCalendar(date, er);
	}
}
