package com.chathall.springchatserver.api.models.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryRequestDTO {
    private String name;
    private String description;
}
