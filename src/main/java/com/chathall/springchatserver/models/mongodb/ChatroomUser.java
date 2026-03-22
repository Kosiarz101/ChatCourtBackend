package com.chathall.springchatserver.models.mongodb;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

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
    @ReadOnlyProperty
    //@DocumentReference(lookup = "{ 'appUser':?#{#self._id} }")
    private Set<Message> messages;
}
