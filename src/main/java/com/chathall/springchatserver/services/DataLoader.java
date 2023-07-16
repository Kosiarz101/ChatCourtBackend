package com.chathall.springchatserver.services;

import com.chathall.springchatserver.repositories.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ChatroomRepository chatroomRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        if (chatroomRepository.findByName("PKP+DS").isEmpty()) {
//            Chatroom pkpChatroom = new Chatroom();
//            pkpChatroom.setName("PKP+DS").setCreationDate(LocalDateTime.now());
//
//            Chatroom eventsChatroom = new Chatroom();
//            eventsChatroom.setName("Wydarzenia").setCreationDate(LocalDateTime.now());
//
//            Chatroom memesChatroom = new Chatroom();
//            memesChatroom.setName("Memy").setCreationDate(LocalDateTime.now());
//
//            List<Chatroom> chatrooms = Arrays.asList(pkpChatroom, eventsChatroom, memesChatroom);
//            chatroomRepository.saveAll(chatrooms);
//        }
    }
}
