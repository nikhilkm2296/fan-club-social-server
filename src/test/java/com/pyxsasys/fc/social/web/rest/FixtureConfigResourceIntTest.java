package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.FixtureConfig;
import com.pyxsasys.fc.social.repository.FixtureConfigRepository;
import com.pyxsasys.fc.social.web.rest.dto.FixtureConfigDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureConfigMapper;

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


/**
 * Test class for the FixtureConfigResource REST controller.
 *
 * @see FixtureConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FixtureConfigResourceIntTest {

    private static final String DEFAULT_SOURCE_CLASS = "AAAAA";
    private static final String UPDATED_SOURCE_CLASS = "BBBBB";
    private static final String DEFAULT_SOURCE_PROCESSOR_CLASS = "AAAAA";
    private static final String UPDATED_SOURCE_PROCESSOR_CLASS = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Inject
    private FixtureConfigRepository fixtureConfigRepository;

    @Inject
    private FixtureConfigMapper fixtureConfigMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFixtureConfigMockMvc;

    private FixtureConfig fixtureConfig;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FixtureConfigResource fixtureConfigResource = new FixtureConfigResource();
        ReflectionTestUtils.setField(fixtureConfigResource, "fixtureConfigRepository", fixtureConfigRepository);
        ReflectionTestUtils.setField(fixtureConfigResource, "fixtureConfigMapper", fixtureConfigMapper);
        this.restFixtureConfigMockMvc = MockMvcBuilders.standaloneSetup(fixtureConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fixtureConfigRepository.deleteAll();
        fixtureConfig = new FixtureConfig();
        fixtureConfig.setSourceClass(DEFAULT_SOURCE_CLASS);
        fixtureConfig.setSourceProcessorClass(DEFAULT_SOURCE_PROCESSOR_CLASS);
        fixtureConfig.setActive(DEFAULT_ACTIVE);
    }

    @Test
    public void createFixtureConfig() throws Exception {
        int databaseSizeBeforeCreate = fixtureConfigRepository.findAll().size();

        // Create the FixtureConfig
        FixtureConfigDTO fixtureConfigDTO = fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig);

        restFixtureConfigMockMvc.perform(post("/api/fixtureConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureConfigDTO)))
                .andExpect(status().isCreated());

        // Validate the FixtureConfig in the database
        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeCreate + 1);
        FixtureConfig testFixtureConfig = fixtureConfigs.get(fixtureConfigs.size() - 1);
        assertThat(testFixtureConfig.getSourceClass()).isEqualTo(DEFAULT_SOURCE_CLASS);
        assertThat(testFixtureConfig.getSourceProcessorClass()).isEqualTo(DEFAULT_SOURCE_PROCESSOR_CLASS);
        assertThat(testFixtureConfig.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void checkSourceClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureConfigRepository.findAll().size();
        // set the field null
        fixtureConfig.setSourceClass(null);

        // Create the FixtureConfig, which fails.
        FixtureConfigDTO fixtureConfigDTO = fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig);

        restFixtureConfigMockMvc.perform(post("/api/fixtureConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureConfigDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSourceProcessorClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureConfigRepository.findAll().size();
        // set the field null
        fixtureConfig.setSourceProcessorClass(null);

        // Create the FixtureConfig, which fails.
        FixtureConfigDTO fixtureConfigDTO = fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig);

        restFixtureConfigMockMvc.perform(post("/api/fixtureConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureConfigDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixtureConfigRepository.findAll().size();
        // set the field null
        fixtureConfig.setActive(null);

        // Create the FixtureConfig, which fails.
        FixtureConfigDTO fixtureConfigDTO = fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig);

        restFixtureConfigMockMvc.perform(post("/api/fixtureConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureConfigDTO)))
                .andExpect(status().isBadRequest());

        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFixtureConfigs() throws Exception {
        // Initialize the database
        fixtureConfigRepository.save(fixtureConfig);

        // Get all the fixtureConfigs
        restFixtureConfigMockMvc.perform(get("/api/fixtureConfigs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fixtureConfig.getId())))
                .andExpect(jsonPath("$.[*].sourceClass").value(hasItem(DEFAULT_SOURCE_CLASS.toString())))
                .andExpect(jsonPath("$.[*].sourceProcessorClass").value(hasItem(DEFAULT_SOURCE_PROCESSOR_CLASS.toString())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    public void getFixtureConfig() throws Exception {
        // Initialize the database
        fixtureConfigRepository.save(fixtureConfig);

        // Get the fixtureConfig
        restFixtureConfigMockMvc.perform(get("/api/fixtureConfigs/{id}", fixtureConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fixtureConfig.getId()))
            .andExpect(jsonPath("$.sourceClass").value(DEFAULT_SOURCE_CLASS.toString()))
            .andExpect(jsonPath("$.sourceProcessorClass").value(DEFAULT_SOURCE_PROCESSOR_CLASS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    public void getNonExistingFixtureConfig() throws Exception {
        // Get the fixtureConfig
        restFixtureConfigMockMvc.perform(get("/api/fixtureConfigs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFixtureConfig() throws Exception {
        // Initialize the database
        fixtureConfigRepository.save(fixtureConfig);

		int databaseSizeBeforeUpdate = fixtureConfigRepository.findAll().size();

        // Update the fixtureConfig
        fixtureConfig.setSourceClass(UPDATED_SOURCE_CLASS);
        fixtureConfig.setSourceProcessorClass(UPDATED_SOURCE_PROCESSOR_CLASS);
        fixtureConfig.setActive(UPDATED_ACTIVE);
        FixtureConfigDTO fixtureConfigDTO = fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig);

        restFixtureConfigMockMvc.perform(put("/api/fixtureConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureConfigDTO)))
                .andExpect(status().isOk());

        // Validate the FixtureConfig in the database
        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeUpdate);
        FixtureConfig testFixtureConfig = fixtureConfigs.get(fixtureConfigs.size() - 1);
        assertThat(testFixtureConfig.getSourceClass()).isEqualTo(UPDATED_SOURCE_CLASS);
        assertThat(testFixtureConfig.getSourceProcessorClass()).isEqualTo(UPDATED_SOURCE_PROCESSOR_CLASS);
        assertThat(testFixtureConfig.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void deleteFixtureConfig() throws Exception {
        // Initialize the database
        fixtureConfigRepository.save(fixtureConfig);

		int databaseSizeBeforeDelete = fixtureConfigRepository.findAll().size();

        // Get the fixtureConfig
        restFixtureConfigMockMvc.perform(delete("/api/fixtureConfigs/{id}", fixtureConfig.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FixtureConfig> fixtureConfigs = fixtureConfigRepository.findAll();
        assertThat(fixtureConfigs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
