package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.AppUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.RegisterUserDTO;
import com.chathall.springchatserver.models.AppUser;
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
            @Mapping(target = "username", source = "appUserUsername"),
            @Mapping(target = "creationDate", source = "creationDate"),
    })
    AppUserDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "username", source = "email"),
            @Mapping(target = "appUserUsername", source = "username"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "messages", expression = "java(new HashSet())")
    })
    AppUser fromRegisterUser(RegisterUserDTO registerUserDTO);
}
