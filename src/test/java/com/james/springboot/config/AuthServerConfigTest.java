package com.james.springboot.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.james.springboot.accounts.Account;
import com.james.springboot.accounts.AccountRepository;
import com.james.springboot.accounts.AccountRole;
import com.james.springboot.accounts.AccountService;
import com.james.springboot.common.BaseControllerTest;
import com.james.springboot.events.common.AppProperties;

public class AuthServerConfigTest extends BaseControllerTest {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AppProperties appProperties;

	@Autowired
	AccountRepository accountRepository;
	/*
	@Before
	public void setUp(){
		this.accountRepository.deleteAll();
	}
	*/
	@Test  // authToken test 
	public void getAuthToken() throws Exception{
		// Given 
		/*
		String clientId = appProperties.getClientId(); //   "myApp";
		String clientSecret = appProperties.getClientSecret(); //   "pass";
		String username =  appProperties.getUserUsername(); //  "jkoo@gmail.com";
		String password =  appProperties.getUserPassword(); //    "pswd";
		Set<AccountRole> roleSet = new HashSet<AccountRole>();
		roleSet.add(AccountRole.ADMIN);
		roleSet.add(AccountRole.USER);

		Account user = Account.builder()
			.email(username)
			.password(password)
			.roles(roleSet)
			.build();
 
	 	this.accountService.saveAccount(user);
	 	*/
		
	 	this.mockMvc.perform(post("/oauth/token")
					.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
					.param("username", appProperties.getUserUsername())
					.param("password", appProperties.getUserPassword())
					.param("grant_type", "password"))
	 			.andDo(print())
	 			.andExpect(status().isOk())
	 			.andExpect(jsonPath("access_token").exists())
	 			;
	}
}
