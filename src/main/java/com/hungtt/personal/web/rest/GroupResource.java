package com.hungtt.personal.web.rest;

import com.hungtt.personal.repository.GroupRepository;
import com.hungtt.personal.service.GroupService;
import com.hungtt.personal.service.dto.GroupDTO;
import com.hungtt.personal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hungtt.personal.domain.Group}.
 */
@RestController
@RequestMapping("/api")
public class GroupResource {

    private final Logger log = LoggerFactory.getLogger(GroupResource.class);

    private static final String ENTITY_NAME = "group";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupService groupService;

    private final GroupRepository groupRepository;

    public GroupResource(GroupService groupService, GroupRepository groupRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
    }

    /**
     * {@code POST  /groups} : Create a new group.
     *
     * @param groupDTO the groupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupDTO, or with status {@code 400 (Bad Request)} if the group has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groups")
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) throws URISyntaxException {
        log.debug("REST request to save Group : {}", groupDTO);
        if (groupDTO.getId() != null) {
            throw new BadRequestAlertException("A new group cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupDTO result = groupService.save(groupDTO);
        return ResponseEntity
            .created(new URI("/api/groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /groups/:id} : Updates an existing group.
     *
     * @param id the id of the groupDTO to save.
     * @param groupDTO the groupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupDTO,
     * or with status {@code 400 (Bad Request)} if the groupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groups/{id}")
    public ResponseEntity<GroupDTO> updateGroup(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GroupDTO groupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Group : {}, {}", id, groupDTO);
        if (groupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupDTO result = groupService.update(groupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /groups/:id} : Partial updates given fields of an existing group, field will ignore if it is null
     *
     * @param id the id of the groupDTO to save.
     * @param groupDTO the groupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupDTO,
     * or with status {@code 400 (Bad Request)} if the groupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the groupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupDTO> partialUpdateGroup(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GroupDTO groupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Group partially : {}, {}", id, groupDTO);
        if (groupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupDTO> result = groupService.partialUpdate(groupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupDTO.getId())
        );
    }

    /**
     * {@code GET  /groups} : get all the groups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groups in body.
     */
    @GetMapping("/groups")
    public List<GroupDTO> getAllGroups() {
        log.debug("REST request to get all Groups");
        return groupService.findAll();
    }

    /**
     * {@code GET  /groups/:id} : get the "id" group.
     *
     * @param id the id of the groupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groups/{id}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable String id) {
        log.debug("REST request to get Group : {}", id);
        Optional<GroupDTO> groupDTO = groupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupDTO);
    }

    /**
     * {@code DELETE  /groups/:id} : delete the "id" group.
     *
     * @param id the id of the groupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        log.debug("REST request to delete Group : {}", id);
        groupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
