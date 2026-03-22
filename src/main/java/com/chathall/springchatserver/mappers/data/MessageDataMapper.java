package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.models.app.Message;
import com.chathall.springchatserver.models.data.mongodb.MessageMongo;
import org.mapstruct.Mapper;

@Mapper(uses = {ChatroomUserDataMapper.class})
public interface MessageDataMapper {

    Message toApp(MessageMongo messageMongo);

    MessageMongo toEntity(Message message);
}
