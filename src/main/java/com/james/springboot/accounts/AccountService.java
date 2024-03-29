package com.james.springboot.accounts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Account saveAccount( Account account) {
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Account account = // accountRepository.findbyEmail(username) //findby -> caused issue, findBy -> correct 
				accountRepository.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));

		return new User(account.getEmail(),account.getPassword(), authorities(account.getRoles()));

	}

	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		// TODO Auto-generated method stub
		//return null;
		return roles.stream()
				.map(r ->  new SimpleGrantedAuthority("ROLE_" + r.name()))
				.collect(Collectors.toSet());
	}



}
