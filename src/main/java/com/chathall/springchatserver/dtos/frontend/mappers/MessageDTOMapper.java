package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.request.MessageRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.MessageResponseDTO;
import com.chathall.springchatserver.models.*;
import com.chathall.springchatserver.models.mongodb.Chatroom;
import com.chathall.springchatserver.models.mongodb.ChatroomUser;
import com.chathall.springchatserver.models.mongodb.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(uses = {ChatroomUserDTOMapper.class})
public interface MessageDTOMapper {

    @Mappings({
            @Mapping(target = "chatroomId", source = "chatroom", qualifiedByName = "resourceId")
    })
    MessageResponseDTO toDTO(Message message);

    @Mappings({
            @Mapping(target = "author", source = "authorId", qualifiedByName = "authorEntity"),
            @Mapping(target = "chatroom", source = "chatroomId", qualifiedByName = "chatroomEntity")
    })
    Message toEntity(MessageRequestDTO messageRequestDTO);

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
    default ChatroomUser setAuthor(UUID authorId) {
        if (authorId != null) {
            ChatroomUser chatroomUser = new ChatroomUser();
            chatroomUser.setId(authorId);
            return chatroomUser;
        } else
            return null;
    }
}
