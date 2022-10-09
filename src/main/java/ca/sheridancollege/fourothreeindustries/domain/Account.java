package ca.sheridancollege.fourothreeindustries.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account implements UserDetails{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	//private List<? extends GrantedAuthority> grantedAuthorities;
	
	@OneToOne()
	@JoinColumn
	private PersonalInfo personalInfo;

	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;

	@ManyToMany(mappedBy = "SFNAccounts")
	List<EmailGroup> groups;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
		
	}
	
	public String JSONify() {
		String json = "{\"id\":\"" + this.id + "\",\"personalInfo\":" + this.getPersonalInfo().JSONify() 
				+",\"role\":" + role.JSONify() +"}";
		return json;
		
	}
	
	public String simpleJSONify() {
		String json = "{\"id\":\"" + this.id + "\",\"personalInfo\":" + this.getPersonalInfo().simpleJSONify()  +",\"role\":" + role.JSONify() +"}";
		return json;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		//return isAccountNonExpired;
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		//return isAccountNonLocked;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		//return isCredentialsNonExpired;
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		//return isEnabled;
		return true;
	}
}
