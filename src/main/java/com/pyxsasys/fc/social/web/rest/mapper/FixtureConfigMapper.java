package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.FixtureConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FixtureConfig and its DTO FixtureConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixtureConfigMapper {

    FixtureConfigDTO fixtureConfigToFixtureConfigDTO(FixtureConfig fixtureConfig);

    FixtureConfig fixtureConfigDTOToFixtureConfig(FixtureConfigDTO fixtureConfigDTO);
}
