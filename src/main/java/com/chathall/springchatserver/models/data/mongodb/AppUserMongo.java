package com.chathall.springchatserver.models.data.mongodb;

import com.chathall.springchatserver.models.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class AppUserMongo extends BaseModel {
    private String email;
    private String password;
    private String username;
}
