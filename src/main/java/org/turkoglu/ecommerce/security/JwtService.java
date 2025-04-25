package org.turkoglu.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "turkoglu_ecommerce_secret_key"; // Dilersen .yml içine alabiliriz
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 saatlik token geçerliliği

    // Token'dan kullanıcı adını çıkar
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token'dan bitiş zamanını çıkar
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'dan özel bir claim (bilgi) çıkar
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Kullanıcı bilgilerini alarak token oluştur
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Ekstra claim'ler ile token oluştur
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // Geçerlilik süresi
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Token geçerliliğini kontrol et
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Token'ın süresi dolmuş mu?
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'ı ve claim'leri çözümle
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()  // parser() yerine parserBuilder() kullanımı
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token doğrulama sırasında hata oluştu", e); // Hata yönetimi
        }
    }
}
