package com.pyxsasys.fc.social.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pyxsasys.fc.social.custom.livescores.LiveScoresProcessor;
import com.pyxsasys.fc.social.repository.FixtureConfigRepository;
import com.pyxsasys.fc.social.repository.FixtureRepository;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureMapper;

/**
 * REST controller for managing Fixture.
 */
@RestController
@RequestMapping("/api")
public class LiveScoreResource {

    private final Logger log = LoggerFactory.getLogger(FixtureResource.class);

    @Inject
    private FixtureRepository fixtureRepository;

    @Inject
    private FixtureMapper fixtureMapper;

    @Inject
    private FixtureConfigRepository fixtureConfigRepository;

    private LiveScoresProcessor liveScoreProcessor;

    /**
     * POST /fixtures/live/start -> Starts the live score service
     */
    @RequestMapping(value = "/livescores/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> startLiveScoresServices() throws URISyntaxException {
        log.debug("Starting Live scores service");
        if (this.liveScoreProcessor == null) {
            log.debug("Live score service started");
            this.liveScoreProcessor = new LiveScoresProcessor(fixtureConfigRepository, fixtureRepository, fixtureMapper);
            Thread t = new Thread(liveScoreProcessor);
            t.start();
        } else {
            log.debug("Live score service already running");
        }
        return ResponseEntity.ok().body("Success");
    }

    /**
     * POST /fixtures/live/stop -> Stops the live score service
     */
    @RequestMapping(value = "/fixtures/live/stop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> stopLiveScores() {
        log.debug("Stopping Live score service");
        if (this.liveScoreProcessor != null) {
            this.liveScoreProcessor.stopProcessing();
            log.debug("Live score service stopped");
        } else {
            log.debug("Live score service already stopped");
        }
        this.liveScoreProcessor = null;
        return ResponseEntity.ok().body("Success");
    }

}
