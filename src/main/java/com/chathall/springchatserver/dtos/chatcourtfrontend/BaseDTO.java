package com.chathall.springchatserver.dtos.chatcourtfrontend;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class BaseDTO {

    private UUID id = UUID.randomUUID();
    private LocalDateTime creationDate;
}
