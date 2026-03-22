package com.chathall.springchatserver.models.app;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import com.chathall.springchatserver.models.BaseModel;
import com.chathall.springchatserver.models.data.mongodb.AppUserMongo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUser extends BaseModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private AppUserMongo user;
    private Chatroom chatroom;
    private Set<Message> messages;
}
