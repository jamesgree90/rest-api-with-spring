package com.james.springboot.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.james.springboot.events.common.ErrorsResource;


@Controller
@RequestMapping(value="/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
	// first way : auto inject
	//@Autowired
	//EventRepository eventRepository;
	
	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;
	private final EventValidator eventValidator;
	
	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator){
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}
	
	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid  EventDto eventDto // Event event
		,Errors errors	){
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		this.eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			return badRequest(errors);		
		}
		Event event =	modelMapper.map(eventDto, Event.class);
		event.update(); // biz logic --> Service class role 
	    Event newEvent = this.eventRepository.save(event);
		
	//	event.setId(250);
	//	URI createUri = linkTo(methodOn(EventController.class).createEvent()).slash("{id}").toUri();

	//	return ResponseEntity.created(createUri).build();
		
		ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI createUri = selfLinkBuilder.toUri();
		EventResource eventResource = new EventResource(event);
		eventResource.add(linkTo(EventController.class).withRel("query-events"));
	//	eventResource.add(selfLinkBuilder.withSelfRel());
		eventResource.add(selfLinkBuilder.withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile") );
		
		return ResponseEntity.created(createUri).body(eventResource);
	}
	
	@GetMapping
	public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
		Page<Event> page = this.eventRepository.findAll(pageable);
	//	PagedResources<Resource<Event>> resources = assembler.toResource(page); // only page _link is available
		PagedResources<Resource<Event>> pagedResources = assembler.toResource(page, e -> new EventResource(e)); // individual event links are available as well as page link
		pagedResources.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));

		return ResponseEntity.ok(pagedResources);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity getEvent(@PathVariable Integer id ){
		Optional<Event> optionalEvent = this.eventRepository.findById(id); //   .getOne(id); caused issue on Hibernate
		
		if(!optionalEvent.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Event event = optionalEvent.get();
		EventResource eventResource = new EventResource(event);
		eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
		return ResponseEntity.ok(eventResource);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity updateEvent(@PathVariable Integer id, 
									  @RequestBody @Valid EventDto eventDto, 
									  Errors errors ){
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		
		if(!optionalEvent.isPresent()){
			return ResponseEntity.notFound().build();
		}
				
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		this.eventValidator.validate(eventDto, errors);
		
		if(errors.hasErrors()) {		
			return badRequest(errors);		
		}
		

		Event existingEvent = optionalEvent.get();
		this.modelMapper.map(eventDto, existingEvent );
		
		Event updatedEvent =this.eventRepository.save(existingEvent );

		EventResource eventResource = new EventResource(updatedEvent);
		
		eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
		
		return ResponseEntity.ok(eventResource);
	}

	private ResponseEntity badRequest(Errors errors) {
			return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
	
	
	
	
}
