package com.chathall.springchatserver.models.app;

import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Message extends BaseModel {
    private String content;
    private ChatroomUser author;
    private Chatroom chatroom;
}
