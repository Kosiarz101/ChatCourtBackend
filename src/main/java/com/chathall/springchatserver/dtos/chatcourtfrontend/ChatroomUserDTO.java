package com.chathall.springchatserver.dtos.chatcourtfrontend;

import com.chathall.springchatserver.enums.ChatroomUserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomUserDTO extends BaseDTOModel {
    private ChatroomUserRole role = ChatroomUserRole.USER;
    private AppUserDTO user;
    private ChatroomDTO chatroom;
}
