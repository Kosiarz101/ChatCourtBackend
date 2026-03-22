package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.response.AppUserSimpleResponseDTO;
import com.chathall.springchatserver.dtos.frontend.request.RegisterUserDTO;
import com.chathall.springchatserver.models.mongodb.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

@Mapper(imports = {HashSet.class, GrantedAuthority.class})
public interface AppUserDTOMapper {

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
