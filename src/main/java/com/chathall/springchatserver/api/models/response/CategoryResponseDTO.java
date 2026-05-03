package com.chathall.springchatserver.api.models.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryResponseDTO extends BaseEntityDTO {
    private String name;
    private String description;
}
