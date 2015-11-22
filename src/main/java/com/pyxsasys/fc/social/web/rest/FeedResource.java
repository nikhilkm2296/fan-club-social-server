package com.pyxsasys.fc.social.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.custom.feeds.base.FeedsProcessor;
import com.pyxsasys.fc.social.domain.Feed;
import com.pyxsasys.fc.social.repository.FeedConfigRepository;
import com.pyxsasys.fc.social.repository.FeedRepository;
import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FeedMapper;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Feed.
 */
@RestController
@RequestMapping("/api")
public class FeedResource {

    private final Logger log = LoggerFactory.getLogger(FeedResource.class);

    @Inject
    private FeedRepository feedRepository;

    @Inject
    private FeedMapper feedMapper;

    FeedsProcessor feedsProcessor;

    @Inject
    FeedConfigRepository feedConfigRepository;

    /**
     * POST /feeds -> Create a new feed.
     */
    @RequestMapping(value = "/feeds", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedDTO> createFeed(@Valid @RequestBody FeedDTO feedDTO) throws URISyntaxException {
        log.debug("REST request to save Feed : {}", feedDTO);
        if (feedDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feed cannot already have an ID").body(null);
        }
        Feed feed = feedMapper.feedDTOToFeed(feedDTO);
        Feed result = feedRepository.save(feed);
        return ResponseEntity.created(new URI("/api/feeds/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("feed", result.getId().toString()))
                .body(feedMapper.feedToFeedDTO(result));
    }

    /**
     * PUT /feeds -> Updates an existing feed.
     */
    @RequestMapping(value = "/feeds", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedDTO> updateFeed(@Valid @RequestBody FeedDTO feedDTO) throws URISyntaxException {
        log.debug("REST request to update Feed : {}", feedDTO);
        if (feedDTO.getId() == null) {
            return createFeed(feedDTO);
        }
        Feed feed = feedMapper.feedDTOToFeed(feedDTO);
        Feed result = feedRepository.save(feed);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("feed", feedDTO.getId().toString()))
                .body(feedMapper.feedToFeedDTO(result));
    }

    /**
     * GET /feeds -> get all the feeds.
     */
    @RequestMapping(value = "/feeds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<FeedDTO>> getAllFeeds(Pageable pageable) throws URISyntaxException {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Direction.DESC, "publication_date"));
        Page<Feed> page = feedRepository.findAll(pageRequest);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds");
        return new ResponseEntity<>(page.getContent().stream().map(feedMapper::feedToFeedDTO)
                .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET /feeds/:id -> get the "id" feed.
     */
    @RequestMapping(value = "/feeds/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedDTO> getFeed(@PathVariable String id) {
        log.debug("REST request to get Feed : {}", id);
        return Optional.ofNullable(feedRepository.findOne(id)).map(feedMapper::feedToFeedDTO)
                .map(feedDTO -> new ResponseEntity<>(feedDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /feeds/:id -> delete the "id" feed.
     */
    @RequestMapping(value = "/feeds/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeed(@PathVariable String id) {
        log.debug("REST request to delete Feed : {}", id);
        feedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feed", id.toString())).build();
    }

    /**
     * POST /feeds/start -> Starts the live feeds service
     */
    @RequestMapping(value = "/feeds/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> startFeed() throws URISyntaxException {
        log.debug("REST request to start Feeds");
        this.feedsProcessor = FeedsProcessor.getProcessor(feedConfigRepository, feedMapper, feedRepository);
        Thread feedThread = new Thread(feedsProcessor);
        feedThread.start();
        return ResponseEntity.ok().body("Success");
    }

    /**
     * POST /feeds/stop -> Stop polling for new feeds.
     */
    @RequestMapping(value = "/feeds/stop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> stopFeed() throws URISyntaxException {
        log.debug("REST request to start Feeds");
        this.feedsProcessor.stop();
        return ResponseEntity.ok().body("Success");
    }
}
