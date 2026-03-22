package com.chathall.springchatserver.models.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryRequestDTO {
    private String name;
}
