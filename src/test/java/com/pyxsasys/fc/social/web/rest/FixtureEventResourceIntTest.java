package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.FixtureEvent;
import com.pyxsasys.fc.social.repository.FixtureEventRepository;
import com.pyxsasys.fc.social.web.rest.dto.FixtureEventDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureEventMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialFixtureEventTypeEnum;

/**
 * Test class for the FixtureEventResource REST controller.
 *
 * @see FixtureEventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FixtureEventResourceIntTest {

    private static final String DEFAULT_EVENT_ID = "AAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBB";
    private static final String DEFAULT_MATCH_ID = "AAAAA";
    private static final String UPDATED_MATCH_ID = "BBBBB";
    private static final String DEFAULT_EVENT_MINUTE = "AAAAA";
    private static final String UPDATED_EVENT_MINUTE = "BBBBB";
    private static final String DEFAULT_EVENT_TEAM = "AAAAA";
    private static final String UPDATED_EVENT_TEAM = "BBBBB";
    private static final String DEFAULT_EVENT_PLAYER = "AAAAA";
    private static final String UPDATED_EVENT_PLAYER = "BBBBB";


private static final FCSocialFixtureEventTypeEnum DEFAULT_EVENT_TYPE = FCSocialFixtureEventTypeEnum.yellowcard;
    private static final FCSocialFixtureEventTypeEnum UPDATED_EVENT_TYPE = FCSocialFixtureEventTypeEnum.redcard;
    private static final String DEFAULT_EVENT_RESULT = "AAAAA";
    private static final String UPDATED_EVENT_RESULT = "BBBBB";

    @Inject
    private FixtureEventRepository fixtureEventRepository;

    @Inject
    private FixtureEventMapper fixtureEventMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFixtureEventMockMvc;

    private FixtureEvent fixtureEvent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FixtureEventResource fixtureEventResource = new FixtureEventResource();
        ReflectionTestUtils.setField(fixtureEventResource, "fixtureEventRepository", fixtureEventRepository);
        ReflectionTestUtils.setField(fixtureEventResource, "fixtureEventMapper", fixtureEventMapper);
        this.restFixtureEventMockMvc = MockMvcBuilders.standaloneSetup(fixtureEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fixtureEventRepository.deleteAll();
        fixtureEvent = new FixtureEvent();
        fixtureEvent.setEventId(DEFAULT_EVENT_ID);
        fixtureEvent.setMatchId(DEFAULT_MATCH_ID);
        fixtureEvent.setEventMinute(DEFAULT_EVENT_MINUTE);
        fixtureEvent.setEventTeam(DEFAULT_EVENT_TEAM);
        fixtureEvent.setEventPlayer(DEFAULT_EVENT_PLAYER);
        fixtureEvent.setEventType(DEFAULT_EVENT_TYPE);
        fixtureEvent.setEventResult(DEFAULT_EVENT_RESULT);
    }

    @Test
    public void createFixtureEvent() throws Exception {
        int databaseSizeBeforeCreate = fixtureEventRepository.findAll().size();

        // Create the FixtureEvent
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isCreated());

        // Validate the FixtureEvent in the database
        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeCreate + 1);
        FixtureEvent testFixtureEvent = fixtureEvents.get(fixtureEvents.size() - 1);
        assertThat(testFixtureEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testFixtureEvent.getMatchId()).isEqualTo(DEFAULT_MATCH_ID);
        assertThat(testFixtureEvent.getEventMinute()).isEqualTo(DEFAULT_EVENT_MINUTE);
        assertThat(testFixtureEvent.getEventTeam()).isEqualTo(DEFAULT_EVENT_TEAM);
        assertThat(testFixtureEvent.getEventPlayer()).isEqualTo(DEFAULT_EVENT_PLAYER);
        assertThat(testFixtureEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testFixtureEvent.getEventResult()).isEqualTo(DEFAULT_EVENT_RESULT);
    }

    @Test
    public void checkEventIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventId(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMatchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setMatchId(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventMinute(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventTeamIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventTeam(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventPlayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventPlayer(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventType(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEventResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureEventRepository.findAll().size();
        // set the field null
        fixtureEvent.setEventResult(null);

        // Create the FixtureEvent, which fails.
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(post("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFixtureEvents() throws Exception {
        // Initialize the database
        fixtureEventRepository.save(fixtureEvent);

        // Get all the fixtureEvents
        restFixtureEventMockMvc.perform(get("/api/fixtureEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fixtureEvent.getId())))
                .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID.toString())))
                .andExpect(jsonPath("$.[*].matchId").value(hasItem(DEFAULT_MATCH_ID.toString())))
                .andExpect(jsonPath("$.[*].eventMinute").value(hasItem(DEFAULT_EVENT_MINUTE.toString())))
                .andExpect(jsonPath("$.[*].eventTeam").value(hasItem(DEFAULT_EVENT_TEAM.toString())))
                .andExpect(jsonPath("$.[*].eventPlayer").value(hasItem(DEFAULT_EVENT_PLAYER.toString())))
                .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].eventResult").value(hasItem(DEFAULT_EVENT_RESULT.toString())));
    }

    @Test
    public void getFixtureEvent() throws Exception {
        // Initialize the database
        fixtureEventRepository.save(fixtureEvent);

        // Get the fixtureEvent
        restFixtureEventMockMvc.perform(get("/api/fixtureEvents/{id}", fixtureEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fixtureEvent.getId()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID.toString()))
            .andExpect(jsonPath("$.matchId").value(DEFAULT_MATCH_ID.toString()))
            .andExpect(jsonPath("$.eventMinute").value(DEFAULT_EVENT_MINUTE.toString()))
            .andExpect(jsonPath("$.eventTeam").value(DEFAULT_EVENT_TEAM.toString()))
            .andExpect(jsonPath("$.eventPlayer").value(DEFAULT_EVENT_PLAYER.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.eventResult").value(DEFAULT_EVENT_RESULT.toString()));
    }

    @Test
    public void getNonExistingFixtureEvent() throws Exception {
        // Get the fixtureEvent
        restFixtureEventMockMvc.perform(get("/api/fixtureEvents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFixtureEvent() throws Exception {
        // Initialize the database
        fixtureEventRepository.save(fixtureEvent);

		int databaseSizeBeforeUpdate = fixtureEventRepository.findAll().size();

        // Update the fixtureEvent
        fixtureEvent.setEventId(UPDATED_EVENT_ID);
        fixtureEvent.setMatchId(UPDATED_MATCH_ID);
        fixtureEvent.setEventMinute(UPDATED_EVENT_MINUTE);
        fixtureEvent.setEventTeam(UPDATED_EVENT_TEAM);
        fixtureEvent.setEventPlayer(UPDATED_EVENT_PLAYER);
        fixtureEvent.setEventType(UPDATED_EVENT_TYPE);
        fixtureEvent.setEventResult(UPDATED_EVENT_RESULT);
        FixtureEventDTO fixtureEventDTO = fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent);

        restFixtureEventMockMvc.perform(put("/api/fixtureEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureEventDTO)))
                .andExpect(status().isOk());

        // Validate the FixtureEvent in the database
        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeUpdate);
        FixtureEvent testFixtureEvent = fixtureEvents.get(fixtureEvents.size() - 1);
        assertThat(testFixtureEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testFixtureEvent.getMatchId()).isEqualTo(UPDATED_MATCH_ID);
        assertThat(testFixtureEvent.getEventMinute()).isEqualTo(UPDATED_EVENT_MINUTE);
        assertThat(testFixtureEvent.getEventTeam()).isEqualTo(UPDATED_EVENT_TEAM);
        assertThat(testFixtureEvent.getEventPlayer()).isEqualTo(UPDATED_EVENT_PLAYER);
        assertThat(testFixtureEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testFixtureEvent.getEventResult()).isEqualTo(UPDATED_EVENT_RESULT);
    }

    @Test
    public void deleteFixtureEvent() throws Exception {
        // Initialize the database
        fixtureEventRepository.save(fixtureEvent);

		int databaseSizeBeforeDelete = fixtureEventRepository.findAll().size();

        // Get the fixtureEvent
        restFixtureEventMockMvc.perform(delete("/api/fixtureEvents/{id}", fixtureEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FixtureEvent> fixtureEvents = fixtureEventRepository.findAll();
        assertThat(fixtureEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
