package org.turkoglu.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {

    private String email;
    private String password;
    private String role; // "USER" ya da "ADMIN" olabilir

}
