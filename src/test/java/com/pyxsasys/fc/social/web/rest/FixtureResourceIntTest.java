package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.Fixture;
import com.pyxsasys.fc.social.repository.FixtureRepository;
import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureMapper;

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
 * Test class for the FixtureResource REST controller.
 *
 * @see FixtureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FixtureResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_MATCH_ID = "AAAAA";
    private static final String UPDATED_MATCH_ID = "BBBBB";
    private static final String DEFAULT_TEAM_ONE_ID = "AAAAA";
    private static final String UPDATED_TEAM_ONE_ID = "BBBBB";
    private static final String DEFAULT_TEAM_TWO_ID = "AAAAA";
    private static final String UPDATED_TEAM_TWO_ID = "BBBBB";
    private static final String DEFAULT_TEAM_ONE = "AAAAA";
    private static final String UPDATED_TEAM_ONE = "BBBBB";
    private static final String DEFAULT_TEAM_TWO = "AAAAA";
    private static final String UPDATED_TEAM_TWO = "BBBBB";
    private static final String DEFAULT_TEAM_ONE_SCORE = "AAAAA";
    private static final String UPDATED_TEAM_ONE_SCORE = "BBBBB";
    private static final String DEFAULT_TEAM_TWO_SCORE = "AAAAA";
    private static final String UPDATED_TEAM_TWO_SCORE = "BBBBB";
    private static final String DEFAULT_MATCH_TIME = "AAAAA";
    private static final String UPDATED_MATCH_TIME = "BBBBB";
    private static final String DEFAULT_HT_SCORE = "AAAAA";
    private static final String UPDATED_HT_SCORE = "BBBBB";
    private static final String DEFAULT_FT_SCORE = "AAAAA";
    private static final String UPDATED_FT_SCORE = "BBBBB";

    private static final ZonedDateTime DEFAULT_MATCH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MATCH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MATCH_DATE_STR = dateTimeFormatter.format(DEFAULT_MATCH_DATE);

    @Inject
    private FixtureRepository fixtureRepository;

    @Inject
    private FixtureMapper fixtureMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFixtureMockMvc;

    private Fixture fixture;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FixtureResource fixtureResource = new FixtureResource();
        ReflectionTestUtils.setField(fixtureResource, "fixtureRepository", fixtureRepository);
        ReflectionTestUtils.setField(fixtureResource, "fixtureMapper", fixtureMapper);
        this.restFixtureMockMvc = MockMvcBuilders.standaloneSetup(fixtureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fixtureRepository.deleteAll();
        fixture = new Fixture();
        fixture.setMatchId(DEFAULT_MATCH_ID);
        fixture.setTeamOneId(DEFAULT_TEAM_ONE_ID);
        fixture.setTeamTwoId(DEFAULT_TEAM_TWO_ID);
        fixture.setTeamOne(DEFAULT_TEAM_ONE);
        fixture.setTeamTwo(DEFAULT_TEAM_TWO);
        fixture.setTeamOneScore(DEFAULT_TEAM_ONE_SCORE);
        fixture.setTeamTwoScore(DEFAULT_TEAM_TWO_SCORE);
        fixture.setMatchTime(DEFAULT_MATCH_TIME);
        fixture.setHtScore(DEFAULT_HT_SCORE);
        fixture.setFtScore(DEFAULT_FT_SCORE);
        fixture.setMatchDate(DEFAULT_MATCH_DATE);
    }

    @Test
    public void createFixture() throws Exception {
        int databaseSizeBeforeCreate = fixtureRepository.findAll().size();

        // Create the Fixture
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isCreated());

        // Validate the Fixture in the database
        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeCreate + 1);
        Fixture testFixture = fixtures.get(fixtures.size() - 1);
        assertThat(testFixture.getMatchId()).isEqualTo(DEFAULT_MATCH_ID);
        assertThat(testFixture.getTeamOneId()).isEqualTo(DEFAULT_TEAM_ONE_ID);
        assertThat(testFixture.getTeamTwoId()).isEqualTo(DEFAULT_TEAM_TWO_ID);
        assertThat(testFixture.getTeamOne()).isEqualTo(DEFAULT_TEAM_ONE);
        assertThat(testFixture.getTeamTwo()).isEqualTo(DEFAULT_TEAM_TWO);
        assertThat(testFixture.getTeamOneScore()).isEqualTo(DEFAULT_TEAM_ONE_SCORE);
        assertThat(testFixture.getTeamTwoScore()).isEqualTo(DEFAULT_TEAM_TWO_SCORE);
        assertThat(testFixture.getMatchTime()).isEqualTo(DEFAULT_MATCH_TIME);
        assertThat(testFixture.getHtScore()).isEqualTo(DEFAULT_HT_SCORE);
        assertThat(testFixture.getFtScore()).isEqualTo(DEFAULT_FT_SCORE);
        assertThat(testFixture.getMatchDate()).isEqualTo(DEFAULT_MATCH_DATE);
    }

    @Test
    public void checkMatchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setMatchId(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTeamOneIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setTeamOneId(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTeamTwoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setTeamTwoId(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTeamOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setTeamOne(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTeamTwoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setTeamTwo(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMatchDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureRepository.findAll().size();
        // set the field null
        fixture.setMatchDate(null);

        // Create the Fixture, which fails.
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(post("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isBadRequest());

        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFixtures() throws Exception {
        // Initialize the database
        fixtureRepository.save(fixture);

        // Get all the fixtures
        restFixtureMockMvc.perform(get("/api/fixtures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fixture.getId())))
                .andExpect(jsonPath("$.[*].matchId").value(hasItem(DEFAULT_MATCH_ID.toString())))
                .andExpect(jsonPath("$.[*].teamOneId").value(hasItem(DEFAULT_TEAM_ONE_ID.toString())))
                .andExpect(jsonPath("$.[*].teamTwoId").value(hasItem(DEFAULT_TEAM_TWO_ID.toString())))
                .andExpect(jsonPath("$.[*].teamOne").value(hasItem(DEFAULT_TEAM_ONE.toString())))
                .andExpect(jsonPath("$.[*].teamTwo").value(hasItem(DEFAULT_TEAM_TWO.toString())))
                .andExpect(jsonPath("$.[*].teamOneScore").value(hasItem(DEFAULT_TEAM_ONE_SCORE.toString())))
                .andExpect(jsonPath("$.[*].teamTwoScore").value(hasItem(DEFAULT_TEAM_TWO_SCORE.toString())))
                .andExpect(jsonPath("$.[*].matchTime").value(hasItem(DEFAULT_MATCH_TIME.toString())))
                .andExpect(jsonPath("$.[*].htScore").value(hasItem(DEFAULT_HT_SCORE.toString())))
                .andExpect(jsonPath("$.[*].ftScore").value(hasItem(DEFAULT_FT_SCORE.toString())))
                .andExpect(jsonPath("$.[*].matchDate").value(hasItem(DEFAULT_MATCH_DATE_STR)));
    }

    @Test
    public void getFixture() throws Exception {
        // Initialize the database
        fixtureRepository.save(fixture);

        // Get the fixture
        restFixtureMockMvc.perform(get("/api/fixtures/{id}", fixture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fixture.getId()))
            .andExpect(jsonPath("$.matchId").value(DEFAULT_MATCH_ID.toString()))
            .andExpect(jsonPath("$.teamOneId").value(DEFAULT_TEAM_ONE_ID.toString()))
            .andExpect(jsonPath("$.teamTwoId").value(DEFAULT_TEAM_TWO_ID.toString()))
            .andExpect(jsonPath("$.teamOne").value(DEFAULT_TEAM_ONE.toString()))
            .andExpect(jsonPath("$.teamTwo").value(DEFAULT_TEAM_TWO.toString()))
            .andExpect(jsonPath("$.teamOneScore").value(DEFAULT_TEAM_ONE_SCORE.toString()))
            .andExpect(jsonPath("$.teamTwoScore").value(DEFAULT_TEAM_TWO_SCORE.toString()))
            .andExpect(jsonPath("$.matchTime").value(DEFAULT_MATCH_TIME.toString()))
            .andExpect(jsonPath("$.htScore").value(DEFAULT_HT_SCORE.toString()))
            .andExpect(jsonPath("$.ftScore").value(DEFAULT_FT_SCORE.toString()))
            .andExpect(jsonPath("$.matchDate").value(DEFAULT_MATCH_DATE_STR));
    }

    @Test
    public void getNonExistingFixture() throws Exception {
        // Get the fixture
        restFixtureMockMvc.perform(get("/api/fixtures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFixture() throws Exception {
        // Initialize the database
        fixtureRepository.save(fixture);

		int databaseSizeBeforeUpdate = fixtureRepository.findAll().size();

        // Update the fixture
        fixture.setMatchId(UPDATED_MATCH_ID);
        fixture.setTeamOneId(UPDATED_TEAM_ONE_ID);
        fixture.setTeamTwoId(UPDATED_TEAM_TWO_ID);
        fixture.setTeamOne(UPDATED_TEAM_ONE);
        fixture.setTeamTwo(UPDATED_TEAM_TWO);
        fixture.setTeamOneScore(UPDATED_TEAM_ONE_SCORE);
        fixture.setTeamTwoScore(UPDATED_TEAM_TWO_SCORE);
        fixture.setMatchTime(UPDATED_MATCH_TIME);
        fixture.setHtScore(UPDATED_HT_SCORE);
        fixture.setFtScore(UPDATED_FT_SCORE);
        fixture.setMatchDate(UPDATED_MATCH_DATE);
        FixtureDTO fixtureDTO = fixtureMapper.fixtureToFixtureDTO(fixture);

        restFixtureMockMvc.perform(put("/api/fixtures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureDTO)))
                .andExpect(status().isOk());

        // Validate the Fixture in the database
        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeUpdate);
        Fixture testFixture = fixtures.get(fixtures.size() - 1);
        assertThat(testFixture.getMatchId()).isEqualTo(UPDATED_MATCH_ID);
        assertThat(testFixture.getTeamOneId()).isEqualTo(UPDATED_TEAM_ONE_ID);
        assertThat(testFixture.getTeamTwoId()).isEqualTo(UPDATED_TEAM_TWO_ID);
        assertThat(testFixture.getTeamOne()).isEqualTo(UPDATED_TEAM_ONE);
        assertThat(testFixture.getTeamTwo()).isEqualTo(UPDATED_TEAM_TWO);
        assertThat(testFixture.getTeamOneScore()).isEqualTo(UPDATED_TEAM_ONE_SCORE);
        assertThat(testFixture.getTeamTwoScore()).isEqualTo(UPDATED_TEAM_TWO_SCORE);
        assertThat(testFixture.getMatchTime()).isEqualTo(UPDATED_MATCH_TIME);
        assertThat(testFixture.getHtScore()).isEqualTo(UPDATED_HT_SCORE);
        assertThat(testFixture.getFtScore()).isEqualTo(UPDATED_FT_SCORE);
        assertThat(testFixture.getMatchDate()).isEqualTo(UPDATED_MATCH_DATE);
    }

    @Test
    public void deleteFixture() throws Exception {
        // Initialize the database
        fixtureRepository.save(fixture);

		int databaseSizeBeforeDelete = fixtureRepository.findAll().size();

        // Get the fixture
        restFixtureMockMvc.perform(delete("/api/fixtures/{id}", fixture.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fixture> fixtures = fixtureRepository.findAll();
        assertThat(fixtures).hasSize(databaseSizeBeforeDelete - 1);
    }
}
