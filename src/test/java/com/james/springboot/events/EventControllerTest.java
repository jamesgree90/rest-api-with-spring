package com.james.springboot.events;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.springboot.common.RestDocsConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest   // // @WebMvcTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")   // will use application-test.properties 
public class EventControllerTest {
	@Autowired
	MockMvc mockMvc; // No webserver required, not unit test DispatcherServlet is involved
	
	@Autowired
	ObjectMapper objectMapper;
	
//	@MockBean
//	EventRepository eventRepository; // this returns always null 
	
	@Test
	public void createEvent() throws Exception {

		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development")
				.beginEventDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.endEventDateTime(LocalDateTime.of(2019,11,16,23,59,59))
				.beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 16, 10,0,0))				
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("GangNam")
	 	//	 	.id(20)
	 	//		.free(true)
	 	//		.offline(false)
	 	//		.eventStatus(EventStatus.PUBLISHED)
				.build();
	
	//	event.setId(50);
	// 	Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event))
				)
			.andDo(print())
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("id").exists())
			   .andExpect(header().exists(HttpHeaders.LOCATION))
			   .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
			   .andExpect(jsonPath("id").value(Matchers.not(100)))
			   .andExpect(jsonPath("free").value(false))
			   .andExpect(jsonPath("offline").value(true))
			   .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
			   .andExpect(jsonPath("_links.self").exists())
			   .andExpect(jsonPath("_links.query-events").exists())
			   .andExpect(jsonPath("_links.update-event").exists())	
			   .andDo(document("create-event",
					   links(
						 linkWithRel("self").description("link to self"),
						 linkWithRel("query-events").description("link to query events"),
						 linkWithRel("update-event").description("link to update-event"),
						 linkWithRel("profile").description("link to profile")
					   ),
					   requestHeaders(
							   headerWithName(HttpHeaders.ACCEPT).description("accept-header"),
							   headerWithName(HttpHeaders.CONTENT_TYPE).description("content-type")
					   ),
					   requestFields(
							   fieldWithPath("name").description("Name of new Event"),
							   fieldWithPath("description").description("Description of new Event"),
							   fieldWithPath("beginEnrollmentDateTime").description("data time of begin Enrollment"),
							   fieldWithPath("closeEnrollmentDateTime").description("data time of close Enrollment"),
							   fieldWithPath("endEventDateTime").description("data time of Event end"),
							   fieldWithPath("beginEventDateTime").description("data time of Event begin"),
							   fieldWithPath("location").description(" event location address"),
							   fieldWithPath("basePrice").description("base price of event"),
							   fieldWithPath("maxPrice").description("max price of event"),
							   fieldWithPath("limitOfEnrollment").description("limit of  Enrollment")
					   ),
					   responseFields( //  responseFields( -> strict 
							   fieldWithPath("id").description(" Event id"),							   
							   fieldWithPath("name").description("Name of new Event"),
							   fieldWithPath("description").description("Description of new Event"),
							   fieldWithPath("beginEnrollmentDateTime").description("data time of begin Enrollment"),
							   fieldWithPath("closeEnrollmentDateTime").description("data time of close Enrollment"),
							   fieldWithPath("endEventDateTime").description("data time of Event end"),
							   fieldWithPath("beginEventDateTime").description("data time of Event begin"),
							   fieldWithPath("location").description(" event location address"),
							   fieldWithPath("basePrice").description("base price of event"),
							   fieldWithPath("maxPrice").description("max price of event"),
							   fieldWithPath("limitOfEnrollment").description("limit of  Enrollment"),
							   fieldWithPath("free").description(" check event is free"),
							   fieldWithPath("offline").description(" check event is offline"),
							   fieldWithPath("eventStatus").description(" status of event"),							   
							   fieldWithPath("_links.self.href").description(" self link "),
							   fieldWithPath("_links.query-events.href").description(" query-events link"), 
							   fieldWithPath("_links.update-event.href").description(" update-event link"), 			   
							   fieldWithPath("_links.profile.href").description(" profile link") 				   
					   ),
					   responseHeaders(
							   headerWithName(HttpHeaders.LOCATION).description("Location header"),
							   headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
					   )
					   
					   
					   ))
			   ;  // response code = 201
	}
	
	@Test
	public void createEvent_Bad_Request() throws Exception {

		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development")
				.beginEventDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.endEventDateTime(LocalDateTime.of(2019,11,11,23,59,59))
				.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 16, 10,0,0))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("GangNam")
			 	.id(100)
				.free(true)
				.offline(false)
				.eventStatus(EventStatus.DRAFT)
				.build();

		/*		
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API Development")
				.beginEventDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.endEventDateTime(LocalDateTime.of(2019,11,11,23,59,59))
				.beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("GangNam")
				.build();
		*/		
	//	event.setId(50);
	// 	Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event))
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
			   ;  // response code = 201
	}
	
	@Test
	public void createEvent_Bad_Request_Empty_Input() throws Exception{
		EventDto eventDto = EventDto.builder().build();
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(eventDto))
				)
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createEvent_Bad_Request_Wrong_Input() throws Exception{
		
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API Development")
				.beginEventDateTime(LocalDateTime.of(2019,10,16, 10,0,0))
				.endEventDateTime(LocalDateTime.of(2019,10,11,23,59,59))
				.beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 16, 10,0,0))
				.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 16, 10,0,0))				
				.basePrice(10000)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("GangNam")
				.build();

		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(eventDto))
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("content[0].objectName").exists())  // json unwrapped not successful for array
				.andExpect(jsonPath("content[0].defaultMessage").exists())
			 	.andExpect(jsonPath("content[0].code").exists())	
			 	
			//	.andExpect(jsonPath("$[0].objectName").exists())
			//	.andExpect(jsonPath("$[0].defaultMessage").exists())
			// 	.andExpect(jsonPath("$[0].code").exists())				
			//	.andExpect(jsonPath("$[0].field").exists())				
			//	.andExpect(jsonPath("$[0].rejectedValue").exists())
			 	.andExpect(jsonPath("_links.index").exists())
				;
	}	
	
	
}
