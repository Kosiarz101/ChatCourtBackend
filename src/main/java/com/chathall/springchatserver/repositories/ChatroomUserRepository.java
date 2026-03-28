package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.app.ChatroomUser;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomUserRepository {

    ChatroomUser create(ChatroomUser chatroomUser);

    Optional<ChatroomUser> getById(UUID id);

    boolean existsByUserIdAndChatroomId(UUID userId, UUID chatroomId);
}
