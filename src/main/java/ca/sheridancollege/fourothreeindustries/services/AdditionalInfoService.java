package ca.sheridancollege.fourothreeindustries.services;

import java.util.InputMismatchException;

import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.AdditionalInfo;

@Service
public class AdditionalInfoService {

	private int minContactNameLength = 5;
	private int maxContactNameLength = 60;
	private int minContactRelationLength = 3;
	private int maxContactRelationLength = 15;
	private int phoneNumberLength = 10;
	
	
	
	public boolean isAdditionalInfoValid(AdditionalInfo additionalInfo) throws InputMismatchException{
		String out ="";
		if(!isEmergencyContactNameValid(additionalInfo.getEmergencyContactName())) {
			out+="Error: Emergency contact namme must be between " + minContactNameLength + " and " + maxContactNameLength + " characters long;";
		}
		if(!isEmergencyContactPhoneNumberValid(additionalInfo.getEmergencyContactPhoneNumber())) {
			out+="Error: Emergency contact phone number must contain " + phoneNumberLength + " numerical deigits;"; 
		}
		if(!isEmergencyContactRelationValid(additionalInfo.getEmergencyContactRelation())) {
			out+="Error: Emergency contact relation must be between " + minContactRelationLength + " and " + maxContactRelationLength + " characters long;";
		}
		
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public boolean isEmergencyContactNameValid(String contactName) {
		if(contactName == null) {
			return false;
		}
		contactName = contactName.trim();	
		if(contactName.length() > maxContactNameLength || contactName.length() < minContactNameLength) {
			return false;
		}
		return true;
		
	}
	
	public boolean isEmergencyContactRelationValid(String relation) {
		if(relation == null) {
			return false;
		}
		relation = relation.trim();	
		if(relation.length() > maxContactRelationLength || relation.length() < minContactRelationLength) {
			return false;
		}
		return true;
	}
	
	public boolean isEmergencyContactPhoneNumberValid(String phoneNumber) {
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
	
}
