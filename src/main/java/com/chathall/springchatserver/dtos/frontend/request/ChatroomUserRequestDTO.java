package com.chathall.springchatserver.dtos.frontend.request;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUserRequestDTO {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private UUID userId;
    private UUID chatroomId;
}
