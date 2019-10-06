package com.james.springboot.accounts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

// import com.james.inflearnapi.accounts.AccountRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void findByUsername(){
		String userEmail = "james";
		
		Set<AccountRole> roleSet = new HashSet<AccountRole>();
		roleSet.add(AccountRole.ADMIN);
		roleSet.add(AccountRole.USER);
		
		Account account = Account.builder()
				.email(userEmail)
				.password("pswd")
				.roles(roleSet)
				.build()
				;
		
		accountRepository.save(account);
		
		UserDetailsService userDetailsService =  ( UserDetailsService )  accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());

		assertThat(userDetails.getPassword()).isEqualTo(account.getPassword()) ;
		
	}
	
	
	
	
	
}
