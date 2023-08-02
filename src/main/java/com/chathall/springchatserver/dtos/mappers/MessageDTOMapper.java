package com.chathall.springchatserver.dtos.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.MessageDTO;
import com.chathall.springchatserver.models.BaseModel;
import com.chathall.springchatserver.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper
@Component
public interface MessageDTOMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author", qualifiedByName = "resourceId"),
            @Mapping(target = "chatroomId", source = "chatroom", qualifiedByName = "resourceId")
    })
    MessageDTO toDTO(Message message);

    @Named("resourceId")
    default UUID setResourceId(BaseModel resource) {
        return resource != null ? resource.getId() : null;
    }
}
