package com.hungtt.personal.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hungtt.personal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalletDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletDTO.class);
        WalletDTO walletDTO1 = new WalletDTO();
        walletDTO1.setId("id1");
        WalletDTO walletDTO2 = new WalletDTO();
        assertThat(walletDTO1).isNotEqualTo(walletDTO2);
        walletDTO2.setId(walletDTO1.getId());
        assertThat(walletDTO1).isEqualTo(walletDTO2);
        walletDTO2.setId("id2");
        assertThat(walletDTO1).isNotEqualTo(walletDTO2);
        walletDTO1.setId(null);
        assertThat(walletDTO1).isNotEqualTo(walletDTO2);
    }
}
