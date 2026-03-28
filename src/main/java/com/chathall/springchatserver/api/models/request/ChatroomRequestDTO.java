package com.chathall.springchatserver.api.models.request;

import com.chathall.springchatserver.api.models.response.CategoryResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class ChatroomRequestDTO {
    private String name;
    private String description;
    private boolean isPublic = true;
    private CategoryResponseDTO category;
    private UUID ownerId;
}
