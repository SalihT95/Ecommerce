package org.turkoglu.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkoglu.ecommerce.dto.*;
import org.turkoglu.ecommerce.entity.User;
import org.turkoglu.ecommerce.service.UserService;
import org.turkoglu.ecommerce.security.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    // Kayıt olma
    @PostMapping("/register")
    public ResponseEntity<UserInfoDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    // Giriş yapma
    @GetMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }

}
