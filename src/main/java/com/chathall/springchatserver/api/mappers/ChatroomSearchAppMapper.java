package com.chathall.springchatserver.api.mappers;

import com.chathall.springchatserver.api.models.response.ChatroomSearchDTO;
import com.chathall.springchatserver.app.models.ChatroomSearch;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { CategoryAppMapper.class })
public interface ChatroomSearchAppMapper {
    ChatroomSearchDTO toDTO(ChatroomSearch chatroom);
    ChatroomSearch toApp(ChatroomSearchDTO chatroomSearchDTO);
}
