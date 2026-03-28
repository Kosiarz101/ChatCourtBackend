package com.chathall.springchatserver.api.mappers;

import com.chathall.springchatserver.api.models.request.ChatroomUserRequestDTO;
import com.chathall.springchatserver.api.models.response.ChatroomUserSimpleResponseDTO;
import com.chathall.springchatserver.app.models.ChatroomUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = { AppUserAppMapper.class, MessageAppMapper.class })
public interface ChatroomUserAppMapper {

    @Mappings({
            @Mapping(source = "chatroom.id", target = "chatroomId")
    })
    ChatroomUserSimpleResponseDTO toDTO(ChatroomUser chatroomUser);
    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "chatroomId", target = "chatroom.id")
    })
    ChatroomUser toApp(ChatroomUserRequestDTO chatroomUserRequestDTO);
}
