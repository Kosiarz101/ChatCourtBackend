package com.chathall.springchatserver.persistence.repositories;

import com.chathall.springchatserver.app.models.ChatroomUser;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomUserRepository {

    ChatroomUser create(ChatroomUser chatroomUser);

    Optional<ChatroomUser> getById(UUID id);

    boolean existsByUserIdAndChatroomId(UUID userId, UUID chatroomId);
}
