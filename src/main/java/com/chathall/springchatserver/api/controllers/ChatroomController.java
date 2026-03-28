package com.chathall.springchatserver.api.controllers;

import com.chathall.springchatserver.api.mappers.ChatroomAppMapper;
import com.chathall.springchatserver.api.mappers.ChatroomSearchAppMapper;
import com.chathall.springchatserver.api.models.request.ChatroomRequestDTO;
import com.chathall.springchatserver.api.models.response.ChatroomResponseDTO;
import com.chathall.springchatserver.api.models.response.ChatroomSearchDTO;
import com.chathall.springchatserver.app.models.Chatroom;
import com.chathall.springchatserver.app.models.ChatroomSearch;
import com.chathall.springchatserver.app.services.ChatroomService;
import com.chathall.springchatserver.models.SliceResponse;
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
    private final ChatroomAppMapper chatroomAppMapper;
    private final ChatroomSearchAppMapper chatroomSearchDTOMapper;

    @PostMapping
    public ResponseEntity<ChatroomResponseDTO> add(@RequestBody ChatroomRequestDTO chatroomRequestDTO) {
        Chatroom Chatroom = chatroomService.add(toApp(chatroomRequestDTO));
        return ResponseEntity.status(201).body(toDTO(Chatroom));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<ChatroomResponseDTO>> getAll(@RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms = chatroomService.findAll(page, size);
        Slice<ChatroomResponseDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(SliceResponse.fromSlice(results));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ChatroomResponseDTO> getById(@PathVariable UUID id,
                                                       @RequestParam(defaultValue = "false") boolean includeMessages,
                                                       @RequestParam(defaultValue = "false") boolean includeUsers) {
        Optional<Chatroom> chatroomOptional = chatroomService.findById(id, includeMessages, includeUsers);
        Chatroom Chatroom = chatroomOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(404), "Chatroom has not been found")
        );
        return ResponseEntity.status(200).body(toDTO(Chatroom));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<SliceResponse<ChatroomResponseDTO>> getAllByUserId(@RequestParam("userId") UUID userId,
                                                                             @RequestParam(defaultValue = "false") boolean includeMessages,
                                                                             @RequestParam(defaultValue = "false") boolean includeUsers,
                                                                             @RequestParam(required = false) Integer page,
                                                                             @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms = chatroomService.findByUserId(userId, includeMessages, includeUsers, page, size);
        Slice<ChatroomResponseDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(SliceResponse.fromSlice(results));
    }

    @GetMapping(params = {"chatroomName", "categoryId"})
    public ResponseEntity<SliceResponse<ChatroomSearchDTO>> findPublicByNameAndCategoryIdPageable(
            @RequestParam("chatroomName") String chatroomName,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameAndCategoryId(chatroomName, categoryId, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(SliceResponse.fromSlice(results));
    }

    @GetMapping(params = {"chatroomName"})
    public ResponseEntity<SliceResponse<ChatroomSearchDTO>> findPublicByNamePageable(@RequestParam("chatroomName") String chatroomName,
                                                                                     @RequestParam(required = false) Integer page,
                                                                                     @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameContains(chatroomName, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(SliceResponse.fromSlice(results));
    }

    private Chatroom toApp(ChatroomRequestDTO dto) {
        return chatroomAppMapper.toApp(dto);
    }

    private ChatroomResponseDTO toDTO(Chatroom chatroom) {
        return chatroomAppMapper.toDTO(chatroom);
    }

    private ChatroomSearchDTO toDTO(ChatroomSearch chatroomSearch) {
        return chatroomSearchDTOMapper.toDTO(chatroomSearch);
    }
}
