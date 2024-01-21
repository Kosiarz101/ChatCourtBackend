package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserDTO;
import com.chathall.springchatserver.models.ChatroomUser;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = { AppUserDTOMapper.class, ChatroomDTOMapper.class })
public interface ChatroomUserDTOMapper {

    ChatroomUserDTO toDTO(ChatroomUser chatroomUser, @Context CycleAvoidingContext cycleAvoidingContext);
    ChatroomUser toEntity(ChatroomUserDTO chatroomUserDTO, @Context CycleAvoidingContext cycleAvoidingContext);
}
