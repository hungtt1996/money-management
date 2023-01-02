package com.hungtt.personal.service.dto.category;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link com.hungtt.personal.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class UpdateCategoryDTO implements Serializable {

    @NotBlank
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String nameVi;

    @NotNull
    private String nameEn;

    @NotNull
    private String icon;

    private String parentId;

    @NotNull
    private Long level;

    @NotBlank
    private String groupId;
}
