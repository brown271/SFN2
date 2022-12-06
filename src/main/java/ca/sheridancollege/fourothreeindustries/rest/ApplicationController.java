package ca.sheridancollege.fourothreeindustries.rest;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Application;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.services.ApplicationService;

@RestController
@RequestMapping("/api/app")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	
	@CrossOrigin()
	@PostMapping("/")
	public String createApplication(@RequestBody Application application) {
		try {
			applicationService.isApplicationValid(application);
			applicationService.createApplication(application);
			String name = application.getPersonalInfo().getFirstName() + " " + application.getPersonalInfo().getLastName();
			return "{\"status\":200,\"message\":\"Application for: " + name + " was created successfully. \"}";
		}catch(InputMismatchException e) {
			return "{\"status\":500,\"message\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
	@CrossOrigin()
	@PostMapping("/acceptById/{id}")
	public String acceptApplication(@PathVariable Long id) {
		Application application = applicationService.getApplicationById(id);
		if(application == null) {
			return "{\"message\":\"Can't find application\",\"status\":500}";
		}
		else {
			SpecialFriend specialFriend = applicationService.acceptApplicationById(id);
			String name = specialFriend.getPersonalInfo().getFirstName() + " " + specialFriend.getPersonalInfo().getLastName();
			return "{\"message\":\"Acceptance of " + name +  " was successful.\",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/deleteById/{id}")
	public String deleteApplicationById(@PathVariable Long id) {
		Application application = applicationService.getApplicationById(id);
		if(application == null) {
			return "{\"message\":\"Can't find application\",\"status\":500}";
		}
		else {
			String name =application.getPersonalInfo().getFirstName() + " " + application.getPersonalInfo().getLastName();
			applicationService.deleteApplicationById(id);
			return "{\"message\":\"Deletion of " + name +  "'s application was successful.\",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/findById/{id}")
	public String getApplicationById(@PathVariable Long id) {
		Application application = applicationService.getApplicationById(id);
		if(application == null) {
			return "{\"message\":\"Can't find application\",\"status\":500}";
		}
		else {
			return "{\"user\":" + application.JSONify() + ",\"status\":200}";
		}
	}
	
	@CrossOrigin()
	@GetMapping("/")
	public String getAllApplications() {
		List<Application> applications = applicationService.findAllApplications();
		String out = "{\"applications\":[";
		for(Application application: applications) {
			out+=application.simpleJSONify();
			out+=",";
		}
		if(out.charAt(out.length()-1) == ',') {
			out = out.substring(0,out.length()-1);
		}
		
		out+="]}";
		return out;
	}
}
