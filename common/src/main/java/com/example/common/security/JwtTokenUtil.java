package com.example.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    @Value("${jwt.expires_in_seconds}")
    private long tokenExpiresInSeconds=1800;

    @Value("${jwt.secret}")
    private String secret;

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //retrieve expiration date from jwt token - predefined claim so using function from getClaimFromToken
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(AppUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AppUser.USERNAME, userDetails.getUserName());
        //Do not include the password
        claims.put(AppUser.ID, userDetails.getUserUUID());
        claims.put(AppUser.FIRST_NAME, userDetails.getFirstName());
        claims.put(AppUser.SURNAME, userDetails.getSurname());
        claims.put(AppUser.EMAIL, userDetails.getEmail());
        claims.put(AppUser.ROLE, userDetails.getRole().toString()); //As this is an object (in AppUser need to toString otherwise null)
        return tokenFactory(claims, userDetails.getUserName());
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String tokenFactory(Map<String, Object> claims, String subject) {
        final int MILLISECONDS_PER_SECOND = 1000;
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()
                        + tokenExpiresInSeconds * MILLISECONDS_PER_SECOND))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        final int MINIMUM_KEY_LENGTH_FOR_HS256 = 32; //32 char = 256 bits
        if (secret.length() < MINIMUM_KEY_LENGTH_FOR_HS256) {
            throw new IllegalStateException("JWT secret must be at least 256 bits");
        }
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(AppUser.ROLE).toString();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }
}