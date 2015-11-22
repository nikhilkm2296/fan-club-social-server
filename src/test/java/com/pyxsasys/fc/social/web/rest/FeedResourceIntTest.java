package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.Feed;
import com.pyxsasys.fc.social.repository.FeedRepository;
import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FeedMapper;

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
 * Test class for the FeedResource REST controller.
 *
 * @see FeedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeedResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_LINK = "AAAAA";
    private static final String UPDATED_LINK = "BBBBB";
    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";
    private static final String DEFAULT_SOURCE = "AAAAA";
    private static final String UPDATED_SOURCE = "BBBBB";
    private static final String DEFAULT_PUBLICATION_DATE = "AAAAA";
    private static final String UPDATED_PUBLICATION_DATE = "BBBBB";

    @Inject
    private FeedRepository feedRepository;

    @Inject
    private FeedMapper feedMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeedMockMvc;

    private Feed feed;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedResource feedResource = new FeedResource();
        ReflectionTestUtils.setField(feedResource, "feedRepository", feedRepository);
        ReflectionTestUtils.setField(feedResource, "feedMapper", feedMapper);
        this.restFeedMockMvc = MockMvcBuilders.standaloneSetup(feedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feedRepository.deleteAll();
        feed = new Feed();
        feed.setTitle(DEFAULT_TITLE);
        feed.setDescription(DEFAULT_DESCRIPTION);
        feed.setLink(DEFAULT_LINK);
        feed.setImage(DEFAULT_IMAGE);
        feed.setSource(DEFAULT_SOURCE);
        feed.setPublicationDate(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    public void createFeed() throws Exception {
        int databaseSizeBeforeCreate = feedRepository.findAll().size();

        // Create the Feed
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isCreated());

        // Validate the Feed in the database
        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeCreate + 1);
        Feed testFeed = feeds.get(feeds.size() - 1);
        assertThat(testFeed.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFeed.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeed.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testFeed.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testFeed.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testFeed.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setTitle(null);

        // Create the Feed, which fails.
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isBadRequest());

        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setDescription(null);

        // Create the Feed, which fails.
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isBadRequest());

        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setLink(null);

        // Create the Feed, which fails.
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isBadRequest());

        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setSource(null);

        // Create the Feed, which fails.
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isBadRequest());

        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setPublicationDate(null);

        // Create the Feed, which fails.
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(post("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isBadRequest());

        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFeeds() throws Exception {
        // Initialize the database
        feedRepository.save(feed);

        // Get all the feeds
        restFeedMockMvc.perform(get("/api/feeds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feed.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
                .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())));
    }

    @Test
    public void getFeed() throws Exception {
        // Initialize the database
        feedRepository.save(feed);

        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", feed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feed.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()));
    }

    @Test
    public void getNonExistingFeed() throws Exception {
        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFeed() throws Exception {
        // Initialize the database
        feedRepository.save(feed);

		int databaseSizeBeforeUpdate = feedRepository.findAll().size();

        // Update the feed
        feed.setTitle(UPDATED_TITLE);
        feed.setDescription(UPDATED_DESCRIPTION);
        feed.setLink(UPDATED_LINK);
        feed.setImage(UPDATED_IMAGE);
        feed.setSource(UPDATED_SOURCE);
        feed.setPublicationDate(UPDATED_PUBLICATION_DATE);
        FeedDTO feedDTO = feedMapper.feedToFeedDTO(feed);

        restFeedMockMvc.perform(put("/api/feeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
                .andExpect(status().isOk());

        // Validate the Feed in the database
        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeUpdate);
        Feed testFeed = feeds.get(feeds.size() - 1);
        assertThat(testFeed.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFeed.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeed.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testFeed.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFeed.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testFeed.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
    }

    @Test
    public void deleteFeed() throws Exception {
        // Initialize the database
        feedRepository.save(feed);

		int databaseSizeBeforeDelete = feedRepository.findAll().size();

        // Get the feed
        restFeedMockMvc.perform(delete("/api/feeds/{id}", feed.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Feed> feeds = feedRepository.findAll();
        assertThat(feeds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
