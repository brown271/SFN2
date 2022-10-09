package ca.sheridancollege.fourothreeindustries.services;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.PersonalInfo;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;

@Service
public class PersonalInfoService {

	
	private int minFirstNameLength = 1;
	private int maxFirstNameLength = 20;
	private int minLastNameLength = 1;
	private int maxLastNameLength = 40;
	private int phoneNumberLength = 10;
	
	public String compileListOfFriendsAndAccountsIntoJSONString(List<Account> accounts, List<SpecialFriend> specialFriends) {
		String message = "{";
		if (accounts == null && specialFriends == null) {
			return "{\"Message\":\"Special Friends and Accounts were null, cannot JSONify\"}";
		}
		if (accounts.size() + specialFriends.size() == 0) {
			return "{\"Message\":\"There were no SpecialFriends or Accounts, cannot JSONify\"}";
		}
		if (accounts.size()> 0 ) {
			message+="\"SFNAccounts\":[";
			for(int i = 0; i < accounts.size(); i++) {
				message+=accounts.get(i).simpleJSONify();
				if (i < accounts.size()-1 && accounts.size() > 1) {
					message+=",";
				}
			}
			message+="]";
		}
		if (specialFriends.size()>0) {
			//if accounts were above, add a comma to split the JSON data
			if( accounts.size() > 0) {
				message+=",";
			}
			message+="\"specialFriends\":[";
			for(int i = 0; i < specialFriends.size(); i++) {
				message+=specialFriends.get(i).simpleJSONify();
				if (i < specialFriends.size()-1 && specialFriends.size() > 1) {
					message+=",";
				}
			}
			message+="]";
		}
		message+="}";
		return message;
	}
	
	public boolean isPersonalInfoValid(PersonalInfo personalInfo) {
		String out = "";
		if(!isFirstNameValid(personalInfo.getFirstName())) {
			out+="Error: firstname must be between "+  minFirstNameLength + " and " + maxFirstNameLength +" characters. ";
		}
		if(!isLastNameValid(personalInfo.getLastName())) {
			out+="Error: lastname must be between "+  minLastNameLength + " and " + maxLastNameLength +" characters. ";
		}
		if(!isBirthDateValid(personalInfo.getBirthDate())) {
			out+="Error: must have a valid Birthdate.";
		}
		if(!isPhoneNumberValid(personalInfo.getPhoneNumber())) {
			out+="Error: phone number must consist of "+ phoneNumberLength + "  numerical characters.";
		}
		if(!isEmailValid(personalInfo.getEmail())) {
			out+="Error: must have a valid email address.";
		}
		
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public boolean isFirstNameValid(String firstName) {
		if(firstName == null) {
			return false;
		}
		firstName = firstName.trim();
		if(firstName.length() > maxFirstNameLength || firstName.length() < minFirstNameLength) {
			return false;
		}
		return true;
	}
	
	public boolean isLastNameValid(String lastName) {
		if(lastName == null) {
			return false;
		}
		lastName = lastName.trim();
		if(lastName.length() > maxLastNameLength || lastName.length() < minLastNameLength) {
			return false;
		}
		return true;
	}
	
	public boolean isBirthDateValid(LocalDate birthDate) {
		if(birthDate == null) {
			return false;
		}
		if(birthDate.isAfter((LocalDate.of(LocalDate.now().getYear()-5, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth())))) {
			return false;
		}
		return true;
	}
	
	public boolean isPhoneNumberValid(String phoneNumber) {
		if(phoneNumber == null) {
			return false;
		}
		phoneNumber = phoneNumber.trim();
		if (phoneNumber.length() < phoneNumberLength) {
			return false;
		}
		int numCounter = 0;
		for(int i = 0; i < phoneNumber.length(); i++) {
			char curChar = phoneNumber.charAt(i);
			if (Character.isDigit(curChar)) {
				numCounter++;
			}
		}
		if (numCounter < phoneNumberLength) {
			return false;
		}
		return true;
	}
	
	public boolean isEmailValid(String email) {
		if (email == null) {
			return false;
		}
		if(!email.contains("@")) {
			return false;
		}
		return true;
	}
}
