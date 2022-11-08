package ca.sheridancollege.fourothreeindustries.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;

import org.junit.jupiter.api.Test;

import ca.sheridancollege.fourothreeindustries.domain.Event;

class EventServiceTest {
	
	public EventService eventService = new EventService();
	
	@Test
	void testIsEventValidGood() {
		Event event = new Event(null,LocalDate.of(2022, 12, 25),LocalTime.of(12, 15),"Bowling Day",null,"zoommeetings.com/meet",null,null,true);
		assertDoesNotThrow(() -> eventService.isEventValid(event));
	}
	
	@Test
	void testIsEventValidBad() {
		Event event = new Event(null,LocalDate.of(2002, 12, 25),LocalTime.of(12, 15),"Bowling Day",null,"zoommeetings.com/meet",null,null,true);
		assertThrows(InputMismatchException.class, () -> eventService.isEventValid(event));
	}
	
	@Test
	void testIsEventValidBoundary() {
		Event event = new Event(null,LocalDate.now(),LocalTime.of(23, 55),"",null,"zoommeetings.com/meet",null,null,true);
		assertThrows(InputMismatchException.class, () -> eventService.isEventValid(event));
	}

	@Test
	void testIsEventTimeValidGood() {
		assertTrue(eventService.isEventTimeValid(LocalTime.NOON));
	}
	
	@Test
	void testIsEventTimeValidBad() {
		assertFalse(eventService.isEventTimeValid(null));
	}
	
	@Test
	void testIsEventTimeValidBoundary() {
		assertThrows(DateTimeException.class, () -> eventService.isEventTimeValid(LocalTime.of(86, 12)));
	}


	@Test
	void testIsEventDateValidGood() {
		assertTrue(eventService.isEventDateValid(LocalDate.of(2023, 10, 15)));
	}
	
	@Test
	void testIsEventDateValidBad() {
		assertFalse(eventService.isEventDateValid(LocalDate.of(2001,10,15)));
	}
	
	@Test
	void testIsEventDateValidBoundary() {
		assertTrue(eventService.isEventDateValid(LocalDate.now()));
	}

	@Test
	void testIsEventNameValidGood() {
		assertTrue(eventService.isEventNameValid("Bowling Thursday"));
	}
	
	@Test
	void testIsEventNameValidBad() {
		assertFalse(eventService.isEventNameValid(null));
	}
	
	@Test
	void testIsEventNameValidBoundary() {
		assertFalse(eventService.isEventNameValid("                      eve               "));
	}

	@Test
	void testIsEventLinkValidGood() {
		assertTrue(eventService.isEventLinkValid("zoommeetings.ca/meetid=113323"));
	}
	
	@Test
	void testIsEventLinkValidBad() {
		assertFalse(eventService.isEventLinkValid(null));
	}
	
	@Test
	void testIsEventLinkValidBoudnary() {
		assertFalse(eventService.isEventLinkValid("         link        "));
	}

	@Test
	void testIsEventAddressValidGood() {
		assertTrue(eventService.isEventAddressValid("230 Shafstbury Ave"));
	}
	
	@Test
	void testIsEventAddressValidBad() {
		assertFalse(eventService.isEventAddressValid(" "));
	}
	
	@Test
	void testIsEventAddressValidBoundary() {
		assertFalse(eventService.isEventAddressValid("                                                   "));
	}

}
