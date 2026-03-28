package com.chathall.springchatserver.api.models.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomSearchDTO {
    private UUID id;
    private String name;
    private String description;
    private boolean isPublic;
    private CategoryResponseDTO category;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private int userCount;
}
