package com.chathall.springchatserver.dtos.frontend.response;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUserSimpleResponseDTO extends BaseEntityDTO {
    private ChatroomUserRole role;
    private AppUserSimpleResponseDTO user;
    private UUID chatroomId;
}
