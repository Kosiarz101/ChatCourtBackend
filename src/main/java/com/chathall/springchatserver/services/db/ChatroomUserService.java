package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.ChatroomUser;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomUserService {

    ChatroomUser add(ChatroomUser chatroomUser);

    Optional<ChatroomUser> getById(UUID id);
}
