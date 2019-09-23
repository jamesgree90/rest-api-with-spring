package com.james.springboot.index;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.james.springboot.events.EventController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class IndexController {
	
	@GetMapping("/api")
	public ResourceSupport index(){
		//return new Link("_links.events");
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(EventController.class).withRel("events"));
		return index;
		// ??  .add(new Link("_links.events"));
	}

}
