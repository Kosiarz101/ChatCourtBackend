package com.chathall.springchatserver.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomSearch extends BaseModel {

    private String name;
    private String description;
    private boolean isPublic = true;
    private Category category;
    private int userCount;
}
