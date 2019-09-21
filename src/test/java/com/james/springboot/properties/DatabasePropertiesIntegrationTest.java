package com.james.springboot.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes ={ AnnotationProcessor.class} )
//@TestPropertySource("classpath:databaseproperties-test.properties")
public class DatabasePropertiesIntegrationTest {
	
	@Autowired
	private DatabaseProperties databaseProperties;
	
	@Test
	public void whenSimplePropertyQueriedThenReturnsPropertyValue() throws Exception{
		Assert.assertEquals("Incorrectly bound Username property", "jamesKoo", databaseProperties.getUsername() );
		Assert.assertEquals("Incorrectly bound password property", "jamesKoo", databaseProperties.getPassword() );
		
	}
	

}
