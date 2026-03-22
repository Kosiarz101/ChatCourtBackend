package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.models.app.ChatroomUser;
import com.chathall.springchatserver.models.data.mongodb.ChatroomUserMongo;
import org.mapstruct.Mapper;

@Mapper(uses = { AppUserDataMapper.class })
public interface ChatroomUserDataMapper {

    ChatroomUser toApp(ChatroomUserMongo chatroomUserMongo);

    ChatroomUserMongo toEntity(ChatroomUser chatroomUser);
}
