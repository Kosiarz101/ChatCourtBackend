package com.chathall.springchatserver.dtos.frontend.request;

import com.chathall.springchatserver.dtos.frontend.response.CategoryResponseDTO;
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
