package com.chathall.springchatserver.models;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Message extends BaseModel {

    @Size(max = 500)
    private String content;
    private LocalDateTime lastModifiedDate;
    @DocumentReference
    private AppUser author;
    @DocumentReference
    private Chatroom chatroom;
}
