package com.hungtt.personal.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Category.
 */
@Document(collection = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

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
    @Field("icon")
    private String icon;

    @NotNull
    @Field("group_id")
    private String groupId;

    @Field("parent_id")
    private String parentId;

    @NotNull
    @Field("level")
    private Long level;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Category id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Category code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameVi() {
        return this.nameVi;
    }

    public Category nameVi(String nameVi) {
        this.setNameVi(nameVi);
        return this;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Category nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getIcon() {
        return this.icon;
    }

    public Category icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public Category groupId(String groupId) {
        this.setGroupId(groupId);
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public Category parentId(String parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getLevel() {
        return this.level;
    }

    public Category level(Long level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameVi='" + getNameVi() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", icon='" + getIcon() + "'" +
            ", groupId='" + getGroupId() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
