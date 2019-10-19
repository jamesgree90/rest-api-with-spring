package com.james.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.james.springboot.accounts.AccountService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;  // contains user login info data 
	
	@Autowired
	TokenStore tokenStore;
	
	@Autowired
	AccountService accountService;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		// 1. passwordEncoder set
		// 2. 
		security.passwordEncoder(passwordEncoder);
		//	super.configure(security);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(clients);
		// client id assignment
		// clients.jdbc(dataSource) --> real storage 
		clients.inMemory()   // just simple test case 
			.withClient("myApp")
			.authorizedGrantTypes("password","refresh_token")
			.scopes("read", "write")
			.secret(this.passwordEncoder.encode("pass"))
			.accessTokenValiditySeconds(10 * 60)
			.refreshTokenValiditySeconds( 6 * 10 * 60)
			;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(endpoints);
		// authenticationManager bean registration
		// 
		endpoints.tokenStore(tokenStore)
			.userDetailsService(accountService)
			.authenticationManager(authenticationManager)
		
		;
	}
	
}
