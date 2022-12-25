package com.hungtt.personal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Wallet.
 */
@Document(collection = "wallet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name_vi")
    private String nameVi;

    @NotNull
    @Field("name_en")
    private String nameEn;

    @NotNull
    @Field("balance")
    private BigDecimal balance;

    @NotNull
    @Field("ccy")
    private String ccy;

    @NotNull
    @Field("icon")
    private String icon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Wallet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Wallet code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameVi() {
        return this.nameVi;
    }

    public Wallet nameVi(String nameVi) {
        this.setNameVi(nameVi);
        return this;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Wallet nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Wallet balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCcy() {
        return this.ccy;
    }

    public Wallet ccy(String ccy) {
        this.setCcy(ccy);
        return this;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getIcon() {
        return this.icon;
    }

    public Wallet icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return id != null && id.equals(((Wallet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameVi='" + getNameVi() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", balance=" + getBalance() +
            ", ccy='" + getCcy() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
