package com.chathall.springchatserver.models;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class ChatroomUser extends BaseModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    @DocumentReference
    private AppUser user;
    @DocumentReference
    private Chatroom chatroom;
}
