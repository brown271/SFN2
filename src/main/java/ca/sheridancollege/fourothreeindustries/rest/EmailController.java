package ca.sheridancollege.fourothreeindustries.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@Autowired
	private EmailService emailService;
	@Autowired
	private JavaMailSender jms;

	@CrossOrigin()
	@GetMapping("/page/{page}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getEmailGroups(@PathVariable Long page) {
		System.out.println("\n\nRequest for a group from page: " + page + "\n\n");
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<EmailGroup> yup = egr.findAll(pageRequest);
		String json ="[";
		for (EmailGroup eg: yup.toList()) {
			json+= eg.JSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		if(json.length() == 1) {
			return "[]";
		}
		return json;
	}
	
	//how to recieve email and subject?
	//maybe need to create new class called email?
	@PostMapping("/sendEmail" )
	@CrossOrigin()
	@ResponseBody
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String sendEmail( @RequestBody List<String> emailInfo) {
		//emailer.sendEmail(jms, groupsToSendTo, null, null)
		System.out.println("EMAIL REQUEST RECIEVED");
		System.out.println("Subject: " + emailInfo.get(emailInfo.size()-1));
		System.out.println("Body: " + emailInfo.get(emailInfo.size()-2));
		String subject = emailInfo.get(emailInfo.size()-1);
		String body = emailInfo.get(emailInfo.size()-2);
		System.out.println("-------Emails:------");
		for(int i = 0; i < emailInfo.size()-2; i++) {
			System.out.println(emailInfo.get(i));
		}
		emailInfo.remove(emailInfo.size()-1);
		emailInfo.remove(emailInfo.size()-1);
		EmailService.sendEmail(jms, emailInfo, body, subject);
		return "";
	}
}
