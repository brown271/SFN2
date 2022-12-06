package ca.sheridancollege.fourothreeindustries.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Date;
import ca.sheridancollege.fourothreeindustries.domain.Event;
import ca.sheridancollege.fourothreeindustries.repos.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepo;
	private int minNameLength = 5;
	private int maxNameLength = 30;
	private int minLinkLength = 5;
	private int maxLinkLength = 200;
	private int minAddressLength = 5;
	private int maxAddressLength = 50;
	
	public List<Date> generateCalendar(LocalDate date) {

		date = LocalDate.of(date.getYear(), date.getMonth(), 1);
		int distFromSunday = date.getDayOfWeek().getValue() % 7;
		List<Date> calendar = new ArrayList<Date>();
		date = date.minusDays(distFromSunday);
		for (int i = 0; i < 42; i++) {
			Date newDateEvent = new Date(date, new ArrayList<Event>());
			newDateEvent.getEvents().addAll(eventRepo.findByDate(date));
			calendar.add(newDateEvent);
			date = date.plusDays(1);
		}
		return calendar;
	}
	
	public List<Event> findByName(String name){
		return eventRepo.findAllByNameContainsIgnoreCase(name);
	}
	
	public Event addEvent(Event event) {
		event = eventRepo.save(event);
		return event;
	}
	
	public Event findEventById(Long id) {
		return eventRepo.findById(id).get();
	}
	
	public void deleteEventById(Long id) {
		eventRepo.deleteById(id);
	}
	
	public Event updateEvent(Event event) {
		event = eventRepo.save(event);
		return event;
	}
	
	public boolean isEventTimeValid(LocalTime time) {
		if(time == null) {
			return false;
		}
		return true;
	}
	
	public boolean isEventValid(Event event) throws InputMismatchException {
		String out ="";
		if (!isEventDateValid(event.getDate())) {
			out+="Event date must be after today;";
		}
		if (!isEventNameValid(event.getName())) {
			out+="Event name must be between " + minNameLength + " and " + maxNameLength + " characters long;";
		}
		if (!isEventTimeValid(event.getTime())) {
			out+="Event must have a time;";
		}
		
		if(event.getIsOnline()) {
			if(!isEventLinkValid(event.getLinkToEvent())) {
				out +="Event link must be between " + minLinkLength + " and " + maxLinkLength + " characters long;";
			}
		}
		else {
			if(!isEventAddressValid(event.getAddress())) {
				out +="Event address must be between " + minAddressLength + " and " + maxAddressLength + " characters long;";
			}
		}
		
		if(out.length()> 0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public boolean isEventDateValid(LocalDate date) {
		if(date == null) {
			return false;
		}
		if(date.isBefore(LocalDate.now())) {
			return false;
		}
		return true;
		
	}
	
	public boolean isEventNameValid(String name) {
		if(name == null) {
			return false;
		}
		name = name.trim();
		if(name.length() < minNameLength || name.length() > maxNameLength ) {
			return false;
		}
		return true;
	}
	public boolean isEventLinkValid(String link) {
		if(link == null) {
			return false;
		}
		link = link.trim();
		if(link.length() < minLinkLength || link.length() > maxLinkLength) {
			return false;
		}
		return true;
	}
	
	public boolean isEventAddressValid(String address) {
		if(address == null) {
			return false;
		}
		address = address.trim();
		if(address.length() < minAddressLength || address.length() > maxAddressLength) {
			return false;
		}
		return true;
	}
	
	

}
