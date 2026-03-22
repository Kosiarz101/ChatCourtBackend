package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.request.ChatroomUserRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.ChatroomUserSimpleResponseDTO;
import com.chathall.springchatserver.models.mongodb.ChatroomUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = { AppUserDTOMapper.class, MessageDTOMapper.class })
public interface ChatroomUserDTOMapper {

    @Mappings({
            @Mapping(source = "chatroom.id", target = "chatroomId")
    })
    ChatroomUserSimpleResponseDTO toDTO(ChatroomUser chatroomUser);
    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "chatroomId", target = "chatroom.id")
    })
    ChatroomUser toEntity(ChatroomUserRequestDTO chatroomUserRequestDTO);
}
