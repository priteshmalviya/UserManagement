package com.pritesh.usermanagement.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import com.pritesh.usermanagement.utils.Constants;

@Component
@Slf4j
public class JwtUtils {

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(Constants.USERNAME_SMALL_STRING, username);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setHeaderParam(Constants.TYPE_STRING, Constants.JWT_STRING)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
            .signWith(getSignInKey())
            .compact();
    }
    
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(getSignInKey())
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    
    public boolean isTokenExpired(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token);
            
            Date expiration = claimsJws.getBody().getExpiration();
            log.info("Expiration: " + expiration);
            return !expiration.before(new Date());
        } catch (JwtException e) {
            // If token parsing fails, consider it expired or invalid
            log.info("Token has no expiration date: " + e.getMessage());
            return false;
        }
    }
}
