package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.request.ChatroomRequestDTO;
import com.chathall.springchatserver.models.api.response.ChatroomResponseDTO;
import com.chathall.springchatserver.models.app.Chatroom;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class,
        uses = { MessageAppMapper.class, CategoryAppMapper.class, ChatroomUserAppMapper.class })
public interface ChatroomAppMapper {
    ChatroomResponseDTO toDTO(Chatroom chatroom);
    Chatroom toApp(ChatroomRequestDTO chatroomResponseDTO);
}
