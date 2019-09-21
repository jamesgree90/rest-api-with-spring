package com.james.springboot.events;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

	public EventResource(Event event, Link... links) {  // Link[] links --> cause compiler error at new EventResource 
		super(event, links);
		// TODO Auto-generated constructor stub
		add(linkTo(EventController.class).slash(event.getId()).withSelfRel());

	}
	

}


/*
public class EventResource extends ResourceSupport {
	
	@JsonUnwrapped
	private Event event;
	
	public EventResource(Event event ){
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}
}

*/