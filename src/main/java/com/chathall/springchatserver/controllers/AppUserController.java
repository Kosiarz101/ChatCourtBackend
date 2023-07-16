package com.chathall.springchatserver.controllers;

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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<AppUser> add(@RequestBody AppUser appUser) {
        appUserService.add(appUser);
        return ResponseEntity.status(201).body(appUserService.getById(appUser.getId()).get());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/exists")
    public ResponseEntity<Boolean> exists(@RequestParam(required = false) UUID id,
                                          @RequestParam(required = false) String email) {
        return ResponseEntity.ok(appUserService.exists("email", email));
    }
}
