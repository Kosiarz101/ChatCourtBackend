package com.chathall.springchatserver.models;

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
public class Chatroom extends BaseModel {

    private String name;
    private String description;
    private boolean isPublic = true;
    @DocumentReference
    private Category category;
    @ReadOnlyProperty
    private Set<ChatroomUser> users;
    @ReadOnlyProperty
    private Set<Message> messages;
}
