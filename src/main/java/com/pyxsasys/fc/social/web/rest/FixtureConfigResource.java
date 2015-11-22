package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.FixtureConfig;
import com.pyxsasys.fc.social.repository.FixtureConfigRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.dto.FixtureConfigDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureConfigMapper;
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
 * REST controller for managing FixtureConfig.
 */
@RestController
@RequestMapping("/api")
public class FixtureConfigResource {

    private final Logger log = LoggerFactory.getLogger(FixtureConfigResource.class);

    @Inject
    private FixtureConfigRepository fixtureConfigRepository;

    @Inject
    private FixtureConfigMapper fixtureConfigMapper;

    /**
     * POST  /fixtureConfigs -> Create a new fixtureConfig.
     */
    @RequestMapping(value = "/fixtureConfigs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureConfigDTO> createFixtureConfig(@Valid @RequestBody FixtureConfigDTO fixtureConfigDTO) throws URISyntaxException {
        log.debug("REST request to save FixtureConfig : {}", fixtureConfigDTO);
        if (fixtureConfigDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fixtureConfig cannot already have an ID").body(null);
        }
        FixtureConfig fixtureConfig = fixtureConfigMapper.fixtureConfigDTOToFixtureConfig(fixtureConfigDTO);
        FixtureConfig result = fixtureConfigRepository.save(fixtureConfig);
        return ResponseEntity.created(new URI("/api/fixtureConfigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fixtureConfig", result.getId().toString()))
            .body(fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(result));
    }

    /**
     * PUT  /fixtureConfigs -> Updates an existing fixtureConfig.
     */
    @RequestMapping(value = "/fixtureConfigs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureConfigDTO> updateFixtureConfig(@Valid @RequestBody FixtureConfigDTO fixtureConfigDTO) throws URISyntaxException {
        log.debug("REST request to update FixtureConfig : {}", fixtureConfigDTO);
        if (fixtureConfigDTO.getId() == null) {
            return createFixtureConfig(fixtureConfigDTO);
        }
        FixtureConfig fixtureConfig = fixtureConfigMapper.fixtureConfigDTOToFixtureConfig(fixtureConfigDTO);
        FixtureConfig result = fixtureConfigRepository.save(fixtureConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fixtureConfig", fixtureConfigDTO.getId().toString()))
            .body(fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(result));
    }

    /**
     * GET  /fixtureConfigs -> get all the fixtureConfigs.
     */
    @RequestMapping(value = "/fixtureConfigs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<FixtureConfigDTO> getAllFixtureConfigs() {
        log.debug("REST request to get all FixtureConfigs");
        return fixtureConfigRepository.findAll().stream()
            .map(fixtureConfig -> fixtureConfigMapper.fixtureConfigToFixtureConfigDTO(fixtureConfig))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /fixtureConfigs/:id -> get the "id" fixtureConfig.
     */
    @RequestMapping(value = "/fixtureConfigs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureConfigDTO> getFixtureConfig(@PathVariable String id) {
        log.debug("REST request to get FixtureConfig : {}", id);
        return Optional.ofNullable(fixtureConfigRepository.findOne(id))
            .map(fixtureConfigMapper::fixtureConfigToFixtureConfigDTO)
            .map(fixtureConfigDTO -> new ResponseEntity<>(
                fixtureConfigDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fixtureConfigs/:id -> delete the "id" fixtureConfig.
     */
    @RequestMapping(value = "/fixtureConfigs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFixtureConfig(@PathVariable String id) {
        log.debug("REST request to delete FixtureConfig : {}", id);
        fixtureConfigRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fixtureConfig", id.toString())).build();
    }
}
