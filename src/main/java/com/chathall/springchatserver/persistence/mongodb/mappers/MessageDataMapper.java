package com.chathall.springchatserver.persistence.mongodb.mappers;

import com.chathall.springchatserver.app.models.Message;
import com.chathall.springchatserver.persistence.mongodb.models.MessageMongo;
import org.mapstruct.Mapper;

@Mapper(uses = {ChatroomUserDataMapper.class})
public interface MessageDataMapper {

    Message toApp(MessageMongo messageMongo);

    MessageMongo toEntity(Message message);
}
