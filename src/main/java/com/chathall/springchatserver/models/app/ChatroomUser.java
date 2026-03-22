package com.chathall.springchatserver.models.app;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUser extends BaseModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private AppUser user;
    private Chatroom chatroom;
    private Set<Message> messages;
}
