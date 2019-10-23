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
import com.james.springboot.events.common.AppProperties;

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

	@Bean
	public ApplicationRunner applicationRunner(){ // This will generate one user when Spring boot app runs
		return new ApplicationRunner(){
			
			@Autowired
			AccountService accountService;
			
			@Autowired
			AppProperties appProperties;
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				String adminEmail = appProperties.getAdminUsername(); // "admin@email.com";
				
				Set<AccountRole> adminRoleSet = new HashSet<AccountRole>();
				adminRoleSet.add(AccountRole.ADMIN);
				adminRoleSet.add(AccountRole.USER);
				String adminPassword = appProperties.getAdminPassword();//         "admin";
				Account admin = Account.builder()
						.email(adminEmail)
						.password(adminPassword)
						.roles(adminRoleSet)
						.build()
						;
				accountService.saveAccount(admin);

				Set<AccountRole> userRoleSet = new HashSet<AccountRole>();
				userRoleSet.add(AccountRole.USER);
				
				Account user = Account.builder()
						.email( appProperties.getUserUsername()) //     "user@email.com")
						.password( appProperties.getUserPassword())  //     "user")
						.roles(userRoleSet)
						.build()
						;
				accountService.saveAccount(user);				
				
				
			}

		};
	}

}
