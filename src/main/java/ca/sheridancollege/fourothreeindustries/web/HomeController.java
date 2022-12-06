package ca.sheridancollege.fourothreeindustries.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/home")
	public String home() {
		return "index.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "index.html";
	}
	
	@GetMapping("/events")
	public String events() {
		return "index.html";
	}
	
	@GetMapping("/send-emails")
	public String sendEmails() {
		return "index.html";
	}
	
	@GetMapping("/create-group")
	public String createGroup() {
		return "index.html";
	}
	
	@GetMapping("/edit-groups")
	public String editGroups() {
		return "index.html";
	}
	
	@GetMapping("/register-members")
	public String registerMembers() {
		return "index.html";
	}
	@GetMapping("/search-accounts")
	public String searchAccounts() {
		return "index.html";
	}
	@GetMapping("/search-events")
	public String searchEvents() {
		return "index.html";
	}
	@GetMapping("/create-events")
	public String createEvents() {
		return "index.html";
	}
	@GetMapping("/apply")
	public String apply() {
		return "index.html";
	}
	@GetMapping("/view-applications")
	public String viewApplications() {
		return "index.html";
	}
	
	@GetMapping("/about-us")
	public String aboutUs() {
		return "index.html";
	}
	
	@GetMapping("/supporters")
	public String supporters() {
		return "index.html";
	}
	
}
