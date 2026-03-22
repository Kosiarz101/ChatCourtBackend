package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.app.ChatroomUser;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomUserService {

    ChatroomUser add(ChatroomUser chatroomUser);

    Optional<ChatroomUser> getById(UUID id);
}
