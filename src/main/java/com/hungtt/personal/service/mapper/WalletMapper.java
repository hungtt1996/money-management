package com.hungtt.personal.service.mapper;

import com.hungtt.personal.domain.Wallet;
import com.hungtt.personal.service.dto.WalletDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring")
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {}
