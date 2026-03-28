package com.chathall.springchatserver.api.mappers;

import com.chathall.springchatserver.api.models.request.RegisterUserDTO;
import com.chathall.springchatserver.api.models.response.AppUserSimpleResponseDTO;
import com.chathall.springchatserver.app.models.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

@Mapper(imports = {HashSet.class, GrantedAuthority.class})
public interface AppUserAppMapper {

    @Mappings({
            @Mapping(target = "username", source = "appUsername"),
    })
    AppUserSimpleResponseDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "appUsername", source = "username"),
    })
    AppUser fromRegisterUser(RegisterUserDTO registerUserDTO);
}
