package ca.sheridancollege.fourothreeindustries.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="event")
public class Event {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime time;
	private String name;
	private String description;
	private String linkToEvent;
	private String address;
	private String host;
	private Boolean isOnline;
	//private Role role;
	
	
	public String JSONify() {
		String json = "{\"id\":\"" + this.getId() + "\"";
		json+=",\"date\":\"" + date + "\"";
		json+=",\"name\":\"" + name + "\"";
		json+=",\"isOnline\":\"" + isOnline + "\"";
		if(description == null || description.length()==0) {
			json+=",\"description\":\"\"";
		}else {
			json+=",\"description\":\"" + description + "\"";
		}
		if(linkToEvent == null || linkToEvent.length()==0) {
			json+=",\"linkToEvent\":\"\"";
		}else {
			json+=",\"linkToEvent\":\"" + linkToEvent + "\"";
		}
		if(address == null || address.length()==0) {
			json+=",\"address\":\"\"";
		}else {
			json+=",\"address\":\"" + address + "\"";
		}
		if(host == null || host.length()==0) {
			json+=",\"host\":\"\"";
		}else {
			json+=",\"host\":\"" + host + "\"";
		}
		json+=",\"time\":\"" + time + "\"}";
		return json;
	}
	
	
}
