package com.chathall.springchatserver.services.entity.mainimpl;

import com.chathall.springchatserver.models.app.ChatroomUser;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import com.chathall.springchatserver.services.entity.ChatroomUserService;
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
