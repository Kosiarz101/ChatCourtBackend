package com.chathall.springchatserver.repositories.mongodb;

import com.chathall.springchatserver.mappers.data.MessageDataMapper;
import com.chathall.springchatserver.models.app.Message;
import com.chathall.springchatserver.models.data.mongodb.MessageMongo;
import com.chathall.springchatserver.repositories.MessageRepository;
import com.chathall.springchatserver.repositories.mongodb.spring.MessageSpringMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MessageMongoRepository implements MessageRepository {

    private final MessageSpringMongoRepository messageRepository;
    private final MessageDataMapper messageDataMapper;

    @Override
    public Message create(Message message) {
        MessageMongo messageMongo = messageDataMapper.toEntity(message);
        messageMongo = messageRepository.save(messageMongo);

        return messageDataMapper.toApp(messageMongo);
    }

    @Override
    public Message update(Message message) {
        MessageMongo messageMongo = messageDataMapper.toEntity(message);
        messageRepository.save(messageMongo);

        return messageDataMapper.toApp(messageMongo);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Optional<Message> getById(UUID id) {
        Optional<MessageMongo> messageMongo = messageRepository.findById(id);
        return messageMongo.map(messageDataMapper::toApp);
    }

    @Override
    public Slice<Message> findByChatroomIdAndCreationDateLessThanEqual(UUID chatroomId, LocalDateTime endDate,
                                                                       int page, int size) {
        var sort = Sort.by(Sort.Direction.DESC, "creationDate").and(Sort.by("id").descending());

        Slice<MessageMongo> messageMongos = messageRepository.findByChatroomIdAndCreationDateLessThanEqual(
                chatroomId, endDate, PageRequest.of(page, size, sort)
        );

        return messageMongos.map(messageDataMapper::toApp);
    }
}
