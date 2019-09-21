package com.james.springboot.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class EventTest2 {
	
	private int basePrice;
	private int maxPrice;
	private String location;
	private Boolean expectedResult;
	private Event event;
	
	@Before
	public void initialize(){
		event = Event.builder().build();
	}
	
	public EventTest2( Integer basePrice, Integer maxPrice,Boolean expectedResult ) {
		this.basePrice = basePrice;
		this.maxPrice = maxPrice;
		this.expectedResult = expectedResult;
	}

	public EventTest2( String location,Boolean expectedResult ) {
		this.location = location;
		this.expectedResult = expectedResult;		
	}	
	
	public static Collection prices(){
		return Arrays.asList( new Object[][]{
			{0,0, true},
			{0,100, false}
		});
	}
	
	
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
	public void testFree(){
		/*
		// Given
		Event event = Event.builder()
				.basePrice(0)
				.maxPrice(0)
				.build();
		// When
		event.update();
		
		// Then
		assertThat(event.isFree()).isTrue();

		// Given
		event = Event.builder()
				.basePrice(0)
				.maxPrice(100)
				.build();
		// When
		event.update();
		
		// Then
		assertThat(event.isFree()).isFalse();		
		*/

	}
	
	@Test
	public void testOffline(){
		// Given
		Event event = Event.builder()
				.location("GangNam")
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isOffline()).isTrue();

		// Given
		event = Event.builder()
				.location("")
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isOffline()).isFalse();		
	}
	
}
