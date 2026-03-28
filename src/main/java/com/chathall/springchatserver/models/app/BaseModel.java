package com.chathall.springchatserver.models.app;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseModel {
    private UUID id = UUID.randomUUID();
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;

    public void setNewId() {
        id = UUID.randomUUID();
    }
}
