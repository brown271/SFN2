package ca.sheridancollege.fourothreeindustries.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.fourothreeindustries.domain.EmailGroup;
import ca.sheridancollege.fourothreeindustries.domain.SpecialFriend;
import ca.sheridancollege.fourothreeindustries.repos.SpecialFriendRepository;

@RestController
@RequestMapping("/api/sf")
public class SpecialFriendController {

	@Autowired
	private SpecialFriendRepository sfr;
	private int pageSize =  5;
	
	@GetMapping("/page/{page}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String getEmailGroups(@PathVariable Long page) {
		Pageable pageRequest = PageRequest.of(page.intValue(), pageSize);
		//System.out.println(egr.findAll(pageRequest));
		Page<SpecialFriend> yup = sfr.findAll(pageRequest);
		String json ="[";
		for (SpecialFriend eg: yup.toList()) {
			json+= eg.JSONify() + ",";
		}
		json = (json.substring(0,json.length()-1) + "]");
		return json;
	}
}
