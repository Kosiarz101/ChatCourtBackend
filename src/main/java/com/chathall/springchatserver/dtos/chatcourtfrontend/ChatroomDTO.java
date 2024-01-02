package com.chathall.springchatserver.dtos.chatcourtfrontend;

import com.chathall.springchatserver.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomDTO extends BaseDTO {

    private String name;
    private String description;
    private boolean isPublic = true;
    private Category category;
    private Set<MessageDTO> messages;
}
