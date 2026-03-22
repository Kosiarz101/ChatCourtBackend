package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.mongodb.Message;
import com.chathall.springchatserver.repositories.MessageRepository;
import com.chathall.springchatserver.services.db.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageMongoService implements MessageService {

    private static final int DEFAULT_MESSAGE_PAGE = 0;
    private static final int DEFAULT_MESSAGE_SIZE = 5;

    private final MessageRepository messageRepository;

    public Message add(Message message) {
        message.setNewId();
        LocalDateTime now = LocalDateTime.now();
        message.setCreationDate(now);
        message.setLastModifiedDate(now);
        return messageRepository.save(message);
    }

    public Slice<Message> getByChatroomIdAndBeforeOrEqualCreationDate(UUID chatroomId,
                                                                      LocalDateTime endDate,
                                                                      Integer page,
                                                                      Integer size) {
        var sort = Sort.by(Sort.Direction.DESC, "creationDate").and(Sort.by("id").descending());
        return messageRepository.findByChatroomIdAndCreationDateLessThanEqual(
                chatroomId, endDate, PageRequest.of(setPage(page), setSize(size), sort)
        );
    }

    public void updateMessage(Message message) {
        message.setLastModifiedDate(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void deleteMessage(UUID id) {
        messageRepository.deleteById(id);
    }

    public Optional<Message> getById(UUID id) {
        return messageRepository.findById(id);
    }

    private int setPage(Integer page) {
        return page == null ? DEFAULT_MESSAGE_PAGE : page;
    }

    private int setSize(Integer size) {
        return size == null ? DEFAULT_MESSAGE_SIZE : size;
    }
}
