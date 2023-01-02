package com.hungtt.personal.service.dto.category;

import com.hungtt.personal.service.dto.GroupDTO;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link com.hungtt.personal.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class CategoryDTO implements Serializable {

    private String id;

    @NotNull
    private String code;

    @NotNull
    private String nameVi;

    @NotNull
    private String nameEn;

    @NotNull
    private String icon;

    @NotNull
    private GroupDTO group;

    private String parentId;

    @NotNull
    private Long level;

    private String groupId;
}
