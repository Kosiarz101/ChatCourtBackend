package com.chathall.springchatserver.dtos.chatcourtfrontend;

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
