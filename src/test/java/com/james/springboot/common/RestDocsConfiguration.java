package com.james.springboot.common;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;

@TestConfiguration
public class RestDocsConfiguration {

	@Bean
	public RestDocsMockMvcConfigurationCustomizer   restDocsMockMvcConfigurationCustomizer(){
		
		return new RestDocsMockMvcConfigurationCustomizer(){

			@Override
			public void customize(MockMvcRestDocumentationConfigurer configurer) {
				// TODO Auto-generated method stub
				configurer.operationPreprocessors()
					.withRequestDefaults(prettyPrint())
					.withResponseDefaults(prettyPrint())
					;
			}
		
			
		};
	}
}
