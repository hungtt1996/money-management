package com.hungtt.personal.web.rest;

import static com.hungtt.personal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hungtt.personal.IntegrationTest;
import com.hungtt.personal.domain.Wallet;
import com.hungtt.personal.repository.WalletRepository;
import com.hungtt.personal.service.dto.WalletDTO;
import com.hungtt.personal.service.mapper.WalletMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link WalletResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WalletResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_VI = "AAAAAAAAAA";
    private static final String UPDATED_NAME_VI = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final String DEFAULT_CCY = "AAAAAAAAAA";
    private static final String UPDATED_CCY = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wallets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private MockMvc restWalletMockMvc;

    private Wallet wallet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createEntity() {
        Wallet wallet = new Wallet()
            .code(DEFAULT_CODE)
            .nameVi(DEFAULT_NAME_VI)
            .nameEn(DEFAULT_NAME_EN)
            .balance(DEFAULT_BALANCE)
            .ccy(DEFAULT_CCY)
            .icon(DEFAULT_ICON);
        return wallet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createUpdatedEntity() {
        Wallet wallet = new Wallet()
            .code(UPDATED_CODE)
            .nameVi(UPDATED_NAME_VI)
            .nameEn(UPDATED_NAME_EN)
            .balance(UPDATED_BALANCE)
            .ccy(UPDATED_CCY)
            .icon(UPDATED_ICON);
        return wallet;
    }

    @BeforeEach
    public void initTest() {
        walletRepository.deleteAll();
        wallet = createEntity();
    }

    @Test
    void createWallet() throws Exception {
        int databaseSizeBeforeCreate = walletRepository.findAll().size();
        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);
        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isCreated());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate + 1);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWallet.getNameVi()).isEqualTo(DEFAULT_NAME_VI);
        assertThat(testWallet.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testWallet.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testWallet.getCcy()).isEqualTo(DEFAULT_CCY);
        assertThat(testWallet.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    void createWalletWithExistingId() throws Exception {
        // Create the Wallet with an existing ID
        wallet.setId("existing_id");
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        int databaseSizeBeforeCreate = walletRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setCode(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameViIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setNameVi(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setNameEn(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setBalance(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCcyIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setCcy(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletRepository.findAll().size();
        // set the field null
        wallet.setIcon(null);

        // Create the Wallet, which fails.
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        restWalletMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllWallets() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        // Get all the walletList
        restWalletMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallet.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameVi").value(hasItem(DEFAULT_NAME_VI)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].ccy").value(hasItem(DEFAULT_CCY)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }

    @Test
    void getWallet() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        // Get the wallet
        restWalletMockMvc
            .perform(get(ENTITY_API_URL_ID, wallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wallet.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameVi").value(DEFAULT_NAME_VI))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.ccy").value(DEFAULT_CCY))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }

    @Test
    void getNonExistingWallet() throws Exception {
        // Get the wallet
        restWalletMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingWallet() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Update the wallet
        Wallet updatedWallet = walletRepository.findById(wallet.getId()).get();
        updatedWallet
            .code(UPDATED_CODE)
            .nameVi(UPDATED_NAME_VI)
            .nameEn(UPDATED_NAME_EN)
            .balance(UPDATED_BALANCE)
            .ccy(UPDATED_CCY)
            .icon(UPDATED_ICON);
        WalletDTO walletDTO = walletMapper.toDto(updatedWallet);

        restWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, walletDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isOk());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWallet.getNameVi()).isEqualTo(UPDATED_NAME_VI);
        assertThat(testWallet.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWallet.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testWallet.getCcy()).isEqualTo(UPDATED_CCY);
        assertThat(testWallet.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    void putNonExistingWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, walletDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWalletWithPatch() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Update the wallet using partial update
        Wallet partialUpdatedWallet = new Wallet();
        partialUpdatedWallet.setId(wallet.getId());

        partialUpdatedWallet.code(UPDATED_CODE).nameVi(UPDATED_NAME_VI).nameEn(UPDATED_NAME_EN).ccy(UPDATED_CCY).icon(UPDATED_ICON);

        restWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWallet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWallet))
            )
            .andExpect(status().isOk());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWallet.getNameVi()).isEqualTo(UPDATED_NAME_VI);
        assertThat(testWallet.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWallet.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testWallet.getCcy()).isEqualTo(UPDATED_CCY);
        assertThat(testWallet.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    void fullUpdateWalletWithPatch() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Update the wallet using partial update
        Wallet partialUpdatedWallet = new Wallet();
        partialUpdatedWallet.setId(wallet.getId());

        partialUpdatedWallet
            .code(UPDATED_CODE)
            .nameVi(UPDATED_NAME_VI)
            .nameEn(UPDATED_NAME_EN)
            .balance(UPDATED_BALANCE)
            .ccy(UPDATED_CCY)
            .icon(UPDATED_ICON);

        restWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWallet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWallet))
            )
            .andExpect(status().isOk());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWallet.getNameVi()).isEqualTo(UPDATED_NAME_VI);
        assertThat(testWallet.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWallet.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testWallet.getCcy()).isEqualTo(UPDATED_CCY);
        assertThat(testWallet.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    void patchNonExistingWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, walletDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();
        wallet.setId(UUID.randomUUID().toString());

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(walletDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWallet() throws Exception {
        // Initialize the database
        walletRepository.save(wallet);

        int databaseSizeBeforeDelete = walletRepository.findAll().size();

        // Delete the wallet
        restWalletMockMvc
            .perform(delete(ENTITY_API_URL_ID, wallet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
