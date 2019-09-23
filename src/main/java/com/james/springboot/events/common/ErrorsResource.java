package com.james.springboot.events.common;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

import com.james.springboot.events.EventController;
import com.james.springboot.index.IndexController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ErrorsResource extends Resource<Errors> {

	public ErrorsResource(Errors errors, Link... links) {
		super(errors, links);
		// TODO Auto-generated constructor stub
		add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
	}

	
}
