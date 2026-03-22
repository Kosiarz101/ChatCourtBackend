package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.response.ChatroomSearchDTO;
import com.chathall.springchatserver.models.app.ChatroomSearch;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { CategoryAppMapper.class })
public interface ChatroomSearchAppMapper {
    ChatroomSearchDTO toDTO(ChatroomSearch chatroom);
    ChatroomSearch toApp(ChatroomSearchDTO chatroomSearchDTO);
}
