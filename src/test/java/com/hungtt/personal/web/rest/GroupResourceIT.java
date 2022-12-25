package com.hungtt.personal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hungtt.personal.IntegrationTest;
import com.hungtt.personal.domain.Group;
import com.hungtt.personal.repository.GroupRepository;
import com.hungtt.personal.service.dto.GroupDTO;
import com.hungtt.personal.service.mapper.GroupMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link GroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_VI = "AAAAAAAAAA";
    private static final String UPDATED_NAME_VI = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private MockMvc restGroupMockMvc;

    private Group group;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group createEntity() {
        Group group = new Group().code(DEFAULT_CODE).nameVi(DEFAULT_NAME_VI).nameEn(DEFAULT_NAME_EN);
        return group;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group createUpdatedEntity() {
        Group group = new Group().code(UPDATED_CODE).nameVi(UPDATED_NAME_VI).nameEn(UPDATED_NAME_EN);
        return group;
    }

    @BeforeEach
    public void initTest() {
        groupRepository.deleteAll();
        group = createEntity();
    }

    @Test
    void createGroup() throws Exception {
        int databaseSizeBeforeCreate = groupRepository.findAll().size();
        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);
        restGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isCreated());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate + 1);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroup.getNameVi()).isEqualTo(DEFAULT_NAME_VI);
        assertThat(testGroup.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
    }

    @Test
    void createGroupWithExistingId() throws Exception {
        // Create the Group with an existing ID
        group.setId("existing_id");
        GroupDTO groupDTO = groupMapper.toDto(group);

        int databaseSizeBeforeCreate = groupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRepository.findAll().size();
        // set the field null
        group.setCode(null);

        // Create the Group, which fails.
        GroupDTO groupDTO = groupMapper.toDto(group);

        restGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isBadRequest());

        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameViIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRepository.findAll().size();
        // set the field null
        group.setNameVi(null);

        // Create the Group, which fails.
        GroupDTO groupDTO = groupMapper.toDto(group);

        restGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isBadRequest());

        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRepository.findAll().size();
        // set the field null
        group.setNameEn(null);

        // Create the Group, which fails.
        GroupDTO groupDTO = groupMapper.toDto(group);

        restGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isBadRequest());

        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGroups() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        // Get all the groupList
        restGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(group.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameVi").value(hasItem(DEFAULT_NAME_VI)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)));
    }

    @Test
    void getGroup() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        // Get the group
        restGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, group.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(group.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameVi").value(DEFAULT_NAME_VI))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN));
    }

    @Test
    void getNonExistingGroup() throws Exception {
        // Get the group
        restGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGroup() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Update the group
        Group updatedGroup = groupRepository.findById(group.getId()).get();
        updatedGroup.code(UPDATED_CODE).nameVi(UPDATED_NAME_VI).nameEn(UPDATED_NAME_EN);
        GroupDTO groupDTO = groupMapper.toDto(updatedGroup);

        restGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupDTO))
            )
            .andExpect(status().isOk());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroup.getNameVi()).isEqualTo(UPDATED_NAME_VI);
        assertThat(testGroup.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    void putNonExistingGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGroupWithPatch() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Update the group using partial update
        Group partialUpdatedGroup = new Group();
        partialUpdatedGroup.setId(group.getId());

        restGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroup))
            )
            .andExpect(status().isOk());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroup.getNameVi()).isEqualTo(DEFAULT_NAME_VI);
        assertThat(testGroup.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
    }

    @Test
    void fullUpdateGroupWithPatch() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Update the group using partial update
        Group partialUpdatedGroup = new Group();
        partialUpdatedGroup.setId(group.getId());

        partialUpdatedGroup.code(UPDATED_CODE).nameVi(UPDATED_NAME_VI).nameEn(UPDATED_NAME_EN);

        restGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroup))
            )
            .andExpect(status().isOk());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroup.getNameVi()).isEqualTo(UPDATED_NAME_VI);
        assertThat(testGroup.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    void patchNonExistingGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();
        group.setId(UUID.randomUUID().toString());

        // Create the Group
        GroupDTO groupDTO = groupMapper.toDto(group);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGroup() throws Exception {
        // Initialize the database
        groupRepository.save(group);

        int databaseSizeBeforeDelete = groupRepository.findAll().size();

        // Delete the group
        restGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, group.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
