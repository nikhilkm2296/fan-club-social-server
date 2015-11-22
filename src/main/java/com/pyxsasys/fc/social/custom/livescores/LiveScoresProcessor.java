package com.pyxsasys.fc.social.custom.livescores;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pyxsasys.fc.social.custom.livescores.base.IStatsProcessor;
import com.pyxsasys.fc.social.custom.livescores.base.IStatsSource;
import com.pyxsasys.fc.social.custom.livescores.source.footballapi.processor.FootballAPIComparator;
import com.pyxsasys.fc.social.domain.Fixture;
import com.pyxsasys.fc.social.domain.FixtureConfig;
import com.pyxsasys.fc.social.repository.FixtureConfigRepository;
import com.pyxsasys.fc.social.repository.FixtureRepository;
import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FixtureMapper;

public class LiveScoresProcessor implements Runnable {

    private final Logger log = LoggerFactory.getLogger(LiveScoresProcessor.class);

    FixtureConfigRepository fixtureCfgRepo;

    FixtureRepository fixtureRepo;

    FixtureMapper fixtureMapper;

    volatile boolean continuePolling = true;

    public LiveScoresProcessor(FixtureConfigRepository fixtureCfgRepo, FixtureRepository fixtureRepo,
            FixtureMapper fixtureMapper) {
        this.fixtureCfgRepo = fixtureCfgRepo;
        this.fixtureRepo = fixtureRepo;
        this.fixtureMapper = fixtureMapper;
    }

    @Override
    public void run() {
        while (continuePolling) {
            log.debug("Fixture processor processing started..");
            List<FixtureConfig> configs = fixtureCfgRepo.findAll();
            for (FixtureConfig fixtureConf : configs) {
                if (fixtureConf.getActive()) {
                    process(fixtureConf);
                    break;
                }
            }
            try {
                log.debug("Fixture processor is going to sleep for 4 seconds");
                Thread.sleep(4500L);
            } catch (InterruptedException e) {
            }
        }
        log.debug("Fixture processor stopped");
    }

    private void process(FixtureConfig fixtureConfig) {
        log.debug("Processing " + fixtureConfig);
        String sourceClassStr = fixtureConfig.getSourceClass();
        String sourceProcessorClassStr = fixtureConfig.getSourceProcessorClass();
        try {
            Class<?> sourceClassInst = Class.forName(sourceClassStr);
            IStatsSource source = (IStatsSource) sourceClassInst.newInstance();
            Class<?> sourceProcClassInst = Class.forName(sourceProcessorClassStr);
            IStatsProcessor sourceProcessor = (IStatsProcessor) sourceProcClassInst.newInstance();
            sourceProcessor.process(source);
            List<FixtureDTO> fixtures = sourceProcessor.getFixtures();
            Collections.sort(fixtures, new FootballAPIComparator());
            for (FixtureDTO fixtureDTO : fixtures) {
                Fixture match = fixtureRepo.findByMatchId(fixtureDTO.getMatchId());
                if (match != null) {
                    log.debug("Found same match. Updating the match id : [ " + match.getId() + " ]");
                    fixtureDTO.setId(match.getId());
                } else {
                    log.debug("Found new fixture. Creating a new match [ " + fixtureDTO + " ]");
                }
                fixtureRepo.save(fixtureMapper.fixtureDTOToFixture(fixtureDTO));
            }
        } catch (Exception e) {
            log.error("Error.. ", e);
        }
    }

    public void stopProcessing() {
        log.debug("Stopping the fixture processor");
        this.continuePolling = false;
    }
}
