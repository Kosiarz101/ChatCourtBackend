package com.chathall.springchatserver.api.models.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class BaseEntityDTO {
    private UUID id;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
