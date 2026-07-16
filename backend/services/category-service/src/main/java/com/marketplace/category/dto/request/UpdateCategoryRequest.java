package com.marketplace.category.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {

    private String name;

    private String slug;

    private String description;

    private UUID parentId;

    private String imageUrl;

    private Boolean active;

}
