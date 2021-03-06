package ca.sheridancollege.fourothreeindustries.services;





import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.*;

@Service
public class EmailService { //VERSION - 2: DOUBLE EMAIL FIXED!
	

	
	//jms is our default java mail sender, just used to pass in email account and password
	public Boolean sendEmail(JavaMailSender jms, List<EmailGroup> groupsToSendTo, String subject, String message )  {
		
		List<String> emailAddresses = new ArrayList<String>();
		for(EmailGroup eg: groupsToSendTo) {
			//for (Account acc: eg.getSFNAccounts()) {
			//	String email = acc.getPersonalInfo().getEmail();
			//	if(!emailAddresses.contains(email)) {
			//		emailAddresses.add(email);
			//	}
			//}
			
			//for (SpecialFriend sf: eg.getSpecialFriends()) {
			//	String email = sf.getPersonalInfo().getEmail();
			//	if(!emailAddresses.contains(email)) {
			//		emailAddresses.add(email);
			//	}
			//}
		}
		//create a new empty email message
		SimpleMailMessage msg = new SimpleMailMessage();
		String[] to = new String[emailAddresses.size()];
		emailAddresses.toArray(to);
		//send email
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(message);
		msg.setFrom("noreplyspecialfriendsnetwork@gmail.com");
		System.out.println(msg);
		jms.send(msg);
		//return false for now, will implement email sending testing in sprint 2
		return false;
		
	}
	
	public String parseSubject(String subject) throws Exception{
		subject = subject.trim();
		if (subject.length() <=0 || subject.length() > 80) {
			
			throw new Exception("Error: Subject must be greater "
					+ "than 0 characters and less than 80.");
		}
		return subject;
	}
	
	public String parseBody(String body) throws Exception{
		body = body.trim();
		if (body.length() <=0 || body.length() > 5000) {
			
			throw new Exception("Error: Body must be greater "
					+ "than 0 characters and less than 5000.");
		}
		return body;
	}

}
