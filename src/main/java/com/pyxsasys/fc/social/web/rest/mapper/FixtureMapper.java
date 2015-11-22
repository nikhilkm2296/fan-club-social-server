package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;

import java.util.List;

import org.mapstruct.*;

/**
 * Mapper for the entity Fixture and its DTO FixtureDTO.
 */
@Mapper(componentModel = "spring", uses = { FixtureEventMapper.class })
public interface FixtureMapper {

    FixtureDTO fixtureToFixtureDTO(Fixture fixture);

    Fixture fixtureDTOToFixture(FixtureDTO fixtureDTO);
    
    List<FixtureDTO> fixturesToFixtureDTOs(List<Fixture> fixtures);
    
    List<Fixture> fixturesDTOsToFixtures(List<FixtureDTO> fixtureDTOs);
    
}
