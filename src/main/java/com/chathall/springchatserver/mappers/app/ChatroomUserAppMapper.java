package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.request.ChatroomUserRequestDTO;
import com.chathall.springchatserver.models.api.response.ChatroomUserSimpleResponseDTO;
import com.chathall.springchatserver.models.app.ChatroomUser;
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
