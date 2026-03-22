package com.chathall.springchatserver.models.data.mongodb;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class ChatroomUserMongo extends BaseModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    @DocumentReference
    private AppUserMongo user;
    @DocumentReference
    private ChatroomMongo chatroom;
}
