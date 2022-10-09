package ca.sheridancollege.fourothreeindustries.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class AdditionalInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String allergies;
	private String favouriteEvents;
	private String leastFavouriteEvents;
	private String needsToBeMet;
	@Lob
	private String additionalComments;
	private String emergencyContactName;
	private String emergencyContactRelation;
	private String emergencyContactPhoneNumber;
	private Boolean isPhotoPermissionSigned;
	@Lob
	private String specialNeeds;
	
	public String JSONify() {
		String json = "{\"id\":\"" + this.getId() + "\"";
		if( allergies == null || allergies.length()==0) {
			json+=",\"allergies\":\"\"";
		}	else {
			json+=",\"allergies\":\"" + this.allergies + "\"";
		}
			
		if(favouriteEvents == null || favouriteEvents.length()==0) {
			json+=",\"favouriteEvents\":\"\"";
		}else {
			json+=",\"favouriteEvents\":\"" + this.favouriteEvents + "\"";
		}
			
		if(needsToBeMet == null || needsToBeMet.length() == 0) {
			json+=",\"needsToBeMet\":\"\"";
		}else {
			json+=",\"needsToBeMet\":\"" + this.needsToBeMet + "\"";
		}
			
		if(additionalComments == null || additionalComments.length()==0) {
			json+=",\"additionalComments\":\"\"";
		}else {
			json+=",\"additionalComments\":\"" + this.additionalComments + "\"";
		}
			
		if(specialNeeds == null || specialNeeds.length()==0 ) {
			json+=",\"specialNeeds\":\"\"";
		}else {
			json+=",\"specialNeeds\":\"" + this.specialNeeds + "\"";
		}
			
		json+=",\"emergencyContactName\":\"" + emergencyContactName + "\"";
		json+=",\"emergencyContactRelation\":\"" + emergencyContactRelation + "\"";
		json+=",\"emergencyContactPhoneNumber\":\"" + emergencyContactPhoneNumber + "\"}";
		
		return json;
	}
}
