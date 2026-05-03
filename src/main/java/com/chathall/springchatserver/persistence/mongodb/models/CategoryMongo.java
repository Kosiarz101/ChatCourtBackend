package com.chathall.springchatserver.persistence.mongodb.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "category")
public class CategoryMongo extends BaseModelMongo {
    private String name;
    private String description;
}
