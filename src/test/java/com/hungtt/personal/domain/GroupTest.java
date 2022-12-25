package com.hungtt.personal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hungtt.personal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Group.class);
        Group group1 = new Group();
        group1.setId("id1");
        Group group2 = new Group();
        group2.setId(group1.getId());
        assertThat(group1).isEqualTo(group2);
        group2.setId("id2");
        assertThat(group1).isNotEqualTo(group2);
        group1.setId(null);
        assertThat(group1).isNotEqualTo(group2);
    }
}
