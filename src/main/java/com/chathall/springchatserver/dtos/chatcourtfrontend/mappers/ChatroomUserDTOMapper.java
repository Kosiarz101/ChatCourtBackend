package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserDTO;
import com.chathall.springchatserver.models.ChatroomUser;
import org.mapstruct.Mapper;

@Mapper(uses = { AppUserDTOMapper.class, MessageDTOMapper.class })
public interface ChatroomUserDTOMapper {

    ChatroomUserDTO toDTO(ChatroomUser chatroomUser);
    ChatroomUser toEntity(ChatroomUserDTO chatroomUserDTO);
}
