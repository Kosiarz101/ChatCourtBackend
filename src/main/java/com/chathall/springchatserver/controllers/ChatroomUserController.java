package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomUserDTOMapper;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.CycleAvoidingContext;
import com.chathall.springchatserver.models.ChatroomUser;
import com.chathall.springchatserver.services.mongodb.ChatroomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final CycleAvoidingContext cycleAvoidingContext = new CycleAvoidingContext();

    @PostMapping
    public ResponseEntity<ChatroomUserDTO> add(@RequestBody ChatroomUserDTO chatroomUserDTO) {
        ChatroomUser chatroomUser = chatroomUserService.add(toEntity(chatroomUserDTO));
        return ResponseEntity.status(201).body(toDTO(chatroomUser));
    }

    private ChatroomUserDTO toDTO(ChatroomUser chatroomUser) {
        return chatroomUserDTOMapper.toDTO(chatroomUser, cycleAvoidingContext);
    }

    private ChatroomUser toEntity(ChatroomUserDTO dto) {
        return chatroomUserDTOMapper.toEntity(dto, cycleAvoidingContext);
    }
}
