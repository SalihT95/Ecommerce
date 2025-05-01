package org.turkoglu.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.TokenDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;
import org.turkoglu.ecommerce.entity.User;
import org.turkoglu.ecommerce.enums.Role;
import org.turkoglu.ecommerce.repository.UserRepository;
import org.turkoglu.ecommerce.security.JwtService;
import org.turkoglu.ecommerce.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserInfoDTO registerUser(UserRegisterDTO userRegisterDTO) {
        // Şifreyi şifreleme
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        // Kullanıcı nesnesi oluşturma
        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(encodedPassword)
                .role(userRegisterDTO.getRole() != null ? Role.valueOf(userRegisterDTO.getRole()) : Role.USER) // Varsayılan olarak USER rolü
                .build();

        // Kullanıcıyı veritabanına kaydetme
        user = userRepository.save(user);

        // Kullanıcı bilgilerini DTO olarak dönme
        return new UserInfoDTO(user.getEmail(), user.getRole().name());
    }

    @Override
    public TokenDTO loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User name or password incorrect")); // Kullanıcı bulunamazsa hata fırlatılıyor
        return jwtService.generateToken(user);  // Token oluşturuluyor ve döndürülüyor
    }

}
