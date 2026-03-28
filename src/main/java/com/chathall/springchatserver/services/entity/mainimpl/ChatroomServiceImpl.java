package com.chathall.springchatserver.services.entity.mainimpl;

import com.chathall.springchatserver.models.app.Chatroom;
import com.chathall.springchatserver.models.app.ChatroomSearch;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import com.chathall.springchatserver.services.entity.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final int DEFAULT_CHATROOM_SIZE = 5;
    private final int DEFAULT_CHATROOM_PAGE = 0;

    private final ChatroomRepository chatroomRepository;

    public Chatroom add(Chatroom chatroom) {
        chatroom.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroom.setCreationDate(now);
        chatroom.setLastModifiedDate(now);

        return chatroomRepository.create(chatroom);
    }

    public Slice<Chatroom> findAll(@Nullable Integer pageNumber, @Nullable Integer pageSize) {
        return chatroomRepository
                .findAllByOrderByCreationDateDesc(getPage(pageNumber), getPageSize(pageSize));
    }

    public Optional<Chatroom> findById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers) {
        return chatroomRepository.findById(id, includeMessages, includeChatroomUsers);
    }

    public Slice<Chatroom> findByUserId(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers,
                                        @Nullable Integer page, @Nullable Integer size) {
        page = getPage(page);
        size = getPageSize(size);
        return chatroomRepository.findByUserId(chatroomUserId, includeMessages, includeChatroomUsers, page, size);
    }

    public Slice<ChatroomSearch> findByNameAndCategoryId(String name, UUID categoryId, @Nullable Integer page,
                                                         @Nullable Integer size) {
        return chatroomRepository
                .findAllPublicByNameAndCategory(name, categoryId, getPage(page), getPageSize(size));
    }

    public Slice<ChatroomSearch> findByNameContains(String name, @Nullable Integer page, @Nullable Integer size) {
        return chatroomRepository.findAllPublicByName(name, getPage(page), getPageSize(size));
    }

    private int getPage(Integer requestPageNumber) {
        return requestPageNumber == null ? DEFAULT_CHATROOM_PAGE : requestPageNumber;
    }

    private int getPageSize(Integer requestPageSize) {
        return requestPageSize == null ? DEFAULT_CHATROOM_SIZE : requestPageSize;
    }
}
