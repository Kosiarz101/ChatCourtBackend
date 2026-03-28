package com.chathall.springchatserver.persistence.mongodb.models;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Accessors(chain = true)
@Document("message")
public class MessageMongo extends BaseModelMongo {
    @Size(max = 500)
    private String content;
    @DocumentReference
    private ChatroomUserMongo author;
    @DocumentReference
    private ChatroomMongo chatroom;
}
