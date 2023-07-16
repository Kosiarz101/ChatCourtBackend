package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;
}
