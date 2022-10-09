package ca.sheridancollege.fourothreeindustries.services;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.AdditionalInfo;
import ca.sheridancollege.fourothreeindustries.domain.Admin;
import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.PersonalInfo;
import ca.sheridancollege.fourothreeindustries.domain.Volunteer;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.AdditionalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;
import ca.sheridancollege.fourothreeindustries.repos.PersonalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.VolunteerRepository;

@Service
public class VolunteerService {
	@Autowired
	private VolunteerRepository volunteerRepo;
	
	@Autowired
	private AdditionalInfoService additionalInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepository;
	@Autowired
	private AdditionalInfoRepository additionalInfoRepo;
	@Autowired
	private EmailGroupRepository emailGroupRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepo;
	@Autowired
	private GroupService groupService;

	public boolean isVolunteerValid(Volunteer volunteer) throws InputMismatchException{
		String out ="";
		try {
			additionalInfoService.isAdditionalInfoValid(volunteer.getAdditionalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		try {
			if(volunteer.getId() != null) {
				System.out.println("CHECKING FOR A CURRENT ADMIN");
				accountService.isCurrentAccountValid(volunteer.getAccount());
			}else {
				accountService.isNewAccountValid(volunteer.getAccount());
			}
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public Volunteer addVolunteer(Volunteer volunteer) {
		volunteer.getAccount().setPersonalInfo(personalInfoRepository.save(volunteer.getAccount().getPersonalInfo()));
		volunteer.setAccount(accountRepo.save(volunteer.getAccount()));
		volunteer.setAdditionalInfo(additionalInfoRepo.save(volunteer.getAdditionalInfo()));
		volunteer = this.volunteerRepo.save(volunteer);
		return volunteer;
	}
	
	public Volunteer updateVolunteer(Volunteer volunteer){
		volunteer.getAccount().setPersonalInfo(personalInfoRepository.save(volunteer.getAccount().getPersonalInfo()));
		volunteer.setAccount(accountRepo.save(volunteer.getAccount()));
		volunteer.setAdditionalInfo(additionalInfoRepo.save(volunteer.getAdditionalInfo()));
		volunteer = this.volunteerRepo.save(volunteer);
		return volunteer;
		
	}
	
	public Volunteer getVolunteerById(Long id) {
		return volunteerRepo.findById(id).get();
	}
	
	public void deleteVolunteerById(Long id) {
		Volunteer volunteer = volunteerRepo.findById(id).get();
		if(volunteer==null) {
			return;
		}
		PersonalInfo personalInfo = volunteer.getAccount().getPersonalInfo();
		Account account = volunteer.getAccount();
		AdditionalInfo additionalInfo = volunteer.getAdditionalInfo();
		groupService.wipeAccountFromAllEmailGroups(account);
		account.setPersonalInfo(null);
		volunteer.setAdditionalInfo(null);
		Account tempAccount = accountRepo.save(account);
		volunteer.setAccount(null);
		Volunteer tempVolunteer = volunteerRepo.save(volunteer);
		volunteerRepo.delete(tempVolunteer);
		personalInfoRepo.delete(personalInfo);
		additionalInfoRepo.delete(additionalInfo);
		accountRepo.delete(tempAccount);
	}
	
	public Volunteer findVolunteerByAccount(Account account) {
		return volunteerRepo.findByAccount(account);
	}
	
	
	
}
