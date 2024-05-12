package com.minsproject.league.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtTokenUtils {

    private static final String BEARER_PREFIX = "Bearer= ";

    public static String getUserEmail(String token, String key) {
        return extractClaims(token, key).get("email", String.class);
    }

    public static boolean validateToken(String token, String key) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey(key)).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    public static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateToken(String email, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
