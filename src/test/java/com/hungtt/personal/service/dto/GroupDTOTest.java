package com.hungtt.personal.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hungtt.personal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupDTO.class);
        GroupDTO groupDTO1 = new GroupDTO();
        groupDTO1.setId("id1");
        GroupDTO groupDTO2 = new GroupDTO();
        assertThat(groupDTO1).isNotEqualTo(groupDTO2);
        groupDTO2.setId(groupDTO1.getId());
        assertThat(groupDTO1).isEqualTo(groupDTO2);
        groupDTO2.setId("id2");
        assertThat(groupDTO1).isNotEqualTo(groupDTO2);
        groupDTO1.setId(null);
        assertThat(groupDTO1).isNotEqualTo(groupDTO2);
    }
}