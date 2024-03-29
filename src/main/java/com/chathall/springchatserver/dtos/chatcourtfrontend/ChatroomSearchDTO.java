package com.chathall.springchatserver.dtos.chatcourtfrontend;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomSearchDTO extends BaseDTOModel {

    private String name;
    private String description;
    private boolean isPublic = true;
    private CategoryDTO category;
    private int userCount;
}
