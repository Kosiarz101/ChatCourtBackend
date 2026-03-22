package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.mappers.app.CategoryAppMapper;
import com.chathall.springchatserver.mappers.app.MessageAppMapper;
import com.chathall.springchatserver.models.app.Chatroom;
import com.chathall.springchatserver.models.data.mongodb.ChatroomMongo;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class,
        uses = { MessageDataMapper.class, CategoryDataMapper.class, ChatroomUserDataMapper.class })
public interface ChatroomDataMapper {
    Chatroom toApp(ChatroomMongo chatroomMongo);
    ChatroomMongo toEntity(Chatroom chatroom);
}
