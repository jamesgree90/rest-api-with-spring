package com.james.springboot.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.james.springboot.accounts.AccountService;
import com.james.springboot.common.BaseControllerTest;

public class AuthServerConfigTest extends BaseControllerTest {
	
	@Autowired
	AccountService accountService;
	
	@Test
	public void getAuthToken(){
		
	}
}
