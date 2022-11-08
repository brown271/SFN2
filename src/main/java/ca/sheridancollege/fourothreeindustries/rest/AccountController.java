package ca.sheridancollege.fourothreeindustries.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import ca.sheridancollege.fourothreeindustries.repos.AccountRepository;

@RestController
@RequestMapping("/api/acc")
public class AccountController {

	@Autowired
	private AccountRepository acr;
	private int pageSize =  5;
	
	@CrossOrigin()
	@GetMapping("/page/{page}")
	public String getEmailGroups(@PathVariable Long page) {
		System.out.println("Page Request For page " + page);
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<Account> yup = acr.findAll(pageRequest);
		String json ="[";
		for (Account eg: yup.toList()) {
			json+= eg.simpleJSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		return json;
	}
}
