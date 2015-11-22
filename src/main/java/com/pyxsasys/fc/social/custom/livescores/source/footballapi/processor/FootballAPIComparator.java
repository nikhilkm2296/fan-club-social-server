package com.pyxsasys.fc.social.custom.livescores.source.footballapi.processor;

import java.util.Comparator;

import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;

public class FootballAPIComparator implements Comparator<FixtureDTO> {

    @Override
    public int compare(FixtureDTO o1, FixtureDTO o2) {
        return o2.getMatchDate().compareTo(o1.getMatchDate());
    }
}
