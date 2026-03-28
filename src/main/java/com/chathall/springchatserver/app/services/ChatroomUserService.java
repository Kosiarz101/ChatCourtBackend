package com.chathall.springchatserver.app.services;

import com.chathall.springchatserver.app.models.ChatroomUser;

import java.util.Optional;
import java.util.UUID;

public interface ChatroomUserService {

    ChatroomUser add(ChatroomUser chatroomUser);

    Optional<ChatroomUser> getById(UUID id);
}
