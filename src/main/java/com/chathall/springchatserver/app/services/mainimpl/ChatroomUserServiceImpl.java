package com.chathall.springchatserver.app.services.mainimpl;

import com.chathall.springchatserver.app.models.ChatroomUser;
import com.chathall.springchatserver.app.services.ChatroomUserService;
import com.chathall.springchatserver.persistence.repositories.ChatroomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomUserServiceImpl implements ChatroomUserService {

    private final ChatroomUserRepository chatroomUserRepository;

    public ChatroomUser add(ChatroomUser chatroomUser) {
        chatroomUser.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroomUser.setCreationDate(now);
        chatroomUser.setLastModifiedDate(now);
        if (chatroomUserRepository.existsByUserIdAndChatroomId(chatroomUser.getUser().getId(), chatroomUser.getChatroom().getId()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom");

        return chatroomUserRepository.create(chatroomUser);
    }

    public Optional<ChatroomUser> getById(UUID id) {
        return chatroomUserRepository.getById(id);
    }
}
