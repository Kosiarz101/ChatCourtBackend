package com.chathall.springchatserver.api.mappers;

import com.chathall.springchatserver.api.models.request.ChatroomRequestDTO;
import com.chathall.springchatserver.api.models.response.ChatroomResponseDTO;
import com.chathall.springchatserver.app.models.Chatroom;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class,
        uses = { MessageAppMapper.class, CategoryAppMapper.class, ChatroomUserAppMapper.class })
public interface ChatroomAppMapper {
    ChatroomResponseDTO toDTO(Chatroom chatroom);
    Chatroom toApp(ChatroomRequestDTO chatroomResponseDTO);
}
