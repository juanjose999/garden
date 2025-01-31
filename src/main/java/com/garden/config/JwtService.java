package com.garden.config;

import com.garden.admin.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private String secret = "";
    private static long TIME_DURATION = TimeUnit.MINUTES.toMillis(9);
    private static long TIME_DURATION_REFRESH = TimeUnit.DAYS.toMillis(8);

    public JwtService(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secret = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email) {
        return builderToken(email, TIME_DURATION);
    }

    public String generateTokenRefresh(String email) {
        return builderToken(email, TIME_DURATION_REFRESH);
    }

    private String builderToken(String email, Long expiration){
        Map<String, Object> claims = Map.of("no3221//","c%%4mm");
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] encodeKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    public String extractUserName(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return ((userDetails.getUsername().equals(extractUserName(token))) &&
                (getClaimsFromToken(token).getExpiration().after(Date.from(Instant.now()))));
    }
}
