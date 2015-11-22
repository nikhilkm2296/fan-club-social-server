package com.pyxsasys.fc.social.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.domain.AccessDirectory;
import com.pyxsasys.fc.social.repository.AccessDirectoryRepository;
import com.pyxsasys.fc.social.web.rest.util.HeaderUtil;
import com.pyxsasys.fc.social.web.rest.dto.AccessDirectoryDTO;
import com.pyxsasys.fc.social.web.rest.mapper.AccessDirectoryMapper;
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
 * REST controller for managing AccessDirectory.
 */
@RestController
@RequestMapping("/api")
public class AccessDirectoryResource {

    private final Logger log = LoggerFactory.getLogger(AccessDirectoryResource.class);

    @Inject
    private AccessDirectoryRepository accessDirectoryRepository;

    @Inject
    private AccessDirectoryMapper accessDirectoryMapper;

    /**
     * POST  /accessDirectorys -> Create a new accessDirectory.
     */
    @RequestMapping(value = "/accessDirectorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccessDirectoryDTO> createAccessDirectory(@Valid @RequestBody AccessDirectoryDTO accessDirectoryDTO) throws URISyntaxException {
        log.debug("REST request to save AccessDirectory : {}", accessDirectoryDTO);
        if (accessDirectoryDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new accessDirectory cannot already have an ID").body(null);
        }
        AccessDirectory accessDirectory = accessDirectoryMapper.accessDirectoryDTOToAccessDirectory(accessDirectoryDTO);
        AccessDirectory result = accessDirectoryRepository.save(accessDirectory);
        return ResponseEntity.created(new URI("/api/accessDirectorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("accessDirectory", result.getId().toString()))
            .body(accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(result));
    }

    /**
     * PUT  /accessDirectorys -> Updates an existing accessDirectory.
     */
    @RequestMapping(value = "/accessDirectorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccessDirectoryDTO> updateAccessDirectory(@Valid @RequestBody AccessDirectoryDTO accessDirectoryDTO) throws URISyntaxException {
        log.debug("REST request to update AccessDirectory : {}", accessDirectoryDTO);
        if (accessDirectoryDTO.getId() == null) {
            return createAccessDirectory(accessDirectoryDTO);
        }
        AccessDirectory accessDirectory = accessDirectoryMapper.accessDirectoryDTOToAccessDirectory(accessDirectoryDTO);
        AccessDirectory result = accessDirectoryRepository.save(accessDirectory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("accessDirectory", accessDirectoryDTO.getId().toString()))
            .body(accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(result));
    }

    /**
     * GET  /accessDirectorys -> get all the accessDirectorys.
     */
    @RequestMapping(value = "/accessDirectorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<AccessDirectoryDTO> getAllAccessDirectorys() {
        log.debug("REST request to get all AccessDirectorys");
        return accessDirectoryRepository.findAll().stream()
            .map(accessDirectory -> accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /accessDirectorys/:id -> get the "id" accessDirectory.
     */
    @RequestMapping(value = "/accessDirectorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AccessDirectoryDTO> getAccessDirectory(@PathVariable String id) {
        log.debug("REST request to get AccessDirectory : {}", id);
        return Optional.ofNullable(accessDirectoryRepository.findOne(id))
            .map(accessDirectoryMapper::accessDirectoryToAccessDirectoryDTO)
            .map(accessDirectoryDTO -> new ResponseEntity<>(
                accessDirectoryDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /accessDirectorys/:id -> delete the "id" accessDirectory.
     */
    @RequestMapping(value = "/accessDirectorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAccessDirectory(@PathVariable String id) {
        log.debug("REST request to delete AccessDirectory : {}", id);
        accessDirectoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("accessDirectory", id.toString())).build();
    }
}
