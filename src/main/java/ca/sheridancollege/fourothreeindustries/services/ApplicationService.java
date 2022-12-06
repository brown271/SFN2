package ca.sheridancollege.fourothreeindustries.services;

import java.util.InputMismatchException;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.AdditionalInfo;
import ca.sheridancollege.fourothreeindustries.domain.Application;
import ca.sheridancollege.fourothreeindustries.domain.PersonalInfo;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.AdditionalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.ApplicationRepository;
import ca.sheridancollege.fourothreeindustries.repos.PersonalInfoRepository;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepo;
	@Autowired
	private SpecialFriendService specialFriendService;
	@Autowired
	private PersonalInfoService personalInfoService;
	@Autowired
	private AdditionalInfoService additionalInfoService;
	@Autowired
	private AdditionalInfoRepository additionalInfoRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepo;
	
	public Application createApplication(Application application) {
		application.setAdditionalInfo(additionalInfoRepo.save(application.getAdditionalInfo()));
		application.setPersonalInfo(personalInfoRepo.save(application.getPersonalInfo()));
		return applicationRepo.save(application);
		
	}
	
	public SpecialFriend acceptApplicationById(Long id) {
		Application application = applicationRepo.findById(id).get();
		PersonalInfo personalInfo = application.getPersonalInfo();
		AdditionalInfo additionalInfo = application.getAdditionalInfo();
		application.setPersonalInfo(null);
		application.setAdditionalInfo(null);
		application = applicationRepo.save(application);
		SpecialFriend newSpecialFriend = SpecialFriend.builder().personalInfo(personalInfo)
				.additionalInfo(additionalInfo).howToComfort(application.getHowToComfort())
				.isCostAFactor(application.getIsCostAFactor()).isBirthdayClubMember(application.getIsBirthdayClubMember()).build();
		newSpecialFriend = specialFriendService.addSpecialFriend(newSpecialFriend);
		applicationRepo.deleteById(id);
		return newSpecialFriend;
	}
	
	
	public Application getApplicationById(Long id) {
		return applicationRepo.findById(id).get();
	}
	
	public void deleteApplicationById(Long id) {
		Application application = applicationRepo.findById(id).get();
		if(application==null) {
			return;
		}
		PersonalInfo personalInfo = application.getPersonalInfo();
		AdditionalInfo additionalInfo = application.getAdditionalInfo();
		application.setPersonalInfo(null);
		application.setAdditionalInfo(null);
		Application tempApplication = applicationRepo.save(application);
		applicationRepo.delete(tempApplication);
		personalInfoRepo.delete(personalInfo);
		additionalInfoRepo.delete(additionalInfo);
	}
	
	public boolean isApplicationValid(Application application) throws InputMismatchException{
		String out = "";
		try {
			additionalInfoService.isAdditionalInfoValid(application.getAdditionalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		try {
			personalInfoService.isPersonalInfoValid(application.getPersonalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		if(out.length() == 0) {
			return true;
		}
		throw new InputMismatchException(out);
	}
	
	public List<Application> findAllApplications(){
		return applicationRepo.findAll();
	}
}
