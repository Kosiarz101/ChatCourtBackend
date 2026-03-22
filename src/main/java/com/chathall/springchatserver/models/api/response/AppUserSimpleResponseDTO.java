package com.chathall.springchatserver.models.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AppUserSimpleResponseDTO extends BaseEntityDTO {
    private String email;
    private String username;
}
