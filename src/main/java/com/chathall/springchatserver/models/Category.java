package com.chathall.springchatserver.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Category extends BaseModel {
    private String name;
}
