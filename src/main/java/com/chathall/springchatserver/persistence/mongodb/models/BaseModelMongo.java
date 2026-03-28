package com.chathall.springchatserver.persistence.mongodb.models;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseModelMongo {
    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;

    public void setNewId() {
        id = UUID.randomUUID();
    }
}
