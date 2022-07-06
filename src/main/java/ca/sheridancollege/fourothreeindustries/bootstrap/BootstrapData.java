package ca.sheridancollege.fourothreeindustries.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ca.sheridancollege.fourothreeindustries.domain.*;
import ca.sheridancollege.fourothreeindustries.repos.*;

@Component
public class BootstrapData implements CommandLineRunner{
	
	@Autowired
	private AccountRepository acr;
	@Autowired
	private AdditionalInfoRepository air;
	@Autowired
	private AdminRepository adr;
	@Autowired
	private PersonalInfoRepository pir;
	@Autowired
	private SpecialFriendRepository sfr;
	@Autowired
	private TeamMemberRepository tmr;
	@Autowired
	private VolunteerRepository vr;
	@Autowired
	private EmailGroupRepository egr;
	@Autowired
	private RoleRepository rgr;
	

	
	private List<String> firstNames = populateListFromFile("firstNames.txt");
	private List<String> lastNames = populateListFromFile("lastNames.txt");
	private List<String> groupNames = populateListFromFile("groups.txt");
	
	@Override
	public void run(String... args) throws Exception {
		Role admin = Role.builder().roleName("ADMIN").build();
		Role tm = Role.builder().roleName("TEAM_MEMBER").build();
		Role volunteer = Role.builder().roleName("VOLUNTEER").build();
		Role sf = Role.builder().roleName("SPECIAL_FRIEND").build();
		Role everyone = Role.builder().roleName("EVERYONE").build();
		rgr.save(admin);
		rgr.save(tm);
		rgr.save(volunteer);
		rgr.save(sf);
		rgr.save(everyone);
		
		make403Industries();
		if (firstNames != null) {
			int sizeFile = firstNames.size();
			int curBuffer = sizeFile/2;
			List<SpecialFriend> savedSFs = makeSpecialFriends(curBuffer);
			sizeFile -=curBuffer;
			curBuffer = sizeFile/2;
			List<Volunteer> savedVs = makeVolunteers(curBuffer);
			sizeFile -=curBuffer;
			makeTeamMembers(sizeFile);
			makeGroups(groupNames.size()/2,savedVs,savedSFs);
		}
		
		
	}
	
	public List<EmailGroup> makeGroups(int num, List<Volunteer> volunteers, List<SpecialFriend> specialFriends){
		List<EmailGroup> groups = new ArrayList<EmailGroup>();
		for (int i = 0; i < num; i++) {
			
			Random rand = new Random();
			int target = rand.nextInt(groupNames.size());
			if(target%2==1) target--;
			ArrayList<Role> roleList = new ArrayList<Role>();
			roleList.add(rgr.findByRoleName("VOLUNTEER"));
			EmailGroup eg = EmailGroup.builder()
					.description(groupNames.get(target+1))
					.name(groupNames.get(target))
					.SFNAccounts(new ArrayList<Account>())
					.specialFriends(new ArrayList<SpecialFriend>())
					.roles(roleList)
					.build();
			
			groupNames.remove(target);
			groupNames.remove(target);
			int amtPeople = rand.nextInt(5)+5;
			for(int j = 0; j < amtPeople; j++) {
				int chooser = rand.nextInt(2);
				if (chooser == 1) {
					int person = rand.nextInt(volunteers.size());
					if(!eg.getSFNAccounts().contains(volunteers.get(person).getAccount()))
					eg.getSFNAccounts().add(volunteers.get(person).getAccount());	
				}
				else {
					int person = rand.nextInt(specialFriends.size());
					if(!eg.getSpecialFriends().contains(specialFriends.get(person)))
					eg.getSpecialFriends().add(specialFriends.get(person));
				}
			}
			egr.save(eg);
		}
		return groups;
	}
	public List<Volunteer> makeVolunteers(int num){
		List<Volunteer> newVolunteers = new ArrayList<Volunteer>();
		
		
		for(int i = 0; i < num; i++) {
			
			Random rand = new Random();
			int targFN = rand.nextInt(firstNames.size());
			int targLN = rand.nextInt(lastNames.size());
			
			PersonalInfo pI = PersonalInfo.builder()
					.birthDate(LocalDate.of(rand.nextInt(30)+1990, rand.nextInt(12)+1, rand.nextInt(25)+1))
					.email(firstNames.get(targFN).toLowerCase() + lastNames.get(targLN).substring(0,1).toLowerCase() + "@gmail.com")
					.firstName(firstNames.get(targFN))
					.lastName(lastNames.get(targLN))
					.phoneNumber("too lazy to check")
					.build();
			AdditionalInfo aI = AdditionalInfo.builder()
					.additionalComments("Cool")
					.allergies("Some Allergy")
					.conditions("way too cool")
					.emergencyContactName("Hospital")
					.emergencyContactPhoneNumber("911")
					.emergencyContactRelation("Tax Payer")
					.favouriteEvents("Eating")
					.isPhotoPermissionSigned(true)
					.leastFavouriteEvents("Andy Pak Class")
					.needsToBeMet("Nothing")
					.build();
			pir.save(pI);
			ArrayList<Role> roleList = new ArrayList<Role>();
			roleList.add(rgr.findByRoleName("VOLUNTEER"));
			Account acc = Account.builder()
					.password("1234")
					.personalInfo(pI)
					.username("volunteer")
					.roles(roleList)
					.build();
			air.save(aI);
			acr.save(acc);
			
			Volunteer v1 = Volunteer.builder()
					.account(acc)
					.additionalInfo(aI)
					.hours(10)
					
					.build();
			vr.save(v1);
			
			newVolunteers.add(v1);
			System.out.println(firstNames);
			firstNames.remove(targFN);
			lastNames.remove(targLN);

		}
		return newVolunteers;
	}
	
