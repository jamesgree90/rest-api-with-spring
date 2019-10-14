package com.james.springboot.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

// import com.james.inflearnapi.accounts.AccountRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void findByUsername(){
		String userEmail = "james@hotmail.com";
		
		Set<AccountRole> roleSet = new HashSet<AccountRole>();
		roleSet.add(AccountRole.ADMIN);
		roleSet.add(AccountRole.USER);
		String password = "pswd";
		Account account = Account.builder()
				.email(userEmail)
				.password(password)
				.roles(roleSet)
				.build()
				;
		
		// accountRepository.save(account);
		accountService.saveAccount(account);
		
		UserDetailsService userDetailsService =  ( UserDetailsService )  accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());

	//	assertThat(userDetails.getPassword()).isEqualTo(account.getPassword()) ;
		assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())) ;
		
	}
	
	@Test // (expected = UsernameNotFoundException.class)
	public void findByUsernameFailed(){
		String username = "nonExisting@email.com";
		/*// First way
		try{
		accountService.loadUserByUsername(username);
			fail("supposed to be failed ");
		} catch(UsernameNotFoundException e) {
			assertThat(e.getMessage()).contains(username);
		}
		*/
		
		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(username));
		accountService.loadUserByUsername(username);		

	}
	
	
	
}
