package ca.sheridancollege.fourothreeindustries.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import ca.sheridancollege.fourothreeindustries.domain.DateEvents;
import ca.sheridancollege.fourothreeindustries.domain.Event;
import ca.sheridancollege.fourothreeindustries.repos.EventRepository;



@Service
public class CalendarService {
public static List<DateEvents> generateCalendar(LocalDate date, EventRepository er) {
		
		LocalDate today = LocalDate.now();
		int offset = -100;
		int dist = distFromSunday(date.getDayOfWeek());	
		List<DateEvents> days = new ArrayList<DateEvents>();
		for (int i = 0; i < dist; i++) {
			date = date.minusDays(1);
		}
		for (int i = 0; i< 42; i++) {
			DateEvents newED = new DateEvents(date.getDayOfMonth(),new ArrayList<Event>());
			if (date.getDayOfMonth() == today.getDayOfMonth() && date.getMonth() == today.getMonth()) {
				offset = i;
			}
			System.out.println(date);
			if (er.findByDate(date).size() > 0) {
				System.out.println("SHIT WORKS!");
				for (Event e: er.findByDate(date)) {
					newED.getEvents().add(e);
				}
				
			}
			
			days.add(newED);
			date = date.plusDays(1);
		}
		
		return days;
	}
	
	public static int distFromSunday(DayOfWeek weekDay) {
		if (weekDay == DayOfWeek.SUNDAY) {
			return 0;
		}
		else if(weekDay == DayOfWeek.MONDAY) {
			return 1;
		}
		else if(weekDay == DayOfWeek.TUESDAY) {
			return 2;
		}
		else if(weekDay == DayOfWeek.WEDNESDAY) {
			return 3;
		}
		else if(weekDay == DayOfWeek.THURSDAY) {
			return 4;
		}
		else if(weekDay == DayOfWeek.FRIDAY) {
			return 5;
		}
	return 6;
	}
}
