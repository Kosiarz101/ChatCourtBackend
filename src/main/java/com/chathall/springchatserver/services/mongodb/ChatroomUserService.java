package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.ChatroomUser;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatroomUserService {

    private final ChatroomUserRepository chatroomUserRepository;

    public ChatroomUser add(ChatroomUser chatroomUser) {
        chatroomUser.setNewId();
        chatroomUser.setCreationDate(LocalDateTime.now());
        if (chatroomUserRepository.existsByUserAndChatroom(chatroomUser.getUser(), chatroomUser.getChatroom()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom");
        return chatroomUserRepository.save(chatroomUser);
    }
}
