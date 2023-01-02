package com.hungtt.personal.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Category.
 */
@Document(collection = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
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
    @Field("group")
    @Reference
    private Group group;

    @Field("parent_id")
    private String parentId;

    @NotNull
    @Field("level")
    private Long level;
}
