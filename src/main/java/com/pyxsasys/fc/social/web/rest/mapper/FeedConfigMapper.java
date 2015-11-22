package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.FeedConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FeedConfig and its DTO FeedConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeedConfigMapper {

    FeedConfigDTO feedConfigToFeedConfigDTO(FeedConfig feedConfig);

    FeedConfig feedConfigDTOToFeedConfig(FeedConfigDTO feedConfigDTO);
}
