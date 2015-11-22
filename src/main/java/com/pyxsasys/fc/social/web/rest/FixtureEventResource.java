package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.FixtureEvent;
import com.pyxsasys.fc.social.repository.FixtureEventRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.dto.FixtureEventDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureEventMapper;
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
 * REST controller for managing FixtureEvent.
 */
@RestController
@RequestMapping("/api")
public class FixtureEventResource {

    private final Logger log = LoggerFactory.getLogger(FixtureEventResource.class);

    @Inject
    private FixtureEventRepository fixtureEventRepository;

    @Inject
    private FixtureEventMapper fixtureEventMapper;

    /**
     * POST  /fixtureEvents -> Create a new fixtureEvent.
     */
    @RequestMapping(value = "/fixtureEvents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureEventDTO> createFixtureEvent(@Valid @RequestBody FixtureEventDTO fixtureEventDTO) throws URISyntaxException {
        log.debug("REST request to save FixtureEvent : {}", fixtureEventDTO);
        if (fixtureEventDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fixtureEvent cannot already have an ID").body(null);
        }
        FixtureEvent fixtureEvent = fixtureEventMapper.fixtureEventDTOToFixtureEvent(fixtureEventDTO);
        FixtureEvent result = fixtureEventRepository.save(fixtureEvent);
        return ResponseEntity.created(new URI("/api/fixtureEvents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fixtureEvent", result.getId().toString()))
            .body(fixtureEventMapper.fixtureEventToFixtureEventDTO(result));
    }

    /**
     * PUT  /fixtureEvents -> Updates an existing fixtureEvent.
     */
    @RequestMapping(value = "/fixtureEvents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureEventDTO> updateFixtureEvent(@Valid @RequestBody FixtureEventDTO fixtureEventDTO) throws URISyntaxException {
        log.debug("REST request to update FixtureEvent : {}", fixtureEventDTO);
        if (fixtureEventDTO.getId() == null) {
            return createFixtureEvent(fixtureEventDTO);
        }
        FixtureEvent fixtureEvent = fixtureEventMapper.fixtureEventDTOToFixtureEvent(fixtureEventDTO);
        FixtureEvent result = fixtureEventRepository.save(fixtureEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fixtureEvent", fixtureEventDTO.getId().toString()))
            .body(fixtureEventMapper.fixtureEventToFixtureEventDTO(result));
    }

    /**
     * GET  /fixtureEvents -> get all the fixtureEvents.
     */
    @RequestMapping(value = "/fixtureEvents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<FixtureEventDTO> getAllFixtureEvents() {
        log.debug("REST request to get all FixtureEvents");
        return fixtureEventRepository.findAll().stream()
            .map(fixtureEvent -> fixtureEventMapper.fixtureEventToFixtureEventDTO(fixtureEvent))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /fixtureEvents/:id -> get the "id" fixtureEvent.
     */
    @RequestMapping(value = "/fixtureEvents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureEventDTO> getFixtureEvent(@PathVariable String id) {
        log.debug("REST request to get FixtureEvent : {}", id);
        return Optional.ofNullable(fixtureEventRepository.findOne(id))
            .map(fixtureEventMapper::fixtureEventToFixtureEventDTO)
            .map(fixtureEventDTO -> new ResponseEntity<>(
                fixtureEventDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fixtureEvents/:id -> delete the "id" fixtureEvent.
     */
    @RequestMapping(value = "/fixtureEvents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFixtureEvent(@PathVariable String id) {
        log.debug("REST request to delete FixtureEvent : {}", id);
        fixtureEventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fixtureEvent", id.toString())).build();
    }
}
