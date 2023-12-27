package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.AppUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.RegisterUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.AppUserDTOMapper;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.services.JWTTokenService;
import com.chathall.springchatserver.services.mongodb.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserDTOMapper appUserDTOMapper;
    private final JWTTokenService tokenService;

    @PostMapping
    public ResponseEntity<AppUserDTO> add(@RequestBody RegisterUserDTO appUserDTO) {
        AppUser appUser = appUserService.add(appUserDTOMapper.fromRegisterUser(appUserDTO));
        return ResponseEntity.status(201).body(appUserDTOMapper.toDTO(appUser));
    }

//    @GetMapping
//    public ResponseEntity<Slice<AppUser>> getBy(@RequestParam(required = false) Integer page,
//                                                @RequestParam(required = false) Integer size,
//                                                @RequestParam(required = false) String chatroomId) {
//        Slice<AppUser> response;
//        Map<String, Object> parameters = Map.of("chatroomId", chatroomId);
//        response = appUserService.getBy(parameters, page, size);
//        return ResponseEntity.ok(appUserService.exists("email", email));
//    }


    @GetMapping(path = "/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(String email) {
        return ResponseEntity.ok(appUserService.exists("email", email));
    }

    @GetMapping(path = "/exists")
    public ResponseEntity<Boolean> exists(@RequestParam(required = false) UUID id,
                                          @RequestParam(required = false) String email) {
        return ResponseEntity.ok(appUserService.exists("email", email));
    }

    @GetMapping
    public ResponseEntity<AppUserDTO> getCurrentUser(HttpServletRequest httpServletRequest) {
        String jwt = tokenService.getJwtFromCookie(httpServletRequest);
        String email = tokenService.getEmailFromJWTToken(jwt);
        AppUser appUser = appUserService.getByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "User with this email doesn't exist")
        );
        return ResponseEntity.ok(appUserDTOMapper.toDTO(appUser));
    }
}
