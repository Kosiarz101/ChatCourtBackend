package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final int DEFAULT_CHATROOM_SIZE = 1;

    public void add(Chatroom chatroom) {
        if (chatroomRepository.existsById(chatroom.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given id already exists");
        }
        if (chatroomRepository.existsByName(chatroom.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        chatroom.setCreationDate(LocalDateTime.now());
        chatroomRepository.save(chatroom);
    }

    public Optional<Chatroom> getById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public List<Chatroom> getAll() {
        return chatroomRepository.findAllByOrderByCreationDateDesc();
    }

    public Slice<Chatroom> getAllPageable(int page) {
        return getAllPageable(page, DEFAULT_CHATROOM_SIZE);
    }

    public Slice<Chatroom> getAllPageable(int page, int size) {
        return chatroomRepository.findAllByOrderByCreationDateDesc(PageRequest.of(page, size));
    }
}
