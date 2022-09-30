package ca.sheridancollege.fourothreeindustries.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;


@RestController
@RequestMapping("/api/test")
public class ConnectionController {
	
	@Autowired
	private AccountRepository acr;
	
	@CrossOrigin()
	@GetMapping("/conn")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getLogin(){
		System.out.println("connection");
		
		return "{\"message\":\"GREAT SUCCESS! CONNECTION WORKS!\"}";
	}
	
	@CrossOrigin()
	@GetMapping("/")
	public String test() {
		System.out.println("yuh");
		return "{\"message\":\"GREAT SUCCESS! CONNECTION WORKS!\"}";
	}
	

}
