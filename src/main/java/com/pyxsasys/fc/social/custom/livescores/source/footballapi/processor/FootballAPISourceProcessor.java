package com.pyxsasys.fc.social.custom.livescores.source.footballapi.processor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pyxsasys.fc.social.custom.livescores.base.CompetitionsEnum;
import com.pyxsasys.fc.social.custom.livescores.base.IStatsProcessor;
import com.pyxsasys.fc.social.custom.livescores.base.IStatsSource;
import com.pyxsasys.fc.social.custom.livescores.source.footballapi.FootballAPILiveScoreSource;
import com.pyxsasys.fc.social.custom.livescores.source.footballapi.model.FootballAPIFixtureEventsModel;
import com.pyxsasys.fc.social.custom.livescores.source.footballapi.model.FootballAPISourceFixtureModel;
import com.pyxsasys.fc.social.custom.livescores.source.footballapi.model.FootballApiFixtureResponseModel;
import com.pyxsasys.fc.social.custom.util.api.RestAPIManager;
import com.pyxsasys.fc.social.domain.enumeration.FCSocialFixtureEventTypeEnum;
import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;
import com.pyxsasys.fc.social.web.rest.dto.FixtureEventDTO;

public class FootballAPISourceProcessor implements IStatsProcessor {

    private final Logger log = LoggerFactory.getLogger(FootballAPISourceProcessor.class);

    /** Stores the list of fixtures */
    List<FixtureDTO> fixtures = new ArrayList<>();

    RestAPIManager<FootballApiFixtureResponseModel> rest = new RestAPIManager<>();

    @Override
    public void process(IStatsSource source) {
        log.debug("Processing source [ " + source + " ]");
        try {
            FootballApiFixtureResponseModel model = rest.getParsedJSON(source.getFixturesAPI(CompetitionsEnum.EPL),
                    FootballApiFixtureResponseModel.class);
            List<FootballAPISourceFixtureModel> matches = model.getMatches();
            if (matches != null) {
                for (FootballAPISourceFixtureModel match : matches) {
                    FixtureDTO dto = new FixtureDTO();
                    dto.setFtScore(match.getMatch_ft_score());
                    dto.setHtScore(match.getMatch_ht_score());
                    dto.setMatchId(match.getMatch_id());
                    dto.setMatchTime(match.getMatch_status());
                    dto.setTeamOne(match.getMatch_localteam_name());
                    dto.setTeamOneId(match.getMatch_localteam_id());
                    dto.setTeamOneScore(match.getMatch_localteam_score());
                    dto.setTeamTwo(match.getMatch_visitorteam_name());
                    dto.setTeamTwoId(match.getMatch_visitorteam_id());
                    dto.setTeamTwoScore(match.getMatch_visitorteam_score());
                    String match_formatted_date = match.getMatch_formatted_date();
                    String match_time = match.getMatch_time();
                    String[] dateSplit = match_formatted_date.split("\\.");
                    String[] timeSplit = match_time.split(":");
                    ZoneId zoneId = ZoneId.of("UTC");
                    ZonedDateTime matchDate = ZonedDateTime.of(Integer.parseInt(dateSplit[2]),
                            Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]),
                            Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]), 0, 0, zoneId);
                    dto.setMatchDate(matchDate);

                    List<FixtureEventDTO> events = new ArrayList<>();
                    List<FootballAPIFixtureEventsModel> matchEvents = match.getMatch_events();
                    if (matchEvents != null) {
                        for (FootballAPIFixtureEventsModel event : matchEvents) {
                            FixtureEventDTO eventDto = new FixtureEventDTO();
                            eventDto.setEventId(event.getEvent_id());
                            eventDto.setMatchId(event.getEvent_match_id());
                            eventDto.setEventMinute(event.getEvent_minute());
                            eventDto.setEventPlayer(event.getEvent_player());
                            eventDto.setEventResult(event.getEvent_result());
                            eventDto.setEventTeam(event.getEvent_team());
                            eventDto.setEventType(FCSocialFixtureEventTypeEnum.valueOf(event.getEvent_type()));
                            events.add(eventDto);
                        }
                    }
                    dto.setMatchEvents(events);
                    fixtures.add(dto);
                }
            }
            Collections.sort(fixtures, new FootballAPIComparator());
            log.debug("Got [ " + fixtures.size() + " ] fixtures");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FixtureDTO> getFixtures() {
        return fixtures;
    }
    
    public static void main(String[] args) {
        FootballAPISourceProcessor processor = new FootballAPISourceProcessor();
        processor.process(new FootballAPILiveScoreSource());
    }
}
