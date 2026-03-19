package com.chathall.springchatserver.dtos.frontend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomResponseDTO extends BaseEntityDTO {
    private String name;
    private String description;
    private boolean isPublic;
    private CategoryResponseDTO category;
    private Set<ChatroomUserSimpleResponseDTO> users;
    private Set<MessageResponseDTO> messages;
}
