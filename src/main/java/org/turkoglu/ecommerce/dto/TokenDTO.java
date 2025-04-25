package org.turkoglu.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;         // JWT token
    private long expiration;      // Geçerlilik süresi (epoch time olarak)
}
