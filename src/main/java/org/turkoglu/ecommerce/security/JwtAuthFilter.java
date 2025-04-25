package org.turkoglu.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization başlığını al
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String userEmail = null;

        // Eğer Authorization başlığı "Bearer " ile başlıyorsa, token'ı al
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);  // "Bearer " kısmını at
            userEmail = jwtService.extractUsername(jwt);  // Kullanıcı adını çıkart
        }

        // Eğer JWT token ve kullanıcı email'i mevcutsa, doğrulama yap
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);  // Kullanıcı bilgilerini yükle

            // Token geçerli mi diye kontrol et
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,  // Kullanıcıyı oturum açmış gibi göster
                        null,  // Şifreyi tutmuyoruz
                        userDetails.getAuthorities()  // Kullanıcının yetkilerini al
                );

                // Detayları ekle
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Güvenlik bağlamına ekle
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // İstek işlemini devam ettir
        filterChain.doFilter(request, response);
    }
}