	public List<TeamMember> makeTeamMembers(int num){
		List<TeamMember> teamMembers = new ArrayList<TeamMember>();
		
		for(int i = 0; i < num; i++) {
			
			Random rand = new Random();
			int targFN = rand.nextInt(firstNames.size());
			int targLN = rand.nextInt(lastNames.size());
			
			PersonalInfo pI = PersonalInfo.builder()
					.birthDate(LocalDate.of(rand.nextInt(30)+1990, rand.nextInt(12)+1, rand.nextInt(25)+1))
					.email(firstNames.get(targFN).toLowerCase() + lastNames.get(targLN).substring(0,1).toLowerCase() + "@gmail.com")
					.firstName(firstNames.get(targFN))
					.lastName(lastNames.get(targLN))
					.phoneNumber("too lazy to check")
					.build();
			AdditionalInfo aI = AdditionalInfo.builder()
					.additionalComments("Cool")
					.allergies("Some Allergy")
					.conditions("way too cool")
					.emergencyContactName("Hospital")
					.emergencyContactPhoneNumber("911")
					.emergencyContactRelation("Tax Payer")
					.favouriteEvents("Eating")
					.isPhotoPermissionSigned(true)
					.leastFavouriteEvents("Andy Pak Class")
					.needsToBeMet("Nothing")
					.build();
			pir.save(pI);
			ArrayList<Role> roleList = new ArrayList<Role>();
			roleList.add(rgr.findByRoleName("TEAM_MEMBER"));
			Account acc = Account.builder()
					.password("9999")
					.username(firstNames.get(targFN).toLowerCase() + lastNames.get(targLN).substring(0,1).toLowerCase() + targFN + "-" + targLN)
					.personalInfo(pI)
					.roles(roleList)
					.build();
			air.save(aI);
			acr.save(acc);
			
			
			
			
			TeamMember t1 = TeamMember.builder()
					.account(acc)
					.additionalInfo(aI)
					.build();
			tmr.save(t1);
			
			teamMembers.add(t1);
			System.out.println(firstNames);
			firstNames.remove(targFN);
			lastNames.remove(targLN);

		}
		return teamMembers;
		//yes
	}
	
