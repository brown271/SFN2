package ca.sheridancollege.fourothreeindustries.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;
import ca.sheridancollege.fourothreeindustries.services.EmailService;


@RestController
@RequestMapping("/api/email")
public class EmailController {
	
	@Autowired
	private EmailGroupRepository egr;
	private int pageSize =  5;
	private EmailService emailer = new EmailService();
	@Autowired
	private JavaMailSender jms;

	
	@GetMapping("/page/{page}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getEmailGroups(@PathVariable Long page) {
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<EmailGroup> yup = egr.findAll(pageRequest);
		String json ="[";
		for (EmailGroup eg: yup.toList()) {
			json+= eg.JSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		return json;
	}
	
	//how to recieve email and subject?
	//maybe need to create new class called email?
	@PostMapping("/sendEmail/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String sendEmail(@RequestBody List<EmailGroup> groupsToSendTo) {
		//emailer.sendEmail(jms, groupsToSendTo, null, null)
		return "";
	}
}
