package com.chathall.springchatserver.app.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Category extends BaseModel {
    private String name;
    private String description;
}
