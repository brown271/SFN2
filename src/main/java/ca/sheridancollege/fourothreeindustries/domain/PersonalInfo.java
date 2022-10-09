package ca.sheridancollege.fourothreeindustries.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PersonalInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthDate;
	private String phoneNumber;
	private String email;
	
	public String JSONify() {
		String out = "{\"id\":" + id + ",\"firstName\":\"" + firstName+ "\",\"lastName\":\"" + lastName + "\",\"birthDay\":\"" + birthDate 
				+ "\",\"email\":\"" + email + "\",\"phoneNumber\":\"" + phoneNumber +"\"}";
		return out;
	}
	
	public String simpleJSONify() {
		String out = "{\"id\":" + id + ",\"name\":\"" + firstName + " " + lastName + "\",\"email\":\"" + email + "\"}";
		return out;
	}
	
	
}
