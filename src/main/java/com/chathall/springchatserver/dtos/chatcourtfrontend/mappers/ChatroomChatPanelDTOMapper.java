package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomChatPanelDTO;
import com.chathall.springchatserver.models.ChatroomChatPanel;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, uses= { MessageDTOMapper.class, CategoryDTOMapper.class, ChatroomUserDTOMapper.class })
public interface ChatroomChatPanelDTOMapper {
    ChatroomChatPanelDTO toDTO(ChatroomChatPanel chatroomChatPanel);
    ChatroomChatPanel toEntity(ChatroomChatPanelDTO chatroomDTO);
}
