package com.pyxsasys.fc.social.custom.livescores.source.footballapi;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pyxsasys.fc.social.custom.livescores.base.CompetitionsEnum;
import com.pyxsasys.fc.social.custom.livescores.base.IStatsSource;
import com.pyxsasys.fc.social.custom.util.FCSocialProcessingUtil;

/**
 * This is the class which gets the live score and other related data
 * 
 * @author nikhilkm
 */
public class FootballAPILiveScoreSource implements IStatsSource {

    private final Logger log = LoggerFactory.getLogger(FootballAPILiveScoreSource.class);

    private static String dateFormat = "dd.MM.yyyy";

    private static String SOURCE_NAME = "http://football-api.com";

    private static String SOURCE_API_KEY = "50c83608-c9d0-8d2f-d43373530e31";

    private static Map<CompetitionsEnum, Long> compToIdMap = null;

    static {
        compToIdMap = new HashMap<>();
        compToIdMap.put(CompetitionsEnum.EPL, 1204L);
        // Future
        compToIdMap.put(CompetitionsEnum.CL, 999999999L);
        compToIdMap.put(CompetitionsEnum.FA, 999999999L);
        compToIdMap.put(CompetitionsEnum.LC, 999999999L);
    }

    @Override
    public String getSourceName() {
        return SOURCE_NAME;
    }

    @Override
    public String getSourceAPIKey() {
        return SOURCE_API_KEY;
    }

    @Override
    public String getStandingsAPI(CompetitionsEnum competition) {
        String standingsAPI = "http://football-api.com/api/?Action=standings&APIKey=" + getSourceAPIKey()
                + "&comp_id={COMP_ID}";
        Long compID = compToIdMap.get(competition);
        standingsAPI = standingsAPI.replace("{COMP_ID}", compID.toString());
        return standingsAPI;
    }

    @Override
    public String getFixturesAPI(CompetitionsEnum competition) {
        String fixturesAPI = "http://football-api.com/api/?Action=fixtures&APIKey=" + getSourceAPIKey()
                + "&comp_id={COMP_ID}&&from_date={FROM_DATE}&to_date={TO_DATE}";
        try {
            Long compID = compToIdMap.get(competition);
            fixturesAPI = fixturesAPI.replace("{COMP_ID}", compID.toString());
            String toDate = FCSocialProcessingUtil.getUTCDate(dateFormat);
            fixturesAPI = fixturesAPI.replace("{TO_DATE}", toDate);
            String fromDate = FCSocialProcessingUtil.getDaysMinusDate(dateFormat, toDate);
            fixturesAPI = fixturesAPI.replace("{FROM_DATE}", fromDate);
        } catch (ParseException e) {
            log.debug("Failed to parse while trying to get fixtures API.", e);
        }
        return fixturesAPI;
    }
}
