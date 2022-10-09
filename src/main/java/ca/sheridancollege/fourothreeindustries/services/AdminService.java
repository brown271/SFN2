package ca.sheridancollege.fourothreeindustries.services;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;
import ca.sheridancollege.fourothreeindustries.repos.AdminRepository;
import ca.sheridancollege.fourothreeindustries.repos.EmailGroupRepository;
import ca.sheridancollege.fourothreeindustries.repos.PersonalInfoRepository;
import ca.sheridancollege.fourothreeindustries.domain.*;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private PersonalInfoRepository personalInfoRepo;
	private int pageSize =  5;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GroupService groupService;
	
	
	
	public List<Admin> getAdminsByPage(Long page) {
		System.out.println("Page Request For page " + page);
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		List<Admin> admins = adminRepo.findAll(pageRequest).toList();
		return admins;
	}
	
	public Admin getAdminById(Long id) {
		return adminRepo.findById(id).get();
	}
	
	public Admin addAdmin(Admin admin) {
		admin.getAccount().setPersonalInfo(personalInfoRepo.save(admin.getAccount().getPersonalInfo()));
		admin.setAccount(accountRepo.save(admin.getAccount()));
		admin = this.adminRepo.save(admin);
		return admin;
	}
	
	public void deleteAdminById(Long id) {
		Admin admin = adminRepo.findById(id).get();
		if(admin==null) {
			return;
		}
		PersonalInfo personalInfo = admin.getAccount().getPersonalInfo();
		Account account = admin.getAccount();
		groupService.wipeAccountFromAllEmailGroups(account);
		account.setPersonalInfo(null);
		Account tempAccount = accountRepo.save(account);
		admin.setAccount(null);
		Admin tempAdmin = adminRepo.save(admin);
		adminRepo.delete(tempAdmin);
		personalInfoRepo.delete(personalInfo);
		accountRepo.delete(tempAccount);
	}
	
	public Admin updateAdmin(Admin admin) {
		admin.getAccount().setPersonalInfo(personalInfoRepo.save(admin.getAccount().getPersonalInfo()));
		admin.setAccount(accountRepo.save(admin.getAccount()));
		admin = this.adminRepo.save(admin);
		return admin;
	}
	
	public boolean isAdminValid(Admin admin) throws InputMismatchException{
		String out = "";
		try {
			if(admin.getId() != null) {
				System.out.println("CHECKING FOR A CURRENT ADMIN");
				accountService.isCurrentAccountValid(admin.getAccount());
			}else {
				accountService.isNewAccountValid(admin.getAccount());
			}
		}catch(InputMismatchException e) {
			out+=e.getMessage();
		}
		if (out.length()>0) {
			throw new InputMismatchException(out);
		}
		return true;
	}
	
	public Admin findAdminByAccount(Account account) {
		return adminRepo.findByAccount(account);
	}
	

}
