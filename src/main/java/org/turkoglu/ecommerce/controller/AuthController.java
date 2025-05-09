package org.turkoglu.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.TokenDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;
import org.turkoglu.ecommerce.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication and Authorization APIs")
public class AuthController {

    private final UserService userService;

    // Kayıt olma
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user with the provided information")
    public ResponseEntity<UserInfoDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    // Giriş yapma
    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Login a user with the provided credentials")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }

}
