package com.james.springboot.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.james.springboot.accounts.Account;
import com.james.springboot.accounts.AccountRole;
import com.james.springboot.accounts.AccountService;
import com.james.springboot.common.BaseControllerTest;

public class AuthServerConfigTest extends BaseControllerTest {
	
	@Autowired
	AccountService accountService;
	
	@Test  // authToken test 
	public void getAuthToken() throws Exception{
		// Given 
		String clientId = "myApp";
		String clientSecret = "pass";
		String username = "jkoo@gmail.com";
		String password = "pswd";
		Set<AccountRole> roleSet = new HashSet<AccountRole>();
		roleSet.add(AccountRole.ADMIN);
		roleSet.add(AccountRole.USER);
		
		Account james = Account.builder()
			.email(username)
			.password(password)
			.roles(roleSet)
			.build();
		
		this.accountService.saveAccount(james);

	 	this.mockMvc.perform(post("/oauth/token")
					.with(httpBasic(clientId, clientSecret))
					.param("username", username)
					.param("password", password)
					.param("grant_type", "password"))
	 			.andDo(print())
	 			.andExpect(status().isOk())
	 			.andExpect(jsonPath("access_token").exists())
	 			;
		
	 
	}
}
