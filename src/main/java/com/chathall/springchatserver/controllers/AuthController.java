package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.AppUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.LoginRequestDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.AppUserDTOMapper;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.services.JWTTokenService;
import com.chathall.springchatserver.services.mongodb.AppUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JWTTokenService tokenService;
    private final AppUserService appUserService;
    private final AppUserDTOMapper appUserDTOMapper;
    private final AuthenticationManager authenticationManager;
    @Value(value = "${jwt.cookie.name}")
    private String AUTHORIZATION_HEADER;
    @Value(value = "${jwt.cookie.length}")
    private Integer cookieLength;

    @PostMapping("/login")
    public ResponseEntity<AppUserDTO> token(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        AppUser appUser = appUserService.getByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(400)));
        try {
            addTokenToCookie(tokenService.generateToken(authentication), response);
            return ResponseEntity.ok().body(appUserDTOMapper.toDTO(appUser));
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    private void addTokenToCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        cookie.setMaxAge(cookieLength);
        response.addCookie(cookie);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class TokenResponse {
        String token;
    }
}
