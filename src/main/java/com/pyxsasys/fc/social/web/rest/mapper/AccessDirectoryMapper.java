package com.pyxsasys.fc.social.web.rest.mapper;

import com.pyxsasys.fc.social.domain.*;
import com.pyxsasys.fc.social.web.rest.dto.AccessDirectoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AccessDirectory and its DTO AccessDirectoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccessDirectoryMapper {

    AccessDirectoryDTO accessDirectoryToAccessDirectoryDTO(AccessDirectory accessDirectory);

    AccessDirectory accessDirectoryDTOToAccessDirectory(AccessDirectoryDTO accessDirectoryDTO);
}
