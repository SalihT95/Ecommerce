package org.turkoglu.ecommerce.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name(); // Bu, Spring Security için önemlidir
    }
}
