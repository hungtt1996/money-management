package com.hungtt.personal.service.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String nameVi;

    @NotBlank
    private String nameEn;

    @NotBlank
    private String icon;

    @NotBlank
    private String groupId;

    private String parentId;

    @NotNull
    private Long level;
}
