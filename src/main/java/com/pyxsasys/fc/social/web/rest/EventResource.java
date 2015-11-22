package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.Event;
import com.pyxsasys.fc.social.repository.EventRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.util.PaginationUtil;
import com.pyxsasys.fc.social.web.rest.dto.EventDTO;
import com.pyxsasys.fc.social.web.rest.mapper.EventMapper;
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
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    @Inject
    private EventRepository eventRepository;

    @Inject
    private EventMapper eventMapper;

    /**
     * POST /events -> Create a new event.
     */
    @RequestMapping(value = "/events", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to save Event : {}", eventDTO);
        if (eventDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new event cannot already have an ID").body(null);
        }
        Event event = eventMapper.eventDTOToEvent(eventDTO);
        Event result = eventRepository.save(event);
        return ResponseEntity.created(new URI("/api/events/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("event", result.getId().toString()))
                .body(eventMapper.eventToEventDTO(result));
    }

    /**
     * PUT /events -> Updates an existing event.
     */
    @RequestMapping(value = "/events", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventDTO> updateEvent(@Valid @RequestBody EventDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to update Event : {}", eventDTO);
        if (eventDTO.getId() == null) {
            return createEvent(eventDTO);
        }
        Event event = eventMapper.eventDTOToEvent(eventDTO);
        Event result = eventRepository.save(event);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("event", eventDTO.getId().toString()))
                .body(eventMapper.eventToEventDTO(result));
    }

    /**
     * GET /events -> get all the events.
     */
    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<EventDTO>> getAllEvents(Pageable pageable) throws URISyntaxException {
        PageRequest pageReq = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Direction.DESC, "event_date"));
        Page<Event> page = eventRepository.findAll(pageReq);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
        return new ResponseEntity<>(page.getContent().stream().map(eventMapper::eventToEventDTO)
                .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET /events/:id -> get the "id" event.
     */
    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventDTO> getEvent(@PathVariable String id) {
        log.debug("REST request to get Event : {}", id);
        return Optional.ofNullable(eventRepository.findOne(id)).map(eventMapper::eventToEventDTO)
                .map(eventDTO -> new ResponseEntity<>(eventDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /events/:id -> delete the "id" event.
     */
    @RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        log.debug("REST request to delete Event : {}", id);
        eventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("event", id.toString())).build();
    }
}
