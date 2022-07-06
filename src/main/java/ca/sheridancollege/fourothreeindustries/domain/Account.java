package ca.sheridancollege.fourothreeindustries.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	
	@OneToOne
	@JoinColumn
	private PersonalInfo personalInfo;

	@ManyToMany()
	private List<Role> roles;

	@ManyToMany(mappedBy = "SFNAccounts")
	List<EmailGroup> groups;
}
