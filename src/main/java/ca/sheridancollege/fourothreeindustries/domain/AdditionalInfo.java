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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AdditionalInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
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
	private String conditions;
	
	
}
