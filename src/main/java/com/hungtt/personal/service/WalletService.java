package com.hungtt.personal.service;

import com.hungtt.personal.domain.Wallet;
import com.hungtt.personal.repository.WalletRepository;
import com.hungtt.personal.service.dto.WalletDTO;
import com.hungtt.personal.service.mapper.WalletMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Wallet}.
 */
@Service
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    /**
     * Save a wallet.
     *
     * @param walletDTO the entity to save.
     * @return the persisted entity.
     */
    public WalletDTO save(WalletDTO walletDTO) {
        log.debug("Request to save Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    /**
     * Update a wallet.
     *
     * @param walletDTO the entity to save.
     * @return the persisted entity.
     */
    public WalletDTO update(WalletDTO walletDTO) {
        log.debug("Request to update Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    /**
     * Partially update a wallet.
     *
     * @param walletDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WalletDTO> partialUpdate(WalletDTO walletDTO) {
        log.debug("Request to partially update Wallet : {}", walletDTO);

        return walletRepository
            .findById(walletDTO.getId())
            .map(existingWallet -> {
                walletMapper.partialUpdate(existingWallet, walletDTO);

                return existingWallet;
            })
            .map(walletRepository::save)
            .map(walletMapper::toDto);
    }

    /**
     * Get all the wallets.
     *
     * @return the list of entities.
     */
    public List<WalletDTO> findAll() {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll().stream().map(walletMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one wallet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<WalletDTO> findOne(String id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id).map(walletMapper::toDto);
    }

    /**
     * Delete the wallet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }
}
