package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.FixtureEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FixtureEvent and its DTO FixtureEventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixtureEventMapper {

    FixtureEventDTO fixtureEventToFixtureEventDTO(FixtureEvent fixtureEvent);

    FixtureEvent fixtureEventDTOToFixtureEvent(FixtureEventDTO fixtureEventDTO);
}
