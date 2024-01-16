package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomSearchDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserFlatDTO;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
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
    private final ChatroomUserRepository chatroomUserRepository;
    private final MongoTemplate mongoTemplate;
    private final int DEFAULT_CHATROOM_SIZE = 5;

    public Chatroom add(Chatroom chatroom) {
        if (chatroomRepository.existsById(chatroom.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given id already exists");
        }
        chatroom.setNewId();
        chatroom.setCreationDate(LocalDateTime.now());
        return chatroomRepository.save(chatroom);
    }

    public Optional<Chatroom> getById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public Slice<Chatroom> findByUserIdPageable(UUID chatroomUserId, boolean includeMessages, int page, @Nullable Integer size) {
        size = size == null ? DEFAULT_CHATROOM_SIZE : size;
        AppUser appUser = new AppUser();
        appUser.setId(chatroomUserId);
        Slice<ChatroomUserFlatDTO> chatroomUsers = chatroomUserRepository.findByUser(appUser, PageRequest.of(page, size));
        List<UUID> chatroomIds = chatroomUsers.getContent().stream().map(ChatroomUserFlatDTO::getChatroom).toList();
        if (includeMessages)
            return chatroomRepository.findAllByIdsOrderByCreationDateDescWithMessages(chatroomIds, PageRequest.of(page, size));
        else
            return chatroomRepository.findAllByOrderByCreationDateDesc(PageRequest.of(page, size));
    }

    public Slice<ChatroomSearchDTO> findByNameAndCategoryId(String name, UUID categoryId, int page, @Nullable Integer size) {
        int pageSize = size == null ? DEFAULT_CHATROOM_SIZE : size;
        return chatroomRepository.findAllPublicByNameAndCategory(name, categoryId, PageRequest.of(page, pageSize));
    }

    public Slice<ChatroomSearchDTO> findByNameContains(String name, int page, @Nullable Integer size) {
        int pageSize = size == null ? DEFAULT_CHATROOM_SIZE : size;
        return chatroomRepository.findAllPublicByName(name, PageRequest.of(page, pageSize));
    }

//    public Slice<Chatroom> getAllPageable(int test, int page, int size) {
//        Query query = new Query();
//        query.limit(DEFAULT_CHATROOM_SIZE);
//        query.fields().include("messages").slice("messages", 1);
//        List<Chatroom> chatrooms = mongoTemplate.find(query, Chatroom.class);
//        return new SliceImpl<Chatroom>(chatrooms, PageRequest.of(0, DEFAULT_CHATROOM_SIZE), chatrooms.size() == 20);
//    }
}
