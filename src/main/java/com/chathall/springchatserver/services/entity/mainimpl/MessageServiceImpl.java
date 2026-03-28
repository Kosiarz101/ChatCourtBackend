package com.chathall.springchatserver.services.entity.mainimpl;

import com.chathall.springchatserver.models.app.Message;
import com.chathall.springchatserver.repositories.MessageRepository;
import com.chathall.springchatserver.services.entity.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final int DEFAULT_MESSAGE_PAGE = 0;
    private static final int DEFAULT_MESSAGE_SIZE = 5;

    private final MessageRepository messageRepository;

    public Message add(Message message) {
        message.setNewId();
        LocalDateTime now = LocalDateTime.now();
        message.setCreationDate(now);
        message.setLastModifiedDate(now);

        return messageRepository.create(message);
    }

    public Slice<Message> getByChatroomIdAndBeforeOrEqualCreationDate(UUID chatroomId,
                                                                      LocalDateTime endDate,
                                                                      Integer page,
                                                                      Integer size) {
        return messageRepository.findByChatroomIdAndCreationDateLessThanEqual(
                chatroomId, endDate, setPage(page), setSize(size)
        );
    }

    public Message update(Message message) {
        message.setLastModifiedDate(LocalDateTime.now());

        return messageRepository.update(message);
    }

    public void delete(UUID id) {
        messageRepository.delete(id);
    }

    public Optional<Message> getById(UUID id) {
        return messageRepository.getById(id);
    }

    private int setPage(Integer page) {
        return page == null ? DEFAULT_MESSAGE_PAGE : page;
    }

    private int setSize(Integer size) {
        return size == null ? DEFAULT_MESSAGE_SIZE : size;
    }
}
