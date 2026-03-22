package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.request.RegisterUserDTO;
import com.chathall.springchatserver.models.api.response.AppUserSimpleResponseDTO;
import com.chathall.springchatserver.models.app.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

@Mapper(imports = {HashSet.class, GrantedAuthority.class})
public interface AppUserAppMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "email", source = "username"),
            @Mapping(target = "username", source = "appUserUsername")
    })
    AppUserSimpleResponseDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "username", source = "email"),
            @Mapping(target = "appUserUsername", source = "username"),
            @Mapping(target = "password", source = "password")
    })
    AppUser fromRegisterUser(RegisterUserDTO registerUserDTO);
}
