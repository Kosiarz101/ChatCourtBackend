package com.chathall.springchatserver.models.data.mongodb;

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
@Document(collection = "chatroom")
public class ChatroomMongo extends BaseModelMongo {
    private String name;
    private String description;
    private boolean isPublic = true;
    @DocumentReference
    private CategoryMongo category;
    @ReadOnlyProperty
    private Set<ChatroomUserMongo> users;
    @ReadOnlyProperty
    private Set<MessageMongo> messages;
}
