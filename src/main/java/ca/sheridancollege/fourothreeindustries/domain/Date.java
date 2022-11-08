package ca.sheridancollege.fourothreeindustries.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

	private LocalDate date;
	private List<Event> events;
}
