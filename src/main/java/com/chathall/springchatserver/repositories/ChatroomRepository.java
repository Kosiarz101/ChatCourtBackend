package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.app.Chatroom;
import com.chathall.springchatserver.models.app.ChatroomSearch;
import org.springframework.data.domain.Slice;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomRepository {

    Chatroom create(Chatroom Chatroom);

    Slice<Chatroom> findAllByOrderByCreationDateDesc(int pageNumber, int pageSize);

    Optional<Chatroom> findById(UUID id);

    Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers);

    Slice<Chatroom> findByUserId(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers,
                                 int page, int size);

    Slice<ChatroomSearch> findAllPublicByNameAndCategory(String name, UUID categoryId, int page, int size);

    Slice<ChatroomSearch> findAllPublicByName(String name, int page, int size);
}
