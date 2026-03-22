package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.request.ChatroomRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.ChatroomResponseDTO;
import com.chathall.springchatserver.models.mongodb.Chatroom;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class,
        uses = { MessageDTOMapper.class, CategoryDTOMapper.class, ChatroomUserDTOMapper.class })
public interface ChatroomDTOMapper {
    ChatroomResponseDTO toDTO(Chatroom chatroom);
    Chatroom toEntity(ChatroomRequestDTO chatroomResponseDTO);
}
