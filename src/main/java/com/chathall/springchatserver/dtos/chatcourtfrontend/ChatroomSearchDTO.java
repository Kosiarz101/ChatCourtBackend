package com.chathall.springchatserver.dtos.chatcourtfrontend;

import com.chathall.springchatserver.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomSearchDTO extends BaseDTO {

    private String name;
    private String description;
    private boolean isPublic = true;
    private Category category;
    private int userCount;
}
