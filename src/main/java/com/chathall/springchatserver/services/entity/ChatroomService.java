package com.chathall.springchatserver.services.entity;

import com.chathall.springchatserver.models.app.ChatroomSearch;
import com.chathall.springchatserver.models.app.Chatroom;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomService {

    Chatroom add(Chatroom Chatroom);

    Slice<Chatroom> findAll(@Nullable Integer pageNumber, @Nullable Integer pageSize);

    Optional<Chatroom> findById(UUID id);

    Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers);

    Slice<Chatroom> findByUserId(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers,
                                 @Nullable Integer page, @Nullable Integer size);

    Slice<ChatroomSearch> findByNameAndCategoryId(String name, UUID categoryId,  @Nullable Integer page,
                                                  @Nullable Integer size);

    Slice<ChatroomSearch> findByNameContains(String name,  @Nullable Integer page, @Nullable Integer size);
}
