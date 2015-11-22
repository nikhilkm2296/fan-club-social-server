package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventMapper {

    EventDTO eventToEventDTO(Event event);

    Event eventDTOToEvent(EventDTO eventDTO);
}
