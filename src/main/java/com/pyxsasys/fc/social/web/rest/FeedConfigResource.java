package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.FeedConfig;
import com.pyxsasys.fc.social.repository.FeedConfigRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.dto.FeedConfigDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FeedConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing FeedConfig.
 */
@RestController
@RequestMapping("/api")
public class FeedConfigResource {

    private final Logger log = LoggerFactory.getLogger(FeedConfigResource.class);

    @Inject
    private FeedConfigRepository feedConfigRepository;

    @Inject
    private FeedConfigMapper feedConfigMapper;

    /**
     * POST  /feedConfigs -> Create a new feedConfig.
     */
    @RequestMapping(value = "/feedConfigs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedConfigDTO> createFeedConfig(@Valid @RequestBody FeedConfigDTO feedConfigDTO) throws URISyntaxException {
        log.debug("REST request to save FeedConfig : {}", feedConfigDTO);
        if (feedConfigDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feedConfig cannot already have an ID").body(null);
        }
        FeedConfig feedConfig = feedConfigMapper.feedConfigDTOToFeedConfig(feedConfigDTO);
        FeedConfig result = feedConfigRepository.save(feedConfig);
        return ResponseEntity.created(new URI("/api/feedConfigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feedConfig", result.getId().toString()))
            .body(feedConfigMapper.feedConfigToFeedConfigDTO(result));
    }

    /**
     * PUT  /feedConfigs -> Updates an existing feedConfig.
     */
    @RequestMapping(value = "/feedConfigs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedConfigDTO> updateFeedConfig(@Valid @RequestBody FeedConfigDTO feedConfigDTO) throws URISyntaxException {
        log.debug("REST request to update FeedConfig : {}", feedConfigDTO);
        if (feedConfigDTO.getId() == null) {
            return createFeedConfig(feedConfigDTO);
        }
        FeedConfig feedConfig = feedConfigMapper.feedConfigDTOToFeedConfig(feedConfigDTO);
        FeedConfig result = feedConfigRepository.save(feedConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feedConfig", feedConfigDTO.getId().toString()))
            .body(feedConfigMapper.feedConfigToFeedConfigDTO(result));
    }

    /**
     * GET  /feedConfigs -> get all the feedConfigs.
     */
    @RequestMapping(value = "/feedConfigs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<FeedConfigDTO> getAllFeedConfigs() {
        log.debug("REST request to get all FeedConfigs");
        return feedConfigRepository.findAll().stream()
            .map(feedConfig -> feedConfigMapper.feedConfigToFeedConfigDTO(feedConfig))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /feedConfigs/:id -> get the "id" feedConfig.
     */
    @RequestMapping(value = "/feedConfigs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeedConfigDTO> getFeedConfig(@PathVariable String id) {
        log.debug("REST request to get FeedConfig : {}", id);
        return Optional.ofNullable(feedConfigRepository.findOne(id))
            .map(feedConfigMapper::feedConfigToFeedConfigDTO)
            .map(feedConfigDTO -> new ResponseEntity<>(
                feedConfigDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feedConfigs/:id -> delete the "id" feedConfig.
     */
    @RequestMapping(value = "/feedConfigs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeedConfig(@PathVariable String id) {
        log.debug("REST request to delete FeedConfig : {}", id);
        feedConfigRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feedConfig", id.toString())).build();
    }
}
