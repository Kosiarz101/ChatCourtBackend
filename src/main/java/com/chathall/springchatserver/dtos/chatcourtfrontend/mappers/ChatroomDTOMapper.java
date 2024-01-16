package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.models.Chatroom;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { MessageDTOMapper.class, CategoryDTOMapper.class }, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ChatroomDTOMapper {
    ChatroomDTO toDTO(Chatroom chatroom, @Context CycleAvoidingContext cycleAvoidingContext);
    Chatroom toEntity(ChatroomDTO chatroomDTO, @Context CycleAvoidingContext cycleAvoidingContext);
}
