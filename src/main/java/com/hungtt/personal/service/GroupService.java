package com.hungtt.personal.service;

import com.hungtt.personal.domain.Group;
import com.hungtt.personal.repository.GroupRepository;
import com.hungtt.personal.service.dto.GroupDTO;
import com.hungtt.personal.service.mapper.GroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Group}.
 */
@Service
public class GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    /**
     * Save a group.
     *
     * @param groupDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupDTO save(GroupDTO groupDTO) {
        log.debug("Request to save Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    /**
     * Update a group.
     *
     * @param groupDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupDTO update(GroupDTO groupDTO) {
        log.debug("Request to update Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    /**
     * Partially update a group.
     *
     * @param groupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GroupDTO> partialUpdate(GroupDTO groupDTO) {
        log.debug("Request to partially update Group : {}", groupDTO);

        return groupRepository
            .findById(groupDTO.getId())
            .map(existingGroup -> {
                groupMapper.partialUpdate(existingGroup, groupDTO);

                return existingGroup;
            })
            .map(groupRepository::save)
            .map(groupMapper::toDto);
    }

    /**
     * Get all the groups.
     *
     * @return the list of entities.
     */
    public List<GroupDTO> findAll() {
        log.debug("Request to get all Groups");
        return groupRepository.findAll().stream().map(groupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one group by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GroupDTO> findOne(String id) {
        log.debug("Request to get Group : {}", id);
        return groupRepository.findById(id).map(groupMapper::toDto);
    }

    /**
     * Delete the group by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.deleteById(id);
    }
}
