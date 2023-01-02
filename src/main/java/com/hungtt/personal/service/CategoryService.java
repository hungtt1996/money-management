package com.hungtt.personal.service;

import com.hungtt.personal.domain.Category;
import com.hungtt.personal.domain.Group;
import com.hungtt.personal.repository.CategoryRepository;
import com.hungtt.personal.repository.GroupRepository;
import com.hungtt.personal.service.dto.category.CategoryDTO;
import com.hungtt.personal.service.dto.category.CreateCategoryDTO;
import com.hungtt.personal.service.dto.category.UpdateCategoryDTO;
import com.hungtt.personal.service.mapper.CategoryMapper;
import com.hungtt.personal.service.mapper.GroupMapper;
import com.hungtt.personal.web.rest.errors.BadRequestAlertException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private static final String ENTITY_NAME = "category";

    private final CategoryRepository categoryRepository;

    private final GroupRepository groupRepository;

    private final CategoryMapper categoryMapper;

    private final GroupMapper groupMapper;

    public CategoryService(
        CategoryRepository categoryRepository,
        GroupRepository groupRepository,
        CategoryMapper categoryMapper,
        GroupMapper groupMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
        this.categoryMapper = categoryMapper;
        this.groupMapper = groupMapper;
    }

    public void create(CreateCategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
        category.setGroup(group);
        categoryRepository.save(category);
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Update a category.
     *
     * @param dto the entity to save.
     * @return the persisted entity.
     */
    public CategoryDTO update(UpdateCategoryDTO dto) {
        log.debug("Request to update Category : {}", dto);
        Category category = categoryRepository.findById(dto.getId()).orElse(null);
        if (category == null) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
        if (group == null) {
            throw new BadRequestAlertException("Entity not found", "group", "idnotfound");
        }

        category.setCode(dto.getCode());
        category.setIcon(dto.getIcon());
        category.setLevel(dto.getLevel());
        category.setNameVi(dto.getNameVi());
        category.setNameEn(dto.getNameEn());
        category.setGroup(group);

        categoryRepository.save(category);
        return this.toDto(category);
    }

    /**
     * Partially update a category.
     *
     * @param categoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoryDTO> partialUpdate(CategoryDTO categoryDTO) {
        log.debug("Request to partially update Category : {}", categoryDTO);

        return categoryRepository
            .findById(categoryDTO.getId())
            .map(existingCategory -> {
                categoryMapper.partialUpdate(existingCategory, categoryDTO);

                return existingCategory;
            })
            .map(categoryRepository::save)
            .map(categoryMapper::toDto);
    }

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        List<Category> entities = categoryRepository.findAll();
        return entities.stream().map(this::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    private CategoryDTO toDto(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setGroup(groupMapper.toDto(entity.getGroup()));
        return dto;
    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CategoryDTO> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id).map(this::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
