package com.chathall.springchatserver.models.data.mongodb;

import com.chathall.springchatserver.models.BaseModel;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class MessageMongo extends BaseModel {
    @Size(max = 500)
    private String content;
    @DocumentReference
    private ChatroomUserMongo author;
    @DocumentReference
    private ChatroomMongo chatroom;
}
