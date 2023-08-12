package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.models.Chatroom;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { MessageDTOMapper.class })
@Component
public interface ChatroomDTOMapper {

    ChatroomDTO toDTO(Chatroom chatroom);
}
