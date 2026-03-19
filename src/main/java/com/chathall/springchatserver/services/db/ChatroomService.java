package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomSearch;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomService {

    Chatroom add(Chatroom chatroom);

    Slice<Chatroom> findAll(@Nullable Integer pageNumber, @Nullable Integer pageSize);

    Optional<Chatroom> findById(UUID id);

    Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers);

    Slice<Chatroom> findByUserIdPageable(UUID chatroomUserId, boolean includeMessages,
                                         boolean includeChatroomUsers, int page, @Nullable Integer size);

    Slice<ChatroomSearch> findByNameAndCategoryId(String name, UUID categoryId, int page, @Nullable Integer size);

    Slice<ChatroomSearch> findByNameContains(String name, int page, @Nullable Integer size);
}
