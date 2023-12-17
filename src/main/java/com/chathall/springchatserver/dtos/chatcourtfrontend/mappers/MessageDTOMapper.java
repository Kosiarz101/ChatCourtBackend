package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.MessageDTO;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.models.BaseModel;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper
public interface MessageDTOMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author", qualifiedByName = "resourceId"),
            @Mapping(target = "chatroomId", source = "chatroom", qualifiedByName = "resourceId")
    })
    MessageDTO toDTO(Message message);

    @Mappings({
            @Mapping(target = "author", source = "authorId", qualifiedByName = "authorEntity"),
            @Mapping(target = "chatroom", source = "chatroomId", qualifiedByName = "chatroomEntity")
    })
    Message toEntity(MessageDTO messageDTO);

    @Named("resourceId")
    default UUID setResourceId(BaseModel resource) {
        return resource != null ? resource.getId() : null;
    }

    @Named("chatroomEntity")
    default Chatroom setChatroom(UUID chatroomId) {
        if (chatroomId != null) {
            Chatroom chatroom = new Chatroom();
            chatroom.setId(chatroomId);
            return chatroom;
        } else
            return null;
    }

    @Named("authorEntity")
    default AppUser setAuthor(UUID authorId) {
        if (authorId != null) {
            AppUser appUser = new AppUser();
            appUser.setId(authorId);
            return appUser;
        } else
            return null;
    }
}
