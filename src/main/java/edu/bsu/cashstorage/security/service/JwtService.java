package edu.bsu.cashstorage.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${cash-app.security.token-ttl-in-seconds}")
    private Integer tokenTtlInSeconds;

    private final SecretKey secretKey;

    public JwtService(@Value("${cash-app.security.signing-key}") String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(UserDetails user) {
        Date iat = new Date();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(iat)
                .expiration(new Date(iat.getTime() + tokenTtlInSeconds * 1000))
                .signWith(secretKey)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaimsJWT(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaimsJWT(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}