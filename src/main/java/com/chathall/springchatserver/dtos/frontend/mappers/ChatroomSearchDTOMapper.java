package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.response.ChatroomSearchDTO;
import com.chathall.springchatserver.models.ChatroomSearch;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { CategoryDTOMapper.class })
public interface ChatroomSearchDTOMapper {
    ChatroomSearchDTO toDTO(ChatroomSearch chatroom);
    ChatroomSearch toEntity(ChatroomSearchDTO chatroomSearchDTO);
}
