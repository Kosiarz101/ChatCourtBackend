package com.chathall.springchatserver.dtos.chatcourtfrontend;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomChatPanelDTO extends BaseDTOModel {
    private String name;
    private String description;
    private boolean isPublic = true;
    private CategoryDTO category;
    private Set<ChatroomUserDTO> users;
    private Set<MessageDTO> messages;
}
