package com.chathall.springchatserver.dtos.frontend.request;

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
