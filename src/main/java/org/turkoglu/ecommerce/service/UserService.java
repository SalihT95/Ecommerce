package org.turkoglu.ecommerce.service;

import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.TokenDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;
import org.turkoglu.ecommerce.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    UserInfoDTO registerUser(UserRegisterDTO userRegisterDTO);
    TokenDTO loginUser(LoginDTO loginDTO);
}
