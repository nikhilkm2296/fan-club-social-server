package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Feed and its DTO FeedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeedMapper {

    FeedDTO feedToFeedDTO(Feed feed);

    Feed feedDTOToFeed(FeedDTO feedDTO);
}
