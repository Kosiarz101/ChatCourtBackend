package com.chathall.springchatserver.persistence.mongodb.mappers;

import com.chathall.springchatserver.app.models.Chatroom;
import com.chathall.springchatserver.persistence.mongodb.models.ChatroomMongo;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class,
        uses = { MessageDataMapper.class, CategoryDataMapper.class, ChatroomUserDataMapper.class })
public interface ChatroomDataMapper {
    Chatroom toApp(ChatroomMongo chatroomMongo);
    ChatroomMongo toEntity(Chatroom chatroom);
}
