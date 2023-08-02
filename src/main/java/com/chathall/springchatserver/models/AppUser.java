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
public class AppUser extends BaseModel {

    private String email;
    private String password;
    private String username;
    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'appUser':?#{#self._id} }")
    private Set<Message> messages;
    @DocumentReference
    private Set<Chatroom> chatrooms;
}
