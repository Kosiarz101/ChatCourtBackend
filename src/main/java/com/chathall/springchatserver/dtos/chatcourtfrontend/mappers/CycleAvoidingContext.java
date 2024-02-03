package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

public class CycleAvoidingContext {

    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        if (knownInstances.containsKey(source) && targetType.equals(ChatroomDTO.class)) {
            source = null;
            targetType.cast(knownInstances.get(source));
        } else if (knownInstances.containsKey(source) && targetType.equals(ChatroomUserDTO.class)) {
            source = null;
            targetType.cast(knownInstances.get(source));
        }
//        if (knownInstances.containsKey(source) && source instanceof Set) {
//            Set set = (Set)source;
//            for (Object o : set) {
//                deleteRedundantComposition(o, targetType);
//                return targetType.cast(knownInstances.get(source));
//            }
//        } else if (knownInstances.containsKey(source)) {
//            deleteRedundantComposition(source, targetType);
//            return targetType.cast(knownInstances.get(source));
//        }
        return null;
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        knownInstances.put(source, target);
    }

    private <T> void deleteRedundantComposition(Object source, Class<T> targetType) {
        if (knownInstances.containsKey(source) && targetType.equals(ChatroomDTO.class)) {
            forChatroomDTO(source);
        } else if (knownInstances.containsKey(source) && targetType.equals(ChatroomUserDTO.class)) {
            forChatroomUserDTO(source);
        }
    }

    private void forChatroomDTO(Object source) {
        ChatroomDTO chatroomDTO = (ChatroomDTO) knownInstances.get(source);
    }

    private void forChatroomUserDTO(Object source) {
        ChatroomUserDTO chatroomUserDTO = (ChatroomUserDTO) knownInstances.get(source);
        chatroomUserDTO.setChatroom(null);
        chatroomUserDTO.setUser(null);
    }
}
