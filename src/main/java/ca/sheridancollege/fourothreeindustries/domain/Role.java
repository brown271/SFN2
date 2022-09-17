package ca.sheridancollege.fourothreeindustries.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String roleName;
	
	public String JSONify() {
		return "{\"id\":\"" + this.id + "\",\"roleName\":\"" + this.roleName + "\"}";
	}
	
}
