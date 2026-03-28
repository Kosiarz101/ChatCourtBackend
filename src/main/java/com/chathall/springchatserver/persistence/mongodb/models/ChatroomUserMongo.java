package com.chathall.springchatserver.persistence.mongodb.models;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "chatroomUser")
public class ChatroomUserMongo extends BaseModelMongo {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    @DocumentReference
    private AppUserMongo user;
    @DocumentReference
    private ChatroomMongo chatroom;
}
