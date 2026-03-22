package com.chathall.springchatserver.models.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class MessageResponseDTO extends BaseEntityDTO {
    private String content;
    private ChatroomUserSimpleResponseDTO author;
    private UUID chatroomId;
}
