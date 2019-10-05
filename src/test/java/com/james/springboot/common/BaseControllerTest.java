package com.james.springboot.common;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest           // @WebMvcTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")   // will use application-test.properties 
@Ignore              // prevent this class from running test method -> This class is an abstraction 
public class BaseControllerTest {
	
	@Autowired
	protected MockMvc mockMvc; // No webserver required, not unit test DispatcherServlet is involved
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected ModelMapper modelMapper;
	
}
