package com.chathall.springchatserver.app.models;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUser extends BaseModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private AppUser user;
    private Chatroom chatroom;
}
