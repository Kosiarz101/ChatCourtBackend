package com.chathall.springchatserver.persistence.mongodb.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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
}
