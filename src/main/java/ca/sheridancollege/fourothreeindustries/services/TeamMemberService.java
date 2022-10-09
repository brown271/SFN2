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
import ca.sheridancollege.fourothreeindustries.domain.TeamMember;
import ca.sheridancollege.fourothreeindustries.domain.Volunteer;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.AdditionalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.PersonalInfoRepository;
import ca.sheridancollege.fourothreeindustries.repos.TeamMemberRepository;

@Service
public class TeamMemberService {
	@Autowired
	private AdditionalInfoService additionalInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TeamMemberRepository teamMemberRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepo;
	@Autowired
	private AdditionalInfoRepository additionalInfoRepo;
	@Autowired
	private GroupService groupService;

	public boolean isTeamMemberValid(TeamMember teamMember) throws InputMismatchException{
		String out ="";
		try {
			additionalInfoService.isAdditionalInfoValid(teamMember.getAdditionalInfo());
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		try {
			if(teamMember.getId() != null) {
				System.out.println("CHECKING FOR A CURRENT ADMIN");
				accountService.isCurrentAccountValid(teamMember.getAccount());
			}else {
				accountService.isNewAccountValid(teamMember.getAccount());
			}
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		if(out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public TeamMember getTeamMemberById(Long id) {
		return teamMemberRepo.findById(id).get();
	}
	
	public TeamMember addTeamMember(TeamMember teamMember) {
		teamMember.getAccount().setPersonalInfo(personalInfoRepo.save(teamMember.getAccount().getPersonalInfo()));
		teamMember.setAccount(accountRepo.save(teamMember.getAccount()));
		teamMember.setAdditionalInfo(additionalInfoRepo.save(teamMember.getAdditionalInfo()));
		teamMember = this.teamMemberRepo.save(teamMember);
		return teamMember;
	}
	
	public TeamMember updateTeamMember(TeamMember teamMember) {
		teamMember.getAccount().setPersonalInfo(personalInfoRepo.save(teamMember.getAccount().getPersonalInfo()));
		teamMember.setAccount(accountRepo.save(teamMember.getAccount()));
		teamMember.setAdditionalInfo(additionalInfoRepo.save(teamMember.getAdditionalInfo()));
		teamMember = this.teamMemberRepo.save(teamMember);
		return teamMember;
	}
	
	public void deleteTeamMemberById(Long id) {
		TeamMember teamMember = teamMemberRepo.findById(id).get();
		if(teamMember==null) {
			return;
		}
		PersonalInfo personalInfo = teamMember.getAccount().getPersonalInfo();
		Account account = teamMember.getAccount();
		AdditionalInfo additionalInfo = teamMember.getAdditionalInfo();
		groupService.wipeAccountFromAllEmailGroups(account);
		account.setPersonalInfo(null);
		teamMember.setAdditionalInfo(null);
		Account tempAccount = accountRepo.save(account);
		teamMember.setAccount(null);
		TeamMember tempTeamMember = teamMemberRepo.save(teamMember);
		teamMemberRepo.delete(tempTeamMember);
		personalInfoRepo.delete(personalInfo);
		additionalInfoRepo.delete(additionalInfo);
		accountRepo.delete(tempAccount);
	}
	
	public TeamMember findByAccount(Account account) {
		return teamMemberRepo.findByAccount(account);
	}
	

}
