package ca.sheridancollege.fourothreeindustries.rest;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Date;
import ca.sheridancollege.fourothreeindustries.domain.Event;
import ca.sheridancollege.fourothreeindustries.services.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@CrossOrigin()
	@GetMapping("/")
	public List<Date> getCurrentCalendar(){
		System.out.println("connection");
		LocalDate date = LocalDate.now();
		date = LocalDate.of(date.getYear(),date.getMonth(),1);
		return eventService.generateCalendar(date);
	}
	
	@CrossOrigin()
	@GetMapping("/{month}/{year}")
	public List<Date> getSpecificCalendar(@PathVariable Long month, @PathVariable Long year){
		LocalDate date = LocalDate.of(year.intValue(), month.intValue(), 1);
		return eventService.generateCalendar(date);
	}
	
	@CrossOrigin()
	@GetMapping("/findByName/{name}")
	public List<Event> getEventByName(@PathVariable String name){
		name = name.trim();
		return eventService.findByName(name);
	}
	
	@PostMapping("/")
	public String addEvent(@RequestBody Event event) {
		try {
			eventService.isEventValid(event);
			eventService.addEvent(event);
			return "{\"status\":200,\"message\":\"" + event.getName() + " was created successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@PutMapping("/")
	public String updateEvent(@RequestBody Event event) {
		try {
			eventService.isEventValid(event);
			eventService.updateEvent(event);
			return "{\"status\":200,\"message\":\"" + event.getName() + " was edited successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@GetMapping("/deleteById/{id}")
	public String deleteEventById(@PathVariable Long id) {
		Event event = eventService.findEventById(id);
		if(event == null) {
			return "{\"message\":\"Can't find Event\",\"status\":500}";
		}
		else {
			String name = event.getName();
			eventService.deleteEventById(id);
			return "{\"message\":\"Deletion of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	@GetMapping("/findById/{id}")
	public String findEventById(@PathVariable Long id) {
		Event event = eventService.findEventById(id);
		if(event == null) {
			return "{\"message\":\"Can't find Event\",\"status\":500}";
		}
		else {
			String name = event.getName();
			eventService.deleteEventById(id);
			return "{\"message\":\"" + event.JSONify() +  "\",\"status\":200}";
		}
	}
	
}
