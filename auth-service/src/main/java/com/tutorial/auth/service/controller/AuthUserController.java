package com.tutorial.auth.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tutorial.auth.service.dto.AuthUserDto;
import com.tutorial.auth.service.dto.NewUserDto;
import com.tutorial.auth.service.dto.RequestDto;
import com.tutorial.auth.service.dto.TokenDto;
import com.tutorial.auth.service.entity.AuthUser;
import com.tutorial.auth.service.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
        TokenDto tokenDto = authUserService.login(dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token,@RequestBody RequestDto dto){
        TokenDto tokenDto = authUserService.validate(token,dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
        AuthUser authUser = authUserService.save(dto);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }
}