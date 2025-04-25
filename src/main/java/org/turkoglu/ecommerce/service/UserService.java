package org.turkoglu.ecommerce.service;

import org.turkoglu.ecommerce.dto.LoginDTO;
import org.turkoglu.ecommerce.dto.TokenDTO;
import org.turkoglu.ecommerce.dto.UserInfoDTO;
import org.turkoglu.ecommerce.dto.UserRegisterDTO;

public interface UserService {

    UserInfoDTO registerUser(UserRegisterDTO userRegisterDTO);

    TokenDTO loginUser(LoginDTO loginDTO);
}
