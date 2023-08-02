package com.chathall.springchatserver.dtos.chatcourtfrontend;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class MessageDTO extends BaseDTO {

    private String content;
    private UUID authorId;
    private UUID chatroomId;
}
