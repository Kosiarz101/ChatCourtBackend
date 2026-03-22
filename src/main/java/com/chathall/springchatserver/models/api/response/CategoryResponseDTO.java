package com.chathall.springchatserver.models.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryResponseDTO extends BaseEntityDTO {
    private String name;
}
