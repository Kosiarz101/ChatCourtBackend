package com.chathall.springchatserver.dtos.frontend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class MessageRequestDTO {
    private String content;
    private UUID authorId;
    private UUID chatroomId;
}
