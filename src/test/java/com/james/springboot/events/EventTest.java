package com.james.springboot.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
	
	@Test
	public void builder(){
		Event event = Event.builder()
				.name("Inflearn Spring REST API")
				.description("REST API development with Spring")
				.build();
		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean(){
		// @AllArgsConstructor @NoArgsConstructor @Getter @Setter annotations are required at the class
		// @EqualsAndHashCode(of="id") --> Entity object identifier for checking equality or identity
		Event event = new Event();
		String name = "Event";
		event.setName(name);
		event.setDescription("Spring");
		
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo("Spring");
		
	}
	
	@Test
	/*
	@Parameters({
		"0,0, true",
		"100,0,false ",
		"0,100, false"
	}) */
	@Parameters   // (method= "paramsForTestFree")
	public void testFree(int basePrice, int maxPrice, boolean isFree){

		// Given
		Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();
		// When
		event.update();
		
		// Then
		assertThat(event.isFree()).isEqualTo(isFree);

	}
	
	private Object[] parametersForTestFree(){ // convention :: "parametersFor" + method name 
		return new Object[]{
				new Object[]{ 0,0,true},
				new Object[]{ 100,0,false},
				new Object[]{ 0,100,false},
				new Object[]{ 100,200, false}				
		};
	}
	
	@Test
	@Parameters  // (method= "parametersForTestoffline")
	public void testOffline(String location, boolean isOffLine ){
		// Given
		Event event = Event.builder()
				.location(location)
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isOffline()).isEqualTo(isOffLine);

	}
	
	private Object[] parametersForTestOffline(){ // convention :: "parametersFor" + method name 
		return new Object[]{
				new Object[]{ "GangNam ",true},
				new Object[]{ "",false},
				new Object[]{ null,false},
				new Object[]{ "Yongsan ", true}				
		};
	}
}
