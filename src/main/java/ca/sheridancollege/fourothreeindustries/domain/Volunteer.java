package ca.sheridancollege.fourothreeindustries.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Volunteer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer hours;
	
	@OneToOne
	@JoinColumn
	private AdditionalInfo additionalInfo;
	
	@OneToOne
	@JoinColumn
	private Account account;
	
	public String JSONify() {
		return "{\"id\":\"" + this.getId() + "\",\"hours\":\"" + this.getHours() +
				"\", \"additionalInfo\":" + additionalInfo.JSONify() + ",\"account\":"
				+ account.JSONify()  + "}";
	}
	public String simpleJSONify() {
		return "{\"id\":\"" + this.getId() + "\",\"hours\":\"" + this.getHours() +
				 ",\"account\":" + account.simpleJSONify()  + "}";
	}
}

