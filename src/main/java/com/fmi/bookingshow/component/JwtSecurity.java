package com.fmi.bookingshow.component;

import com.fmi.bookingshow.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtSecurity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2628501608718103532L;
    private static final long jwtTokenValidity = 12 * 60 * 60;
    private final String secret = System.getenv("JWT_SECRET");
    private final SecretKey key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(
                    secret != null
                            ? secret
                            : "dG9wc2VjcmV0cGFzc3dvcmRyZXF1aXJlZHdpdGhoczI1Nmlzc29zZWN1cmVhbmRzb3Bvd2VyZnVsZm9ydGhpc2tpbmRvZnByb2plY3QhQCM0UVdFciFAIzRRV0Vy"
            )
    );

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userEntity.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
                .signWith(key)
                .compact();
    }

    public String validateTokenAndReturnUsername(String token) {
        final String username = getUsernameFromToken(token);
        return (username != null && !isTokenExpired(token)) ? username : null;
    }

}
