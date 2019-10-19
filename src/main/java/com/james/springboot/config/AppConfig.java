package com.james.springboot.config;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.james.springboot.accounts.Account;
import com.james.springboot.accounts.AccountRole;
import com.james.springboot.accounts.AccountService;

@Configuration
public class AppConfig {
	
	@Bean
	public ModelMapper modelMapper(){  // Bean registration
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	/*
	@Bean
	public ApplicationRunner applicationRunner(){ // This will generate one user when Spring boot app runs
		return new ApplicationRunner(){
			
			@Autowired
			AccountService accountService;
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				String userEmail = "james@gmail.com";
				
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
				accountService.saveAccount(account);
				
			}

		};
	}
	
	*/
}
