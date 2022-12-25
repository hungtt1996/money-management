package com.hungtt.personal.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hungtt.personal.domain.Wallet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalletDTO implements Serializable {

    private String id;

    @NotNull
    private String code;

    @NotNull
    private String nameVi;

    @NotNull
    private String nameEn;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String ccy;

    @NotNull
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameVi() {
        return nameVi;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletDTO)) {
            return false;
        }

        WalletDTO walletDTO = (WalletDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, walletDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletDTO{" +
            "id='" + getId() + "'" +
            ", code='" + getCode() + "'" +
            ", nameVi='" + getNameVi() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", balance=" + getBalance() +
            ", ccy='" + getCcy() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
