package ca.sheridancollege.fourothreeindustries.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
public class SpecialFriend {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne 
	@JoinColumn 
	private PersonalInfo personalInfo;
	
	@OneToOne 
	@JoinColumn
	private AdditionalInfo additionalInfo;
	
	@Lob
	private String howToComfort;
	private Boolean isCostAFactor;
	private Boolean isBirthdayClubMember;
	
	public String JSONify() {
		return "{\"id\":\"" + this.id + "\",\"howToComfort\":\"" + howToComfort + "\",\"isCostAFactor\":" 
				+isCostAFactor+ ",\"isBirthdayClubMember\":" + isBirthdayClubMember +
				",\"personalInfo\":" + this.getPersonalInfo().JSONify() + 
				",\"additionalInfo\":" +  additionalInfo.JSONify() +  "}";
		
	}
	
	public String simpleJSONify() {
		return "{\"id\":\"" + this.id + "\",\"personalInfo\":" + this.getPersonalInfo().simpleJSONify() +  "}";
		
	}
	

	@ManyToMany(mappedBy = "specialFriends")
	List<EmailGroup> groups;
}
