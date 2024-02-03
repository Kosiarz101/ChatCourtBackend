package com.chathall.springchatserver.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomChatPanel extends BaseModel {
    private String name;
    private String description;
    private boolean isPublic = true;
    private Category category;
    private Set<ChatroomUser> users;
    private Set<Message> messages;
}
