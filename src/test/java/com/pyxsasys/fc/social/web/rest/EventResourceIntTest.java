package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.Event;
import com.pyxsasys.fc.social.repository.EventRepository;
import com.pyxsasys.fc.social.web.rest.dto.EventDTO;
import com.pyxsasys.fc.social.web.rest.mapper.EventMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_EVENT_DATE_STR = dateTimeFormatter.format(DEFAULT_EVENT_DATE);
    private static final String DEFAULT_VENUE = "AAAAA";
    private static final String UPDATED_VENUE = "BBBBB";
    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";

    @Inject
    private EventRepository eventRepository;

    @Inject
    private EventMapper eventMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEventMockMvc;

    private Event event;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventResource eventResource = new EventResource();
        ReflectionTestUtils.setField(eventResource, "eventRepository", eventRepository);
        ReflectionTestUtils.setField(eventResource, "eventMapper", eventMapper);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eventRepository.deleteAll();
        event = new Event();
        event.setName(DEFAULT_NAME);
        event.setDescription(DEFAULT_DESCRIPTION);
        event.setEventDate(DEFAULT_EVENT_DATE);
        event.setVenue(DEFAULT_VENUE);
        event.setImage(DEFAULT_IMAGE);
    }

    @Test
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.eventToEventDTO(event);

        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
                .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testEvent.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testEvent.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setName(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.eventToEventDTO(event);

        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
                .andExpect(status().isBadRequest());

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setDescription(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.eventToEventDTO(event);

        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
                .andExpect(status().isBadRequest());

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEventDate(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.eventToEventDTO(event);

        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
                .andExpect(status().isBadRequest());

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get all the events
        restEventMockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE_STR)))
                .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(event.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE_STR))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

		int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        event.setName(UPDATED_NAME);
        event.setDescription(UPDATED_DESCRIPTION);
        event.setEventDate(UPDATED_EVENT_DATE);
        event.setVenue(UPDATED_VENUE);
        event.setImage(UPDATED_IMAGE);
        EventDTO eventDTO = eventMapper.eventToEventDTO(event);

        restEventMockMvc.perform(put("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
                .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testEvent.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testEvent.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

		int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeDelete - 1);
    }
}