	public List<SpecialFriend> makeSpecialFriends(int num){
		List<SpecialFriend> newFriends = new ArrayList<SpecialFriend>();
		
		
		for(int i = 0; i < num; i++) {
			
			Random rand = new Random();
			int targFN = rand.nextInt(firstNames.size());
			int targLN = rand.nextInt(lastNames.size());
			
			PersonalInfo pI = PersonalInfo.builder()
					.birthDate(LocalDate.of(rand.nextInt(30)+1990, rand.nextInt(12)+1, rand.nextInt(25)+1))
					.email(firstNames.get(targFN).toLowerCase() + lastNames.get(targLN).substring(0,1).toLowerCase() + "@gmail.com")
					.firstName(firstNames.get(targFN))
					.lastName(lastNames.get(targLN))
					.phoneNumber("too lazy to check")
					.build();
			AdditionalInfo aI = AdditionalInfo.builder()
					.additionalComments("Cool")
					.allergies("Some Allergy")
					.conditions("way too cool")
					.emergencyContactName("Hospital")
					.emergencyContactPhoneNumber("911")
					.emergencyContactRelation("Tax Payer")
					.favouriteEvents("Eating")
					.isPhotoPermissionSigned(true)
					.leastFavouriteEvents("Andy Pak Class")
					.needsToBeMet("Nothing")
					.build();
			air.save(aI);
			pir.save(pI);
			
			SpecialFriend sF = SpecialFriend.builder()
					.howToComfort("Hugs")
					.isCostAFactor(false)
					.isBirthdayClubMember(true)
					.additionalInfo(aI)
					.personalInfo(pI)
					.build();
			
			sfr.save(sF);
			newFriends.add(sF);
			System.out.println(firstNames);
			firstNames.remove(targFN);
			lastNames.remove(targLN);

		}
		return newFriends;
	}
	
	public void make403Industries() {
		
		ArrayList<Role> roleList = new ArrayList<Role>();
		roleList.add(rgr.findByRoleName("ADMIN"));
		
		PersonalInfo p1 = PersonalInfo.builder()
				.birthDate(LocalDate.of(2001, 8, 30))
				.email("alexander.brown0830@gmail.com")
				.firstName("Alexander")
				.lastName("Brown")
				.phoneNumber("647-504-5876")
				.build();
		pir.save(p1);
		Account acc1 = Account.builder()
				.password("adminLogin123")
				.username("alex")
				.personalInfo(p1)
				.roles(roleList)
				.build();
		acr.save(acc1);
		
		Admin ad1 = Admin.builder()
				.account(acc1)
				.build();
		adr.save(ad1);
		
		
		PersonalInfo p2 = PersonalInfo.builder()
				.birthDate(LocalDate.of(2001, 2, 20))
				.email("varunvadali1@gmail.com")
				.firstName("Varun")
				.lastName("Vadali")
				.phoneNumber("too lazy to check")
				.build();
		pir.save(p2);
		Account acc2 = Account.builder()
				.password("adminLogin123")
				.personalInfo(p2)
				.username("varun")
				.roles(roleList)
				.build();
		acr.save(acc2);
		Admin ad2 = Admin.builder()
				.account(acc2)
				.build();
		adr.save(ad2);
		//
		
		PersonalInfo p3 = PersonalInfo.builder()
				.birthDate(LocalDate.of(1996, 5, 27))
				.email("bradmonster96@gmail.com")
				.firstName("Bradley")
				.lastName("Persaud")
				.phoneNumber("too lazy to check")
				.build();
		pir.save(p3);
		Account acc3 = Account.builder()
				.password("adminLogin123")
				.personalInfo(p3)
				.username("brad")
				.roles(roleList)
				.build();
		acr.save(acc3);
		
		Admin ad3 = Admin.builder()
				.account(acc3)
				.build();
		adr.save(ad3);
		
		
		//
		
		PersonalInfo p4 = PersonalInfo.builder()
				.birthDate(LocalDate.of(2001, 2, 20))
				.email("bautistagenevievejose@gmail.com")
				.firstName("Genevieve")
				.lastName("Jose Bautista")
				.phoneNumber("too lazy to check")
				.build();
		
		pir.save(p4);
		Account acc4 = Account.builder()
				.password("adminLogin123")
				.username("gene")
				.personalInfo(p4)
				.roles(roleList)
				.build();
		acr.save(acc4);
		
		Admin ad4 = Admin.builder()
				.account(acc4)
				.build();
		adr.save(ad4);
		
		
		//sfr.save(s2);
		EmailGroup eg = EmailGroup.builder()
				.description("The best of the best")
				.name("403 INDUSTRIES")
				.SFNAccounts(new ArrayList<Account>())
				.specialFriends(new ArrayList<SpecialFriend>())
				.roles(roleList)
				.build();
		eg.getSFNAccounts().add(ad4.getAccount());
		eg.getSFNAccounts().add(ad3.getAccount());
		eg.getSFNAccounts().add(ad2.getAccount());
		eg.getSFNAccounts().add(ad1.getAccount());
		
		egr.save(eg);
	}
	
	
	public List<String> populateListFromFile(String nameFile){
		List<String> list = new ArrayList<String>();
		File file = new File("src/main/resources/static/data/"+nameFile);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				list.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found!");
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
		return list;
	}

}
