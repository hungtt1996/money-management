package com.hungtt.personal.service.mapper;

import com.hungtt.personal.domain.Group;
import com.hungtt.personal.service.dto.GroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {}
