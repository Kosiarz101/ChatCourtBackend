package com.chathall.springchatserver.api.models.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginRequestDTO {
    private String email;
    private String password;
}
