package ca.sheridancollege.fourothreeindustries.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EmailGroup {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	
	
	@ManyToMany
	@JoinTable(
			name = "group_friends",
			joinColumns = @JoinColumn(name = "group_id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<SpecialFriend> specialFriends;
	
	@ManyToMany
	@JoinTable(
			name = "group_sfnaccounts",
			joinColumns = @JoinColumn(name = "group_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private List<Account> SFNAccounts;
	
	public String JSONify() {
		String json =  "{\"id\":\"" + this.id + "\",\"name\":\"" + this.name + "\",\"description\":\""+ this.description + "\","
				+ "\"SFNAccounts\":[ ";
				for(Account a: SFNAccounts) {
					json +=	a.JSONify() + ",";
				}
				json = (json.substring(0,json.length()-1) + "],\"specialFriends\":[ ");
				for(SpecialFriend sf: specialFriends) {
					json +=	sf.JSONify() + ",";
				}
				json = (json.substring(0,json.length()-1) + "],\"roles\":[");
				for(Role r: roles) {
					json +=	r.JSONify() + ",";
				}
				json = (json.substring(0,json.length()-1) + "]}");
		
		return json;
	}

	@ManyToMany()
	private List<Role> roles;
}
