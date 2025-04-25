package org.turkoglu.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.LoginResponseDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Kullanıcıyı login etme
            String token = userService.loginUser(loginDTO);

            // Token'ı response olarak döndür
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (RuntimeException e) {
            // Hatalı giriş durumunda
            return ResponseEntity.status(401).body(new LoginResponseDTO("Email veya şifre hatalı"));
        }
    }

}
