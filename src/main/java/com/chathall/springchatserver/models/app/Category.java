package com.chathall.springchatserver.models.app;

import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Category extends BaseModel {
    private String name;
}
