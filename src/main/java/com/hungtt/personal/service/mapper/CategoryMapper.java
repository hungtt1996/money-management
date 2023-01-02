package com.hungtt.personal.service.mapper;

import com.hungtt.personal.domain.Category;
import com.hungtt.personal.service.dto.category.CategoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
