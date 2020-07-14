package com.azet.vehicle.security;

import com.azet.vehicle.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.azet.vehicle.security.SecurityConstants.EXPIRATION_TIME;
import static com.azet.vehicle.security.SecurityConstants.SECRET;

@Component
public class JwtGenerator {
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        claims.put("enable", user.isEnabled());

        return doGenerateToken(claims, user.getEmail());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        final Date createDate = new Date();
        final Date expirationDate = new Date(createDate.getTime() + EXPIRATION_TIME * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
