package com.chathall.springchatserver.dtos.chatcourtfrontend;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUserFlatDTO {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private UUID user;
    private UUID chatroom;
}
