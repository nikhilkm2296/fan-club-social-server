package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.FeedConfig;
import com.pyxsasys.fc.social.repository.FeedConfigRepository;
import com.pyxsasys.fc.social.web.rest.dto.FeedConfigDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FeedConfigMapper;

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
 * Test class for the FeedConfigResource REST controller.
 *
 * @see FeedConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeedConfigResourceIntTest {

    private static final String DEFAULT_FEED_CLASS = "AAAAA";
    private static final String UPDATED_FEED_CLASS = "BBBBB";

    @Inject
    private FeedConfigRepository feedConfigRepository;

    @Inject
    private FeedConfigMapper feedConfigMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeedConfigMockMvc;

    private FeedConfig feedConfig;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedConfigResource feedConfigResource = new FeedConfigResource();
        ReflectionTestUtils.setField(feedConfigResource, "feedConfigRepository", feedConfigRepository);
        ReflectionTestUtils.setField(feedConfigResource, "feedConfigMapper", feedConfigMapper);
        this.restFeedConfigMockMvc = MockMvcBuilders.standaloneSetup(feedConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feedConfigRepository.deleteAll();
        feedConfig = new FeedConfig();
        feedConfig.setFeedClass(DEFAULT_FEED_CLASS);
    }

    @Test
    public void createFeedConfig() throws Exception {
        int databaseSizeBeforeCreate = feedConfigRepository.findAll().size();

        // Create the FeedConfig
        FeedConfigDTO feedConfigDTO = feedConfigMapper.feedConfigToFeedConfigDTO(feedConfig);

        restFeedConfigMockMvc.perform(post("/api/feedConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedConfigDTO)))
                .andExpect(status().isCreated());

        // Validate the FeedConfig in the database
        List<FeedConfig> feedConfigs = feedConfigRepository.findAll();
        assertThat(feedConfigs).hasSize(databaseSizeBeforeCreate + 1);
        FeedConfig testFeedConfig = feedConfigs.get(feedConfigs.size() - 1);
        assertThat(testFeedConfig.getFeedClass()).isEqualTo(DEFAULT_FEED_CLASS);
    }

    @Test
    public void checkFeedClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedConfigRepository.findAll().size();
        // set the field null
        feedConfig.setFeedClass(null);

        // Create the FeedConfig, which fails.
        FeedConfigDTO feedConfigDTO = feedConfigMapper.feedConfigToFeedConfigDTO(feedConfig);

        restFeedConfigMockMvc.perform(post("/api/feedConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedConfigDTO)))
                .andExpect(status().isBadRequest());

        List<FeedConfig> feedConfigs = feedConfigRepository.findAll();
        assertThat(feedConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFeedConfigs() throws Exception {
        // Initialize the database
        feedConfigRepository.save(feedConfig);

        // Get all the feedConfigs
        restFeedConfigMockMvc.perform(get("/api/feedConfigs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feedConfig.getId())))
                .andExpect(jsonPath("$.[*].feedClass").value(hasItem(DEFAULT_FEED_CLASS.toString())));
    }

    @Test
    public void getFeedConfig() throws Exception {
        // Initialize the database
        feedConfigRepository.save(feedConfig);

        // Get the feedConfig
        restFeedConfigMockMvc.perform(get("/api/feedConfigs/{id}", feedConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feedConfig.getId()))
            .andExpect(jsonPath("$.feedClass").value(DEFAULT_FEED_CLASS.toString()));
    }

    @Test
    public void getNonExistingFeedConfig() throws Exception {
        // Get the feedConfig
        restFeedConfigMockMvc.perform(get("/api/feedConfigs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFeedConfig() throws Exception {
        // Initialize the database
        feedConfigRepository.save(feedConfig);

		int databaseSizeBeforeUpdate = feedConfigRepository.findAll().size();

        // Update the feedConfig
        feedConfig.setFeedClass(UPDATED_FEED_CLASS);
        FeedConfigDTO feedConfigDTO = feedConfigMapper.feedConfigToFeedConfigDTO(feedConfig);

        restFeedConfigMockMvc.perform(put("/api/feedConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedConfigDTO)))
                .andExpect(status().isOk());

        // Validate the FeedConfig in the database
        List<FeedConfig> feedConfigs = feedConfigRepository.findAll();
        assertThat(feedConfigs).hasSize(databaseSizeBeforeUpdate);
        FeedConfig testFeedConfig = feedConfigs.get(feedConfigs.size() - 1);
        assertThat(testFeedConfig.getFeedClass()).isEqualTo(UPDATED_FEED_CLASS);
    }

    @Test
    public void deleteFeedConfig() throws Exception {
        // Initialize the database
        feedConfigRepository.save(feedConfig);

		int databaseSizeBeforeDelete = feedConfigRepository.findAll().size();

        // Get the feedConfig
        restFeedConfigMockMvc.perform(delete("/api/feedConfigs/{id}", feedConfig.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FeedConfig> feedConfigs = feedConfigRepository.findAll();
        assertThat(feedConfigs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
