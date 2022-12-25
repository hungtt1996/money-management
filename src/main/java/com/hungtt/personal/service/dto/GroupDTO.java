package com.hungtt.personal.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hungtt.personal.domain.Group} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GroupDTO implements Serializable {

    private String id;

    @NotNull
    private String code;

    @NotNull
    private String nameVi;

    @NotNull
    private String nameEn;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupDTO)) {
            return false;
        }

        GroupDTO groupDTO = (GroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, groupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupDTO{" +
            "id='" + getId() + "'" +
            ", code='" + getCode() + "'" +
            ", nameVi='" + getNameVi() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            "}";
    }
}
