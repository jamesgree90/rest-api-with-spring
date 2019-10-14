package com.james.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.james.springboot.accounts.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AccountService accountService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	public TokenStore tokenStore(){
		return new InMemoryTokenStore();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(accountService)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/docs/index.html");
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		
	}
	
 	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous()
				.and()
			.formLogin()
				.and()
			.authorizeRequests()
		//	.mvcMatchers("/docs/index.html").anonymous()
				.mvcMatchers(HttpMethod.GET, "/api/**").authenticated()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).anonymous()
			// missing  .anonymous() causes issue 
		//	org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'springSecurityFilterChain' defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw exception; nested exception is java.lang.IllegalStateException: An incomplete mapping was found for [org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest$StaticResourceRequestMatcher@5cf7474f]. Try completing it with something like requestUrls().<something>.hasRole('USER')
			.anyRequest().authenticated()
		;
		
	} 
		
}
