package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.frontend.mappers.ChatroomDTOMapper;
import com.chathall.springchatserver.dtos.frontend.mappers.ChatroomSearchDTOMapper;
import com.chathall.springchatserver.dtos.frontend.request.ChatroomRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.ChatroomResponseDTO;
import com.chathall.springchatserver.dtos.frontend.response.ChatroomSearchDTO;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomSearch;
import com.chathall.springchatserver.services.db.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    private final ChatroomDTOMapper chatroomDTOMapper;
    private final ChatroomSearchDTOMapper chatroomSearchDTOMapper;

    @PostMapping
    public ResponseEntity<ChatroomResponseDTO> add(@RequestBody ChatroomRequestDTO chatroomRequestDTO) {
        Chatroom chatroom = chatroomService.add(toEntity(chatroomRequestDTO));
        return ResponseEntity.status(201).body(toDTO(chatroom));
    }

    @GetMapping
    public ResponseEntity<Slice<ChatroomResponseDTO>> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms = chatroomService.findAll(page, size);
        Slice<ChatroomResponseDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.status(200).body(results);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ChatroomResponseDTO> getById(@PathVariable UUID id) {
//        Optional<Chatroom> chatroomOptional = chatroomService.findById(id);
//        Chatroom chatroom = chatroomOptional.orElseThrow(() ->
//                new ResponseStatusException(HttpStatusCode.valueOf(404), "Chatroom has not been found")
//        );
//        return ResponseEntity.status(200).body(toDTO(chatroom));
//    }

    @GetMapping(path = "/{id}", params = {"includeMessages", "includeUsers"})
    public ResponseEntity<ChatroomResponseDTO> getById(@PathVariable UUID id,
                                                       @RequestParam(required = false, defaultValue = "false") boolean includeMessages,
                                                       @RequestParam(required = false, defaultValue = "false") boolean includeUsers) {
        Optional<Chatroom> chatroomOptional = chatroomService.findById(id, includeMessages, includeUsers);
        Chatroom chatroom = chatroomOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(404), "Chatroom has not been found")
        );
        return ResponseEntity.status(200).body(toDTO(chatroom));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<Slice<ChatroomResponseDTO>> getAllByUserIdPageable(@RequestParam("userId") UUID userId,
                                                                             @RequestParam(required = false, defaultValue = "false") boolean includeMessages,
                                                                             @RequestParam(required = false, defaultValue = "false") boolean includeUsers,
                                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                                             @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms = chatroomService.findByUserIdPageable(userId, includeMessages, includeUsers, page, size);
        Slice<ChatroomResponseDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName", "categoryId"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByNameAndCategoryIdPageable(
            @RequestParam("chatroomName") String chatroomName,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameAndCategoryId(chatroomName, categoryId, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByNamePageable(@RequestParam("chatroomName") String chatroomName,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameContains(chatroomName, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    private Chatroom toEntity(ChatroomRequestDTO dto) {
        return chatroomDTOMapper.toEntity(dto);
    }

    private ChatroomResponseDTO toDTO(Chatroom chatroom) {
        return chatroomDTOMapper.toDTO(chatroom);
    }

    private ChatroomSearchDTO toDTO(ChatroomSearch chatroomSearch) {
        return chatroomSearchDTOMapper.toDTO(chatroomSearch);
    }
}
