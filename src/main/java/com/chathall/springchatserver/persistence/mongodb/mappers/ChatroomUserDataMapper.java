package com.chathall.springchatserver.persistence.mongodb.mappers;

import com.chathall.springchatserver.app.models.ChatroomUser;
import com.chathall.springchatserver.persistence.mongodb.models.ChatroomUserMongo;
import org.mapstruct.Mapper;

@Mapper(uses = { AppUserDataMapper.class })
public interface ChatroomUserDataMapper {

    ChatroomUser toApp(ChatroomUserMongo chatroomUserMongo);

    ChatroomUserMongo toEntity(ChatroomUser chatroomUser);
}
