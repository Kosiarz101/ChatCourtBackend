package com.chathall.springchatserver.app.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Chatroom extends BaseModel {
    private String name;
    private String description;
    private boolean isPublic = true;
    private Category category;
}
