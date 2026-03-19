package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.frontend.mappers.ChatroomUserDTOMapper;
import com.chathall.springchatserver.dtos.frontend.request.ChatroomUserRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.ChatroomUserSimpleResponseDTO;
import com.chathall.springchatserver.models.ChatroomUser;
import com.chathall.springchatserver.services.db.ChatroomUserService;
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
    public ResponseEntity<ChatroomUserSimpleResponseDTO> add(@RequestBody ChatroomUserRequestDTO chatroomUserRequestDTO) {
        ChatroomUser chatroomUser = toEntity(chatroomUserRequestDTO);
        chatroomUserService.add(chatroomUser);
        chatroomUser = chatroomUserService.getById(chatroomUser.getId()).orElse(null);
        ChatroomUserSimpleResponseDTO chatroomUserResponseDTOFromDB = toDTO(chatroomUser);
        simpMessagingTemplate.convertAndSend(
                stompDestination + "/chatroom-user/add/" + chatroomUserResponseDTOFromDB.getChatroomId(),
                ResponseEntity.status(201).body(chatroomUserResponseDTOFromDB)
        );
        return ResponseEntity.status(201).body(chatroomUserResponseDTOFromDB);
    }

    private ChatroomUserSimpleResponseDTO toDTO(ChatroomUser chatroomUser) {
        return chatroomUserDTOMapper.toDTO(chatroomUser);
    }

    private ChatroomUser toEntity(ChatroomUserRequestDTO dto) {
        return chatroomUserDTOMapper.toEntity(dto);
    }
}
