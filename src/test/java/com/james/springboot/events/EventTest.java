package com.james.springboot.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
	
}
