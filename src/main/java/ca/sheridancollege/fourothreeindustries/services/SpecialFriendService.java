package ca.sheridancollege.fourothreeindustries.services;

import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.domain.AdditionalInfo;
import ca.sheridancollege.fourothreeindustries.domain.PersonalInfo;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.domain.TeamMember;
import ca.sheridancollege.fourothreeindustries.repos.AdditionalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.PersonalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;

@Service
public class SpecialFriendService {
	
	@Autowired
	private SpecialFriendRepository specialFriendRepo;
	@Autowired
	private PersonalInfoService personalInfoService;
	@Autowired
	private AdditionalInfoService additionalInfoService;
	@Autowired
	private AdditionalInfoRepository additionalInfoRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepo;
	@Autowired
	private GroupService groupService;
	
	public SpecialFriend addSpecialFriend(SpecialFriend specialFriend) {
		specialFriend.setAdditionalInfo(additionalInfoRepo.save(specialFriend.getAdditionalInfo()));
		specialFriend.setPersonalInfo(personalInfoRepo.save(specialFriend.getPersonalInfo()));
		return specialFriendRepo.save(specialFriend);
		
	}
	
	public SpecialFriend updateSpecialFriend(SpecialFriend specialFriend){
		specialFriend.setAdditionalInfo(additionalInfoRepo.save(specialFriend.getAdditionalInfo()));
		specialFriend.setPersonalInfo(personalInfoRepo.save(specialFriend.getPersonalInfo()));
		return specialFriendRepo.save(specialFriend);
		
	}
	
	public SpecialFriend getSpecialFriendById(Long id) {
		return specialFriendRepo.findById(id).get();
	}
	
	public void deleteSpecialFriendById(Long id) {
		SpecialFriend specialFriend = specialFriendRepo.findById(id).get();
		if(specialFriend==null) {
			return;
		}
		PersonalInfo personalInfo = specialFriend.getPersonalInfo();
		AdditionalInfo additionalInfo = specialFriend.getAdditionalInfo();
		groupService.wipeSpecialFriendFromAllEmailGroups(specialFriend);
		specialFriend.setPersonalInfo(null);
		specialFriend.setAdditionalInfo(null);
		SpecialFriend tempSpecialFriend = specialFriendRepo.save(specialFriend);
		specialFriendRepo.delete(tempSpecialFriend);
		personalInfoRepo.delete(personalInfo);
		additionalInfoRepo.delete(additionalInfo);
	}
	
	public boolean isSpecialFriendValid(SpecialFriend specialFriend) throws InputMismatchException{
		String out = "";
		try {
			additionalInfoService.isAdditionalInfoValid(specialFriend.getAdditionalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		try {
			personalInfoService.isPersonalInfoValid(specialFriend.getPersonalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		if(out.length() == 0) {
			return true;
		}
		throw new InputMismatchException(out);
	}
	
}
