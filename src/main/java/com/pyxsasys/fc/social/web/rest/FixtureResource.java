package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.Fixture;
import com.pyxsasys.fc.social.repository.FixtureRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.util.PaginationUtil;
import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * REST controller for managing Fixture.
 */
@RestController
@RequestMapping("/api")
public class FixtureResource {

    private final Logger log = LoggerFactory.getLogger(FixtureResource.class);

    @Inject
    private FixtureRepository fixtureRepository;

    @Inject
    private FixtureMapper fixtureMapper;

    /**
     * POST /fixtures -> Create a new fixture.
     */
    @RequestMapping(value = "/fixtures", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureDTO> createFixture(@Valid @RequestBody FixtureDTO fixtureDTO)
            throws URISyntaxException {
        log.debug("REST request to save Fixture : {}", fixtureDTO);
        if (fixtureDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fixture cannot already have an ID").body(null);
        }
        Fixture fixture = fixtureMapper.fixtureDTOToFixture(fixtureDTO);
        Fixture result = fixtureRepository.save(fixture);
        return ResponseEntity.created(new URI("/api/fixtures/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("fixture", result.getId().toString()))
                .body(fixtureMapper.fixtureToFixtureDTO(result));
    }

    /**
     * PUT /fixtures -> Updates an existing fixture.
     */
    @RequestMapping(value = "/fixtures", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureDTO> updateFixture(@Valid @RequestBody FixtureDTO fixtureDTO)
            throws URISyntaxException {
        log.debug("REST request to update Fixture : {}", fixtureDTO);
        if (fixtureDTO.getId() == null) {
            return createFixture(fixtureDTO);
        }
        Fixture fixture = fixtureMapper.fixtureDTOToFixture(fixtureDTO);
        Fixture result = fixtureRepository.save(fixture);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("fixture", fixtureDTO.getId().toString()))
                .body(fixtureMapper.fixtureToFixtureDTO(result));
    }

    /**
     * GET /fixtures -> get all the fixtures.
     */
    @RequestMapping(value = "/fixtures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<FixtureDTO>> getAllFixtures(Pageable pageable) throws URISyntaxException {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "match_date"));;
        Page<Fixture> page = fixtureRepository.findAll(pageRequest);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fixtures");
        return new ResponseEntity<>(page.getContent().stream().map(fixtureMapper::fixtureToFixtureDTO)
                .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET /fixtures/:id -> get the "id" fixture.
     */
    @RequestMapping(value = "/fixtures/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureDTO> getFixture(@PathVariable String id) {
        log.debug("REST request to get Fixture : {}", id);
        return Optional.ofNullable(fixtureRepository.findOne(id)).map(fixtureMapper::fixtureToFixtureDTO)
                .map(fixtureDTO -> new ResponseEntity<>(fixtureDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /fixtures/:id -> delete the "id" fixture.
     */
    @RequestMapping(value = "/fixtures/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFixture(@PathVariable String id) {
        log.debug("REST request to delete Fixture : {}", id);
        fixtureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fixture", id.toString())).build();
    }
}
