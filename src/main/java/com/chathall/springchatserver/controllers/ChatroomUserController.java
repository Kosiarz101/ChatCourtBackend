package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomUserDTOMapper;
import com.chathall.springchatserver.models.ChatroomUser;
import com.chathall.springchatserver.services.mongodb.ChatroomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom-user")
@RequiredArgsConstructor
public class ChatroomUserController {

    private final ChatroomUserService chatroomUserService;
    private final ChatroomUserDTOMapper chatroomUserDTOMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @Value("${client.stomp.destination}")
    private String stompDestination;

    @PostMapping
    public ResponseEntity<ChatroomUserDTO> add(@RequestBody ChatroomUserDTO chatroomUserDTO) {
        ChatroomUser chatroomUser = toEntity(chatroomUserDTO);
        chatroomUserService.add(chatroomUser);
        chatroomUser = chatroomUserService.getById(chatroomUser.getId()).orElse(null);
        ChatroomUserDTO chatroomUserDTOFromDB = toDTO(chatroomUser);
        simpMessagingTemplate.convertAndSend(
                stompDestination + "/chatroom-user/add/" + chatroomUserDTOFromDB.getChatroom().getId(),
                ResponseEntity.status(201).body(chatroomUserDTOFromDB)
        );
        return ResponseEntity.status(201).body(chatroomUserDTOFromDB);
    }

    private ChatroomUserDTO toDTO(ChatroomUser chatroomUser) {
        return chatroomUserDTOMapper.toDTO(chatroomUser);
    }

    private ChatroomUser toEntity(ChatroomUserDTO dto) {
        return chatroomUserDTOMapper.toEntity(dto);
    }
}
