package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final MongoTemplate mongoTemplate;
    private final int DEFAULT_CHATROOM_SIZE = 10;

    public void add(Chatroom chatroom) {
        if (chatroomRepository.existsById(chatroom.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given id already exists");
        }
        if (chatroomRepository.existsByName(chatroom.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        chatroom.setNewId();
        chatroom.setCreationDate(LocalDateTime.now());
        chatroomRepository.save(chatroom);
    }

    public Optional<Chatroom> getById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public Slice<Chatroom> getAllPageable(int page) {
        return getAllPageable(page, DEFAULT_CHATROOM_SIZE);
    }

    public Slice<Chatroom> getAllPageable(int page, int size) {
        return chatroomRepository.findAllByOrderByCreationDateDesc(PageRequest.of(page, size));
    }

//    public Slice<Chatroom> getAllPageable(int test, int page, int size) {
//        Query query = new Query();
//        query.limit(DEFAULT_CHATROOM_SIZE);
//        query.fields().include("messages").slice("messages", 1);
//        List<Chatroom> chatrooms = mongoTemplate.find(query, Chatroom.class);
//        return new SliceImpl<Chatroom>(chatrooms, PageRequest.of(0, DEFAULT_CHATROOM_SIZE), chatrooms.size() == 20);
//    }
}
