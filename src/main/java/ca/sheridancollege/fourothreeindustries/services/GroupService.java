package ca.sheridancollege.fourothreeindustries.services;
import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;

import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;

@Service
public class GroupService {
	
	@Autowired
	private EmailGroupRepository egr;
	
	public EmailGroup addGroup(EmailGroup eg){
		try {
			validateEmailGroup(eg);
			eg = egr.save(eg);
			return eg;
		}catch(InputMismatchException e) {
			return new EmailGroup(null,null,e.getMessage(),null,null,null);
		}
	}
	
	public  void deleteEmailGroupById(Long id) {
		if (egr.findById(id).get() != null) {
			 egr.deleteById(id);
			 
		}
		
	}
	
	public EmailGroup updateEmailGroup(EmailGroup eg) {
		try {
			validateEmailGroup(eg);
			if (eg.getId() == null) {
				return new EmailGroup(null,null,"Email Group Missing ID. Can't update.",null,null,null);
			}
			eg = egr.save(eg);
			return eg;
		}catch(InputMismatchException e) {
			return new EmailGroup(null,null,e.getMessage(),null,null,null);
		}
	}
	
	public  boolean validateEmailGroup(EmailGroup eg) throws InputMismatchException{
		boolean isValid = true;
		String out =  "";
		if (eg.getName() == null || eg.getName().length() < 5 || eg.getName().length()>30 ) {
			out+="Name must be between 5 and 30 characters.";
			isValid = false;
		}
		if (eg.getDescription() == null || eg.getDescription().length() < 10 || eg.getDescription().length()>100 ) {
			out+="Description must be between 10 and 100 characters.";
			isValid = false;
		}
		if ((eg.getSpecialFriends() == null || eg.getSFNAccounts() == null )||(eg.getSpecialFriends().size() + eg.getSFNAccounts().size() )< 2) {
			out+="There must be atleast 2 Special Freinds or SFN Accounts chosen.";
			isValid = false;
		}
		if (eg.getRoles() == null|| eg.getRoles().size() <1) {
			out+="There must be atleast 1 permission selected.";
			isValid = false;
		}
		if (isValid) {
			return true;
			}
		else {
			throw new InputMismatchException(out);
		}
	}
}
