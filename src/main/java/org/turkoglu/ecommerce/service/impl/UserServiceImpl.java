package org.turkoglu.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;
import org.turkoglu.ecommerce.entity.Role;
import org.turkoglu.ecommerce.entity.User;
import org.turkoglu.ecommerce.repository.UserRepository;
import org.turkoglu.ecommerce.security.JwtService;
import org.turkoglu.ecommerce.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

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
    public String loginUser(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        User user = userOptional.get();
        return jwtService.generateToken(user);  // Token oluşturuluyor ve döndürülüyor
    }

}
