package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.RegisterUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.AppUserDTOMapper;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.services.mongodb.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserDTOMapper appUserDTOMapper;

    @PostMapping
    public ResponseEntity<AppUser> add(@RequestBody RegisterUserDTO appUserDTO) {
        AppUser appUser = appUserService.add(appUserDTOMapper.fromRegisterUser(appUserDTO));
        return ResponseEntity.status(201).body(appUser);
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
}
